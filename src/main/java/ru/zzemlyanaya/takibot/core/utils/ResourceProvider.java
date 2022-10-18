package ru.zzemlyanaya.takibot.core.utils;

import java.util.ResourceBundle;

/* created by zzemlyanaya on 18/10/2022 */

public class ResourceProvider {

    private static final ResourceBundle stringBundle =
            ResourceBundle.getBundle("strings", new XmlResourceBundleControl());

    public static String getString(String name) {
        return stringBundle.getString(name);
    }

}
