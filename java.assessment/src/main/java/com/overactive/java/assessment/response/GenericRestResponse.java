package com.overactive.java.assessment.response;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;

@Getter
@ToString
public class GenericRestResponse<T> {

    private final ArrayList<T> data;
    private final HashMap<String, String> meta;

    public GenericRestResponse(ArrayList<T> list, HashMap<String, String> meta) {
        this.data = list;
        this.meta = meta;
    }
}
