package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.ServicoDescontos;

@Component
public class ServicoDescontosImpl implements ServicoDescontos {
    private PedidoRepository pedidoRepository;

    @Autowired
    public ServicoDescontosImpl(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public double calculaDesconto(long idCliente) {
        // Desconto de 7% para clientes com mais de 3 pedidos nos últimos 20 dias
        int pedidosRecentes = pedidoRepository.findPedidosRecentesByCliente(idCliente, 20).size();
        if (pedidosRecentes > 3) {
            return 0.07; // 7%
        }
        return 0.0; // Sem desconto
    }
}   