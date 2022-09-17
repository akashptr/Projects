package com.semproject.shoppingnow.ui.home;

public class ProductRecModel
{
    private String imageUrl;
    private String productId;
    private String productName;
    private String productDesc;

    public static final String PRODUCT_NAME = "name";
    public static final String PRODUCT_DESC = "description";
    public static final String PRODUCT_IMAGE_URL = "image";


    public ProductRecModel() {
    }

    public ProductRecModel(String imageUrl, String productId, String productName, String productDesc) {
        this.imageUrl = imageUrl;
        this.productId = productId;
        this.productName = productName;
        this.productDesc = productDesc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }
}
