package com.intuit.benten.flickr.helpers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flickr4java.flickr.Transport;
import com.flickr4java.flickr.cameras.Camera;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotoList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by itstc on 2019-10-15
 */
public class FlickrResultBuilder {
    public static List<Camera> getCamerasFromJSON(String filename) {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("src/test/java/com/intuit/benten/" + filename);
        try {
            return (List<Camera>) mapper.readValue(file,new TypeReference<List<Camera>>(){});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
