package com.casic.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * jdbcType和java数据类型之间的转换
 */
public class JdbcTypeConvertJavaType {

    public static Map<String, String> mysqlColumnMap2JavaProperty = new HashMap<>();

    public static Map<String, String> javaProperty2SqlColumnMap = new HashMap<>();

    static {
        javaProperty2SqlColumnMap.put("Integer", "INTEGER");
        javaProperty2SqlColumnMap.put("Short", "tinyint");
        javaProperty2SqlColumnMap.put("Long", "bigint");
        javaProperty2SqlColumnMap.put("BigDecimal", "decimal(19,2)");
        javaProperty2SqlColumnMap.put("Double", "double precision not null");
        javaProperty2SqlColumnMap.put("Float", "float");
        javaProperty2SqlColumnMap.put("Boolean", "bit");
        javaProperty2SqlColumnMap.put("Timestamp", "datetime");
        javaProperty2SqlColumnMap.put("String", "VARCHAR(255)");


        mysqlColumnMap2JavaProperty.put("bigint", "Long");
        mysqlColumnMap2JavaProperty.put("binary", "byte[]");
        mysqlColumnMap2JavaProperty.put("bit", "Boolean");
        mysqlColumnMap2JavaProperty.put("blob", "byte[]");

        mysqlColumnMap2JavaProperty.put("char", "String");

        mysqlColumnMap2JavaProperty.put("date", "Date");
        mysqlColumnMap2JavaProperty.put("datetime", "Timestamp");
        mysqlColumnMap2JavaProperty.put("decimal", "BigDecimal");
        mysqlColumnMap2JavaProperty.put("double", "Double");

        mysqlColumnMap2JavaProperty.put("enum", "String");

        mysqlColumnMap2JavaProperty.put("float", "Float");

        mysqlColumnMap2JavaProperty.put("int", "Integer");
        mysqlColumnMap2JavaProperty.put("integer", "Integer");


        mysqlColumnMap2JavaProperty.put("longblob", "byte[]");
        mysqlColumnMap2JavaProperty.put("longtext", "String");


        mysqlColumnMap2JavaProperty.put("mediumblob", "byte[]");
        mysqlColumnMap2JavaProperty.put("mediumint", "Integer");
        mysqlColumnMap2JavaProperty.put("mediumtext", "String");

        mysqlColumnMap2JavaProperty.put("numeric", "Integer");

        mysqlColumnMap2JavaProperty.put("real", "Integer");

        mysqlColumnMap2JavaProperty.put("set", "String");
        mysqlColumnMap2JavaProperty.put("smallint", "Integer");

        mysqlColumnMap2JavaProperty.put("text", "String");
        mysqlColumnMap2JavaProperty.put("time", "Time");
        mysqlColumnMap2JavaProperty.put("timestamp", "Timestamp");
        mysqlColumnMap2JavaProperty.put("tinyblob", "byte[]");
        mysqlColumnMap2JavaProperty.put("tinyint", "Integer");
        mysqlColumnMap2JavaProperty.put("tinytext", "String");

        mysqlColumnMap2JavaProperty.put("varbinary", "byte[]");
        mysqlColumnMap2JavaProperty.put("varchar", "String");

        mysqlColumnMap2JavaProperty.put("year", "Date");
    }
}
