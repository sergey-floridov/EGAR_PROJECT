package ru.pnz.floridov.RestDemo.controller.mvcController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.pnz.floridov.RestDemo.model.Card;
import ru.pnz.floridov.RestDemo.model.DebetAccount;
import ru.pnz.floridov.RestDemo.service.CardService;
import ru.pnz.floridov.RestDemo.service.DebetAccountService;

import javax.validation.Valid;

@Controller
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;

    private final DebetAccountService debetAccountService;


    public CardController(CardService cardService, DebetAccountService debetAccountService) {
        this.cardService = cardService;
        this.debetAccountService = debetAccountService;
    }


    @GetMapping()
    public String index(Model model, @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "debets_per_page", required = false) Integer cardsPerPage) {
        if (page == null || cardsPerPage == null)
            model.addAttribute("cards", cardService.findAll());
        else
            model.addAttribute("cards", cardService.findWithPagination(page, cardsPerPage));
        return "cards/index";
    }


    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model, @ModelAttribute("card") Card card) {
        model.addAttribute("card", cardService.findOne(id));
        model.addAttribute("debet", cardService.getDebetAccount(id));
        return "cards/show";
    }


    @GetMapping("/new")
    public String newCard(Model model, @ModelAttribute("card") Card card,
                          @ModelAttribute("debet") DebetAccount debetAccount) {
        model.addAttribute("debets", debetAccountService.findAll());
        return "cards/new";
    }


    @PostMapping("/new")
    public String create(@ModelAttribute("card") @Valid Card card,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "cards/new";
        cardService.save(card);
        return "redirect:/cards";
    }


    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("card", cardService.findOne(id));
        model.addAttribute("debets", debetAccountService.findAll());
        return "cards/edit";
    }


    @PatchMapping("/{id}")
    public String update(Model model, @ModelAttribute("card") @Valid Card card, BindingResult bindingResult,
                         @PathVariable("id") Long id) {
        model.addAttribute("debets", debetAccountService.findAll());
        if (bindingResult.hasErrors())
            return "cards/edit";
        cardService.update(id, card);
        return "redirect:/cards";
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        cardService.delete(id);
        return "redirect:/cards";
    }


    @PostMapping("/delete/{id}")                              // для BootStrap
    public String deleteCard(@PathVariable Long id) {
        cardService.delete(id);
        return "redirect:/cards";
    }
}