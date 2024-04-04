package umb.v1.informationandproductmanagement.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class WebScraperResponseDTO {

    @JsonProperty("ktronixWebScraperPhonesTotal")
    private int ktronixWebScraperPhonesTotal;

    @JsonProperty("falabellaWebScraperPhonesTotal")
    private int falabellaWebScraperPhonesTotal;

    @JsonProperty("exitoWebScraperPhonesTotal")
    private int exitoWebScraperPhonesTotal;

    @JsonProperty("mercadoLibreWebScraperPhonesTotal")
    private int mercadoLibreWebScraperPhonesTotal;

    @JsonProperty("ktronixWebScraperComputerTotal")
    private int ktronixWebScraperComputerTotal;

    @JsonProperty("falabellaWebScraperComputerTotal")
    private int falabellaWebScraperComputerTotal;

    @JsonProperty("exitoWebScraperComputerTotal")
    private int exitoWebScraperComputerTotal;

    @JsonProperty("ktronixWebScraperPhonesTotal")
    private int mercadoLibreWebScraperComputerTotal;

}
