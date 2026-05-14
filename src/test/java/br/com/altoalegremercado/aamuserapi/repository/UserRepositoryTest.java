package br.com.altoalegremercado.aamuserapi.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {
        // Configurações
    }

    @Test
    void testExistsByRole() {
        Role role = new Role();
        when(userRepository.existsByRole(role)).thenReturn(true);
        assertTrue(userRepository.existsByRole(role));
    }

    @Test
    void testFindByCpf() {
        User user = new User();
        user.setCpf("12345678901");
        when(userRepository.findByCpf("12345678901")).thenReturn(Optional.of(user));
        Optional<User> result = userRepository.findByCpf("12345678901");
        assertTrue(result.isPresent());
        assertEquals("12345678901", result.get().getCpf());
    }
}