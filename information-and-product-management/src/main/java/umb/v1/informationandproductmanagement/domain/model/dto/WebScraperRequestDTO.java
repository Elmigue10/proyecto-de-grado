package umb.v1.informationandproductmanagement.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class WebScraperRequestDTO {

    @JsonProperty("ktronixPhones")
    private boolean ktronixPhones;

    @JsonProperty("falabellaPhones")
    private boolean falabellaPhones;

    @JsonProperty("exitoPhones")
    private boolean exitoPhones;

    @JsonProperty("mercadoLibrePhones")
    private boolean mercadoLibrePhones;

    @JsonProperty("ktronixComputers")
    private boolean ktronixComputers;

    @JsonProperty("falabellaComputers")
    private boolean falabellaComputers;

    @JsonProperty("exitoComputers")
    private boolean exitoComputers;

    @JsonProperty("mercadoLibreComputers")
    private boolean mercadoLibreComputers;

}
