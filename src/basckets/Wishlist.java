package basckets;

import bundles.Bundle;
import java.util.ArrayList;
import java.util.List;

import static basckets.Utilities.getString;

public class Wishlist {
    private List<Bundle> bundles = new ArrayList<>();

    public void add(Bundle bundle) {
        bundles.add(bundle);
    }

    public List<Bundle> getBundles() {
        return new ArrayList<>(bundles);
    }

    public void remove(Bundle bundle) {
        bundles.remove(bundle);
    }

    public void clear() {
        bundles.clear();
    }

    @Override
    public String toString() {
        return getString(bundles);
    }

}
