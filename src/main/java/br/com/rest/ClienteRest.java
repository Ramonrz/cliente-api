package br.com.rest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import br.com.service.ClienteService;
import io.swagger.api.ClientesApi;
import io.swagger.model.ClienteDto;
import io.swagger.model.ClienteResponseDto;

@RestController
public class ClienteRest implements ClientesApi {

    @Autowired
    private ClienteService clienteService;

    @Override
    public ResponseEntity<ClienteDto> salvaCliente(@Valid ClienteDto body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.salvaCliente(body));
    }

    @Override
    public ResponseEntity<ClienteResponseDto> buscaClientes(@NotNull @Valid Integer pagina, String nome,
            Integer idade, String sexo) {
        return ResponseEntity.ok(clienteService.buscaClientes(nome, idade, sexo, pagina));
    }

    @Override
    public ResponseEntity<ClienteDto> getCliente(Long id) {
        return ResponseEntity.ok(clienteService.getCliente(id));
    }

    @Override
    public ResponseEntity<ClienteDto> atualizaCliente(@Valid ClienteDto body, Long id) {
        return ResponseEntity.ok(clienteService.atualizaCliente(id, body));
    }

    @Override
    public ResponseEntity<Void> deletaCliente(Long id) {
        clienteService.deletaCliente(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}