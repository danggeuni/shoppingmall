package com.shop.shoppingmall.domain.entity;

import lombok.Getter;

@Getter
public class ImgFile {
    private final Long id;
    private final String imgName;
    private final String imgPath;

    public ImgFile(Long id, String imgName, String imgPath) {
        this.id = id;
        this.imgName = imgName;
        this.imgPath = imgPath;
    }
}
