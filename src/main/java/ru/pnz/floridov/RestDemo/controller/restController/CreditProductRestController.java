package ru.pnz.floridov.RestDemo.controller.restController;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.pnz.floridov.RestDemo.exception.creditProductException.CreditProductErrorResponse;
import ru.pnz.floridov.RestDemo.exception.creditProductException.CreditProductNotCreatedException;
import ru.pnz.floridov.RestDemo.exception.creditProductException.CreditProductNotFoundException;
import ru.pnz.floridov.RestDemo.model.CreditProduct;
import ru.pnz.floridov.RestDemo.service.CreditProductService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/credits")
public class CreditProductRestController {

    private final CreditProductService creditProductService;

    @Autowired
    public CreditProductRestController(CreditProductService creditProductService) {
        this.creditProductService = creditProductService;
    }


    @GetMapping()
    public List<CreditProduct> getCreditProducts() {
        return creditProductService.findAll(); // Jackson конвертирует эти объекты в JSON
    }


    @GetMapping("/{id}")
    public CreditProduct getCreditProduct(@PathVariable("id") Long id) {
        return creditProductService.findOne(id); // Jackson конвертирует в JSON
    }



    @PostMapping
    public ResponseEntity<HttpStatus> create (@RequestBody @Valid CreditProduct creditProduct, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors){
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new CreditProductNotCreatedException(errorMsg.toString());
        }
        creditProductService.save(creditProduct);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @ExceptionHandler
    private ResponseEntity<CreditProductErrorResponse> HandleException (CreditProductNotFoundException e){
        CreditProductErrorResponse response = new CreditProductErrorResponse(
                "Credit with this id wasn't found"
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);  //
    }


    @ExceptionHandler
    private ResponseEntity<CreditProductErrorResponse> HandleException (CreditProductNotCreatedException e){
        CreditProductErrorResponse response = new CreditProductErrorResponse(
                e.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);  //
    }
}
