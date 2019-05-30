package pl.parser.nbp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.util.ArrayList;
import java.util.Date;

public class RateChart {

    @JsonProperty("typ")
    private char type;

    @JsonProperty("numer_tabeli")
    private String chartNumber;

    @JsonProperty("data_notowania")
    private Date listingDate;

    @JsonProperty("data_publikacji")
    private Date publicationDate;

    @JsonProperty("pozycja")
    @JacksonXmlElementWrapper(useWrapping = false)
    private ArrayList<Currency> currencies;
}
