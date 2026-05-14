package br.com.altoalegremercado.aamuserapi.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CNPJValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CNPJ {

    String message() default "CNPJ inválido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
