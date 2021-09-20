package com.overactive.java.assessment.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;

@Getter
@ToString
public class GenericRestResponse<T> {

    @ApiModelProperty(value = "Response data")
    private final ArrayList<T> data;

    @ApiModelProperty(value = "Response metadata")
    private final HashMap<String, String> meta;

    public GenericRestResponse(ArrayList<T> list, HashMap<String, String> meta) {
        this.data = list;
        this.meta = meta;
    }
}
