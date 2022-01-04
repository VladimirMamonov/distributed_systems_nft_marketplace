package com.nft.servicesupport.api.dto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class SupportDTO {
    private long user_id;
    private long order_id;
    private String feedback;
}