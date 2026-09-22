package com.example.budgetBook.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "bookings")

public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal amount;

    @NotNull
    private LocalDate bookingDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255)")
    private TransactionType type;

    @ManyToOne(optional = false)
    private Category category;

    @ManyToOne(optional = false)
    private AppUser user;

    @Size(max = 200)
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public TransactionType getType() {
        return type;
    }

    public Category getCategory() {
        return category;
    }

    public AppUser getUser() {
        return user;
    }

    public String getDescription() {
        return description;
    }
}
