package com.purelove.util;

public class SqlUtil {

     public static String getPageSQL(String sql, int page, int pageSize) {
       return sql+" limit "+(page-1)*pageSize+","+page+pageSize;
    }
}