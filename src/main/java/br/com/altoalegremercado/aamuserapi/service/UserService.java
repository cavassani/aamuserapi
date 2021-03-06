package br.com.altoalegremercado.aamuserapi.service;

import br.com.altoalegremercado.aamuserapi.domain.model.Role;
import br.com.altoalegremercado.aamuserapi.domain.model.User;

import java.util.List;

public interface UserService {

    /**
     * Método que retorna usuario fazendo a busca pelo nome passado como parâmetro.
     *
     * @param  name nome do usuário a ser buscado
     * @return usuario
     */
    User getUserByName(String name);

    /**
     * Método que retorna  usuario fazendo a busca pelo cpf passado como parâmetro.
     *
     * @param cpf parametro contendo numero do cpf do usuário a ser buscado
     * @return usuario
     */
    User getUserByCpf(String cpf);

    /**
     * Método que retorna  usuario fazendo a busca pelo cnpj passado como parâmetro.
     *
     * @param cnpj  numero do cnpj do usuario a ser buscado
     * @return usuario
     */
    User getUserByCnpj(String cnpj);

    /**
     * Método que retorna uma lista de  usuarios fazendo a busca pelo role passado como parâmetro.
     *
     * @param role tipo de usuários a serem buscados, podendo ser :ADMIN, SHOP, CUSTOMER
     * @return lista usuarios
     */
    List<User> getUsersByRole(Role role);

    /**
     * Método que retorna uma lista de  usuarios fazendo a busca pelo role passado como parâmetro.
     *
     * @param newUser objeto usuário
     * @return usuário gerado
     */
    User createUser(User newUser);

    /**
     * Método que retorna uma lista de  usuarios fazendo a busca pelo role passado como parâmetro.
     *
     * @param id identificador do usuario para fins de comparação
     * @param userUpdate  objeto usuario com dados atualizados
     *
     * @throws Exception caso não seja encontrado na database o usuario
     * @return usuário atualizado
     */
    User updateUser(Long id, User userUpdate) throws Exception;

    /**
     * Método que retorna uma lista de todos usuários do sistema.
     *
     * @return lista com todos os usuarios
     */
    Iterable<User> getAllUsers();

    /**
     * Método que exclui usuário da base de dados
     *
     * @param id Long contendo ide do usuário que se quer deletar
     * @return lista usuarios
     * @throws Exception caso o usuario cujo id foi passado por parametro não exista
     */
    Long deleteUser(Long id) throws Exception;
}
