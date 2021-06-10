package br.com.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import br.com.model.Cliente;
import br.com.repository.ClienteRepository;
import io.swagger.model.ClienteDto;
import io.swagger.model.SexoEnum;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ModelMapper modelMapper;
    @Mock
    private ClienteRepository clienteRepository;

    @Test
    void salvaClienteTest() {

        ClienteDto dto = new ClienteDto().idade(30).nome("Joe Ishi").sexo(SexoEnum.MASCULINO);
        Cliente cliente = Cliente.builder().nome(dto.getNome()).idade(dto.getIdade()).sexo(dto.getSexo()).build();

        when(modelMapper.map(dto, Cliente.class)).thenReturn(cliente);
        when(clienteRepository.save(cliente)).thenReturn(cliente);
        when(modelMapper.map(cliente, ClienteDto.class)).thenReturn(dto);

        ClienteDto save = clienteService.salvaCliente(dto);
        assertEquals(30, save.getIdade());
        assertEquals(SexoEnum.MASCULINO, save.getSexo());
    }

    @Test
    void getClienteTest() {

        Long id = 30L;
        ClienteDto dto = new ClienteDto().idade(30).nome("Lizz Gomez").sexo(SexoEnum.FEMININO);
        Cliente cliente = Cliente.builder().nome(dto.getNome()).idade(dto.getIdade()).sexo(dto.getSexo()).build();

        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));
        when(modelMapper.map(cliente, ClienteDto.class)).thenReturn(dto);

        ClienteDto byId = clienteService.getCliente(id);
        assertEquals(30, byId.getIdade());
        assertEquals(SexoEnum.FEMININO, byId.getSexo());
    }

    @Test
    void getClienteNotFoundTest() {

        Long id = 30L;

        when(clienteRepository.findById(id)).thenReturn(Optional.ofNullable(null));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            clienteService.getCliente(id);
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void atualizaClienteTest() {

        Long id = 30L;
        ClienteDto dto = new ClienteDto().idade(40).nome("Julia Santos").sexo(SexoEnum.FEMININO);
        Cliente cliente = Cliente.builder().nome(dto.getNome()).idade(dto.getIdade()).sexo(dto.getSexo()).build();

        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(cliente)).thenReturn(cliente);
        when(modelMapper.map(cliente, ClienteDto.class)).thenReturn(dto);

        ClienteDto byId = clienteService.atualizaCliente(id, dto);

        assertEquals(40, byId.getIdade());
        assertEquals(SexoEnum.FEMININO, byId.getSexo());
    }

    @Test
    void atualizaClienteNotFoundTest() {

        Long id = 30L;

        ClienteDto dto = new ClienteDto().idade(28).nome("Alex Dith").sexo(SexoEnum.MASCULINO);
        when(clienteRepository.findById(id)).thenReturn(Optional.ofNullable(null));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            clienteService.atualizaCliente(id, dto);
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void deletaClienteTest() {

        Long id = 30L;
        Cliente cliente = Cliente.builder().idade(30).nome("Nick Joe").sexo(SexoEnum.MASCULINO).build();
        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));

        clienteService.deletaCliente(id);
    }

    @Test
    void deletaClienteNotFoundTest() {

        Long id = 30L;
        when(clienteRepository.findById(id)).thenReturn(Optional.ofNullable(null));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            clienteService.deletaCliente(id);
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

}