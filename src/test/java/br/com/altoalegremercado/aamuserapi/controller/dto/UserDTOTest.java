package br.com.altoalegremercado.aamuserapi.controller.dto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.Test;

import java.util.Set;

public class UserDTOTest {
    private final Validator validator;

    public UserDTOTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testInvalidCpf() {
        UserDTO dto = new UserDTO();
        dto.setCpf("123");
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertEquals("CPF inválido", violations.iterator().next().getMessage());
    }

    @Test
    void testValidDTO() {
        UserDTO dto = new UserDTO();
        dto.setName("João");
        dto.setCpf("52998224725");
        dto.setCnpj("11222333000181");
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }
}