package com.example.library.controller;

import com.example.library.model.Client;
import com.example.library.service.ClientService;
import com.example.library.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/clients")
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public String findAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "fullName") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection,
            Model model) {

        Page<Client> pageClients = clientService.findAllPaginated(page, size, sortField, sortDirection);

        model.addAttribute("clients", pageClients.getContent());
        PaginationUtil.addPaginationAttributes(model, pageClients, sortField, sortDirection);

        return "clients/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("client", new Client());
        return "clients/add";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute Client client, BindingResult result,
                      RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "clients/add";
        }
        try {
            clientService.save(client);
            redirectAttributes.addFlashAttribute("success", "Клиент успешно добавлен!");
        } catch (DataIntegrityViolationException e){
            redirectAttributes.addFlashAttribute("error", "Ошибка добавления");
        }

        return "redirect:/clients";
    }


    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Client client = clientService.findById(id);
        model.addAttribute("client", client);
        return "clients/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute Client client,
                         BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "clients/edit";
        }
        try {
            client.setId(id);
            clientService.save(client);
            redirectAttributes.addFlashAttribute("success", "Клиент успешно обновлен!");
        } catch (DataIntegrityViolationException e){
            redirectAttributes.addFlashAttribute("error", "Ошибка обновления");
        }
        return "redirect:/clients";
    }

}