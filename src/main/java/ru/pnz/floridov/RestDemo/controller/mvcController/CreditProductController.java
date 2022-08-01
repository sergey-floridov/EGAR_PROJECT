package ru.pnz.floridov.RestDemo.controller.mvcController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.pnz.floridov.RestDemo.model.Client;
import ru.pnz.floridov.RestDemo.model.CreditProduct;
import ru.pnz.floridov.RestDemo.service.ClientService;
import ru.pnz.floridov.RestDemo.service.CreditProductService;

import javax.validation.Valid;


@Controller
@RequestMapping("/credits")
public class CreditProductController {

    private final CreditProductService creditProductService;
    private final ClientService clientService;

    @Autowired
    public CreditProductController(CreditProductService creditProductService, ClientService clientService) {
        this.creditProductService = creditProductService;
        this.clientService = clientService;
    }


    @GetMapping()
    public String index(Model model, @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "credits_per_page", required = false) Integer creditsPerPage) {
        if (page == null || creditsPerPage == null)
            model.addAttribute("credits", creditProductService.findAll()); // выдача всех кредитных продуктов
        else
            model.addAttribute("credits", creditProductService.findWithPagination(page, creditsPerPage));

        return "credits/index";
    }


    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model, @ModelAttribute("client") Client client) {
        model.addAttribute("credit", creditProductService.findOne(id));
        model.addAttribute("client", creditProductService.getClient(id));
        model.addAttribute("pay", creditProductService.getMonthPay(id));
        model.addAttribute("overPayment", creditProductService.getOverPayment(id));
        return "credits/show";
    }


    @GetMapping("/new")
    public String newCredit(Model model, @ModelAttribute("credit") CreditProduct creditProduct,
                            @ModelAttribute("client") Client client) {
        model.addAttribute("clients", clientService.findAll());
        return "credits/new";
    }


    @PostMapping("/new")
    public String create(@ModelAttribute("credit") @Valid CreditProduct creditProduct,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "credits/new";
        creditProductService.save(creditProduct);
        return "redirect:/credits";
    }


    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("credit", creditProductService.findOne(id));
        model.addAttribute("clients", clientService.findAll());
        return "credits/edit";
    }


    @PatchMapping("/{id}")
    public String update(@ModelAttribute("credit") @Valid CreditProduct creditProduct, BindingResult bindingResult,
                         @PathVariable("id") Long id) {
        if (bindingResult.hasErrors())
            return "credits/edit";
        creditProductService.update(id, creditProduct);
        return "redirect:/credits";
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        creditProductService.delete(id);
        return "redirect:/credits";
    }

    @PostMapping("/delete/{id}")                              // для BootStrap
    public String deleteCreditProduct(@PathVariable Long id) {
        creditProductService.delete(id);
        return "redirect:/credits";
    }
}
