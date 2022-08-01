package ru.pnz.floridov.RestDemo.controller.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.pnz.floridov.RestDemo.exception.debetAccountException.DebetAccountErrorResponse;
import ru.pnz.floridov.RestDemo.exception.debetAccountException.DebetAccountNotCreatedException;
import ru.pnz.floridov.RestDemo.exception.debetAccountException.DebetAccountNotFoundException;
import ru.pnz.floridov.RestDemo.model.DebetAccount;
import ru.pnz.floridov.RestDemo.service.DebetAccountService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/debets")
public class DebetAccountRestController {

    private final DebetAccountService debetAccountService;

    @Autowired
    public DebetAccountRestController(DebetAccountService debetAccountService) {
        this.debetAccountService = debetAccountService;
    }


    @GetMapping()
    public List<DebetAccount> getDebetAccounts() {
        return debetAccountService.findAll();
    }


    @GetMapping("/{id}")
    public DebetAccount getDebetAccount(@PathVariable("id") Long id) {
        return debetAccountService.findOne(id);
    }


    @PostMapping
    public ResponseEntity<HttpStatus> create (@RequestBody @Valid DebetAccount debetAccount, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors){
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new DebetAccountNotCreatedException(errorMsg.toString());
        }
        debetAccountService.save(debetAccount);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @ExceptionHandler
    private ResponseEntity<DebetAccountErrorResponse> HandleException (DebetAccountNotFoundException e){
        DebetAccountErrorResponse response = new DebetAccountErrorResponse(
                "Credit with this id wasn't found"
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler
    private ResponseEntity<DebetAccountErrorResponse> HandleException (DebetAccountNotCreatedException e){
        DebetAccountErrorResponse response = new DebetAccountErrorResponse(
                e.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
