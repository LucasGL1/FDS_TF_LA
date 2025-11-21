package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.Descontos;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EstrategiaClienteGastador implements IEstrategiaDesconto {

    private PedidoRepository pedidoRepository;

    @Autowired
    public EstrategiaClienteGastador(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public String getNomeEstrategia() {
        return "ClienteGastador";
    }

    @Override
    public double getPercentualDesconto(Pedido pedido) {
        long idCliente = pedido.getCliente().getId();
        

        List<Pedido> pedidosRecentes = pedidoRepository.findPedidosRecentesByCliente(idCliente, 30);
        
        double gastoTotalRecente = pedidosRecentes.stream()
                                    .mapToDouble(Pedido::getValorCobrado)
                                    .sum();
        
        if (gastoTotalRecente > 500.00) {
            return 0.15; // 15%
        }
        
        return 0.0;
    }
}