package umb.v1.informationandproductmanagement.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.ToString;

import java.util.List;

@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PqrsRequestResponseDTO {

    @JsonProperty("message")
    private String message;

    @JsonProperty("status")
    private int status;

    @JsonProperty("total")
    private long total;

    @JsonProperty("pqrs")
    private List<PqrsRequestDTO> pqrsRequest;

}
