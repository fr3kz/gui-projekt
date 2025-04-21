package basckets;

import bundles.Bundle;
import prices.PriceList;
import bundles.enums.SubscriptionStatus;

public class Basket extends Bucket {

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
}