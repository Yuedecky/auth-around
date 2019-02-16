package com.broad.security.auth.core.code;


import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

public class ImageCode extends BaseCode {


    private BufferedImage bufferedImage;


    public ImageCode(String code, BufferedImage bufferedImage, LocalDateTime expireTime) {
        super(code, expireTime);
        this.bufferedImage = bufferedImage;
    }

    public ImageCode(String code, BufferedImage bufferedImage, int expireInSeconds) {
        this(code, bufferedImage, LocalDateTime.now().plusSeconds(expireInSeconds));
    }


    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

}
