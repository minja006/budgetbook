package com.example.budgetBook.repository;

import com.example.budgetBook.model.Transaction;
import com.example.budgetBook.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByUserUsernameOrderByBookingDateDesc(String username);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.user.username = :username AND t.type = :type")
    BigDecimal sumByUserAndType(@Param("username") String username, @Param("type") TransactionType type);
}