package com.overactive.java.assessment.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@Getter
@ToString
public class GenericRestResponse<T> {

    @ApiModelProperty(value = "Response data")
    private final ArrayList<T> data;

    @ApiModelProperty(value = "Response metadata")
    private final GenericMetadata meta;

    public GenericRestResponse(ArrayList<T> list, GenericMetadata meta) {
        this.data = list;
        this.meta = meta;
    }


    @Getter
    @ToString
    public static class GenericMetadata{
        private String apiVersion;
        private Date responseTime;
        private String responseCode;
        private String errorMessage;

        public GenericMetadata(String apiVersion, Date responseTime, String responseCode, String errorMessage) {
            this.apiVersion = apiVersion;
            this.responseTime = responseTime;
            this.responseCode = responseCode;
            this.errorMessage = errorMessage;
        }
    }

}
