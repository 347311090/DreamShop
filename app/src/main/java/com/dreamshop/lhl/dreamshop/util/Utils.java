package com.dreamshop.lhl.dreamshop.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lhl on 2015/10/30.
 */
public class Utils {
    public static boolean isEmail(String strEmail) {
        String strPattern = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }
}
