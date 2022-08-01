package ru.pnz.floridov.RestDemo.controller.mvcController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.pnz.floridov.RestDemo.model.Client;
import ru.pnz.floridov.RestDemo.service.ClientService;

import javax.validation.Valid;

@Controller
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    @GetMapping()
    public String index(Model model) {
        model.addAttribute("clients",clientService.findAll());
        return "clients/index";
    }


    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("client", clientService.findOne(id));
        model.addAttribute("credits", clientService.getCreditProductsByClientId(id));
        return "clients/show";
    }


    @GetMapping("/new")
    public String newClient(@ModelAttribute("client")Client client) {
        return "clients/new";
    }


    @PostMapping("/new")
    public String create(@ModelAttribute("client") @Valid Client client,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "clients/new";
        clientService.save(client);
        return "redirect:/clients";
    }


    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("client", clientService.findOne(id));
        return "clients/edit";
    }


    @PatchMapping("/{id}")
    public String update(@ModelAttribute("client") @Valid Client client, BindingResult bindingResult,
                         @PathVariable("id") Long id) {
        if (bindingResult.hasErrors())
            return "clients/edit";
        clientService.update(id, client);
        return "redirect:/clients";
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        clientService.delete(id);
        return "redirect:/clients";
    }


        @GetMapping("/search")
    public String searchPage() {
        return "clients/search";
    }


    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("lastName") String lastName) {
        model.addAttribute("client", clientService.findByLastName(lastName));
        return "clients/search";
    }
}