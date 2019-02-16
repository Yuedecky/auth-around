package com.broad.security.auth.core.web;

import com.broad.security.auth.core.code.ImageCode;
import com.broad.security.auth.core.code.ImgCodeUtils;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/code")
public class ValidateCodeController {

    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @RequestMapping("/image")
    public void createCode(HttpServletRequest request, HttpServletResponse httpServletResponse) throws IOException {
        ImageCode code = ImgCodeUtils.createImage();
        sessionStrategy.setAttribute(new ServletWebRequest(request),SESSION_KEY,code);
        ImageIO.write(code.getBufferedImage(),"JPEG",httpServletResponse.getOutputStream());
    }
}

