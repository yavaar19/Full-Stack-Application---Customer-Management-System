package com.yavaarnosi.customer;

public record CustomerUpdateRequest(

        String name,
        String email,
        Integer age

        ) {
}
