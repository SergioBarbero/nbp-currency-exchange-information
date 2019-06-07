package pl.parser.nbp.CurrencyStatistics;

public class CurrencyStatistics {

    RateStatistics sellingRate;

    RateStatistics buyingRate;

    public CurrencyStatistics(RateStatistics sellingRate, RateStatistics buyingRate) {
        this.sellingRate = sellingRate;
        this.buyingRate = buyingRate;
    }

    public RateStatistics getSellingRate() {
        return sellingRate;
    }

    public void setSellingRate(RateStatistics sellingRate) {
        this.sellingRate = sellingRate;
    }

    public RateStatistics getBuyingRate() {
        return buyingRate;
    }

    public void setBuyingRate(RateStatistics buyingRate) {
        this.buyingRate = buyingRate;
    }
}
