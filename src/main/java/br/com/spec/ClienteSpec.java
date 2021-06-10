package br.com.spec;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import br.com.model.Cliente;

public class ClienteSpec implements Specification<Cliente> {

    private static final long serialVersionUID = 1L;

    private final Cliente cliente;

    public ClienteSpec(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public Predicate toPredicate(Root<Cliente> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(cliente.getNome())) {
            predicates.add(cb.like(cb.lower(root.<String> get("nome")), cliente.getNome().toLowerCase() + "%"));
        }

        if (Objects.nonNull(cliente.getSexo())) {
            predicates.add(cb.equal(root.<String> get("sexo"), cliente.getSexo()));
        }

        if (Objects.nonNull(cliente.getIdade())) {
            predicates.add(cb.equal(root.<Integer> get("idade"), cliente.getIdade()));
        }
        return cb.and(predicates.toArray(new Predicate[0]));
    }

}
