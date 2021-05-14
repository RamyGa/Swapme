package com.ramy.swapme.giftcards;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class GiftCardDTO implements Serializable {



    private Long id;
    private String name;
    private Long cardNumber;
    private int cardBalance;
    private int pin;
    private int expMonth;
    private int expYear;
    private String fullExpDate;
    private Long userId;
    private Long wantedCardId;
    private String wantedCardName;
    private String timestamp;
    private double wantedCardBalance;



}
