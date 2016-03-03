package com.clicklabs.shopkeeperassignment.sharedpreferences;

import com.clicklabs.shopkeeperassignment.models.customersdata.Customer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;


//Adapter For writing a serializable class object
class LinkAdapter implements JsonSerializer<Customer> {


    @Override
    public JsonElement serialize(Customer src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("_id",src.getId());
        jsonObject.addProperty("name",src.getName());
        return jsonObject;
    }
}