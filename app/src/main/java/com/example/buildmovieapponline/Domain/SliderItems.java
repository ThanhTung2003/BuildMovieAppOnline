package com.example.buildmovieapponline.Domain;

public class SliderItems {
    // Thay đổi để lưu trữ URL hình ảnh
    private String imageUrl;

    public SliderItems(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
