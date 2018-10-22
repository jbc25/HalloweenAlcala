package org.halloweenalcala.app.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.net.Uri;

import java.io.Serializable;

/**
 * Created by julio on 7/10/17.
 */

@Entity(tableName = "PARTICIPANT")
public class Participant implements Serializable {

    @PrimaryKey
    private int id_server;
    private String name;
    private String artistic_modality;
    private String image1, image2;
    private String description;
    private String web,	facebook,	twitter;
    private String youtube_video;


    public boolean hasImage1() {
        return image1 != null && !image1.isEmpty();
    }
    public boolean hasImage2() {
        return image2 != null && !image2.isEmpty();
    }

    public boolean hasValidYoutubeVideo() {
        if (youtube_video == null || youtube_video.isEmpty()) {
            return false;
        }

        Uri uriVideo = Uri.parse(youtube_video);
        String host = uriVideo.getHost();
        return host.contains("youtu.be") || host.contains("youtube.com");

    }

    public String getYoutube_video_ID() {

        try {
            Uri uriVideo = Uri.parse(youtube_video);
            if (uriVideo.getHost().contains("youtu.be")) {
                return uriVideo.getPathSegments().get(0);
            } else if(uriVideo.getHost().contains("youtube.com")) {
                return uriVideo.getQueryParameter("v");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public boolean hasValidLinkWeb() {
        return web != null && !web.isEmpty();
    }

    public boolean hasValidLinkFacebook() {
        return facebook != null && !facebook.isEmpty();
    }

    public boolean hasValidLinkTwitter() {
        return twitter != null && !twitter.isEmpty();
    }

    public Participant() {
    }

    public int getId_server() {
        return id_server;
    }

    public void setId_server(int id_server) {
        this.id_server = id_server;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtistic_modality() {
        return artistic_modality;
    }

    public void setArtistic_modality(String artistic_modality) {
        this.artistic_modality = artistic_modality;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getDescription() {
        if (!description.contains("<")) {
            return description.replace("\n\n", "<p>").replace("\n", "<br>");
        } else {
            return description;
        }
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getYoutube_video() {
        return youtube_video;
    }

    public void setYoutube_video(String youtube_video) {
        this.youtube_video = youtube_video;
    }

}
