package com.nft.serviceorders.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class OrderDTO {
    private long user_id;
    private String description;
    private float price;
    private String nft_url;
}