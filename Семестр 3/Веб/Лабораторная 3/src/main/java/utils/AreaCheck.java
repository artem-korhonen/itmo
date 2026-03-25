package utils;

import java.math.BigDecimal;

public class AreaCheck {
    public static boolean checkArea(BigDecimal x, BigDecimal y, BigDecimal r) {
        BigDecimal zero = BigDecimal.ZERO;

        return (x.compareTo(zero) <= 0 &&
                y.compareTo(zero) <= 0 &&
                x.pow(2).add(y.pow(2)).compareTo(r.pow(2)) <= 0)
                ||
                (x.compareTo(zero) <= 0 &&
                y.compareTo(zero) >= 0 &&
                y.compareTo(x.add(r)) <= 0)
                ||
                (x.compareTo(zero) >= 0 &&
                x.compareTo(r) <= 0 &&
                y.compareTo(zero) >= 0 &&
                y.compareTo(r) <= 0);
    }
}
