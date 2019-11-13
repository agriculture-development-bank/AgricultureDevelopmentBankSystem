package com.casic.common.utils;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHelper {

    /**
     * 将驼峰式命名的字符串转换为下划线大写方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。</br>
     * 例如：HelloWorld->HELLO_WORLD
     * @param name 转换前的驼峰式命名的字符串
     * @return 转换后下划线大写方式命名的字符串
     */
    public static String underscoreName(String name) {
        StringBuilder result = new StringBuilder();
        if (name != null && name.length() > 0) {
            // 将第一个字符处理成大写
            result.append(name.substring(0, 1).toUpperCase());
            // 循环处理其余字符
            for (int i = 1; i < name.length(); i++) {
                String s = name.substring(i, i + 1);
                // 在大写字母前添加下划线
                if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
                    result.append("_");
                }
                // 其他字符直接转成大写
                result.append(s.toUpperCase());
            }
        }
        return result.toString();
    }

    /**
     * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。</br>
     * 例如：HELLO_WORLD->HelloWorld
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String camelName(String name) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (name == null || name.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!name.contains("_")) {
            // 不含下划线，仅将首字母小写
            return name.substring(0, 1).toLowerCase() + name.substring(1);
        }
        // 用下划线将原始字符串分割
        String camels[] = name.split("_");
        for (String camel :  camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 处理真正的驼峰片段
            if (result.length() == 0) {
                // 第一个驼峰片段，全部字母都小写
                result.append(camel.toLowerCase());
            } else {
                // 其他的驼峰片段，首字母大写
                result.append(camel.substring(0, 1).toUpperCase());
                result.append(camel.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

    /**
     * 首字母转小写
     * @param s 需要转换的字符串
     * @return
     */
    public static String toLowerCaseFirstOne(String s){
        if(Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return Character.toLowerCase(s.charAt(0)) + s.substring(1);
    }

    /**
     * 首字母转大写
     * @param s 需要转换的字符串
     * @return
     */
    public static String toUpperCaseFirstOne(String s){
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    public static String convert(String humpWords) {
        // 初始化过滤的字符 start
        char[] fiterCharArray = "abcdefghigklmnopqrstuvwxyz.0123456789".toCharArray();
        Arrays.sort(fiterCharArray);
        // 初始化过滤的字符 end

        // 初始化要反回的char数组,双倍长度,因为要以下划线分割，所以长度必须大于原字符串长度，这里取双倍，确保够用
        char[] ch = new char[humpWords.length() * 2];

        for (int i = 0, j = 0; i < humpWords.length(); i++, j++) {
            if (Arrays.binarySearch(fiterCharArray, humpWords.charAt(i)) > -1) {
                ch[j] = (char) (humpWords.charAt(i));// 在声明的过滤字符数组里面的字符直接赋值
            } else {
                ch[j] = '_';
                ch[j + 1] = (char) (humpWords.charAt(i) + ' ');// 不在声明的过滤字符数组里面的字符则认为该字符是大写字符，大写字符先添加下划线，再将字符加上空格转为小写赋值
                j++;
            }
        }
        return new String(ch).trim();
    }

    private static Pattern linePattern = Pattern.compile("_(\\w)");
    /**下划线转驼峰*/
    public static String lineToHump(String str){
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()){
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
    /**驼峰转下划线(简单写法，效率低于{@link #humpToLine2(String)})*/
    public static String humpToLine(String str){
        return str.replaceAll("[A-Z]", "_$0").toLowerCase();
    }
    private static Pattern humpPattern = Pattern.compile("[A-Z]");
    /**驼峰转下划线,效率比上面高*/
    public static String humpToLine2(String str){
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()){
            matcher.appendReplacement(sb, "_"+matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
    public static void main(String[] args) {
        String lineToHump = lineToHump("f_parent_no_leader");
        System.out.println(lineToHump);//fParentNoLeader
        System.out.println(humpToLine(lineToHump));//f_parent_no_leader
        System.out.println(humpToLine2(lineToHump));//f_parent_no_leader
    }
}
