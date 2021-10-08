package com.overactive.java.assessment.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Date;

@Getter
@ToString
public class GenericRestResponse<T> {

    private static final Logger logger = LoggerFactory.getLogger(GenericRestResponse.class);

    @ApiModelProperty(value = "Response data")
    private final ArrayList<T> data;

    @ApiModelProperty(value = "Response metadata")
    private final GenericMetadata meta;

    public GenericRestResponse(ArrayList<T> list, GenericMetadata meta) {
        this.data = list;
        this.meta = meta;
    }

    public static GenericRestResponse<RewardPointsResponse> getGenericRestResponse
            (ArrayList<?> resultList, String apiVersion, String responseCode, String errorMessage) {
        var response = new GenericRestResponse<>(
                (ArrayList<RewardPointsResponse>) resultList,
                new GenericRestResponse.GenericMetadata(
                        apiVersion, new Date(), responseCode, errorMessage)
        );
        logger.error(errorMessage);
        logger.debug("response:" + response);

        return response;
    }

    public static GenericRestResponse<RewardPointsResponse> getGenericErrorRestResponse
            (String exMessage, String apiVersion, Integer errorCode) {
        return getGenericRestResponse(new ArrayList<>(), apiVersion, errorCode.toString(), exMessage);
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
