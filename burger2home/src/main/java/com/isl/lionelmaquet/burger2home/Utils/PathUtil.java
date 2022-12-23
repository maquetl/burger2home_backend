package com.isl.lionelmaquet.burger2home.Utils;

import com.isl.lionelmaquet.burger2home.Keys.KEYS;

public class PathUtil {
    public static String getImagesPath(){
        String envVar = System.getenv("BURGER2HOME_IMAGE_PATH");
        if (envVar == null) return "/burger2home/uploads/";
        if (envVar.charAt(envVar.length() - 1) != '\\') envVar += "\\";
        return envVar;
    }
}
