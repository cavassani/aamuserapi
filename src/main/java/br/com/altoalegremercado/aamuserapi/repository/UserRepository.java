package br.com.altoalegremercado.aamuserapi.repository;

import br.com.altoalegremercado.aamuserapi.domain.model.Role;
import br.com.altoalegremercado.aamuserapi.domain.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Método que retorna usuario fazendo a busca pelo nome passado como parâmetro.
     *
     * @param  name
     * @return usuario
     */
    User findByName(String name);

    /**
     * Método que retorna  usuario fazendo a busca pelo cpf passado como parâmetro.
     *
     * @param cpf
     * @return usuario
     */
    User findByCpf(String cpf);

    /**
     * Método que retorna  usuario fazendo a busca pelo cnpj passado como parâmetro.
     *
     * @param cnpj
     * @return usuario
     */
    User findByCnpj(String cnpj);

    /**
     * Método que retorna uma lista de  usuarios fazendo a busca pelo role passado como parâmetro.
     *
     * @param role
     * @return lista usuarios
     */
    List<User> findByRole(Enum<Role> role);
}
