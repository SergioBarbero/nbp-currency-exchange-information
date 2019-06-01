package pl.parser.nbp;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class CurrencyDeserializer extends StdDeserializer<Currency> {
    private static final long serialVersionUID = 1L;

    public CurrencyDeserializer() {
        this(Currency.class);
    }

    protected CurrencyDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Currency deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        String currencyName;
        int base;
        String currencyCode;
        float buyingRate;
        float sellingRate;

        currencyName = node.get("nazwa_waluty").asText();
        base = Integer.parseInt(node.get("przelicznik").asText());
        currencyCode = node.get("kod_waluty").asText();
        buyingRate = Float.parseFloat(node.get("kurs_kupna").asText().replace("," , "."));
        sellingRate = Float.parseFloat(node.get("kurs_sprzedazy").asText().replace("," , "."));

        return new Currency(currencyName, base, currencyCode, buyingRate, sellingRate);
    }
}
