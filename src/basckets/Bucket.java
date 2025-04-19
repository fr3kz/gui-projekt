package basckets;

import bundles.Bundle;

import java.util.ArrayList;
import java.util.List;

import static utilities.Utilities.getString;

public abstract class Bucket {
    protected List<Bundle> bundles = new ArrayList<>();

    public void add(Bundle bundle) {
        bundles.add(bundle);
    }

    public void remove(Bundle bundle) {
        bundles.remove(bundle);
    }

    public List<Bundle> getBundles() {
        return new ArrayList<>(bundles);
    }

    public void clear() {
        bundles.clear();
    }

    @Override
    public String toString() {
        return getString(bundles);
    }
}