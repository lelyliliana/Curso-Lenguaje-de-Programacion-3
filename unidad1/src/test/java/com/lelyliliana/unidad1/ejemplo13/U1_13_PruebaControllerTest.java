package com.lelyliliana.unidad1.ejemplo13;

import com.lelyliliana.unidad1.ejemplo01.U1_01_PrimeraApi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(U1_13_PruebaController.class)
@ContextConfiguration(classes = U1_01_PrimeraApi.class)
class U1_13_PruebaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void consultarEstadoRetornaRespuestaCorrecta() throws Exception {

        mockMvc.perform(get("/estado-api"))
                .andExpect(status().isOk())
                .andExpect(content().string("API funcionando correctamente"));
    }
}