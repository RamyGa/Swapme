package com.ramy.swapme.giftcards;

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
public class GiftCardEntity {

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
    private int expMonth; //was called expMonth type int
    private int expYear;
    private String fullExpDate;
    private Long userId;
    private Long wantedCardId;
    private String wantedCardName;
    private String timestamp;
    private double wantedCardBalance;


}
