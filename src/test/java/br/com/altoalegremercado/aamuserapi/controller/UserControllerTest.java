package br.com.altoalegremercado.aamuserapi.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {
        // Configurações iniciais
    }

    @Test
    void testGetUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(Arrays.asList(new User(), new User()));
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    void testGetUserByCpf() throws Exception {
        User user = new User();
        when(userService.getUserByCpf("12345678901")).thenReturn(user);
        mockMvc.perform(get("/users/cpf/12345678901"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());
    }

    @Test
    void testCreateUser() throws Exception {
        UserDTO dto = new UserDTO();
        dto.setName("João");
        dto.setCpf("12345678901");
        dto.setCnpj("12345678901234");

        when(userService.createUserFromDTO(dto)).thenReturn(new User());

        mockMvc.perform(post("/users")
                .contentType("application/json")
                .content("{\"name\":\"João\",\"cpf\":\"12345678901\",\"cnpj\":\"12345678901234\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void testDeleteUser() throws Exception {
        when(userService.userExists(1L)).thenReturn(true);
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNoContent());
    }
}