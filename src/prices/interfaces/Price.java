package prices.interfaces;

import bundles.enums.SubscriptionStatus;

public interface Price {
    int getPrice(int periods, SubscriptionStatus status);
}