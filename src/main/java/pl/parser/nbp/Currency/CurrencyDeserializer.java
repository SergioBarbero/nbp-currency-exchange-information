package pl.parser.nbp.Currency;

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

        String currencyName = node.get("nazwa_waluty").asText();
        int base = Integer.parseInt(node.get("przelicznik").asText());
        CurrencyCode currencyCode = CurrencyCode.valueOf(node.get("kod_waluty").asText());
        float buyingRate = Float.parseFloat(node.get("kurs_kupna").asText().replace("," , "."));
        float sellingRate = Float.parseFloat(node.get("kurs_sprzedazy").asText().replace("," , "."));

        return new Currency(currencyName, base, currencyCode, buyingRate, sellingRate);
    }
}
