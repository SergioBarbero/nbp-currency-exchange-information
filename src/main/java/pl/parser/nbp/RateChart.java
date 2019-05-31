package pl.parser.nbp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.util.Date;
import java.util.List;

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
    private List<Currency> currencies;

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public String getChartNumber() {
        return chartNumber;
    }

    public void setChartNumber(String chartNumber) {
        this.chartNumber = chartNumber;
    }

    public Date getListingDate() {
        return listingDate;
    }

    public void setListingDate(Date listingDate) {
        this.listingDate = listingDate;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public Currency getRateByCurrency(String currencyCode) {
        return this.getCurrencies().stream().filter(currency -> currency.getCurrencyCode().equals(currencyCode)).findFirst().get();
    }
}
