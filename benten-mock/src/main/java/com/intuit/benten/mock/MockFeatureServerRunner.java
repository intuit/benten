package com.intuit.benten.mock;

import com.intuit.karate.FileUtils;
import com.intuit.karate.netty.FeatureServer;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
@Component
public class MockFeatureServerRunner {

    public  void testServer() throws URISyntaxException {
        File file = new File(getClass().getClassLoader().getResource("benten-mock.feature").toURI());
        FeatureServer server = FeatureServer.start(file, 8001, false, null);
        //server.waitSync();
    }

}
