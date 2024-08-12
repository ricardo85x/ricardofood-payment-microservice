package com.ricardofood.payment.controller;

import com.ricardofood.payment.dto.PaymentDto;
import com.ricardofood.payment.service.PaymentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PaymentController.class)
@AutoConfigureMockMvc
@DisplayName("Payment Controller Test")
class PaymentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PaymentService service;

    @MockBean
    private RabbitTemplate rabbitTemplate;


    @Nested
    @DisplayName("GET /payments")
    class findAllTest {

        @Test
        @DisplayName("Should return 200")
        void findAllTest1() throws Exception {
            // Arrange
            var emptyList = Collections.<PaymentDto>emptyList();
            var emptyPage = new PageImpl<>(emptyList, PageRequest.of(0, 10), 0);

            // Act
            when(service.findAll(any())).thenReturn(emptyPage);

            // Assert
            mvc.perform(
                   get("/payments")
                            .param("page", "0")
                            .param("size", "10"))
                   .andExpect(status().isOk())
                   .andExpect(jsonPath("$.content").isArray());
        }
    }
}
