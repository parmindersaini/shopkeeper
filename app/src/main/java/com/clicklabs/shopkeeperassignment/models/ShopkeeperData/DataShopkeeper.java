
package com.clicklabs.shopkeeperassignment.models.ShopkeeperData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class DataShopkeeper {

    @SerializedName("_id")
    @Expose
    private String Id;
    @SerializedName("phoneNo")
    @Expose
    private String phoneNo;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("profilePicURL")
    @Expose
    private ProfilePicURL profilePicURL;
    @SerializedName("registrationDate")
    @Expose
    private String registrationDate;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("__v")
    @Expose
    private Integer V;
    @SerializedName("accessToken")
    @Expose
    private String accessToken;

    /**
     * 
     * @return
     *     The Id
     */
    public String getId() {
        return Id;
    }

    /**
     * 
     * @param Id
     *     The _id
     */
    public void setId(String Id) {
        this.Id = Id;
    }

    /**
     * 
     * @return
     *     The phoneNo
     */
    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     * 
     * @param phoneNo
     *     The phoneNo
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    /**
     * 
     * @return
     *     The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * 
     * @param email
     *     The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 
     * @return
     *     The password
     */
    public String getPassword() {
        return password;
    }

    /**
     * 
     * @param password
     *     The password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 
     * @return
     *     The profilePicURL
     */
    public ProfilePicURL getProfilePicURL() {
        return profilePicURL;
    }

    /**
     * 
     * @param profilePicURL
     *     The profilePicURL
     */
    public void setProfilePicURL(ProfilePicURL profilePicURL) {
        this.profilePicURL = profilePicURL;
    }

    /**
     * 
     * @return
     *     The registrationDate
     */
    public String getRegistrationDate() {
        return registrationDate;
    }

    /**
     * 
     * @param registrationDate
     *     The registrationDate
     */
    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The V
     */
    public Integer getV() {
        return V;
    }

    /**
     * 
     * @param V
     *     The __v
     */
    public void setV(Integer V) {
        this.V = V;
    }

    /**
     * 
     * @return
     *     The accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * 
     * @param accessToken
     *     The accessToken
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}
