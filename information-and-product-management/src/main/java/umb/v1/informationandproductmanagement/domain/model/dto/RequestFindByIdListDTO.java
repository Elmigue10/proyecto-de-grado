package umb.v1.informationandproductmanagement.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class RequestFindByIdListDTO {

    @JsonProperty("idList")
    private List<String> idList;

    @JsonProperty("skip")
    private int skip;

    @JsonProperty("limit")
    private int limit;

}
