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

    @JsonProperty("mercadoLibreWebScraperComputerTotal")
    private int mercadoLibreWebScraperComputerTotal;

    @JsonProperty("ktronixWebScraperMonitorTotal")
    private int ktronixWebScraperMonitorTotal;

    @JsonProperty("falabellaWebScraperMonitorTotal")
    private int falabellaWebScraperMonitorTotal;

    @JsonProperty("exitoWebScraperMonitorTotal")
    private int exitoWebScraperMonitorTotal;

    @JsonProperty("mercadoLibreWebScraperMonitorTotal")
    private int mercadoLibreWebScraperMonitorTotal;

    @JsonProperty("ktronixWebScraperTabletTotal")
    private int ktronixWebScraperTabletTotal;

    @JsonProperty("falabellaWebScraperTabletTotal")
    private int falabellaWebScraperTabletTotal;

    @JsonProperty("exitoWebScraperTabletTotal")
    private int exitoWebScraperTabletTotal;

    @JsonProperty("mercadoLibreWebScraperTabletTotal")
    private int mercadoLibreWebScraperTabletTotal;

}
