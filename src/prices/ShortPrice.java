package prices;

import bundles.enums.SubscriptionStatus;
import prices.interfaces.Price;

public class ShortPrice implements Price {
    private int singlePrice;
    private int mediumPrice;
    private int lowPrice;
    private int threshold;

    public ShortPrice(int singlePrice, int mediumPrice, int lowPrice, int threshold) {
        this.singlePrice = singlePrice;
        this.mediumPrice = mediumPrice;
        this.lowPrice = lowPrice;
        this.threshold = threshold;
    }

    @Override
    public int getPrice(int periods, SubscriptionStatus status) {
        if (periods == 1) {
            return singlePrice;
        } else if (periods >= 2 && periods <= threshold) {
            return mediumPrice;
        } else {
            return lowPrice;
        }
    }
}
