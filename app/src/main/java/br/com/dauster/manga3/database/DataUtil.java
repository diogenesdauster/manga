package br.com.dauster.manga3.database;


import java.util.List;

public class DataUtil {


    public static String listToString(List<String> lista){
        String result = "";
        for (String value: lista) {
            result +=value +" ,";
        }

        return result.substring(0,result.length()-1);
    }

}
