package ru.pnz.floridov.RestDemo.controller.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.pnz.floridov.RestDemo.exception.cardException.CardErrorResponse;
import ru.pnz.floridov.RestDemo.exception.cardException.CardNotCreatedException;
import ru.pnz.floridov.RestDemo.exception.cardException.CardNotFoundException;
import ru.pnz.floridov.RestDemo.model.Card;
import ru.pnz.floridov.RestDemo.service.CardService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cards")
public class CardRestController {

    private final CardService cardService;

    @Autowired
    public CardRestController(CardService cardService) {
        this.cardService = cardService;
    }


    @GetMapping()
    public List<Card> getCards() {
        return cardService.findAll();
    }


    @GetMapping("/{id}")
    public Card getCard(@PathVariable("id") Long id) {
        return cardService.findOne(id);
    }


    @PostMapping
    public ResponseEntity<HttpStatus> create (@RequestBody @Valid Card card, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors){
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new CardNotCreatedException(errorMsg.toString());
        }

        cardService.save(card);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @ExceptionHandler
    private ResponseEntity<CardErrorResponse> HandleException (CardNotFoundException e){
       CardErrorResponse response = new CardErrorResponse(
                "Card with this id wasn't found"
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler
    private ResponseEntity<CardErrorResponse> HandleException (CardNotCreatedException e){
        CardErrorResponse response = new CardErrorResponse(
                e.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}

