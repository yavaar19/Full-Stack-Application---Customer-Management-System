package com.yavaarnosi.customer;

public record CustomerRegistrationRequest(

        String name,
        String email,
        Integer age

        ) {
}
