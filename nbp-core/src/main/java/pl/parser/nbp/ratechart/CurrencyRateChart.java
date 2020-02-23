package pl.parser.nbp.ratechart;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public abstract class CurrencyRateChart {

    private String chartNumber;

    private Date publicationDate;

    @JsonProperty("chart_number")
    public String getChartNumber() {
        return chartNumber;
    }

    @JsonProperty("numer_tabeli")
    public void setChartNumber(String chartNumber) {
        this.chartNumber = chartNumber;
    }


    @JsonProperty("publication_date")
    public Date getPublicationDate() {
        return publicationDate;
    }

    @JsonProperty("data_publikacji")
    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }


}
