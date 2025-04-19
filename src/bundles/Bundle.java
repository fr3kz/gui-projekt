package bundles;

import bundles.enums.SubscriptionStatus;
import bundles.enums.Type;
import prices.PriceList;

import java.util.Comparator;

public abstract class Bundle {
    protected String name;
    protected int periods;
    protected Type type;

    public Bundle(String name, int periods) {
        this.name = name;
        this.periods = periods;
    }

    public String getName() {
        return name;
    }

    public int getPeriods() {
        return periods;
    }

    public Type getType() {
        return type;
    }

    public static Comparator<Bundle> priceComparator(PriceList priceList, SubscriptionStatus status) {
        return Comparator.comparingInt(bundle -> bundle.getPrice(priceList, status));
    }


    public void setPeriods(int periods) {
        this.periods = periods;
    }

    public abstract int getPrice(PriceList priceList, SubscriptionStatus status);

    @Override
    public String toString() {
        int price = getPrice(PriceList.getPricelist(),null);
        String pricestr = price == -1 ? "Brak ceny" : "Cena: " + price;
        return name + ", typ: " + getTypeName() + ", ile: " + periods + " " +
                (periods == 1 ? "okres" : "okresy") + ", " + pricestr;
    }

    protected String getTypeName() {
        switch (type) {
            case SHORT: return "krótkoterminowy";
            case MID: return "średnioterminowy";
            case LONG: return "długotermoniwy";
            case FREE: return "darmowy";
            default: return "nieznany";
        }
    }
}