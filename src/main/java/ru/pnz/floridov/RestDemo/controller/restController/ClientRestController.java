package ru.pnz.floridov.RestDemo.controller.restController;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.pnz.floridov.RestDemo.DTO.ClientBalanceDetail;
import ru.pnz.floridov.RestDemo.exception.clientException.ClientNotCreatedException;
import ru.pnz.floridov.RestDemo.model.Client;
import ru.pnz.floridov.RestDemo.service.ClientService;
import ru.pnz.floridov.RestDemo.exception.clientException.ClientErrorResponse;
import ru.pnz.floridov.RestDemo.exception.clientException.ClientNotFoundException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientRestController {

    private final ClientService clientService;

    @GetMapping()
    public List<Client> getClients() {
        return clientService.findAll(); // Jackson конвертирует эти объекты в JSON
    }


    @GetMapping("/{id}")
    public Client getClient(@PathVariable("id") Long id) {
        return clientService.findOne(id); // Jackson конвертирует в JSON
    }


    @PostMapping
    public  ResponseEntity<HttpStatus> create (@RequestBody @Valid Client client, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors){
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new ClientNotCreatedException(errorMsg.toString());
        }
        clientService.save(client);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<ClientBalanceDetail> getTotalBalance(@PathVariable(name = "id") Long userId) {
        return ResponseEntity.ok(clientService.getClientBalance(userId));
    }


    @ExceptionHandler
    private ResponseEntity<ClientErrorResponse> HandleException (ClientNotFoundException e){
        ClientErrorResponse response = new ClientErrorResponse(
                "Client with this id wasn't found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);  //
    }


    @ExceptionHandler
    private ResponseEntity<ClientErrorResponse> HandleException (ClientNotCreatedException e){
        ClientErrorResponse response = new ClientErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);  //
    }
}
