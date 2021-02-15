package br.com.altoalegremercado.aamuserapi.service;

import br.com.altoalegremercado.aamuserapi.domain.model.Role;
import br.com.altoalegremercado.aamuserapi.domain.model.User;

import java.util.List;

public interface UserService {
    /**
     * Método que retorna usuario fazendo a busca pelo nome passado como parâmetro.
     *
     * @param  name
     * @return usuario
     */
    User getUserByName(String name);

    /**
     * Método que retorna  usuario fazendo a busca pelo cpf passado como parâmetro.
     *
     * @param cpf
     * @return usuario
     */
    User getUserByCpf(String cpf);

    /**
     * Método que retorna  usuario fazendo a busca pelo cnpj passado como parâmetro.
     *
     * @param cnpj
     * @return usuario
     */
    User getUserByCnpj(String cnpj);

    /**
     * Método que retorna uma lista de  usuarios fazendo a busca pelo role passado como parâmetro.
     *
     * @param role
     * @return lista usuarios
     */
    List<User> getUsersByRole(Enum<Role> role);

    User createUser(User newUser);

    User updateUser(Long id, User userUpdate);

    void deleteUser(Long id);
}
