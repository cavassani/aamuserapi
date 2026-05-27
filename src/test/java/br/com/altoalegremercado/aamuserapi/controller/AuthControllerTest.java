package br.com.altoalegremercado.aamuserapi.controller;

import br.com.altoalegremercado.aamuserapi.config.JwtService;
import br.com.altoalegremercado.aamuserapi.controller.dto.UserDTO;
import br.com.altoalegremercado.aamuserapi.domain.model.User;
import br.com.altoalegremercado.aamuserapi.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    void testLoginSuccess() throws Exception {
        User user = new User();
        user.setEmail("teste@test.com");
        user.setPassword("encoded-password");

        when(userService.getUserByEmail("teste@test.com")).thenReturn(user);
        when(passwordEncoder.matches("123456", "encoded-password")).thenReturn(true);
        when(jwtService.generateToken("teste@test.com")).thenReturn("fake-jwt-token");

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"teste@test.com\",\"password\":\"123456\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("fake-jwt-token"))
                .andExpect(jsonPath("$.type").value("Bearer"));
    }

    @Test
    void testLoginInvalidEmail() throws Exception {
        when(userService.getUserByEmail("naoexiste@test.com")).thenReturn(null);

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"naoexiste@test.com\",\"password\":\"123456\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testLoginInvalidPassword() throws Exception {
        User user = new User();
        user.setEmail("teste@test.com");
        user.setPassword("encoded-password");

        when(userService.getUserByEmail("teste@test.com")).thenReturn(user);
        when(passwordEncoder.matches("wrong", "encoded-password")).thenReturn(false);

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"teste@test.com\",\"password\":\"wrong\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testRegister() throws Exception {
        User user = new User();
        user.setEmail("novo@test.com");

        when(userService.createUserFromDTO(any(UserDTO.class))).thenReturn(user);
        when(jwtService.generateToken("novo@test.com")).thenReturn("fake-jwt-token");

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Novo\",\"email\":\"novo@test.com\",\"password\":\"123456\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").value("fake-jwt-token"));
    }

    @Test
    void testLoginValidationError() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"invalido\",\"password\":\"\"}"))
                .andExpect(status().isBadRequest());
    }
}
