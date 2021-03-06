package com.broad.security.auth.core.validate.processor.impl;

import com.broad.security.auth.core.validate.code.ImageCode;
import com.broad.security.auth.core.validate.processor.AbstractValidateCodeProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;

@Component("imageCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {
    @Override
    protected void send(ServletWebRequest request, ImageCode code) throws Exception {
        ImageIO.write(code.getBufferedImage(), "JPEG", request.getResponse().getOutputStream());
    }
}
