package com.ramy.swapme.giftcards.wanted_card;

import com.ramy.swapme.giftcards.GiftCardEntity;
import com.ramy.swapme.giftcards.GiftCardRepository;
import com.ramy.swapme.giftcards.GiftCardService;
import com.ramy.swapme.giftcards.card_wallet.CardWalletEntity;
import com.ramy.swapme.giftcards.card_wallet.CardWalletService;
import com.ramy.swapme.users.MyDateTime;
import com.ramy.swapme.users.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
public class WantedCardService {


    private final WantedCardRepository wantedCardRepository;
    private UserEntity userEntity;
    private GiftCardEntity giftCardEntity;
    private final GiftCardRepository giftCardRepository;
    private final GiftCardService giftCardService;
    private final CardWalletService cardWalletService;

    @Autowired
    public WantedCardService(WantedCardRepository wantedCardRepository, UserEntity userEntity, GiftCardEntity giftCardEntity, GiftCardRepository giftCardRepository, GiftCardService giftCardService, CardWalletService cardWalletService) {
        this.wantedCardRepository = wantedCardRepository;
        this.userEntity = userEntity;
        this.giftCardEntity = giftCardEntity;
        this.giftCardRepository = giftCardRepository;
        this.giftCardService = giftCardService;
        this.cardWalletService = cardWalletService;
    }

    @Transactional//either run this transaction or rollback if an error occurs
    public WantedCardDTO saveCardWantedToDatabase(WantedCardDTO wantedCardDTO){



        String currentDateTime = new MyDateTime().Date_Time();

        //grab the previously entered data from add_card form and save to a giftCardEntity object
        //reason we are not saving the data of the add_card form in the giftCard service is because we only want that data to be saved
        //once both pages have been filled out, otherwise disregard them
        giftCardEntity = new GiftCardEntity(
                giftCardService.getGiftCardDTO().getId(),
                giftCardService.getGiftCardDTO().getName(),
                giftCardService.getGiftCardDTO().getCardNumber(),
                giftCardService.getGiftCardDTO().getCardBalance(),
                giftCardService.getGiftCardDTO().getPin(),
                giftCardService.getGiftCardDTO().getExpMonth(),
                giftCardService.getGiftCardDTO().getExpYear(),
                giftCardService.getGiftCardDTO().getFullExpDate(),
                getPrincipal(),
                wantedCardDTO.getId(),
                wantedCardDTO.getName(),
                currentDateTime,
                wantedCardDTO.getCardBalance());

        giftCardService.saveCardEntityToDatabase(giftCardEntity);//save the principals giftCard to the DB

        //The wanted card of the principal is saved to an object here
        WantedCardEntity wantedCardEntity = new WantedCardEntity(
                wantedCardDTO.getId(),
                wantedCardDTO.getName(),
                wantedCardDTO.getCardBalance(),
                getPrincipal(),
                giftCardEntity.getId());

        saveWantedCardToDatabase(wantedCardEntity); //save the principals wanted card to the DB

        giftCardEntity.setWantedCardId(wantedCardEntity.getId());//set the giftCardEntity's wantedCard member variable to the wantedCardEntity's id
        giftCardService.saveCardEntityToDatabase(giftCardEntity); //then save all the data again to the repos


        MyDateTime sysDate = new MyDateTime();
        int currentMonth = sysDate.SystemCurrentMonth();
        int currentYear = sysDate.SystemCurrentYear();

        //return a card matching the criteria needed by the object,
        //this is where we will query for a card in the database that matches the criteria of the card we are passing in.
        GiftCardEntity foundCard = giftCardRepository.cardMatchingCriteria(wantedCardEntity.getName(), giftCardEntity.getName(), wantedCardEntity.getCardBalance(), giftCardEntity.getCardBalance(),getPrincipal(), currentMonth, currentYear);//find my wanted card in "gift_card_Entity" Table


       //this is where the swapping of cards will take place if there happens to be a criteria match
        if(foundCard!= null) {


                    giftCardEntity.setUserId(foundCard.getUserId());//set the user id of the current card to be swapped with the found cards user id
                    foundCard.setUserId(getPrincipal());//set the found cards user id to the user id of this principal

//                    giftCardRepository.save(giftCardEntity);//save this current users card to giftCard database(not sure why I had it save here)

                    //save the swapped card of the principal to cardWallet repository with its new user id
                    cardWalletService.saveCardToDatabase(new CardWalletEntity(
                            giftCardEntity.getId(),
                            giftCardEntity.getName(),
                            giftCardEntity.getCardNumber(),
                            giftCardEntity.getCardBalance(),
                            giftCardEntity.getPin(),giftCardEntity.getExpMonth(),
                            giftCardEntity.getExpYear(),
                            giftCardEntity.getFullExpDate(),
                            giftCardEntity.getUserId(),
                            currentDateTime,
                            true));

            //save the swapped card of the found cards user to cardWallet repository with its new user id
                    cardWalletService.saveCardToDatabase(new CardWalletEntity(
                            foundCard.getId(),
                            foundCard.getName(),
                            foundCard.getCardNumber(),
                            foundCard.getCardBalance(),
                            foundCard.getPin(),
                            foundCard.getExpMonth(),
                            foundCard.getExpYear(),
                            foundCard.getFullExpDate(),
                            foundCard.getUserId(),
                            currentDateTime,
                            true));


                    //delete card records from the wantedCard repository
                    //found card wanted repository
                    deleteByCardId(foundCard.getWantedCardId());
                    //Current user card wanted repository
                    deleteByCardId(giftCardEntity.getWantedCardId());

                    //delete card records from the giftCard repository
                    //found card giftCard(up for swap) repository
                    giftCardService.deleteByCardId(foundCard.getId());
                    //Current user card giftCard(up for swap) repository
                    giftCardService.deleteByCardId(giftCardEntity.getId());
            }

        return wantedCardDTO;

        }


     public Long getPrincipal(){
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         userEntity = (UserEntity)authentication.getPrincipal();
         Long userId = userEntity.getId();
         return userId;
     }

     public void saveWantedCardToDatabase(WantedCardEntity wantedCardEntity){
        wantedCardRepository.save(wantedCardEntity);
     }

    public void deleteByOwnedCardId(Long id){

        wantedCardRepository.deleteByOwnedCardId(id);
    }


    public void deleteByCardId(Long id){

        wantedCardRepository.deleteById(id);
    }


    public List<WantedCardEntity> findAllByUserId(Long userId){

        return wantedCardRepository.findAllByUserId(userId);
    }


}
