package pl.parser.nbp;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = CurrencyDeserializer.class)
public class Currency {

    private String currencyName;

    private int base;

    private String currencyCode;

    private float buyingRate;

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public int getBase() {
        return base;
    }

    public void setBase(int base) {
        this.base = base;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public float getBuyingRate() {
        return buyingRate;
    }

    public void setBuyingRate(float buyingRate) {
        this.buyingRate = buyingRate;
    }

    public float getSellingRate() {
        return sellingRate;
    }

    public void setSellingRate(float sellingRate) {
        this.sellingRate = sellingRate;
    }

    private float sellingRate;

    public Currency(String currencyName, int base, String currencyCode, float buyingRate, float sellingRate) {
        this.currencyName = currencyName;
        this.base = base;
        this.currencyCode = currencyCode;
        this.buyingRate = buyingRate;
        this.sellingRate = sellingRate;
    }
}
