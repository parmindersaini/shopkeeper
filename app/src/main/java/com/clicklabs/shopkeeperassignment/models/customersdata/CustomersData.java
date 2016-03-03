
package com.clicklabs.shopkeeperassignment.models.customersdata;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomersData {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private ArrayList<Customer> data = new ArrayList<>();




    /**
     * 
     * @return
     *     The statusCode
     */
    public Integer getStatusCode() {
        return statusCode;
    }

    /**
     * 
     * @param statusCode
     *     The statusCode
     */
    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * 
     * @return
     *     The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * 
     * @param message
     *     The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 
     * @return
     *     The data
     */
    public List<Customer> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(ArrayList<Customer> data) {
        this.data = data;
    }

}
