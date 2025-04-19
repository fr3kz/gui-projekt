package prices;

import bundles.enums.SubscriptionStatus;
import prices.interfaces.Price;

public class FreePrice implements Price {
    @Override
    public int getPrice(int periods, SubscriptionStatus status) {
        return 0;
    }
}