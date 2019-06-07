package pl.parser.nbp.RateChart;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import pl.parser.nbp.Currency.Currency;

import java.util.Date;
import java.util.List;

public class CurrencyRateChart {

    private char type;

    private String uid;

    private String chartNumber;

    private Date listingDate;

    private Date publicationDate;

    private List<Currency> currencies;

    @JsonProperty("type")
    public char getType() {
        return type;
    }

    @JsonProperty("typ")
    public void setType(char type) {
        this.type = type;
    }

    @JsonProperty("uid")
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @JsonProperty("chart_number")
    public String getChartNumber() {
        return chartNumber;
    }

    @JsonProperty("numer_tabeli")
    public void setChartNumber(String chartNumber) {
        this.chartNumber = chartNumber;
    }

    @JsonProperty("listing_date")
    public Date getListingDate() {
        return listingDate;
    }

    @JsonProperty("data_notowania")
    public void setListingDate(Date listingDate) {
        this.listingDate = listingDate;
    }

    @JsonProperty("publication_date")
    public Date getPublicationDate() {
        return publicationDate;
    }

    @JsonProperty("data_publikacji")
    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    @JsonProperty("currencies")
    public List<Currency> getCurrencies() {
        return currencies;
    }

    @JsonProperty("pozycja")
    @JacksonXmlElementWrapper(useWrapping = false)
    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public Currency getRateByCurrency(String currencyCode) {
        return this.getCurrencies().stream().filter(currency -> currency.getCurrencyCode().equals(currencyCode)).findFirst().get();
    }
}
