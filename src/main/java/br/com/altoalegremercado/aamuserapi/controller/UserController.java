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
    public ResponseEntity<List<User>> getUserByRole(@PathVariable Role role) {
        if (!userService.roleExists(role)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userService.getUsersByRole(role));
    }

    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestBody @Valid UserDTO userDTO) {
        User user = userService.createUserFromDTO(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) throws Exception {
        User updatedUser = userService.updateUser(id, user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (!userService.userExists(id)) {
            return ResponseEntity.notFound().build();
        }
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
