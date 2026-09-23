package com.example.budgetBook.config;

import com.example.budgetBook.model.*;
import com.example.budgetBook.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seed(CategoryRepository categories,
                           AppUserRepository users,
                           TransactionRepository transactions) {
        return args -> {
            Category beitrag = newCategory(categories, "Mitgliedsbeiträge", TransactionType.INCOME);
            Category spende  = newCategory(categories, "Spenden", TransactionType.INCOME);
            Category miete   = newCategory(categories, "Raummiete", TransactionType.EXPENSE);
            Category material = newCategory(categories, "Material", TransactionType.EXPENSE);

            AppUser u = new AppUser();
            u.setUsername("anna");
            u.setPasswordHash(new BCryptPasswordEncoder().encode("test1234"));
            u.setRole("USER");
            users.save(u);

            newTransaction(transactions, u, beitrag, "50.00", TransactionType.INCOME, "Jahresbeitrag");
            newTransaction(transactions, u, miete, "120.00", TransactionType.EXPENSE, "Miete Vereinsraum");
        };
    }

    private Category newCategory(CategoryRepository repo, String name, TransactionType type) {
        Category c = new Category();
        c.setName(name);
        c.setType(type);
        return repo.save(c);
    }

    private void newTransaction(TransactionRepository repo, AppUser u, Category c,
                                String amount, TransactionType type, String desc) {
        Transaction t = new Transaction();
        t.setUser(u);
        t.setCategory(c);
        t.setAmount(new BigDecimal(amount));
        t.setBookingDate(LocalDate.now());
        t.setType(type);
        t.setDescription(desc);
        repo.save(t);
    }
}