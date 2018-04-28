package com.overc_i3.mars.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Mars on 2017-10-21.
 */

public class StringUtils {
    private StringUtils() {
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || "".equals(str.trim());
    }

    public static boolean isIdFixed(String ids, String id) {
        return !isNullOrEmpty(ids) && !isNullOrEmpty(id) && (ids.startsWith(id + "_") || ids.indexOf("_" + id + "_") != -1 || ids.endsWith("_" + id) || compare(ids, id) == 0);
    }

    public static String iddReplace(String ids, String id) {
        if(!isNullOrEmpty(ids) && !isNullOrEmpty(id)) {
            ids = ids.replace("_" + id + "_", "_");
            if(ids.startsWith(id + "_")) {
                ids = ids.substring((id + "_").length());
            }

            if(ids.endsWith("_" + id)) {
                ids = ids.substring(0, ids.lastIndexOf("_" + id));
            }
        }

        return ids;
    }

    public static boolean isIdLeftFixed(String ids, String id) {
        return ids != null && (ids.startsWith(id + "_") || ids.indexOf("#" + id + "_") != -1);
    }

    public static boolean isApproxMatched(String src, String target) {
        if(!isNullOrEmpty(src) && !isNullOrEmpty(target)) {
            src = src.toLowerCase();
            target = target.toLowerCase();
            if(src.indexOf(target) != -1) {
                return true;
            }
        }

        return false;
    }

    public static int compare(String str1, String str2) {
        return str1 == null?(str2 == null?0:-1):(str2 == null?1:str1.compareTo(str2));
    }

    public static int compareIgnoreCase(String str1, String str2) {
        if(str1 == null) {
            return str2 == null?0:-1;
        } else if(str2 == null) {
            return 1;
        } else {
            str1 = str1.toLowerCase();
            str2 = str2.toLowerCase();
            return str1.compareTo(str2);
        }
    }

    public static int toInt(Object obj, int defaultValue) {
        if(obj != null) {
            if(obj instanceof Integer) {
                return ((Integer)obj).intValue();
            }

            try {
                return Integer.parseInt(obj.toString().trim());
            } catch (Exception var3) {
                var3.printStackTrace();
            }
        }

        return defaultValue;
    }

    public static float toFloat(String str, float defaultValue) {
        if(!isNullOrEmpty(str)) {
            try {
                return Float.parseFloat(str.trim());
            } catch (Exception var3) {
                var3.printStackTrace();
            }
        }

        return defaultValue;
    }

    public static double toDouble(String str, double defaultValue) {
        if(!isNullOrEmpty(str)) {
            try {
                return Double.parseDouble(str.trim());
            } catch (Exception var4) {
                var4.printStackTrace();
            }
        }

        return defaultValue;
    }

    public static long toLong(String str, long defaultValue) {
        if(!isNullOrEmpty(str)) {
            try {
                return Long.parseLong(str.trim());
            } catch (Exception var4) {
                var4.printStackTrace();
            }
        }

        return defaultValue;
    }

    public static boolean toBoolean(Object str, boolean defaultValue) {
        if(str != null) {
            try {
                return Boolean.parseBoolean(str.toString().trim());
            } catch (Exception var3) {
                var3.printStackTrace();
            }
        }

        return defaultValue;
    }

    public static List<String> toList(String str, String delim) {
        ArrayList list = new ArrayList();
        if(!isNullOrEmpty(str) && !isNullOrEmpty(delim)) {
            str = str.trim();
            StringTokenizer stk = new StringTokenizer(str, delim);

            while(stk.hasMoreTokens()) {
                String spStr = stk.nextToken();
                if(!isNullOrEmpty(spStr)) {
                    list.add(spStr);
                }
            }
        }

        return list;
    }

    public static String[] toArray(String str, String delim) {
        List list = toList(str, delim);
        if(!list.isEmpty()) {
            int size = list.size();
            String[] arr = new String[size];
            int c = 0;
            Iterator i$ = list.iterator();

            while(i$.hasNext()) {
                String cstr = (String)i$.next();
                if(!isNullOrEmpty(cstr)) {
                    arr[c++] = cstr;
                }
            }

            return arr;
        } else {
            return null;
        }
    }

    public static String[] toArray(List<String> strList) {
        if(strList != null && !strList.isEmpty()) {
            int size = strList.size();
            String[] arr = new String[size];
            int c = 0;
            Iterator i$ = strList.iterator();

            while(i$.hasNext()) {
                String cstr = (String)i$.next();
                if(!isNullOrEmpty(cstr)) {
                    arr[c++] = cstr;
                }
            }

            return arr;
        } else {
            return null;
        }
    }

    public static Map<String, String> toMap(String str, String delim) {
        HashMap map = new HashMap();
        if(!isNullOrEmpty(str) && !isNullOrEmpty(delim)) {
            str = str.trim();
            StringTokenizer stk = new StringTokenizer(str, delim);

            while(stk.hasMoreTokens()) {
                String spStr = stk.nextToken();
                if(!isNullOrEmpty(spStr)) {
                    map.put(spStr, spStr);
                }
            }
        }

        return map;
    }

    public static String getString(byte[] data) {
        return getString(data, "UTF-8");
    }

    public static String getString(byte[] data, String encoding) {
        try {
            return new String(data, encoding);
        } catch (UnsupportedEncodingException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static byte[] getBytes(String str, String encoding) {
        if(str == null) {
            str = "";
        }

        try {
            return str.getBytes(encoding);
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

//    public static String getCellValue(Cell cell) {
//        if(null == cell) {
//            return "";
//        } else {
//            switch(cell.getCellType()) {
//                case 0:
//                    if(DateUtil.isCellDateFormatted(cell)) {
//                        return com.dev.ncu.util.DateUtil.format(cell.getDateCellValue(), "yyyyMMddHHmmssSSS");
//                    }
//
//                    return cell.getNumericCellValue() + "";
//                case 1:
//                    return cell.getRichStringCellValue().getString().trim();
//                case 2:
//                case 3:
//                default:
//                    return "";
//                case 4:
//                    return cell.getBooleanCellValue() + "";
//            }
//        }
//    }
//
//    public static boolean isCorrectTemplate(Row firstRow, String[] titles) {
//        for(int i = 0; i < titles.length; ++i) {
//            Cell cell = firstRow.getCell(i);
//            String title = getCellValue(cell);
//            if(!titles[i].equals(title)) {
//                return false;
//            }
//        }
//
//        return true;
//    }
//
//    public static boolean isEmptyRow(Row row, int columnLength) {
//        if(null == row) {
//            return true;
//        } else {
//            for(int i = 0; i < columnLength; ++i) {
//                Cell cell = row.getCell(i);
//                String value = getCellValue(cell);
//                if(!"".equals(value)) {
//                    return false;
//                }
//            }
//
//            return true;
//        }
//    }

    public static boolean isTelephone(String phone) {
        Pattern pattern = Pattern.compile("[0-9()（）+-]{1,20}");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    public static boolean isStrLengthGtN(String string, int n) {
        return string.length() > n;
    }

    public static byte[] getBytes(String str) {
        return getBytes(str, "UTF-8");
    }

    public static String toString(Object object) {
        return object != null?object.toString():"";
    }

    public static String toUtf8String(String string) {
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if(c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = Character.toString(c).getBytes("utf-8");
                } catch (Exception var7) {
                    var7.printStackTrace();
                    b = new byte[0];
                }

                for(int j = 0; j < b.length; ++j) {
                    int k = b[j];
                    if(k < 0) {
                        k += 256;
                    }

                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }

        return sb.toString();
    }

    public static String format1(String str) {
        if(str == null) {
            return null;
        } else {
            str = str.replace(" ", "");
            str = str.replace("\'", "");
            str = str.replace("\"", "");
            return str;
        }
    }

    public static String format2(String str, String newseparator, String oldseparator) {
        if(str == null) {
            return null;
        } else if(str.indexOf("\'") != -1) {
            return str;
        } else {
            str = str.replace(" ", "");
            str = str.replace(oldseparator, newseparator);
            return str;
        }
    }

    public static String format3(String str, String suffix) {
        return str != null && str.endsWith(suffix)?str.substring(0, str.length() - 1):str;
    }

    public static String intToInsql(String str, String separator) {
        if(isNullOrEmpty(str)) {
            return str;
        } else {
            str = str.replace(" ", "");
            str = str.replace("\'", "");
            str = str.replace("\"", "");
            if(str.endsWith(separator)) {
                str = str.substring(0, str.length() - 1);
            }

            return str;
        }
    }

    public static String strToInsql(String str, String separator) {
        if(isNullOrEmpty(str)) {
            return str;
        } else {
            str = str.replace(" ", "");
            str = str.replace("\'", "");
            str = str.replace("\"", "");
            if(str.endsWith(separator)) {
                str = str.substring(0, str.length() - 1);
            }

            str = str.replace(separator, "\',\'");
            str = "\'" + str + "\'";
            return str;
        }
    }

    public static List<Integer> toIntList(String str, String delim) {
        List list = toList(str, delim);
        ArrayList intlist = new ArrayList();
        if(!list.isEmpty()) {
            Iterator i$ = list.iterator();

            while(i$.hasNext()) {
                String cstr = (String)i$.next();
                if(!isNullOrEmpty(cstr)) {
                    intlist.add(Integer.valueOf(toInt(cstr, 0)));
                }
            }

            return intlist;
        } else {
            return null;
        }
    }

    public static List<String> getIntersection(List<String> strList1, List<String> strList2) {
        if(strList1 != null && !strList1.isEmpty() && strList2 != null && !strList2.isEmpty()) {
            ArrayList resList = new ArrayList();
            Iterator i$ = strList1.iterator();

            while(true) {
                while(i$.hasNext()) {
                    String str1 = (String)i$.next();
                    Iterator i$1 = strList2.iterator();

                    while(i$1.hasNext()) {
                        String str2 = (String)i$1.next();
                        if(str1.equals(str2)) {
                            resList.add(str1);
                            break;
                        }
                    }
                }

                return resList;
            }
        } else {
            return null;
        }
    }

    public static String stringToSql(String string, String table, String variable, int type) {
        if(isNullOrEmpty(string)) {
            return "";
        } else if(string.indexOf(";") != -1) {
            return "";
        } else {
            StringBuffer sb = new StringBuffer();
            List list = toList(string, ",");
            int size = list.size();
            variable = table + "." + variable;
            sb.append("(");

            for(int i = 0; i < size; ++i) {
                if(i != 0) {
                    sb.append(" or ");
                }

                sb.append(variable);
                sb.append(" = ");
                if(type == 1 && string.indexOf("\'") == -1) {
                    sb.append("\'");
                }

                sb.append(((String)list.get(i)).trim());
                if(type == 1 && string.indexOf("\'") == -1) {
                    sb.append("\'");
                }
            }

            sb.append(")");
            return sb.toString();
        }
    }

    public static String trim(String str) {
        if(str != null && str != "") {
            str = str.replace(" ", "");
            return str;
        } else {
            return null;
        }
    }

    public static String getFenxiaoRight(String params, String right) {
        if(isNullOrEmpty(params)) {
            if(right == "all") {
                params = "";
            } else if(isNullOrEmpty(right)) {
                params = "0";
            } else {
                params = right;
            }
        }

        return params;
    }
}
