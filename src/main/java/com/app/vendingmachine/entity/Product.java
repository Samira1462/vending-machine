package com.app.vendingmachine.entity;

import com.app.vendingmachine.exception.BadRequestException;
import com.app.vendingmachine.utils.CommonUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
@Entity
@Table(name="TBL_PRODUCT")
public class Product{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @GenericGenerator(name = "incremental_id", strategy = "increment")
    @Column(name = "ID", nullable = false, unique = true, updatable = false)
    private Long id;

    private Long amountAvailable;

    private Long cost;
    private String productName;


    @ManyToOne
    @JoinColumn(name = "sellerId")
    private User seller;

    public User getSeller() {
        return seller;
    }

    public Long getAmountAvailable() {
        return amountAvailable;
    }

    public void setAmountAvailable(Long amountAvailable) {
        this.amountAvailable = amountAvailable;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) throws BadRequestException {
        if(CommonUtils.costValidation(cost))
            this.cost = cost;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
