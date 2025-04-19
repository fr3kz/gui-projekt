package prices;

import bundles.enums.SubscriptionStatus;
import bundles.enums.Type;
import prices.interfaces.Price;
import static utilities.Utilities.getKey;
import java.util.HashMap;
import java.util.Map;

public class PriceList {
    private static PriceList instance;
    private Map<String, Price> prices = new HashMap<>();

    private PriceList() {}

    // Singleton
    public static PriceList getPricelist() {
        if (instance == null) {
            instance = new PriceList();
        }
        return instance;
    }

    public void add(Type type, String name, int singlePrice, int mediumPrice, int lowPrice, int threshold) {
        String key = getKey(type, name);
        prices.put(key, new ShortPrice(singlePrice, mediumPrice, lowPrice, threshold));
    }

    public void add(Type type, String name, int singlePrice, int discountPrice, int threshold) {
        String key = getKey(type, name);
        prices.put(key, new MidPrice(singlePrice, discountPrice, threshold));
    }

    public void add(Type type, String name, int normalPrice, int subscriptionPrice) {
        String key = getKey(type, name);
        prices.put(key, new LongPrice(normalPrice, subscriptionPrice));
    }

    public void add(Type type, String name) {
        String key = getKey(type, name);
        prices.put(key, new FreePrice());
    }

    public int getPrice(Type type, String name, int periods, SubscriptionStatus status) {
        String key = getKey(type, name);
        Price price = prices.get(key);
        if (price == null) {
            return -1;
        }
        return price.getPrice(periods, status);
    }

}