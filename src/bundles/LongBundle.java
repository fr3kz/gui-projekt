package bundles;

import bundles.enums.SubscriptionStatus;
import bundles.enums.Type;
import prices.PriceList;

public class LongBundle extends Bundle {
    public LongBundle(String name, int periods) {
        super(name, periods);
        this.type = Type.LONG;
    }
    
    @Override
    public int getPrice(PriceList priceList, SubscriptionStatus status) {
        return priceList.getPrice(type, name, periods, status);
    }
}
