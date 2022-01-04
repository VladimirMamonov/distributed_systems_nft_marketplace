package com.nft.serviceorders.repo.model;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long user_id;
    private String description;
    private float price;
    private String nft_url;

    public Order() {
    }

    public Order(long user_id, String description, float price, String nft_url) {
        this.user_id = user_id;
        this.description = description;
        this.price = price;
        this.nft_url = nft_url;
    }

    public long getId() {
        return id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getNft_url() {
        return nft_url;
    }

    public void setNft_url(String nft_url) {
        this.nft_url = nft_url;
    }
}
