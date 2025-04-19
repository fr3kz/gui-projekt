package bundles;

import bundles.enums.SubscriptionStatus;
import bundles.enums.Type;
import prices.PriceList;

public class MidBundle extends Bundle {
    public MidBundle(String name, int periods){
        super(name, periods);
        this.type = Type.MID;
    }

    @Override
    public int getPrice(PriceList priceList, SubscriptionStatus status) {
        return priceList.getPrice(type, name, periods, status);
    }

}
