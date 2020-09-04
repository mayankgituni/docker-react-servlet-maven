package com.jsp.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

public class JsonConverter<T> {

    private final Gson gson;

    public JsonConverter() {

        gson = new GsonBuilder().create();
    }

    public String convertToJson(List<T> data) {

        JsonArray jarray = gson.toJsonTree(data).getAsJsonArray();
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("students", jarray);
        return jsonObject.toString();
    }
}
