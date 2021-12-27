package com.example.json_exrcs.model.dto.PR100;

import com.google.gson.annotations.Expose;

public class ProductsWithBuyerAndSeller {
    @Expose
    private String name;
//    @Expose
//    private String buyerFirstName;
    @Expose
    private String buyerLastName;
//    @Expose
//    private String sellerFirstName;
    @Expose
    private String sellerLastName;

    public ProductsWithBuyerAndSeller() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getBuyerFirstName() {
//        return buyerFirstName;
//    }
//
//    public void setBuyerFirstName(String buyerFirstName) {
//        this.buyerFirstName = buyerFirstName;
//    }

    public String getBuyerLastName() {
        return buyerLastName;
    }

    public void setBuyerLastName(String buyerLastName) {
        this.buyerLastName = buyerLastName;
    }

//    public String getSellerFirstName() {
//        return sellerFirstName;
//    }
//
//    public void setSellerFirstName(String sellerFirstName) {
//        this.sellerFirstName = sellerFirstName;
//    }

    public String getSellerLastName() {
        return sellerLastName;
    }

    public void setSellerLastName(String sellerLastName) {
        this.sellerLastName = sellerLastName;
    }
}
