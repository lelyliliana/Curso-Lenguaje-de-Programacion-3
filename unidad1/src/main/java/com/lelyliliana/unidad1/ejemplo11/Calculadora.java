package com.lelyliliana.unidad1.ejemplo11;

public class Calculadora {

    public int sumar(int a, int b) {
        return a + b;
    }

    public int dividir(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("No se puede dividir entre cero");
        }

        return a / b;
    }
}