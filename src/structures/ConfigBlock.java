package structures;

import java.util.HashMap;
import java.util.Map;

public class ConfigBlock {
    private String defaultCurrency;
    private Map<String, Double> exchangeRate = new HashMap<>();

    public String getDefaultCurrency() {
        return defaultCurrency;
    }

    public Map<String, Double> getExchangeRate() {
        return exchangeRate;
    }

    public void setDefaultCurrency(String defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    public void addCurrency(String currency, double exchangeRate){
        this.exchangeRate.put(currency, exchangeRate);
    }
}
