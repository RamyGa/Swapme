package com.ramy.swapme.giftcards;


import com.ramy.swapme.giftcards.card_wallet.CardWalletEntity;
import com.ramy.swapme.giftcards.card_wallet.CardWalletService;
import com.ramy.swapme.giftcards.wanted_card.WantedCardDTO;
import com.ramy.swapme.giftcards.wanted_card.WantedCardEntity;
import com.ramy.swapme.giftcards.wanted_card.WantedCardService;
import com.ramy.swapme.users.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class GiftCardController {
    private final WantedCardService wantedCardService;
    private final GiftCardService giftCardService;
    private UserEntity userEntity;
    private final CardWalletService cardWalletService;


    @Autowired
    public GiftCardController(WantedCardService wantedCardService, GiftCardService giftCardService, UserEntity userEntity, CardWalletService cardWalletService) {

        this.wantedCardService = wantedCardService;
        this.giftCardService = giftCardService;
        this.userEntity = userEntity;
        this.cardWalletService = cardWalletService;
    }


    @PostMapping("/my_card_wallet")
    public String myCardWalletPostMapping(@RequestParam(value = "id") Long id) {

        cardWalletService.deleteById(id);
        return "redirect:/my_card_wallet";

    }


    @GetMapping("/my_card_wallet")
    public String myCardWalletGetMapping(Model model) {

        List<CardWalletEntity> cardWalletEntities = cardWalletService.findAllByUserId(principalUser().getId());
        model.addAttribute("cardWalletEntities", cardWalletEntities);

        if (cardWalletEntities.isEmpty()) {
            model.addAttribute("response", "NoData");//sending this key/value pair over so we can show "nothing here" image
        }
        return "web_pages/my_card_wallet";
    }


    @PostMapping("/up_for_swap")
    public String upForSwapPostMapping(Model model, @RequestParam(value = "id", required = false) Long id) {


        cardWalletService.setShowPopUpToFalse(principalUser().getId());//once form is submitted in page this will be called to set the pop up to false
        model.addAttribute("trigger", "false");

        if (id != null) { //(user initiated deletion) delete the cards if there is an id value passed in from form
            giftCardService.deleteByCardId(id);

            wantedCardService.deleteByOwnedCardId(id);
        }

        return "redirect:/up_for_swap";
    }


    @GetMapping("/up_for_swap")
    public String upForSwapGetMapping(Model model) {

       //this will populate the cards that are up for swap by grabbing them from the repository and passing them over to the front end
        List<GiftCardEntity> giftCardEntities = giftCardService.findAllByUserId(principalUser().getId());
        List<WantedCardEntity> wantedCardEntities = wantedCardService.findAllByUserId(principalUser().getId());
        List<CardWalletEntity> cardWalletEntities = cardWalletService.findAllByUserId(principalUser().getId());

        List<CardWalletEntity> newCardWalletEntities = cardWalletService.returnAllNewCardsAddedToCardWallet(principalUser().getId());

        //if empty set response key to "NoDate" (this way we can display the "Oh no!" image)
        if (giftCardEntities.isEmpty() || wantedCardEntities.isEmpty()) {
            model.addAttribute("response", "NoData");
        }

        //sending the List over to the front end via key value pair
        model.addAttribute("giftCardEntities", giftCardEntities);
        model.addAttribute("wantedCardEntities", wantedCardEntities);
        model.addAttribute("wantedCardService", wantedCardService);
        model.addAttribute("newCardWalletEntities", newCardWalletEntities);
        //------------------------------------------------------------------------

        //initially the "isShowPopUp" is set to true once a new card has been created
        for (CardWalletEntity cardWalletEntity : cardWalletEntities) {//loop through all incoming entities...
            if (cardWalletEntity.isShowPopUp()) {//...if any of them are true
                model.addAttribute("trigger", "true");//set this key to true->which will show the pop up to user
            } else {
                model.addAttribute("trigger", "false");
            }

        }
        //if more than one card is added to the card wallet we want to make it plural
        if (newCardWalletEntities.size() > 1) {
            model.addAttribute("newCardsArraySize", "newCardsArraySize");
        }


        return "web_pages/up_for_swap";
    }

    @PostMapping("/add_card")
    public String addCardPostMapping(GiftCardDTO giftCardDTO) {

        //check if the card has an expiration if it does then separate the date out
        if (giftCardDTO.getFullExpDate() != null) {//to avoid NPE
            //grabbing the date entered via form, separating them out, assigning them to local variables
            int formMonth = Integer.parseInt(giftCardDTO.getFullExpDate().split("-")[0]);
            int formYear = Integer.parseInt(giftCardDTO.getFullExpDate().split("-")[1]);


            GiftCardDTO giftCardDTO_Object = new GiftCardDTO(
                    giftCardDTO.getId(),
                    giftCardDTO.getName(),
                    giftCardDTO.getCardNumber(),
                    giftCardDTO.getCardBalance(),
                    giftCardDTO.getPin(),
                    formMonth,
                    formYear,
                    giftCardDTO.getFullExpDate(),
                    giftCardDTO.getUserId(),
                    giftCardDTO.getWantedCardId(),
                    giftCardDTO.getWantedCardName(),
                    giftCardDTO.getTimestamp(),
                    giftCardDTO.getWantedCardBalance());


            giftCardService.GiftCardDTO(giftCardDTO_Object);
        }
        //otherwise if the card does not expire then just add 0 to expiration month and year and save the card to DB
        else {

            GiftCardDTO giftCardDTO1 = new GiftCardDTO(
                    giftCardDTO.getId(),
                    giftCardDTO.getName(),
                    giftCardDTO.getCardNumber(),
                    giftCardDTO.getCardBalance(),
                    giftCardDTO.getPin(),
                    0,
                    0,
                    giftCardDTO.getFullExpDate(),
                    giftCardDTO.getUserId(),
                    giftCardDTO.getWantedCardId(),
                    giftCardDTO.getWantedCardName(),
                    giftCardDTO.getTimestamp(),
                    giftCardDTO.getWantedCardBalance());

            giftCardService.GiftCardDTO(giftCardDTO1);
        }
        return "redirect:/choose_card";//go to next page (choose_card) This is where user will choose their wanted card
    }

    @GetMapping("/add_card")
    public String addCardGetMapping(Model model) {
        model.addAttribute("item2", new GiftCardDTO());//key value pair for GiftCardDTO data extraction
        return "web_pages/add_card";
    }

    @PostMapping("/choose_card")
    public String chooseCardPostMapping(WantedCardDTO wanted_card, RedirectAttributes redirectAttributes) {
        wantedCardService.saveCardWantedToDatabase(wanted_card);

        redirectAttributes.addAttribute("id", wanted_card.getId()).addFlashAttribute("success", true);
        return "redirect:/up_for_swap";
    }

    @GetMapping("/choose_card")
    public String chooseCardGetMapping(Model model) {
        model.addAttribute("add_card", giftCardService.getGiftCardDTO().getCardBalance());
        model.addAttribute("item", new WantedCardDTO());
        return "web_pages/choose_card";
    }


    public UserEntity principalUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userEntity = (UserEntity) authentication.getPrincipal();

        return userEntity;
    }


}
