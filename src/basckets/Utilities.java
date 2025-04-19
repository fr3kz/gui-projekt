package basckets;

import bundles.Bundle;

import java.util.List;

public class Utilities {
    static String getString(List<Bundle> bundles) {
        if (bundles.isEmpty()) {
            return "-- pusto";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bundles.size(); i++) {
            sb.append(bundles.get(i).toString());
            if (i < bundles.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
