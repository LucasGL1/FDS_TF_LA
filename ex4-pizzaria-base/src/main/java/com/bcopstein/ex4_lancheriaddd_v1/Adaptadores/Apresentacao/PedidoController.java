package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao;

import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.*;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido; 
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
        List<Produto> cardapio = carregaCardapioUC.run();
        return ResponseEntity.ok(cardapio);
    }

    // UC2: Submeter pedido para aprovação
    @PostMapping
    public ResponseEntity<Pedido> submetePedido(@RequestBody Pedido pedido) {
        Pedido pedidoAprovado = submetePedidoUC.run(pedido);
        return ResponseEntity.ok(pedidoAprovado);
    }

    // UC3: Solicitar status de pedido - MÉTODO CORRIGIDO
    @GetMapping("/{id}/status")
    public ResponseEntity<Pedido.Status> getStatusPedido(@PathVariable long id) {
        // A variável 'status' e o tipo de retorno do ResponseEntity agora são "Pedido.Status"
        Pedido.Status status = consultaStatusUC.run(id);
        if (status != null) {
            return ResponseEntity.ok(status);
        }
        return ResponseEntity.notFound().build();
    }

    // UC4: Cancelar pedido
    @PostMapping("/{id}/cancelar")
    public ResponseEntity<Pedido> cancelarPedido(@PathVariable long id) {
        Pedido pedidoCancelado = cancelaPedidoUC.run(id);
        return (pedidoCancelado != null) ? ResponseEntity.ok(pedidoCancelado) : ResponseEntity.badRequest().build();
    }
}