package com.ramy.swapme.giftcards.card_wallet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CardWalletEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private Long cardNumber;
    @NonNull
    private int cardBalance;
    @NonNull
    private int pin;
    private int expMonth;
    private int expYear;
    private String date;
    private Long userId;
    private String timestamp;
    private boolean showPopUp;




}
