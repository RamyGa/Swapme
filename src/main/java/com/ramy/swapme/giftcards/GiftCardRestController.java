package com.ramy.swapme.giftcards;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;

@RestController
@RequestMapping("/giftcards")
public class GiftCardRestController {

    private final GiftCardService giftCardService;

    @Autowired
    public GiftCardRestController(GiftCardService giftCardService) {
        this.giftCardService = giftCardService;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity createNewCard(@RequestBody GiftCardDTO giftCardDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(giftCardService.GiftCardDTO(giftCardDTO));
    }



    //**this will handle the error if the user does not provide a value that was defined in the entity as being not null**
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity constraintViolation(ConstraintViolationException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data");
    }

}
