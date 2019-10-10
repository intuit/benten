package com.intuit.benten.converters;

import com.intuit.benten.hackernews.exceptions.ImageGenerationException;
import gui.ava.html.image.generator.HtmlImageGenerator;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.script.ScriptException;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
@Component
public class HtmlToImageConverter {

    public static HtmlImageGenerator imageGenerator
            = new HtmlImageGenerator();

    public static InputStream generateImage(String htmlAsText){
        InputStream inputStream;
        try{
            imageGenerator.loadHtml(htmlAsText);
            BufferedImage input = imageGenerator.getBufferedImage();
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            ImageIO.write(input, "png", output);
            inputStream = new ByteArrayInputStream(output.toByteArray());

        }catch(Exception e){
            e.printStackTrace();
            throw new ImageGenerationException(e.getMessage());
        }
        return inputStream;
    }

    public static String decorateHtml(String html) throws ScriptException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!DOCTYPE html><head><body>").append(html).append("</body>");
        return stringBuilder.toString();
    }

}
