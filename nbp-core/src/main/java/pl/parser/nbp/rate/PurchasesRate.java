package pl.parser.nbp.rate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = PurchasesRateDeserializer.class)
public class PurchasesRate extends Rate {

    private float buyingRate;

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

    public PurchasesRate(String currencyName, int base, CurrencyCode currencyCode, float buyingRate, float sellingRate) {
        super(currencyCode, currencyName, base);
        this.buyingRate = buyingRate;
        this.sellingRate = sellingRate;
    }
}
