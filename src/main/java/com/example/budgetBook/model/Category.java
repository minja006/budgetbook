package com.example.budgetBook.model;

import jakarta.persistence.*;

@Entity
public class Category {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String  name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public TransactionType getType() {
        return type;
    }

    public boolean isActive() {
        return active;
    }

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255)")
    private TransactionType  type ;


    private boolean active = true;

}
