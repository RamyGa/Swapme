package com.ramy.swapme.giftcards.card_wallet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;



@AllArgsConstructor
@NoArgsConstructor
@Data
public class CardWalletDTO implements Serializable {

    private Long id;
    private String name;
    private Long cardNumber;
    private int cardBalance;
    private int pin;
    private int expMonth;
    private int expYear;
    private String date;
    private Long userId;
    private String timestamp;
    private boolean showPopUp;




}
