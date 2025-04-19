package clients;

import basckets.Basket;
import basckets.Wishlist;
import bundles.*;
import bundles.enums.PaymentMethod;
import bundles.enums.SubscriptionStatus;
import bundles.enums.Type;
import prices.PriceList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utilities.Utilities.getKey;


public class Client {
    private String name;
    private double wallet;
    private SubscriptionStatus subscriptionStatus;
    private Wishlist wishlist;
    private Basket basket;
    private boolean UsedFreeBundle;
    private Map<String, Bundle> lastTransaction = new HashMap<>();

    public Client(String name, double wallet, SubscriptionStatus subscriptionStatus) {
        this.name = name;
        this.wallet = wallet;
        this.subscriptionStatus = subscriptionStatus;
        this.wishlist = new Wishlist();
        this.basket = new Basket();
        this.UsedFreeBundle = false;
    }

    public void add(Bundle bundle) {
        wishlist.add(bundle);
    }

    public Wishlist getWishlist() {
        return wishlist;
    }

    public Basket getBasket() {
        return basket;
    }

    public String getName() {
        return name;
    }

    public double getWallet() {
        return wallet;
    }


    public void pack() {
        basket.clear();
        PriceList priceList = PriceList.getPricelist();

        List<Bundle> toBeDeleted = new ArrayList<>();

        for (Bundle bundle : wishlist.getBundles()) {
            if (bundle.getPrice(priceList, subscriptionStatus) != -1) {
                basket.add(bundle);
                toBeDeleted.add(bundle);
            }
        }

        for (Bundle bundle : toBeDeleted) {
            wishlist.remove(bundle);
        }
    }

    public void pay(PaymentMethod method, boolean partial) {
        double totalPrice = basket.getTotalPrice(subscriptionStatus);

        double finalPrice = method == PaymentMethod.CARD ? totalPrice * 1.02 : totalPrice;

        if (wallet >= finalPrice) {
            lastTransaction.clear();
            for (Bundle bundle : basket.getBundles()) {
                String key = bundle.getType() + "_" + bundle.getName();
                lastTransaction.put(key, bundle);
            }

            wallet -= finalPrice;
            basket.clear();
        } else if (partial) {
            payPartially(method);
        } else {
            basket.clear();
            wishlist.clear();
        }
    }

    private void payPartially(PaymentMethod method) {
        PriceList priceList = PriceList.getPricelist();
        List<Bundle> BundlestoBuy = new ArrayList<>();
        double remainingMoney = wallet;

        List<Bundle> sortedBundles = basket.getBundles();
        sortedBundles.sort(Bundle.priceComparator(priceList, subscriptionStatus));

        lastTransaction.clear();

        for (Bundle bundle : sortedBundles) {
            int price = bundle.getPrice(priceList, subscriptionStatus);
            int maxPeriods = (int) (remainingMoney / price);

            if (method == PaymentMethod.CARD) {
                maxPeriods = (int) (remainingMoney / (price * 1.02));
            }

            if (maxPeriods > 0) {
                int periodsToKeep = Math.min(maxPeriods, bundle.getPeriods());

                if (periodsToKeep < bundle.getPeriods()) {
                    Bundle remainingBundle = createBundleOfType(bundle.getType(), bundle.getName(), bundle.getPeriods() - periodsToKeep);
                    basket.add(remainingBundle);

                    bundle.setPeriods(periodsToKeep);
                }

                double cost = periodsToKeep * price;
                if (method == PaymentMethod.CARD) {
                    cost *= 1.02;
                }

                remainingMoney -= cost;
                BundlestoBuy.add(bundle);

                String key = getKey(bundle.getType(), bundle.getName());
                lastTransaction.put(key, bundle);
            }
        }

        basket.clear();
        for (Bundle bundle : sortedBundles) {
            if (!BundlestoBuy.contains(bundle)) {
                basket.add(bundle);
            }
        }

        wallet = remainingMoney;
    }


    private Bundle createBundleOfType(Type type, String name, int periods) {
        switch (type) {
            case SHORT:
                return new ShortBundle(name, periods);
            case MID:
                return new MidBundle(name, periods);
            case LONG:
                return new LongBundle(name, periods);
            case FREE:
                return new FreeBundle(name, periods);
            default:
                throw new IllegalArgumentException("Unknown bundle type: " + type);
        }
    }

    public void returnGB(Type type, String name, int periods) {
        String key = getKey(type, name);
        Bundle bundle = lastTransaction.get(key);

        if (bundle != null) {
            int price = bundle.getPrice(PriceList.getPricelist(), subscriptionStatus);
            wallet += price * periods;

            Bundle returnedBundle = createBundleOfType(type, name, periods);
            basket.add(returnedBundle);
        }
    }
}