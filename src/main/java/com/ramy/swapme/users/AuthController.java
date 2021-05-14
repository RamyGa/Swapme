package com.ramy.swapme.users;

import com.ramy.swapme.giftcards.card_wallet.CardWalletEntity;
import com.ramy.swapme.giftcards.card_wallet.CardWalletService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Slf4j
@Controller

public class AuthController {

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final UserService userService;
    private final CardWalletService cardWalletService;


   @Autowired
    public AuthController(UserService userService, CardWalletService cardWalletService) {
        this.userService = userService;
        this.cardWalletService = cardWalletService;
    }



    @GetMapping("/")//once logged in it will go to "/" (home page)
    public String logInHomePage(){
        return "web_pages/index";
    }

    @GetMapping("/test")//admin access only
    public String test(Model model){
        List<CardWalletEntity> newCardWalletEntities = cardWalletService.returnAllNewCardsAddedToCardWallet(7L);
        model.addAttribute("newCardWalletEntities", newCardWalletEntities);
        if(newCardWalletEntities.size()>1){
            model.addAttribute("newCardsArraySize", "newCardsArraySize");
        }
        return "web_pages/test";
    }

    @GetMapping("/login")
    public String logIn(){

        return "web_pages/login";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new UserDTO());
        return "web_pages/register";//will return the register.html
    }

    @PostMapping("/register")
    public String registerNewUser( UserDTO user, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){


        try{
            userService.registerUser(user);
        } catch (DataIntegrityViolationException ex){
            model.addAttribute("user", user);
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            redirectAttributes.addAttribute("id", user.getId()).addFlashAttribute("failed", true);
            return "redirect:/register";
        }
        redirectAttributes.addAttribute("id", user.getId()).addFlashAttribute("success", true);
        return "redirect:/register";





    }

}
