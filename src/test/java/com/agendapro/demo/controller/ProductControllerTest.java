package com.agendapro.demo.controller;


import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser // Simula un usuario autenticado
    public void testCreateProductWithBasicAuth() throws Exception {
        String username = "user";
        String password = "password";
        String productJson = "{\"name\":\"Product1\",\"category\":\"Category1\",\"price\":100.0}";

        mockMvc.perform(post("/api/products")
                        .with(httpBasic(username, password))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Product1")));
    }

    @Test
    @WithMockUser // Simula un usuario autenticado
    public void testUpdateProductWithBasicAuth() throws Exception {
        String username = "user";
        String password = "password";
        String initialProductJson = "{\"name\":\"Product1\",\"category\":\"Category1\",\"price\":100.0}";
        String updatedProductJson = "{\"name\":\"UpdatedProduct\",\"category\":\"UpdatedCategory\",\"price\":150.0}";

        // Crear un producto de prueba
        mockMvc.perform(post("/api/products")
                        .with(httpBasic(username, password))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(initialProductJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andDo(result -> {
                    String productId = JsonPath.read(result.getResponse().getContentAsString(), "$.id").toString();

                    // Actualizar el producto creado
                    mockMvc.perform(put("/api/products/" + productId)
                                    .with(httpBasic(username, password))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(updatedProductJson))
                            .andExpect(status().isOk())
                            .andExpect(jsonPath("$.name", is("UpdatedProduct")));
                });
    }

    @Test
    @WithMockUser // Simula un usuario autenticado
    public void testDeleteProductWithBasicAuth() throws Exception {
        String username = "user";
        String password = "password";

        mockMvc.perform(delete("/api/products/1")
                        .with(httpBasic(username, password)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser // Simula un usuario autenticado
    public void testGetAllProductsWithBasicAuth() throws Exception {
        String username = "user";
        String password = "password";

        // Verificar si hay productos, si no, crear uno
        mockMvc.perform(get("/api/products")
                        .with(httpBasic(username, password)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andDo(result -> {
                    if (result.getResponse().getContentAsString().equals("[]")) {
                        String productJson = "{\"name\":\"Product1\",\"category\":\"Category1\",\"price\":100.0}";
                        mockMvc.perform(post("/api/products")
                                        .with(httpBasic(username, password))
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(productJson))
                                .andExpect(status().isOk());
                    }
                });

        // Ejecutar el test para listar todos los productos
        mockMvc.perform(get("/api/products")
                        .with(httpBasic(username, password)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    @WithMockUser // Simula un usuario autenticado
    public void testCheckIfProductExists() throws Exception {
        String username = "user";
        String password = "password";
        String productJson = "{\"name\":\"Product1\",\"category\":\"Category1\",\"price\":100.0}";

        // Crear un producto de prueba
        mockMvc.perform(post("/api/products")
                        .with(httpBasic(username, password))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andDo(result -> {
                    String productId = JsonPath.read(result.getResponse().getContentAsString(), "$.id").toString();

                    // Verificar si el producto creado existe
                    mockMvc.perform(get("/api/products/" + productId)
                                    .with(httpBasic(username, password)))
                            .andExpect(status().isOk())
                            .andExpect(jsonPath("$.id", is(Integer.parseInt(productId))));

                    // Eliminar el producto creado
                    mockMvc.perform(delete("/api/products/" + productId)
                                    .with(httpBasic(username, password)))
                            .andExpect(status().isOk());
                });

        // Verificar si un producto inexistente retorna 404
        Long nonExistentProductId = 999L;
        mockMvc.perform(get("/api/products/" + nonExistentProductId)
                        .with(httpBasic(username, password)))
                .andExpect(status().isNotFound());
    }
}
