package com.ramy.swapme.giftcards.wanted_card;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;



@AllArgsConstructor
@NoArgsConstructor
@Data
public class WantedCardDTO implements Serializable {



    private Long id;
    private String name;
    private int cardBalance;
    private Long userId;
    private Long ownedCardId;



}
