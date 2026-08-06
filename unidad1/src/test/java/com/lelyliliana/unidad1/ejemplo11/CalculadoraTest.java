package com.lelyliliana.unidad1.ejemplo11;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculadoraTest {

    private final Calculadora calculadora = new Calculadora();

    @Test
    void sumarDosNumerosRetornaElResultadoCorrecto() {
        int resultado = calculadora.sumar(4, 6);

        assertEquals(10, resultado);
    }

    @Test
    void dividirDosNumerosRetornaElResultadoCorrecto() {
        int resultado = calculadora.dividir(10, 2);

        assertEquals(5, resultado);
    }

    @Test
    void dividirEntreCeroLanzaExcepcion() {
        assertThrows(
                ArithmeticException.class,
                () -> calculadora.dividir(10, 0)
        );
    }
}