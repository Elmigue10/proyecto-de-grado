package umb.v1.informationandproductmanagement.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CharacteristicDTO {

    @JsonProperty("nombre")
    private String nombre;

    @JsonProperty("valor")
    private String valor;

}
