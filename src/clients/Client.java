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
        this.wishlist.setStatus(subscriptionStatus);
        this.basket.setStatus(subscriptionStatus);
    }

    public void add(Bundle bundle) {
        if(bundle.getType() == Type.FREE){
            if(!UsedFreeBundle){
                UsedFreeBundle = true;
            }else {
                return;
            }
        }
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
                String key = getKey(bundle.getType(), bundle.getName());
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
        double remainingMoney = wallet;

        List<Bundle> sortedBundles = new ArrayList<>(basket.getBundles());

        sortedBundles.sort(Bundle.priceComparator(priceList, subscriptionStatus));

        lastTransaction.clear();
        basket.clear();

        for (Bundle bundle : sortedBundles) {
            double Price = bundle.getPrice(priceList, subscriptionStatus);

            int periods = bundle.getPeriods();
            if (periods <= 0) continue;

            if (method == PaymentMethod.CARD) {
                Price *= 1.02;
            }

            int affordablePeriods = (int) (remainingMoney /Price);

            if (affordablePeriods >= periods) {
                remainingMoney -= Price * periods;

                String key = getKey(bundle.getType(), bundle.getName());
                lastTransaction.put(key, bundle);

            } else if (affordablePeriods > 0) {
                Bundle partialBundle = createBundleOfType(bundle.getType(), bundle.getName(), affordablePeriods);
                String key = getKey(partialBundle.getType(), partialBundle.getName());
                lastTransaction.put(key, partialBundle);
                remainingMoney -= Price * affordablePeriods;

                int leftover = periods - affordablePeriods;
                Bundle leftoverBundle = createBundleOfType(bundle.getType(), bundle.getName(), leftover);
                basket.add(leftoverBundle);
            } else {
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
            int Price = bundle.getPrice(PriceList.getPricelist(), subscriptionStatus);
            int totalPeriods = bundle.getPeriods();

            if (periods > totalPeriods) {
                periods = totalPeriods;
            }

            wallet += Price * periods;

            Bundle returnedBundle = createBundleOfType(type, name, periods);
            basket.add(returnedBundle);
        }
    }




}