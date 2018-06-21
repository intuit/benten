package com.intuit.benten.jira;

import com.intuit.karate.FileUtils;
import com.intuit.karate.netty.FeatureServer;
import org.junit.Test;

import java.io.File;

/**
 * @author Divakar Ungatla
 * @version 1.0
 */
public class FeatureServerRunner {

    @Test
    public void testServer() {
        File file = FileUtils.getFileRelativeTo(FeatureServerRunner.class, "jira-mock.feature");
        FeatureServer server = FeatureServer.start(file, 8095, false, null);
        server.waitSync();
    }

}
