package com.ramy.swapme;

import com.ramy.swapme.giftcards.GiftCardEntity;
import com.ramy.swapme.giftcards.wanted_card.WantedCardDTO;
import com.ramy.swapme.giftcards.wanted_card.WantedCardEntity;
import com.ramy.swapme.users.UserDTO;
import com.ramy.swapme.users.UserEntity;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ApplicationConfig {

    @Bean
    public MapperFactory mapperFactory(){
        DefaultMapperFactory mapperFactory = new DefaultMapperFactory.Builder()
                .dumpStateOnException(false)
                .useBuiltinConverters(true)
                .build();
        mapperFactory.classMap(UserDTO.class, UserEntity.class)
                .mapNulls(true)
                .byDefault()
                .register();
        mapperFactory.classMap(UserEntity.class, UserDTO.class)
                .mapNulls(true)
                .byDefault()
                .register();
        mapperFactory.classMap(WantedCardDTO.class, WantedCardEntity.class)
                .mapNulls(true)
                .byDefault()
                .register();
        mapperFactory.classMap(WantedCardEntity.class, WantedCardDTO.class)
                .mapNulls(true)
                .byDefault()
                .register();

        return mapperFactory;
    }
    @Bean
    MapperFacade mapper(MapperFactory mapperFactory){
        return mapperFactory.getMapperFacade();
    }


    @Bean
    GiftCardEntity giftCardEntity( ){
        return new GiftCardEntity();
    }


}
