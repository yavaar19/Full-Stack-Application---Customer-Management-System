package com.yavaarnosi.auth;

import com.yavaarnosi.customer.CustomerDTO;

public record AuthenticationResponse(
        String token,
        CustomerDTO customerDTO) {
}
