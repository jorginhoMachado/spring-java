package br.com.jorgevmachado.springjava.services;

import br.com.jorgevmachado.springjava.domain.Categoria;
import br.com.jorgevmachado.springjava.dto.CategoriaDTO;
import br.com.jorgevmachado.springjava.repositories.CategoriaRepository;
import br.com.jorgevmachado.springjava.services.exceptions.DataIntegrityException;
import br.com.jorgevmachado.springjava.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;


    public List<Categoria> findAll() {
        return repository.findAll();
    }

    public Page<Categoria> findPage(
            Integer page,
            Integer linesPerPage,
            String orderBy,
            String direction
    ) {
        PageRequest pageRequest = PageRequest.of(
                page,
                linesPerPage,
                Direction.valueOf(direction)
                , orderBy
        );
        return repository.findAll(pageRequest);
    }

    public Categoria find(Integer id) {
        Optional<Categoria> obj = repository.findById(id);
        return obj.orElseThrow(
                () -> new ObjectNotFoundException(
                        "Objeto não encontrado! id:" +
                         id +
                         ", Tipo: " +
                         Categoria.class.getName()
                )
        );
    }

    public Categoria insert(Categoria obj) {
        obj.setId(null);
        return repository.save(obj);
    }

    public Categoria update(Categoria obj) {
        Categoria newOBJ = find(obj.getId());
        updateData(newOBJ, obj);
        return repository.save(newOBJ);
    }

    public void delete(Integer id) {
        find(id);
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir uma categuria que possui produtos");
        }
    }

    public Categoria fromDTO(CategoriaDTO objDTO) {
        return new Categoria(objDTO.getId(), objDTO.getNome());
    }

    private void updateData(Categoria newOBJ, Categoria obj) {
        newOBJ.setNome(obj.getNome());
    }
}
