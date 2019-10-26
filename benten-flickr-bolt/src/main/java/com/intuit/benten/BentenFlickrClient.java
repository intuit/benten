package com.intuit.benten;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.cameras.Camera;
import com.flickr4java.flickr.photos.Extras;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotoList;
import com.flickr4java.flickr.photos.SearchParameters;
import com.intuit.benten.flickr.exceptions.BentenFlickrException;
import com.intuit.benten.flickr.properties.FlickrProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by itstc on 2019-10-07
 */
@Component
public class BentenFlickrClient{
   @Autowired
   private FlickrProperties flickrProperties;
   private final Logger logger = LoggerFactory.getLogger(this.getClass());
   private Flickr flickr;

   @PostConstruct
   public void init() {
      try {
         flickr = new Flickr(flickrProperties.getApikey(), flickrProperties.getSecret(), new REST());
      } catch(Exception e) {
          logger.error(e.getMessage());
          e.printStackTrace();
      }
   }

   public PhotoList<Photo> searchPhotos(String mediaType, String searchValue, Integer searchLimit) throws BentenFlickrException {
     try {
         logger.info(String.format("Searching flickr %s of %s", mediaType, searchValue));
         SearchParameters params = new SearchParameters();
         params.setMedia(mediaType);
         params.setExtras(Extras.MIN_EXTRAS);
         params.setText(searchValue);
         PhotoList<Photo> result = flickr.getPhotosInterface().search(params, searchLimit, 1);
         return result;
     } catch (Exception e) {
         logger.error("Unable to search flickr for media");
         e.printStackTrace();
         throw new BentenFlickrException(e.getMessage());
     }
   }

   public PhotoList<Photo> getTrendyPhotos() throws BentenFlickrException {
       try {
           logger.info("Getting trendy flickr photos");
           PhotoList<Photo> result = flickr.getInterestingnessInterface().getList((String) null, Extras.MIN_EXTRAS, 30, 1);
           return result;
       } catch (Exception e) {
           logger.error("Unable to get trendy photos from flickr");
           e.printStackTrace();
           throw new BentenFlickrException(e.getMessage());
       }
   }

   public List<Camera> getBrandCameras(String brandName) throws BentenFlickrException {
       try {
           logger.info(String.format("Getting Camera models for %s", brandName));
           List<Camera> result = flickr.getCamerasInterface().getBrandModels(brandName);
           return result;
       } catch (Exception e) {
           logger.error("Unable to find camera models");
           e.printStackTrace();
           throw new BentenFlickrException(e.getMessage());
       }
   }

}
