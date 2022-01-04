package com.nft.servicesupport.repo.model;

import javax.persistence.*;

@Entity
@Table(name = "support")
public class Support {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long user_id;
    private long order_id;
    private String feedback;

    public Support() {
    }

    public Support(long user_id, long order_id, String feedback) {
        this.user_id = user_id;
        this.order_id = order_id;
        this.feedback = feedback;
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

    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
