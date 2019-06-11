package pl.parser.nbp.Currency;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = CurrencyDeserializer.class)
public class Currency {

    private String currencyName;

    private int base;

    private CurrencyCode currencyCode;

    private float buyingRate;

    @JsonProperty("currency_name")
    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    @JsonProperty("base")
    public int getBase() {
        return base;
    }

    public void setBase(int base) {
        this.base = base;
    }

    @JsonProperty("curency_code")
    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(CurrencyCode currencyCode) {
        this.currencyCode = currencyCode;
    }

    @JsonProperty("buying_rate")
    public float getBuyingRate() {
        return buyingRate;
    }

    public void setBuyingRate(float buyingRate) {
        this.buyingRate = buyingRate;
    }

    @JsonProperty("selling_rate")
    public float getSellingRate() {
        return sellingRate;
    }

    public void setSellingRate(float sellingRate) {
        this.sellingRate = sellingRate;
    }

    private float sellingRate;

    public Currency(String currencyName, int base, CurrencyCode currencyCode, float buyingRate, float sellingRate) {
        this.currencyName = currencyName;
        this.base = base;
        this.currencyCode = currencyCode;
        this.buyingRate = buyingRate;
        this.sellingRate = sellingRate;
    }
}
