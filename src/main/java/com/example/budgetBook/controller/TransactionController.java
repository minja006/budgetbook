package com.example.budgetBook.controller;

import com.example.budgetBook.model.AppUser;
import com.example.budgetBook.model.Category;
import com.example.budgetBook.model.Transaction;
import com.example.budgetBook.repository.AppUserRepository;
import com.example.budgetBook.repository.CategoryRepository;
import com.example.budgetBook.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final CategoryRepository categoryRepository;
    private final AppUserRepository appUserRepository;

    public TransactionController(TransactionService transactionService,
                                 CategoryRepository categoryRepository,
                                 AppUserRepository appUserRepository) {
        this.transactionService = transactionService;
        this.categoryRepository = categoryRepository;
        this.appUserRepository = appUserRepository;
    }

    @GetMapping
    public String list(Model model, Principal principal) {
        List<Transaction> transactions = transactionService.findOwn(principal.getName());
        model.addAttribute("transactions", transactions);
        return "transactions/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("categories", categoryRepository.findAll());
        return "transactions/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("transaction") Transaction transaction,
                         BindingResult result, Principal principal, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryRepository.findAll());
            return "transactions/form";
        }
        AppUser user = appUserRepository.findByUsername(principal.getName())
                .orElseThrow();
        transaction.setUser(user);
        transactionService.save(transaction);
        return "redirect:/transactions";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model, Principal principal) {
        Transaction t = transactionService.getOwned(id, principal.getName());
        model.addAttribute("transaction", t);
        model.addAttribute("categories", categoryRepository.findAll());
        return "transactions/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("transaction") Transaction transaction,
                         BindingResult result, Principal principal, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryRepository.findAll());
            return "transactions/form";
        }
        Transaction existing = transactionService.getOwned(id, principal.getName());
        existing.setAmount(transaction.getAmount());
        existing.setBookingDate(transaction.getBookingDate());
        existing.setType(transaction.getType());
        existing.setCategory(transaction.getCategory());
        existing.setDescription(transaction.getDescription());
        transactionService.save(existing);
        return "redirect:/transactions";
    }

    @GetMapping("/{id}/delete")
    public String deleteConfirm(@PathVariable Long id, Model model, Principal principal) {
        Transaction t = transactionService.getOwned(id, principal.getName());
        model.addAttribute("transaction", t);
        return "transactions/delete-confirm";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, Principal principal) {
        transactionService.delete(id, principal.getName());
        return "redirect:/transactions";
    }
}