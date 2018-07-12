package structures;

import java.util.HashMap;
import java.util.Map;

public class ConfigBlock {
    private String defaultCurrency;
    private Map<String, Double> exchangeRate;

    public ConfigBlock(String defaultCurrency, Map<String, Double> exchangeRate) {
        this.defaultCurrency = defaultCurrency;
        this.exchangeRate = exchangeRate;
    }

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
    public double getExchangeRate(String currency) {
         return exchangeRate.get(currency).doubleValue();
    }
}
