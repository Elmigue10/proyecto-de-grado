package umb.v1.informationandproductmanagement.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RequestFindByIdDTO {

    @JsonProperty("productoId")
    private String productoId;

}
