package com.cquisper.msvc.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.beans.ConstructorProperties;
import java.util.List;

@Builder
@Data
public class RazorpayOrder {
    @JsonProperty("amount")
    private Long amount;

    @JsonProperty("amount_paid")
    private Long amountPaid;

    @JsonProperty("notes")
    private List<String> notes;  // Puedes ajustar el tipo según tus necesidades

    @JsonProperty("created_at")
    private Long createdAt;

    @JsonProperty("amount_due")
    private Long amountDue;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("receipt")
    private Object receipt;  // Puedes ajustar el tipo según tus necesidades

    @JsonProperty("id")
    private String id;

    @JsonProperty("entity")
    private String entity;

    @JsonProperty("offer_id")
    private Object offerId;  // Puedes ajustar el tipo según tus necesidades

    @JsonProperty("status")
    private String status;

    @JsonProperty("attempts")
    private Integer attempts;

    @ConstructorProperties({"amount", "amount_paid", "notes", "created_at", "amount_due", "currency", "receipt", "id", "entity", "offer_id", "status", "attempts"})
    public RazorpayOrder(Long amount, Long amountPaid, List<String> notes, Long createdAt, Long amountDue, String currency, Object receipt, String id, String entity, Object offerId, String status, Integer attempts) {
        this.amount = amount;
        this.amountPaid = amountPaid;
        this.notes = notes;
        this.createdAt = createdAt;
        this.amountDue = amountDue;
        this.currency = currency;
        this.receipt = receipt;
        this.id = id;
        this.entity = entity;
        this.offerId = offerId;
        this.status = status;
        this.attempts = attempts;
    }
}