package umb.v1.informationandproductmanagement.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.List;

@Getter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchHistoryDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("fecha")
    private Timestamp fecha;

    @JsonProperty("busqueda")
    private String busqueda;

    @JsonProperty("productos")
    private List<ProductDTO> productos;


}
