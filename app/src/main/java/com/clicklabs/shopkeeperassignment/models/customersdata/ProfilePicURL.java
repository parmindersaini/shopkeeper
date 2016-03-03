
package com.clicklabs.shopkeeperassignment.models.customersdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProfilePicURL implements Serializable{

    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("original")
    @Expose
    private String original;


    @Override
    public String toString() {
        return "ProfilePicURL{" +
                "thumbnail='" + thumbnail + '\'' +
                ", original='" + original + '\'' +
                '}';
    }

    /**
     * 
     * @return
     *     The thumbnail
     */
    public Object getThumbnail() {
        return thumbnail;
    }

    /**
     * 
     * @param thumbnail
     *     The thumbnail
     */
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     * 
     * @return
     *     The original
     */
    public Object getOriginal() {
        return original;
    }

    /**
     * 
     * @param original
     *     The original
     */
    public void setOriginal(String original) {
        this.original = original;
    }

}
