package com.mooracle.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/** Entry 23a: Creating WebUtils.java class to support RestApiService.java
 *  1.  This class provides URI Encoder for other classes
 *  2.  The encoder make UTF-8 to be prepared as URI template for services in REST
 *  3.  The encoder replace the regex characters into characters in that list, make sure to add % in the regex
 * */
public class WebUtils {
    // the only method it needed

    public static String uriEncode(String s){
        try {
            return URLEncoder.encode(s, "UTF-8")
                    .replaceAll("\\%20", "+")
                    .replaceAll("\\%2C", ",")
                    .replaceAll("\\%21", "!")
                    .replaceAll("\\%27","'")
                    .replaceAll("\\%28", "(")
                    .replaceAll("\\%29", ")")
                    .replaceAll("\\%7e", "~");
        } catch (UnsupportedEncodingException e){
            return s;
        }
    }
}
