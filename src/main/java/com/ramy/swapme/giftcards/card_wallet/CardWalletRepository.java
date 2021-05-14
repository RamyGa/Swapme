package com.ramy.swapme.giftcards.card_wallet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import javax.transaction.Transactional;
import java.util.List;

public interface CardWalletRepository extends JpaRepository<CardWalletEntity, Long> {

    List<CardWalletEntity> findAllByUserId(Long userId);

    CardWalletEntity findByName(String name);

    //will return a cards that have just been added to the card wallet based on user Id (for displaying on pop up after login)
    @Transactional
    @Query(value = "SELECT * FROM card_wallet_entity WHERE show_pop_up = 1 AND user_id= :userId ;", nativeQuery = true)
    List<CardWalletEntity> returnAllCardWalletCardsJustAdded(@Param("userId") Long userId);

    //will set the "show_pop_up" column in table to false based on userId (after user interacts with pop up message)
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE card_wallet_entity SET show_pop_up = false WHERE user_id =:userId", nativeQuery = true)
    void updateShowPopUp(@Param("userId") Long userId);

    //update the new owner of the gift card (after swapping)
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE gift_card_entity SET user_id = :userId WHERE id =:id", nativeQuery = true)
    void updateCardOwner(@Param("userId") Long userId, @Param("id") Long id);

}
