package com.ricardofood.payment.service;

import com.ricardofood.payment.dto.PaymentDto;
import com.ricardofood.payment.model.Payment;
import com.ricardofood.payment.enums.Status;
import com.ricardofood.payment.repository.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository repository;

    @Autowired
    private ModelMapper mapper;

    public Page<PaymentDto> findAll(Pageable pagination) {
        return repository.findAll(pagination).map( p ->  mapper.map(p, PaymentDto.class));
    }

    public PaymentDto getById(Long id) {
        var payment = this.findById(id);
        return mapper.map(payment, PaymentDto.class);
    }

    public PaymentDto create(PaymentDto paymentDto) {
        var payment = mapper.map(paymentDto, Payment.class);
        payment.setStatus(Status.CREATED);
        repository.save(payment);

        return mapper.map(payment, PaymentDto.class);
    }


    public PaymentDto update(Long id, PaymentDto paymentDto) {
        var payment = this.findById(id);
        mapper.map(paymentDto, payment);
        payment.setId(id);
        repository.save(payment);
        return mapper.map(payment, PaymentDto.class);
    }


    public void delete(Long id) {
        var payment = this.findById(id);
        repository.delete(payment);
    }

    private Payment findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Payment not found"));
    }

}
