package com.ramy.swapme.giftcards.card_wallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CardWalletService {


    private final CardWalletRepository cardWalletRepository;


    @Autowired
    public CardWalletService(CardWalletRepository cardWalletRepository) {
        this.cardWalletRepository = cardWalletRepository;
    }

    public CardWalletEntity saveCardToDatabase(CardWalletEntity cardWalletEntity){
        cardWalletRepository.save(cardWalletEntity);

        return cardWalletEntity;
    }


    public List<CardWalletEntity> findAllByUserId(Long userId){

        return cardWalletRepository.findAllByUserId(userId);
    }

    public List<CardWalletEntity> returnAllNewCardsAddedToCardWallet(Long userId){
       return cardWalletRepository.returnAllCardWalletCardsJustAdded(userId);
    }

    public void deleteById(Long id){
        cardWalletRepository.deleteById(id);
    }

    public void setShowPopUpToFalse(Long userId){
        cardWalletRepository.updateShowPopUp(userId);
    }



}
