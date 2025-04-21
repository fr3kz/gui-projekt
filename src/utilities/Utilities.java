package utilities;

import bundles.Bundle;
import bundles.enums.SubscriptionStatus;
import bundles.enums.Type;

import java.util.List;

public class Utilities {
    public static String getString(List<Bundle> bundles, SubscriptionStatus status) {
        if (bundles.isEmpty()) {
            return "-- pusto";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bundles.size(); i++) {
            sb.append(bundles.get(i).toString(status));
            if (i < bundles.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public static String getKey(Type type, String name) {
        return type + "_" + name;
    }
}
