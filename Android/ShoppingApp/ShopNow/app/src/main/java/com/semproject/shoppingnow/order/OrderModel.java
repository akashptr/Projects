package com.semproject.shoppingnow.order;

public class OrderModel
{
    private String orderId;
    private String productId;
    private String purchaseDate;
    private String deliveryDate;
    private int quantity;

    public OrderModel() {
    }

    public OrderModel(String orderId, String productId, String purchaseDate, String deliveryDate, int quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.purchaseDate = purchaseDate;
        this.deliveryDate = deliveryDate;
        this.quantity = quantity;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
