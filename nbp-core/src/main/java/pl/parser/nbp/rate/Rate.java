package pl.parser.nbp.rate;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class Rate {

    private CurrencyCode currencyCode;

    private String currencyName;

    private int base;

    public Rate(CurrencyCode currencyCode, String currencyName, int base) {
        this.base = base;
        this.currencyCode = currencyCode;
        this.currencyName = currencyName;
    }

    @JsonProperty("base")
    public int getBase() {
        return base;
    }

    public void setBase(int base) {
        this.base = base;
    }

    @JsonProperty("currency_name")
    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    @JsonProperty("curency_code")
    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(CurrencyCode currencyCode) {
        this.currencyCode = currencyCode;
    }
}
