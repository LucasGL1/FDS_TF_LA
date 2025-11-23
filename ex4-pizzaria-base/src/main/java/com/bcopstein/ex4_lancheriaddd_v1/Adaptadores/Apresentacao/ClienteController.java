package com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao;

import com.bcopstein.ex4_lancheriaddd_v1.Aplicacao.RegistraClienteUC;
import com.bcopstein.ex4_lancheriaddd_v1.Dominio.Entidades.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private RegistraClienteUC registraClienteUC;

    @Autowired
    public ClienteController(RegistraClienteUC registraClienteUC) {
        this.registraClienteUC = registraClienteUC;
    }

    @PostMapping("/registrar")
    public ResponseEntity<Cliente> registraNovoCliente(@RequestBody Cliente novoCliente) {
        Cliente clienteSalvo = registraClienteUC.run(novoCliente);
        return ResponseEntity.ok(clienteSalvo);
    }
}