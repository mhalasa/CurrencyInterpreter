package structures.ex;

import java.util.HashMap;
import java.util.Map;

public class Configuration {
    private String defaultCurrency;
    private Map<String, Double> exchangeRates = new HashMap<>();

    public String getDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrency(String defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    public void addCurrency(String currency, double exchangeRate){
        this.exchangeRates.put(currency, exchangeRate);
    }
}
