package basckets;

import bundles.Bundle;
import prices.PriceList;
import bundles.enums.SubscriptionStatus;
import java.util.ArrayList;
import java.util.List;

import static basckets.Utilities.getString;


public class Basket {
    private List<Bundle> bundles = new ArrayList<>();

    public void add(Bundle bundle) {
        bundles.add(bundle);
    }

    public List<Bundle> getBundles() {
        return new ArrayList<>(bundles);
    }

    public void clear() {
        bundles.clear();
    }

    public double getTotalPrice(SubscriptionStatus status) {
        PriceList priceList = PriceList.getPricelist();

        double total = 0;
        for (Bundle bundle : bundles) {
            int price = bundle.getPrice(priceList, status);
            if (price != -1) {
                total += price * bundle.getPeriods();
            }
        }
        return total;
    }

    @Override
    public String toString() {
        return getString(bundles);
    }
}