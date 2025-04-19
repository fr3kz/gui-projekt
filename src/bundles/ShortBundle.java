package bundles;

import bundles.enums.SubscriptionStatus;
import bundles.enums.Type;
import prices.PriceList;

public class ShortBundle extends Bundle {
    public ShortBundle(String name,int periods) {
        super(name, periods);
        this.type = Type.SHORT;
    }

    @Override
    public int getPrice(PriceList priceList, SubscriptionStatus status) {
        return priceList.getPrice(type, name, periods, status);
    }
}
