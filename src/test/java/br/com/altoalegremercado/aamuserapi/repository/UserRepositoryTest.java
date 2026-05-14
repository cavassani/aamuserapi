package br.com.altoalegremercado.aamuserapi.repository;

import br.com.altoalegremercado.aamuserapi.domain.model.User;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByCpf() {
        User user = new User();
        user.setName("João");
        user.setEmail("joao@email.com");
        user.setCpf("12345678901");
        userRepository.save(user);

        User result = userRepository.findByCpf("12345678901");
        assertNotNull(result);
        assertEquals("12345678901", result.getCpf());
    }
}