package com.broad.security.auth.core.code;


import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

public class ImageCode {

    private String code;

    private BufferedImage bufferedImage;

    private LocalDateTime expireTime;

    public ImageCode(String code, BufferedImage bufferedImage, int expireInSeconds) {
        this.code = code;
        this.bufferedImage = bufferedImage;
        this.expireTime = LocalDateTime.now().plusSeconds(expireInSeconds);
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireTime);
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public String getCode() {
        return code;
    }
}
