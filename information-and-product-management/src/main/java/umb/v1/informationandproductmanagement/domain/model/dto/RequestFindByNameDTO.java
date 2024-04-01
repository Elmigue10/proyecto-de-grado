package umb.v1.informationandproductmanagement.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RequestFindByNameDTO {

    @JsonProperty("productName")
    private String productName;

    @JsonProperty("skip")
    private int skip;

    @JsonProperty("limit")
    private int limit;

}
