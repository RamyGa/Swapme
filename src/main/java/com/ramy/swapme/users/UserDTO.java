package com.ramy.swapme.users;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@PasswordsMatch
public class UserDTO  {



    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;







}
