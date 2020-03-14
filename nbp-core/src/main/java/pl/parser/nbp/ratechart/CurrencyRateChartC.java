package pl.parser.nbp.ratechart;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import pl.parser.nbp.rate.PurchasesRate;

import java.util.Date;
import java.util.List;

public class CurrencyRateChartC extends CurrencyRateChart {

    private char type;

    private String uid;

    private Date listingDate;

    private List<PurchasesRate> currencies;

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

    @JsonProperty("listing_date")
    public Date getListingDate() {
        return listingDate;
    }

    @JsonProperty("data_notowania")
    public void setListingDate(Date listingDate) {
        this.listingDate = listingDate;
    }

    @JsonProperty("currencies")
    public List<PurchasesRate> getCurrencies() {
        return currencies;
    }

    @JsonProperty("pozycja")
    @JacksonXmlElementWrapper(useWrapping = false)
    public void setCurrencies(List<PurchasesRate> currencies) {
        this.currencies = currencies;
    }

    public PurchasesRate getRateByCurrency(String currencyCode) {
        return this.getCurrencies().stream().filter(PurchasesRate -> PurchasesRate.getCurrencyCode().equals(currencyCode)).findFirst().get();
    }
}
