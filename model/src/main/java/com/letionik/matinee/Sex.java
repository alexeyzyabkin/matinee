package com.letionik.matinee;

/**
 * Created by Alexey Zyabkin on 12.12.2015.
 */
public enum Sex {
    MALE, FEMALE;

    //strings in switch are not supported in -source 1.5
    public static Sex parseSex(String num) {
        if (num.equals("0")) {
            return MALE;
        } else if (num.equals("1")) {
            return FEMALE;
        } else {
            return MALE;
        }
    }
}
