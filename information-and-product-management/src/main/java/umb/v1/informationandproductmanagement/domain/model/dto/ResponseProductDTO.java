package umb.v1.informationandproductmanagement.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseProductDTO {

    @JsonProperty("message")
    private String message;

    @JsonProperty("status")
    private int status;

    @JsonProperty("producto")
    private ProductDTO product;

    public ResponseProductDTO(String message, int status, ProductDTO product) {
        this.message = message;
        this.status = status;
        this.product = product;
    }

    public ResponseProductDTO() {
    }
}
