package com.live.programming.learningapp.models;

public class Food {
    private String foodId;
    private String foodName;
    private String foodCost;
    private String foodImage;

    public Food(String foodId, String foodName, String foodCost, String foodImage) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.foodCost = foodCost;
        this.foodImage = foodImage;
    }

    public String getFoodId() {
        return foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getFoodCost() {
        return foodCost;
    }

    public String getFoodImage() {
        return foodImage;
    }
}
