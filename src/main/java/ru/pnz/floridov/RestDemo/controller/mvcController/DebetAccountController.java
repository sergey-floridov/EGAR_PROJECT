package ru.pnz.floridov.RestDemo.controller.mvcController;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.pnz.floridov.RestDemo.model.Client;
import ru.pnz.floridov.RestDemo.model.DebetAccount;
import ru.pnz.floridov.RestDemo.service.ClientService;
import ru.pnz.floridov.RestDemo.service.DebetAccountService;

import javax.validation.Valid;

@Controller
@RequestMapping("/debets")
public class DebetAccountController {

    private final DebetAccountService debetAccountService;
    private final ClientService clientService;

    @Autowired
    public DebetAccountController(DebetAccountService debetAccountService, ClientService clientService) {
        this.debetAccountService = debetAccountService;
        this.clientService = clientService;
    }


    @GetMapping()
    public String index(Model model, @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "debets_per_page", required = false) Integer debetsPerPage) {

        if (page == null || debetsPerPage == null)
            model.addAttribute("debets", debetAccountService.findAll()); // выдача всех кредитных продуктов
        else
            model.addAttribute("debets", debetAccountService.findWithPagination(page, debetsPerPage));

        return "debets/index";
    }


    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model, @ModelAttribute("client") Client client) {
        model.addAttribute("debet", debetAccountService.findOne(id));
        model.addAttribute("client", debetAccountService.getClient(id));
        return "debets/show";
    }


    @GetMapping("/new")
    public String newDebet(Model model, @ModelAttribute("debet") DebetAccount debetAccount,
                           @ModelAttribute("client") Client client) {
        model.addAttribute("clients", clientService.findAll());
        return "debets/new";
    }


    @PostMapping("/new")
    public String create(@ModelAttribute("debet") @Valid DebetAccount debetAccount,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "debets/new";
        debetAccountService.save(debetAccount);
        return "redirect:/debets";
    }


    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id, @ModelAttribute("client") Client client) {
        model.addAttribute("debet", debetAccountService.findOne(id));
        model.addAttribute("clients", clientService.findAll());
        return "debets/edit";
    }


    @PostMapping("/{id}")
    public String update(@ModelAttribute("debet") @Valid DebetAccount debetAccount, BindingResult bindingResult,
                         @PathVariable("id") Long id) {
        if (bindingResult.hasErrors())
            return "debets/edit";
        debetAccountService.update(id, debetAccount);
        return "redirect:/debets";
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        debetAccountService.delete(id);
        return "redirect:/debets";
    }


    @PostMapping("/delete/{id}")                              // для BootStrap
    public String deleteDebetAccount(@PathVariable Long id) {
        debetAccountService.delete(id);
        return "redirect:/debets";
    }
}
