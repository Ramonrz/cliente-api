package br.com.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.swagger.model.ClienteResponseDto;
import io.swagger.model.SexoEnum;

@SpringBootTest
public class ClienteServiceIntegrationTest {

    @Autowired
    private ClienteService clienteService;

    @Test
    void buscaClientesTest() {

        ClienteResponseDto clientes = clienteService.buscaClientes("Zoe", 19, SexoEnum.FEMININO.toString(), 0);
        assertEquals(1, clientes.getTotalRegistros());
    }

}