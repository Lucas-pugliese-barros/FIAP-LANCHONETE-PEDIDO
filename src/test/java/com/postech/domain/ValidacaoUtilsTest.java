package com.postech.domain.utils;

import com.postech.domain.exceptions.DominioException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidacaoUtilsTest {

    @Test
    public void testValidaValorPositivo_ValidValue() {
        assertDoesNotThrow(() -> ValidacaoUtils.validaValorPositivo(10.0, "Valor não pode ser negativo"));
    }

    @Test
    public void testValidaValorPositivo_NegativeValue() {
        DominioException exception = assertThrows(DominioException.class, () -> {
            ValidacaoUtils.validaValorPositivo(-10.0, "Valor não pode ser negativo");
        });
        assertEquals("Valor não pode ser negativo", exception.getMessage(), "The exception message should match.");
    }

    @Test
    public void testValidaArgumentoNaoVazio_ValidString() {
        assertDoesNotThrow(() -> ValidacaoUtils.validaArgumentoNaoVazio("Valid String", "String não pode estar vazia"));
    }

    @Test
    public void testValidaArgumentoNaoVazio_EmptyString() {
        DominioException exception = assertThrows(DominioException.class, () -> {
            ValidacaoUtils.validaArgumentoNaoVazio("", "String não pode estar vazia");
        });
        assertEquals("String não pode estar vazia", exception.getMessage(), "The exception message should match.");
    }

    @Test
    public void testValidaArgumentoNaoVazio_NullString() {
        DominioException exception = assertThrows(DominioException.class, () -> {
            ValidacaoUtils.validaArgumentoNaoVazio(null, "String não pode estar vazia");
        });
        assertEquals("String não pode estar vazia", exception.getMessage(), "The exception message should match.");
    }

    @Test
    public void testValidaArgumentoNaoNulo_ValidObject() {
        assertDoesNotThrow(() -> ValidacaoUtils.validaArgumentoNaoNulo(new Object(), "Objeto não pode ser nulo"));
    }

    @Test
    public void testValidaArgumentoNaoNulo_NullObject() {
        DominioException exception = assertThrows(DominioException.class, () -> {
            ValidacaoUtils.validaArgumentoNaoNulo(null, "Objeto não pode ser nulo");
        });
        assertEquals("Objeto não pode ser nulo", exception.getMessage(), "The exception message should match.");
    }
}
