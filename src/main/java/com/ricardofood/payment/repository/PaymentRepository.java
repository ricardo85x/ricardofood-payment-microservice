package com.ricardofood.payment.repository;

import com.ricardofood.payment.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
