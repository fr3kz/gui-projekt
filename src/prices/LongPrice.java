package prices;

import bundles.enums.SubscriptionStatus;
import prices.interfaces.Price;

public class LongPrice implements Price {
    private int normalPrice;
    private int subscriptionPrice;

    public LongPrice(int normalPrice, int subscriptionPrice) {
        this.normalPrice = normalPrice;
        this.subscriptionPrice = subscriptionPrice;
    }

    @Override
    public int getPrice(int periods, SubscriptionStatus status) {
        if (status == SubscriptionStatus.YES) {
            return subscriptionPrice;
        } else {
            return normalPrice;
        }
    }
}