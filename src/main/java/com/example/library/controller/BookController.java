package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.service.BookService;
import com.example.library.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.sql.SQLDataException;
import java.sql.SQLException;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String findAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "title") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection,
            Model model) {

        Page<Book> pageBooks = bookService.findAllPaginated(page, size, sortField, sortDirection);

        model.addAttribute("books", pageBooks.getContent());
        PaginationUtil.addPaginationAttributes(model, pageBooks, sortField, sortDirection);

        return "books/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("book", new Book());
        return "books/add";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute Book book, BindingResult result,
                      RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "books/add";
        }
        try {
            bookService.save(book);
            redirectAttributes.addFlashAttribute("success", "Книга успешно добавлена!");
            return "redirect:/books";
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка создания");
            return "redirect:/books/add";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Book book = bookService.findById(id);
        model.addAttribute("book", book);
        return "books/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute Book book,
                         BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "books/edit";
        }
        try {
            book.setId(id);
            bookService.save(book);
            redirectAttributes.addFlashAttribute("success", "Книга успешно обновлена!");
            return "redirect:/books";
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("error", "ISBN уже существует");
            return "redirect:/books/edit/" + id;
        }
    }

}