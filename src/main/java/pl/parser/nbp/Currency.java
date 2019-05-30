package pl.parser.nbp;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = CurrencyDeserializer.class)
public class Currency {

    private String currencyName;

    private int base;

    private String currencyCode;

    private float buyingRate;

    private float sellingRate;

    public Currency(String currencyName, int base, String currencyCode, float buyingRate, float sellingRate) {
        this.currencyName = currencyName;
        this.base = base;
        this.currencyCode = currencyCode;
        this.buyingRate = buyingRate;
        this.sellingRate = sellingRate;
    }
}
