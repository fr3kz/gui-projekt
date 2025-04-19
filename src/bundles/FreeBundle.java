package bundles;

import bundles.enums.SubscriptionStatus;
import bundles.enums.Type;
import prices.PriceList;

public class FreeBundle extends Bundle {
    public FreeBundle(String name,int periods) {
        super(name, periods);
        this.type = Type.FREE;
        this.setPeriods(1);
    }

    @Override
    public int getPrice(PriceList priceList, SubscriptionStatus status) {
        return priceList.getPrice(type, name, periods, status);
    }
}
