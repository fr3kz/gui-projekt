package basckets;

import bundles.Bundle;
import prices.PriceList;
import bundles.enums.SubscriptionStatus;

public class Basket extends Bucket {

    private static Basket basket;
    private Basket(){}

    public static Basket getInstance(){
        if(basket == null){
            basket = new Basket();
        }
        return basket;
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
}