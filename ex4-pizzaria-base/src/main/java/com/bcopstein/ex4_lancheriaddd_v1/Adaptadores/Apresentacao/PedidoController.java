package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.CancelaPedidoUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.CarregaCardapioUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.ConsultaStatusUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.SubmetePedidoUC;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Produto;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.StatusPedido;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final CarregaCardapioUC carregaCardapioUC;
    private final SubmetePedidoUC submetePedidoUC;
    private final ConsultaStatusUC consultaStatusUC;
    private final CancelaPedidoUC cancelaPedidoUC;

    @Autowired
    public PedidoController(CarregaCardapioUC carregaCardapioUC, SubmetePedidoUC submetePedidoUC, 
                            ConsultaStatusUC consultaStatusUC, CancelaPedidoUC cancelaPedidoUC) {
        this.carregaCardapioUC = carregaCardapioUC;
        this.submetePedidoUC = submetePedidoUC;
        this.consultaStatusUC = consultaStatusUC;
        this.cancelaPedidoUC = cancelaPedidoUC;
    }

    // UC1: Carregar cardápio
    @GetMapping("/cardapio")
    public ResponseEntity<List<Produto>> getCardapio() {
        // Corrigido para usar "Produto", conforme o modelo do projeto-base
        List<Produto> cardapio = carregaCardapioUC.run();
        return ResponseEntity.ok(cardapio);
    }

    // UC2: Submeter pedido para aprovação
    @PostMapping
    public ResponseEntity<Pedido> submetePedido(@RequestBody Pedido pedido) {
        Pedido pedidoAprovado = submetePedidoUC.run(pedido);
        return ResponseEntity.ok(pedidoAprovado);
    }

    // UC3: Solicitar status de pedido
    @GetMapping("/{id}/status")
    public ResponseEntity<StatusPedido> getStatusPedido(@PathVariable long id) {
        StatusPedido status = consultaStatusUC.run(id);
        return (status != null) ? ResponseEntity.ok(status) : ResponseEntity.notFound().build();
    }

    // UC4: Cancelar pedido
    @PostMapping("/{id}/cancelar")
    public ResponseEntity<Pedido> cancelarPedido(@PathVariable long id) {
        Pedido pedidoCancelado = cancelaPedidoUC.run(id);
        return (pedidoCancelado != null) ? ResponseEntity.ok(pedidoCancelado) : ResponseEntity.badRequest().build();
    }
}