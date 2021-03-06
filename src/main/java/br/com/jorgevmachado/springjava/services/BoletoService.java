package br.com.jorgevmachado.springjava.services;

import br.com.jorgevmachado.springjava.domain.PagamentoComBoleto;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class BoletoService {

    public void preencherPagamentoComBoleto (PagamentoComBoleto pagamento, Date instanteDoPedido) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(instanteDoPedido);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        pagamento.setDataVencimento(calendar.getTime());
    }
}
