package br.com.altoalegremercado.aamuserapi.controller;

import br.com.altoalegremercado.aamuserapi.domain.model.Role;
import br.com.altoalegremercado.aamuserapi.domain.model.User;
import br.com.altoalegremercado.aamuserapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Iterable<User> listUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{name}")
    public User getUserByName(@PathVariable String name) {
        return userService.getUserByName(name);
    }
    @GetMapping("/cpf/{cpf}")
    public User getUserByCpf(@PathVariable String cpf) {
        return userService.getUserByCpf(cpf);
    }
    @GetMapping("/cnpj/{cnpj}")
    public User getUserByCnpj(@PathVariable String cnpj) {
        return userService.getUserByCnpj(cnpj);
    }

    @GetMapping("/role/{role}")
    public List<User> getUserbyRole(@PathVariable Role role) {
        return  userService.getUsersByRole(role);
    }

    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User newUser = userService.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) throws Exception {
        User updatedUser = userService.updateUser(id, user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteUser(@PathVariable Long id) throws Exception {
        Long UserDeleted = userService.deleteUser(id);
        return new ResponseEntity<>(UserDeleted, HttpStatus.ACCEPTED);
    }
}
