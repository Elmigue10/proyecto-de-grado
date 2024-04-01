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
public class ResponseProductListDTO {

    @JsonProperty("message")
    private String message;

    @JsonProperty("status")
    private int status;

    @JsonProperty("productos")
    private List<ProductDTO> products;

    @JsonProperty("totalProductos")
    private int totalProductos;

    @JsonProperty("marcas")
    private List<String> marcas;

    @JsonProperty("categorias")
    private List<String> categorias;

    @JsonProperty("plataformas")
    private List<String> plataformas;

    public ResponseProductListDTO(String message, int status, List<ProductDTO> products,
                                  int totalProductos, List<String> marcas, List<String> categorias,
                                  List<String> plataformas) {
        this.message = message;
        this.status = status;
        this.products = products;
        this.totalProductos = totalProductos;
        this.marcas = marcas;
        this.categorias = categorias;
        this.plataformas = plataformas;
    }

    public ResponseProductListDTO() {
    }
}
