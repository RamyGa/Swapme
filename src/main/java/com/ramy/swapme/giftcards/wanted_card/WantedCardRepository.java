package com.ramy.swapme.giftcards.wanted_card;

import org.springframework.data.jpa.repository.JpaRepository;
import javax.transaction.Transactional;
import java.util.List;

public interface WantedCardRepository extends JpaRepository<WantedCardEntity, Long> {

    List<WantedCardEntity> findAllByUserId(Long userId);

    void deleteByUserId(Long userId);

    void deleteById(Long id);

    @Transactional
    void deleteByOwnedCardId(Long id);


}
