package com.ricardofood.payment.controller;

import com.ricardofood.payment.constants.PaymentExchangeName;
import com.ricardofood.payment.dto.PaymentDto;
import com.ricardofood.payment.service.PaymentService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService service;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping
    public Page<PaymentDto> findAll(@PageableDefault(size = 10) Pageable pagination) {
        return service.findAll(pagination);
    }

    @GetMapping("{id}")
    public ResponseEntity<PaymentDto> getById(@PathVariable @NotNull Long id) {
        var dto = service.getById(id);

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<PaymentDto> create(@RequestBody @Valid PaymentDto paymentDto, UriComponentsBuilder uriBuilder) {
        var dto = service.create(paymentDto);
        var uri = uriBuilder.path("/payments/{id}").buildAndExpand(dto.getId()).toUri();

        rabbitTemplate.convertAndSend(PaymentExchangeName.PAYMENT_EXCHANGE, "", dto);
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("{id}")
    public ResponseEntity<PaymentDto> update(@PathVariable @NotNull Long id, @RequestBody @Valid PaymentDto paymentDto) {
        var dto = service.update(id, paymentDto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{id}/confirm")
    @CircuitBreaker(name = "confirmPayment", fallbackMethod = "confirmPaymentFallback")
    public void confirmPayment(@PathVariable @NotNull Long id) {
        service.confirmPayment(id);
    }

    public void confirmPaymentFallback(Long id, Exception e) {
        service.confirmPaymentFallback(id);
    }


}
