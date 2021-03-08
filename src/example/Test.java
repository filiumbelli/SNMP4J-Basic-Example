package example;

import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

    public static void main(String[] args) {
        byte[] b = {0,-120,2,1,3,2,5,2,11};
        StringBuilder s = new StringBuilder();
        for(byte a : b){
            s.append(a);
        }
        System.out.println(s.toString());
        System.out.println(s.indexOf("132"));
        String arr = s.substring(s.indexOf("132"));
        System.out.println(arr);
    }
}
