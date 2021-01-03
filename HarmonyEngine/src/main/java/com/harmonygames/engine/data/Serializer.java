package com.harmonygames.engine.data;

import com.google.gson.Gson;

public class Serializer<T> {

    private final T object;

    public Serializer(T object) {
        this.object = object;
    }

    public String serialize() {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

}
