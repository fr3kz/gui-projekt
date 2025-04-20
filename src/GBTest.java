import basckets.Basket;
import basckets.Wishlist;
import bundles.*;
import bundles.enums.PaymentMethod;
import bundles.enums.SubscriptionStatus;
import bundles.enums.Type;
import clients.Client;
import prices.PriceList;

// Klasa testowa
public class GBTest {

    // Cena pakietów o podanej nazwie z koszyka
    static int price(String bundleName, Basket b) {
        PriceList priceList = PriceList.getPricelist();
        int totalPrice = 0;

        for (Bundle bundle : b.getBundles()) {
            if (bundle.getName().equals(bundleName)) {
                int unitPrice = bundle.getPrice(priceList, null);
                if (unitPrice != -1) {
                    totalPrice += unitPrice * bundle.getPeriods();
                }
            }
        }

        return totalPrice;
    }

    public static void main(String[] args) {
        // Cennik
        PriceList cennik = PriceList.getPricelist();

        // Dodawanie nowych cen do cennika
        cennik.add(Type.SHORT, "5GB", 20, 15, 10, 3);
        cennik.add(Type.MID, "10GB", 25, 20, 2);
        cennik.add(Type.LONG, "30GB", 35, 30);
        cennik.add(Type.FREE, "1GB");

        // Klient lte deklaruje kwotę 200 zł na zamównienia; YES oznacza, że klient posiada abonament
        Client lte = new Client("lte", 200, SubscriptionStatus.YES);

        // Klient lte dodaje do listy życzeń różne pakiety
        lte.add(new ShortBundle("5GB", 4));
        lte.add(new MidBundle("10GB", 3));
        lte.add(new LongBundle("30GB", 2));
        lte.add(new FreeBundle("2GB", 2));

        // Lista życzeń klienta lte
        Wishlist listaLte = lte.getWishlist();
        System.out.println("Lista życzeń klienta "+lte.getName() + " \n"+ listaLte +"\n");

        // Przed płaceniem, klient przepakuje pakiety z listy życzeń do koszyka
        Basket koszykLte = lte.getBasket();
        lte.pack();

        // Co jest na liście życzeń klienta lte
        System.out.println("Po przepakowaniu, lista życzeń klienta " + lte.getWishlist() + " \n");

        // Co jest w koszyku klienta lte
        System.out.println("Po przepakowaniu, koszyk klienta " + koszykLte + " \n" );

        // Ile wynosi cena wszystkich pakietów o nazwie 5GB w koszyku klienta lte
        System.out.println("Pakiety 5GB w koszyku klienta lte kosztowały: " + price("5GB", koszykLte));

        // Klient zapłaci...
        lte.pay(PaymentMethod.CARD, false);

        // Ile klientowi lte zostało pieniędzy?
        System.out.println("Po zapłaceniu, klientowi lte zostało: " + String.format("%.2f", lte.getWallet()) + " zł");

        // Koszyk po zapłaceniu
        System.out.println("Po zapłaceniu, koszyk klienta " + lte.getBasket());
        System.out.println("Po zapłaceniu, koszyk klienta " + koszykLte+ " \n");

        // Teraz przychodzi klient gsm,
        // deklaruje 65 zł na zamówienia
        Client gsm = new Client("gsm", 65, SubscriptionStatus.NO);

        // Zamówił za dużo jak na tę kwotę
        gsm.add(new MidBundle("10GB", 2));
        gsm.add(new LongBundle("30GB", 1));

        // Co klient gsm ma na swojej liście życzeń
        System.out.println("Lista życzeń klienta "+ " \n" + gsm.getWishlist());

        Basket koszykGsm = gsm.getBasket();
        gsm.pack();

        // Co jest na liście życzeń klienta gsm
        System.out.println("Po przepakowaniu, lista życzeń klienta " + gsm.getWishlist());

        // A co jest w koszyku klienta gsm
        System.out.println("Po przepakowaniu, koszyk klienta " + " \n" + gsm.getBasket());

        // klient gsm płaci
        gsm.pay(PaymentMethod.TRANSFER, true);

        // Ile klientowi gsm zostało pieniędzy?
        System.out.println("Po zapłaceniu, klientowi gsm zostało: " + String.format("%.2f", gsm.getWallet()) + " zł");

        // Co zostało w koszyku klienta gsm (za mało pieniędzy miał)
        System.out.println("Po zapłaceniu, koszyk klienta " + koszykGsm);

        gsm.returnGB(Type.MID, "10GB", 1);    // zwrot (do koszyka) pakietu (na 1 okres) z ostatniej transakcji

        // Ile klientowi gsm zostało pieniędzy?
        System.out.println("Po zwrocie, klientowi gsm zostało: " + String.format("%.2f", gsm.getWallet()) + " zł");

        // Co zostało w koszyku klienta gsm
        System.out.println("Po zwrocie, koszyk klienta \n" + koszykGsm);
    }
}