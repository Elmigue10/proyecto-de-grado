package umb.v1.informationandproductmanagement.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

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

    @JsonProperty("similar")
    private List<ProductDTO> similars;

    public ResponseProductDTO(String message, int status, ProductDTO product, List<ProductDTO> similars) {
        this.message = message;
        this.status = status;
        this.product = product;
        this.similars = similars;
    }

    public ResponseProductDTO() {
    }
}
