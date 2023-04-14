package com.yavaarnosi.customer;

public record CustomerRegistrationRequest(

        String name,
        String email,
        String password,
        Integer age,
        Gender gender

) {
}
