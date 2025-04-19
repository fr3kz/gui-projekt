package prices;

import bundles.enums.SubscriptionStatus;
import prices.interfaces.Price;

public class MidPrice implements Price {
    private int singlePrice;
    private int discountPrice;
    private int threshold;

    public MidPrice(int singlePrice, int discountPrice, int threshold) {
        this.singlePrice = singlePrice;
        this.discountPrice = discountPrice;
        this.threshold = threshold;
    }

    @Override
    public int getPrice(int periods, SubscriptionStatus status) {
        if (periods <= threshold) {
            return singlePrice;
        } else {
            return discountPrice;
        }
    }
}