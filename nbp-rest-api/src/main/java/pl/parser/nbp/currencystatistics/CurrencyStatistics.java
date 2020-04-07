package pl.parser.nbp.currencystatistics;

public class CurrencyStatistics {

    private final RateStatistics sellingRate;
    private final RateStatistics buyingRate;

    public CurrencyStatistics(RateStatistics sellingRate, RateStatistics buyingRate) {
        this.sellingRate = sellingRate;
        this.buyingRate = buyingRate;
    }

    public RateStatistics getSellingRate() {
        return sellingRate;
    }

    public RateStatistics getBuyingRate() {
        return buyingRate;
    }
}
