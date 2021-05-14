package com.ramy.swapme.giftcards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class GiftCardService {


    private final GiftCardRepository giftCardRepository;
    private GiftCardDTO giftCardDTO;

    @Autowired
    public GiftCardService(GiftCardRepository giftCardRepository) {
        this.giftCardRepository = giftCardRepository;

    }

    public GiftCardDTO GiftCardDTO(GiftCardDTO giftCardDTO) {

        this.giftCardDTO = giftCardDTO;

        return giftCardDTO;//return form data from add_card end point

    }

    public void saveCardEntityToDatabase(GiftCardEntity giftCardEntity){
        giftCardRepository.save(giftCardEntity);
    }

    public void deleteByCardId(Long id) {

        giftCardRepository.deleteById(id);
    }


    public List<GiftCardEntity> findAllByUserId(Long userId) {

        return giftCardRepository.findAllByUserId(userId);
    }

    public List<GiftCardEntity> findAllCards() {

        return giftCardRepository.findAll();
    }


    public GiftCardDTO getGiftCardDTO() {
        return giftCardDTO;
    }




}
