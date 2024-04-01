package umb.v1.informationandproductmanagement.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class ProductDTO {

    @JsonProperty("_id")
    private String id;

    @JsonProperty("caracteristicas")
    private List<CharacteristicDTO> characteristics;

    @JsonProperty("categoria")
    private String categoria;

    @JsonProperty("comentarios")
    private List<CommentDTO> comments;

    @JsonProperty("createdOrUpdatedAt")
    private String createdOrUpdatedAt;

    @JsonProperty("imagenUrl")
    private String imagen_url;

    @JsonProperty("marca")
    private String marca;

    @JsonProperty("nombre")
    private String nombre;

    @JsonProperty("plataforma")
    private String plataforma;

    @JsonProperty("precio")
    private String precio;

    @JsonProperty("url")
    private String url;

}
