package br.com.altoalegremercado.aamuserapi.controller;

import br.com.altoalegremercado.aamuserapi.domain.model.User;
import br.com.altoalegremercado.aamuserapi.repository.UserRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping({"/users"})
public class UserController {
    private UserRepository userRepository;

    public List<User> listUsers() {
        return (List<User>) userRepository.findAll();
    }

    public User getUserByName(String name) {
        return userRepository.findByName(name);
    }

    public User getUserByCpf(String cpf) {
        return userRepository.findByCpf(cpf);
    }

    public User getUserByCnpj(String cnpj) {
        return userRepository.findByCnpj(cnpj);
    }

    public List<User> gettUserbyRole(Enum role) {
        return (List<User>) userRepository.findByRole(role);
    }
}
