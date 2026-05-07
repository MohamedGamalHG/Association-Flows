package com.mg.Association_Flows.payment.controller;

import com.mg.Association_Flows.payment.domain.dto.PaymentDto;
import com.mg.Association_Flows.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentDto> requestToPay(@RequestBody PaymentDto paymentDto) {
        return ResponseEntity.ok().body(paymentService.requestToPay(paymentDto));
    }

    @PostMapping("/{paymentId}")
    public ResponseEntity<Boolean> confirmPayment(@PathVariable UUID paymentId, @RequestBody PaymentDto paymentDto) {
        return ResponseEntity.ok().body(paymentService.confirmPayment(paymentId,paymentDto));
    }

    @GetMapping("/all-payemnt-to-confirme/{assocId}")
    public ResponseEntity<List<PaymentDto>> getAllPaymentConfirmed(@PathVariable UUID assocId) {
        return ResponseEntity.ok().body(paymentService.getListOfPaymentToConfirmedByAssociationId(assocId));
    }




}
