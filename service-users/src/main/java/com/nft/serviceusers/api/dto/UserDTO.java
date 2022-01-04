package com.nft.serviceusers.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class UserDTO {
    private String username;
    private String password;
    private String email;
    private String phone;
}