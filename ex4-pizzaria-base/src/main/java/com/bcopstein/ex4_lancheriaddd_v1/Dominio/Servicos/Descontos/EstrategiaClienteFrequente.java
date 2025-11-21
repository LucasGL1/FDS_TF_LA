package com.bcopstein.ex4_lancheriaddd_v1.Dominio.Servicos.Descontos;

import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Dados.PedidoRepository;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EstrategiaClienteFrequente implements IEstrategiaDesconto {
    
    private PedidoRepository pedidoRepository;

    @Autowired
    public EstrategiaClienteFrequente(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public String getNomeEstrategia() {
        return "ClienteFrequente";
    }

    @Override
    public double getPercentualDesconto(Pedido pedido) {
        long idCliente = pedido.getCliente().getId();
        
 
        int pedidosRecentes = pedidoRepository.findPedidosRecentesByCliente(idCliente, 20).size();
        
        if (pedidosRecentes > 3) {
            return 0.07;
        }
        return 0.0;
    }
}