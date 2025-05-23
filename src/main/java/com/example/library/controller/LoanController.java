package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.model.Client;
import com.example.library.model.Loan;
import com.example.library.service.BookService;
import com.example.library.service.ClientService;
import com.example.library.service.LoanService;
import com.example.library.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/loans")
public class LoanController {
    private final LoanService loanService;
    private final BookService bookService;
    private final ClientService clientService;

    @Autowired
    public LoanController(LoanService loanService, BookService bookService, ClientService clientService) {
        this.loanService = loanService;
        this.bookService = bookService;
        this.clientService = clientService;
    }

    @GetMapping
    public String findAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "loanDate") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection,
            Model model) {

        Page<Loan> pageLoans = loanService.findAllPaginated(page, size, sortField, sortDirection);

        model.addAttribute("loans", pageLoans.getContent());
        PaginationUtil.addPaginationAttributes(model, pageLoans, sortField, sortDirection);

        return "loans/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("books", bookService.findAll());
        model.addAttribute("clients", clientService.findAll());
        return "loans/add";
    }

    @PostMapping("/add")
    public String createLoan(@RequestParam Long clientId, @RequestParam Long bookId,
                             RedirectAttributes redirectAttributes) {
        try {
            Client client = clientService.findById(clientId);
            Book book = bookService.findById(bookId);

            if (client == null || book == null) {
                redirectAttributes.addFlashAttribute("error", "Клиент или книга не найден");
                return "redirect:/loans/add";
            }

            loanService.createLoan(client, book);
            redirectAttributes.addFlashAttribute("success", "Заказ успешно добавлен!");
            return "redirect:/loans";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка добавления: " + e.getMessage());
            return "redirect:/loans/add";
        }
    }

    @GetMapping("/api")
    @ResponseBody
    public List<Loan> getAllLoans() {
        return loanService.findAll();
    }
}