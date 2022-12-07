package zeee.blog.utils;

import java.util.Comparator;

/**
 * @author wz
 * @date 2022/12/5
 * @description 排序工具类
 */
public class SortUtil implements Comparator {

    public static final Character CHARACTER_ZERO = '0';

    private SortUtil() {}

    /**
     * 比较两个字符串的顺序，一般用于混合字符串
     * @return ，a在前时返回-1， b在前时返回1，相等时返回0
     */
    public static int compareRight(String a, String b) {
        int bias = 0;
        int ia = 0;
        int ib = 0;

        // The longest run of digits wins.  That aside, the greatest
        // value wins, but we can't know that it will until we've scanned
        // both numbers to know that they have the same magnitude, so we
        // remember it in BIAS.
        for (; ; ia++, ib++) {
            char ca = charAt(a, ia);
            char cb = charAt(b, ib);

            if (!isDigit(ca) && !isDigit(cb)) {
                return bias;
            }
            if (!isDigit(ca)) {
                return -1;
            }
            if (!isDigit(cb)) {
                return +1;
            }
            if (ca == 0 && cb == 0) {
                return bias;
            }
            if (bias == 0) {
                if (ca < cb) {
                    bias = -1;
                } else if (ca > cb) {
                    bias = +1;
                }
            }
        }
    }

    @Override
    public int compare(Object o1, Object o2) {
        if (o1 == null) {
            return -1;
        }
        if (o2 == null) {
            return 1;
        }
        String a = o1.toString();
        String b = o2.toString();

        int ia = 0, ib = 0;
        int nza, nzb;
        char ca, cb;

        while (true) {
            // only count the number of zeroes leading the last number compared
            nza = nzb = 0;

            ca = charAt(a, ia);
            cb = charAt(b, ib);

            // skip over leading zeros
            while (Character.isSpaceChar(ca) || ca == CHARACTER_ZERO) {
                if (ca == CHARACTER_ZERO) {
                    nza++;
                } else {
                    // only count consecutive zeroes
                    nza = 0;
                }
                ca = charAt(a, ++ia);
            }

            while (Character.isSpaceChar(cb) || cb == CHARACTER_ZERO) {
                if (cb == CHARACTER_ZERO) {
                    nzb++;
                } else {
                    // only count consecutive zeroes
                    nzb = 0;
                }
                cb = charAt(b, ++ib);
            }

            // process run of digits
            if (Character.isDigit(ca) && Character.isDigit(cb)) {
                int bias = compareRight(a.substring(ia), b.substring(ib));
                if (bias != 0) {
                    return bias;
                }
            }

            if (ca == 0 && cb == 0) {
                // The strings compare the same.  Perhaps the caller
                // will want to call strcmp to break the tie.
                return compareEqual(a, b, nza, nzb);
            }

            if (ca < cb) {
                return -1;
            } else if (ca > cb) {
                return +1;
            }

            ++ia;
            ++ib;
        }
    }

    private static boolean isDigit(char c) {
        return Character.isDigit(c) || c == '.' || c == ',';
    }

    private static char charAt(String s, int i) {
        return (i >= s.length()) ? 0 : s.charAt(i);
    }

    private static int compareEqual(String a, String b, int nza, int nzb) {
        if (nza - nzb != 0) {
            return nza - nzb;
        }

        if (a.length() == b.length()) {
            return a.compareTo(b);
        }

        return a.length() - b.length();
    }

}