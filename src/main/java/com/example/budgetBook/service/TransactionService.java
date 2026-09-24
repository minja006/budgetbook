package com.example.budgetBook.service;

import com.example.budgetBook.model.AppUser;
import com.example.budgetBook.model.Category;
import com.example.budgetBook.model.Transaction;
import com.example.budgetBook.model.TransactionType;
import com.example.budgetBook.repository.TransactionRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public List<Transaction> findOwn(String username) {
        return repository.findByUserUsernameOrderByBookingDateDesc(username);
    }

    public Transaction getOwned(Long id, String username) {
        Transaction t = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!t.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("Not your booking");
        }
        return t;
    }

    public Transaction save(Transaction t) {
        return repository.save(t);
    }

    public void delete(Long id, String username) {
        Transaction t = getOwned(id, username);
        repository.delete(t);
    }

    public BigDecimal sumByType(String username, TransactionType type) {
        BigDecimal sum = repository.sumByUserAndType(username, type);
        return sum == null ? BigDecimal.ZERO : sum;
    }
}