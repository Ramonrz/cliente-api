package br.com.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.model.Cliente;
import br.com.repository.ClienteRepository;
import br.com.spec.ClienteSpec;
import io.swagger.model.ClienteDto;
import io.swagger.model.ClienteResponseDto;
import io.swagger.model.SexoEnum;

@Service
public class ClienteService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ClienteRepository clienteRepository;

    public ClienteDto salvaCliente(ClienteDto clienteDto) {

        clienteDto.setId(null);
        Cliente cliente = toCliente(clienteDto);
        return toDto(salvaCliente(cliente));
    }

    public ClienteResponseDto buscaClientes(String nome, Integer idade, String sexo,
            Integer pagina) {

        ClienteSpec spec = new ClienteSpec(Cliente.builder().nome(nome).idade(idade).sexo(SexoEnum.fromValue(sexo)).build());

        Page<Cliente> findAll = clienteRepository.findAll(spec, PageRequest.of(pagina, 10));

        List<ClienteDto> clientes = findAll.getContent()
                .stream()
                .map(cliente -> toDto(cliente))
                .collect(Collectors.toList());

        return new ClienteResponseDto().clientes(clientes).totalRegistros(findAll.getTotalElements());
    }

    public ClienteDto getCliente(Long id) {

        Optional<Cliente> byId = findById(id);
        if (byId.isPresent()) {
            return toDto(byId.get());
        }
        throw notFoundException();
    }

    public ClienteDto atualizaCliente(Long id, ClienteDto clienteDto) {

        Optional<Cliente> byId = findById(id);
        if (byId.isPresent()) {
            Cliente cliente = byId.get();
            cliente.setNome(clienteDto.getNome());
            cliente.setSexo(clienteDto.getSexo());
            cliente.setIdade(clienteDto.getIdade());
            return toDto(salvaCliente(cliente));
        }
        throw notFoundException();
    }

    public void deletaCliente(Long id) {

        Optional<Cliente> byId = findById(id);
        if (byId.isPresent()) {
            clienteRepository.delete(byId.get());
            return;
        }
        throw notFoundException();
    }

    protected Cliente toCliente(ClienteDto cliente) {
        return modelMapper.map(cliente, Cliente.class);
    }

    protected ClienteDto toDto(Cliente cliente) {
        return modelMapper.map(cliente, ClienteDto.class);
    }

    protected Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }

    private Cliente salvaCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    protected ResponseStatusException notFoundException() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

}