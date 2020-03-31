package org.prepare.data;

public class Utility {

    public static boolean isNumericRegex(String str) {
        if (str == null)
            return false;
        return str.matches("-?\\d+");
    }
}
