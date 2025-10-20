package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.CancelaPedidoUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.CarregaCardapioUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.ConsultaStatusUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.ListaPedidosEntreguesUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.ListaPedidosUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.PagaPedidoUC;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.SubmetePedidoUC;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Produto;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final CarregaCardapioUC carregaCardapioUC;
    private final SubmetePedidoUC submetePedidoUC;
    private final ConsultaStatusUC consultaStatusUC;
    private final CancelaPedidoUC cancelaPedidoUC;

    private final PagaPedidoUC pagaPedidoUC;
    private final ListaPedidosEntreguesUC listaPedidosEntreguesUC;
    private final ListaPedidosUC listaPedidosUC; 

    @Autowired
    public PedidoController(CarregaCardapioUC carregaCardapioUC, SubmetePedidoUC submetePedidoUC, 
                            ConsultaStatusUC consultaStatusUC, CancelaPedidoUC cancelaPedidoUC,
                            PagaPedidoUC pagaPedidoUC, ListaPedidosEntreguesUC listaPedidosEntreguesUC,
                            ListaPedidosUC listaPedidosUC) {
        this.carregaCardapioUC = carregaCardapioUC;
        this.submetePedidoUC = submetePedidoUC;
        this.consultaStatusUC = consultaStatusUC;
        this.cancelaPedidoUC = cancelaPedidoUC;
        this.pagaPedidoUC = pagaPedidoUC;
        this.listaPedidosEntreguesUC = listaPedidosEntreguesUC;
        this.listaPedidosUC = listaPedidosUC;
    }

    @GetMapping()
    public ResponseEntity<List<Pedido>> getTodosPedidos() {
        List<Pedido> pedidos = listaPedidosUC.run();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/cardapio")
    public ResponseEntity<List<Produto>> getCardapio() {
        List<Produto> cardapio = carregaCardapioUC.run();
        return ResponseEntity.ok(cardapio);
    }

    @PostMapping
    public ResponseEntity<Pedido> submetePedido(@RequestBody Pedido pedido) {
        Pedido pedidoAprovado = submetePedidoUC.run(pedido);
        return ResponseEntity.ok(pedidoAprovado);
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<Pedido.Status> getStatusPedido(@PathVariable long id) {
        Pedido.Status status = consultaStatusUC.run(id);
        return (status != null) ? ResponseEntity.ok(status) : ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<Pedido> cancelarPedido(@PathVariable long id) {
        Pedido pedidoCancelado = cancelaPedidoUC.run(id);
        return (pedidoCancelado != null) ? ResponseEntity.ok(pedidoCancelado) : ResponseEntity.badRequest().build();
    }

    @PostMapping("/{id}/pagar")
    public ResponseEntity<Pedido> pagarPedido(@PathVariable long id) {
        Pedido pedido = pagaPedidoUC.run(id);
        return (pedido != null) ? ResponseEntity.ok(pedido) : ResponseEntity.badRequest().build();
    }
    
    @GetMapping("/entregues")
    public ResponseEntity<List<Pedido>> getPedidosEntregues(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        List<Pedido> pedidos = listaPedidosEntreguesUC.run(dataInicio, dataFim);
        return ResponseEntity.ok(pedidos);
    }
}