package com.intuit.benten.converters;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.script.ScriptException;
import java.io.InputStream;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={HtmlToImageConverter.class})
public class HtmlToImageConverterTest {

    @Test
    public void testGenerateImage() throws ScriptException {
        HtmlToImageConverter.decorateHtml("helllo");
        InputStream image =  HtmlToImageConverter.generateImage("jnnkanknva");
        Assert.assertNotNull(image);
    }

}
