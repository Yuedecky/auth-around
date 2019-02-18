package com.broad.security.auth.core.validate.generator;

import com.broad.security.auth.core.validate.code.BaseCode;
import com.broad.security.auth.core.validate.code.ImageCode;
import com.broad.security.auth.core.properties.CoreProperties;
import com.broad.security.auth.core.properties.ImageCodeProperties;
import lombok.Data;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;


@Data
public class ImageCodeGenerator implements ValidateCodeGenerator {


    private CoreProperties coreProperties;
    // 验证码字符集
    private static final char[] chars = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};




    /**
     * 生成随机验证码及图片
     */
    @Override
    public BaseCode generate(ServletWebRequest servletWebRequest) {
        checkProperties(this.coreProperties);
        int width, height, size, fontSize, miss;
        ImageCodeProperties imageCodeProperties = coreProperties.getCode().getImage();
        width = ServletRequestUtils.getIntParameter(servletWebRequest.getRequest(),
                "image.width", imageCodeProperties.getWidth());
        height = ServletRequestUtils.getIntParameter(servletWebRequest.getRequest(), "image.height", imageCodeProperties.getHeight());
        ;
        size = imageCodeProperties.getLength();
        miss = imageCodeProperties.getMiss();
        fontSize = imageCodeProperties.getFontSize();
        final StringBuffer sb = new StringBuffer();
        // 1.创建空白图片
        BufferedImage image = new BufferedImage(
                width, height, BufferedImage.TYPE_INT_RGB);
        // 2.获取图片画笔
        Graphics graphic = image.getGraphics();
        // 3.设置画笔颜色
        graphic.setColor(Color.LIGHT_GRAY);
        // 4.绘制矩形背景
        graphic.fillRect(0, 0, width, height);
        // 5.画随机字符
        Random ran = new Random();
        for (int i = 0; i < size; i++) {
            // 取随机字符索引
            int n = ran.nextInt(chars.length);
            // 设置随机颜色
            graphic.setColor(getRandomColor());
            // 设置字体大小
            graphic.setFont(new Font(
                    null, Font.BOLD + Font.ITALIC, fontSize));
            // 画字符
            graphic.drawString(
                    chars[n] + "", i * width / size, height * 2 / 3);
            // 记录字符
            sb.append(chars[n]);
        }
        // 6.画干扰线
        for (int i = 0; i < miss; i++) {
            // 设置随机颜色
            graphic.setColor(getRandomColor());
            // 随机画线
            graphic.drawLine(ran.nextInt(width), ran.nextInt(height),
                    ran.nextInt(width), ran.nextInt(height));
        }
        // 7.返回验证码和图片
        //return new Object[]{sb.toString(), image};
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpeg", baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ImageCode(sb.toString(), image, 60);

    }

    /**
     * 随机取色
     */
    public static Color getRandomColor() {
        Random ran = new Random();
        return new Color(ran.nextInt(256),
                ran.nextInt(256), ran.nextInt(256));
    }

}
