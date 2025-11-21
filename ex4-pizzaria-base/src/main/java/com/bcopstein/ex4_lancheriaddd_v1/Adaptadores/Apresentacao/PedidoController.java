package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao;

import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.*;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.PedidoResponseDTO;
import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.Responses.ProdutoCardapioDTO; 
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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
    
    private final DefineCardapioAtivoUC defineCardapioAtivoUC;

    @Autowired
    public PedidoController(CarregaCardapioUC carregaCardapioUC, SubmetePedidoUC submetePedidoUC, 
                            ConsultaStatusUC consultaStatusUC, CancelaPedidoUC cancelaPedidoUC,
                            PagaPedidoUC pagaPedidoUC, ListaPedidosEntreguesUC listaPedidosEntreguesUC,
                            ListaPedidosUC listaPedidosUC,
                            DefineCardapioAtivoUC defineCardapioAtivoUC) {
        this.carregaCardapioUC = carregaCardapioUC;
        this.submetePedidoUC = submetePedidoUC;
        this.consultaStatusUC = consultaStatusUC;
        this.cancelaPedidoUC = cancelaPedidoUC;
        this.pagaPedidoUC = pagaPedidoUC;
        this.listaPedidosEntreguesUC = listaPedidosEntreguesUC;
        this.listaPedidosUC = listaPedidosUC;
        this.defineCardapioAtivoUC = defineCardapioAtivoUC; 
    }

    @GetMapping()
    public ResponseEntity<List<Pedido>> getTodosPedidos() {
        return ResponseEntity.ok(listaPedidosUC.run());
    }

    @GetMapping("/cardapio")
    public ResponseEntity<List<ProdutoCardapioDTO>> getCardapio() {
        return ResponseEntity.ok(carregaCardapioUC.run());
    }
    
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> submetePedido(@RequestBody Pedido pedido) {
        return ResponseEntity.ok(submetePedidoUC.run(pedido));
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<Pedido.Status> getStatusPedido(@PathVariable long id) {
        return ResponseEntity.ok(consultaStatusUC.run(id));
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<PedidoResponseDTO> cancelarPedido(@PathVariable long id) {
        return ResponseEntity.ok(cancelaPedidoUC.run(id)); 
    }

    @PostMapping("/{id}/pagar")
    public ResponseEntity<PedidoResponseDTO> pagarPedido(@PathVariable long id) {
        return ResponseEntity.ok(pagaPedidoUC.run(id));
    }
    
    @GetMapping("/entregues")
    public ResponseEntity<List<PedidoResponseDTO>> getPedidosEntregues(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        return ResponseEntity.ok(listaPedidosEntreguesUC.run(dataInicio, dataFim));
    }
    
 
    @PostMapping("/cardapio/ativar/{id}")
    @ResponseStatus(HttpStatus.OK) 
    public void defineCardapioAtivo(@PathVariable long id) {
        defineCardapioAtivoUC.run(id);
    }
}