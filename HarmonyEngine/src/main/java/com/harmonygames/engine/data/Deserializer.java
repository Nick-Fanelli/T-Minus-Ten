package com.harmonygames.engine.data;

import com.google.gson.Gson;

public class Deserializer<T extends Class<?>> {

    private T t;
    private final String json;

    public Deserializer(String json) {
        this.json = json;

    }

    public T deserialize() {
        Gson gson = new Gson();
        return gson.fromJson(json, t);
    }

}
