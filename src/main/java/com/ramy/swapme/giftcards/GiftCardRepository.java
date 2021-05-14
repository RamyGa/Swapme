package com.ramy.swapme.giftcards;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import javax.transaction.Transactional;
import java.util.List;


public interface GiftCardRepository extends JpaRepository<GiftCardEntity, Long> {



    void deleteById(Long id);

    List<GiftCardEntity> findAllByUserId(Long userId);
    GiftCardEntity findByName(String name);
    void deleteByUserId(Long userId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE gift_card_entity SET user_id = :userId WHERE id =:id", nativeQuery = true)
    void updateCardOwner(@Param("userId") Long userId, @Param("id") Long id);



    @Transactional//either run this transaction or rollback if an error occurs
    @Query(value = "SELECT * FROM gift_card_entity WHERE name = :wantedCardName And wanted_card_name = :currentCardName AND card_balance >= :wantedCardMinAmount AND wanted_card_balance <= :currentCardAmount AND user_id <> :userId AND ((exp_month > :current_month AND exp_year = :current_year) OR exp_year > :current_year OR exp_month = 0)  order by id Limit 1;", nativeQuery = true)
    GiftCardEntity cardMatchingCriteria(@Param("wantedCardName") String wantedCardName, @Param("currentCardName") String currentCardName , @Param("wantedCardMinAmount") int wantedCardMinAmount,  @Param("currentCardAmount") int currentCardAmount,@Param("userId") Long userId , @Param("current_month") int current_month,  @Param("current_year") int current_year);



}
