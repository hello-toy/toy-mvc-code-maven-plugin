package org.hellotoy.maven.plugins.code.generation.util;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;


/**
 * The Class Tools.
 */
public class Tools {

    /**
     * Gets the null able.
     *
     * @param nullable the nullable
     * @return the null able
     */
    public static String getNullAble(String nullable) {
        if ("YES".equals(nullable) || "yes".equals(nullable) || "y".equals(nullable) || "Y".equals(nullable)
                || "f".equals(nullable))
            return "Y";
        if ("NO".equals(nullable) || "N".equals(nullable) || "no".equals(nullable) || "n".equals(nullable)
                || "t".equals(nullable))
            return "N";
        else
            return null;
    }

    /**
     * Gets the null string.
     *
     * @param nullable the nullable
     * @return the null string
     */
    public static String getNullString(String nullable) {
        if (StringUtils.isBlank(nullable))
            return "";
        else
            return nullable;
    }

    /**
     * Format field class type.
     *
     * @param columnt the columnt
     */
    /*
     * public static void formatFieldClassType(TableColumn columnt) { String
     * fieldType = columnt.getFieldType(); String scale = columnt.getScale();
     * columnt.setClassType("inputxt"); if ("N".equals(columnt.getNullable()))
     * columnt.setOptionType("*"); if ("datetime".equals(fieldType) ||
     * fieldType.contains("time")) columnt.setClassType("easyui-datetimebox"); else
     * if ("date".equals(fieldType)) columnt.setClassType("easyui-datebox"); else if
     * (fieldType.contains("int")) columnt.setOptionType("n"); else if
     * ("number".equals(fieldType)) { if (StringUtils.isNotBlank(scale) &&
     * Integer.parseInt(scale) > 0) columnt.setOptionType("d"); } else if
     * ("float".equals(fieldType) || "double".equals(fieldType) ||
     * "decimal".equals(fieldType)) columnt.setOptionType("d"); else if
     * ("numeric".equals(fieldType)) columnt.setOptionType("d"); }
     */

    /**
     * 鏍煎紡鍖栫被鍨�.
     *
     * @param dataType  the data type
     * @param precision the precision
     * @param scale     the scale
     * @return the string
     */
    public static String formatDataType(String dataType, String precision, String scale) {
        if (dataType.contains("char"))
            dataType = "String";
        else if (dataType.toUpperCase().contains("INT8")) {
            dataType = "Long";
        }
        else if (dataType.toUpperCase().contains("BIGINT")) {
            dataType = "Long";
        }
        else if (dataType.contains("int"))
            dataType = "Integer";
        else if (dataType.contains("float"))
            dataType = "Float";
        else if (dataType.contains("double"))
            dataType = "Double";
        else if (dataType.contains("number")) {
            if (StringUtils.isNotBlank(scale) && Integer.parseInt(scale) > 0)
                dataType = "BigDecimal";
            else if (StringUtils.isNotBlank(precision) && Integer.parseInt(precision) > 10)
                dataType = "Long";
            else
                dataType = "Integer";
        }
        else if (dataType.contains("decimal"))
            dataType = "BigDecimal";
        else if (dataType.contains("date"))
            dataType = "Date";
        else if (dataType.contains("time"))
            dataType = "Date";
        else if (dataType.contains("clob"))
            dataType = "Clob";
        else if (dataType.contains("numeric"))
            dataType = "BigDecimal";
        else
            dataType = "Object";
        if ("java.lang.Object".equals(dataType)) {
            System.err.println("warn  datatype unkonwn....");
        }
        return dataType;
    }

    /**
     * Format DB type.
     *
     * @param upperCase the upper case
     * @return the string
     */
    public static String formatDBType(String upperCase) {
        if ("NUMBER".equals(upperCase)) {
            return "DECIMAL";
        }
        else if ("INTEGER".equals(upperCase)) {
            return "INTEGER";
        }
        else if ("DOUBLE".equals(upperCase)) {
            return "DECIMAL";
        }
        else if ("LONG".equals(upperCase)) {
            return "DECIMAL";
        }
        else if (upperCase.contains("VARCHAR")) {
            return "VARCHAR";
        }
        else if (upperCase.contains("TEXT")) {
            return "VARCHAR";
        }
        else if (upperCase.equals("CHAR")) {
            return "VARCHAR";
        }
        else if ("BIGINT".equals(upperCase)) {
            return "BIGINT";
        }
        else if ("BPCHAR".equals(upperCase)) {
            return "CHAR";
        }
        else if (upperCase.contains("INT") || upperCase.equals("TINYINT")) {
            return "INTEGER";
        }
        else if (upperCase.startsWith("TIMESTAMP")) {
            return "TIMESTAMP";
        }
        else if (upperCase.startsWith("DATETIME")) {
            return "TIMESTAMP";
        }
        return upperCase;
    }

    /**
     * Format field.
     *
     * @param field the field
     * @return the string
     */
    // 鏍煎紡鍖栧垪鍚� hello_world to helloWorld
    public static String formatField(String field) {
        String strs[] = field.split("_");
        field = "";
        int m = 0;
        for (int length = strs.length; m < length; m++)
            if (m > 0) {
                String tempStr = strs[m];
                tempStr = (new StringBuilder(String.valueOf(tempStr.substring(0, 1).toUpperCase()))).append(
                        tempStr.substring(1, tempStr.length())).toString();
                field = (new StringBuilder(String.valueOf(field))).append(tempStr).toString();
            }
            else {
                field = (new StringBuilder(String.valueOf(field))).append(strs[m]).toString();
            }

        return field;
    }

    public static String formatHumpFieldCapital(String field) {
        if (StringUtils.isEmpty(field)) {
            return field;
        }
        String strs[] = field.split("_");
        field = "";
        int m = 0;
        for (int length = strs.length; m < length; m++) {
            if (m > 0) {
                String tempStr = strs[m];
                tempStr = (new StringBuilder(String.valueOf(tempStr.substring(0, 1).toUpperCase()))).append(
                        tempStr.substring(1, tempStr.length())).toString();
                field = (new StringBuilder(String.valueOf(field))).append(tempStr).toString();
            }
            else {
                field = (new StringBuilder(String.valueOf(field))).append(strs[m]).toString();
            }
        }
        field = (new StringBuilder(String.valueOf(field.substring(0, 1).toLowerCase()))).append(field.substring(1)).toString();
        return field;
    }

    /**
     * Format field capital.
     *
     * @param field the field
     * @return the string
     */
    public static String formatFieldCapital(String field) {
        if (StringUtils.isEmpty(field)) {
            return field;
        }
        String strs[] = field.split("_");
        field = "";
        int m = 0;
        for (int length = strs.length; m < length; m++)
            if (m > 0) {
                String tempStr = strs[m];
                tempStr = (new StringBuilder(String.valueOf(tempStr.substring(0, 1).toUpperCase()))).append(
                        tempStr.substring(1, tempStr.length())).toString();
                field = (new StringBuilder(String.valueOf(field))).append(tempStr).toString();
            }
            else {
                field = (new StringBuilder(String.valueOf(field))).append(strs[m]).toString();
            }

        field = (new StringBuilder(String.valueOf(field.substring(0, 1).toUpperCase()))).append(field.substring(1))
                .toString();
        return field;
    }

    /**
     * Insert.
     *
     * @param orginalFilePath the orginal file path
     * @param content         the content
     * @param positionRow     the position row
     * @throws java.io.IOException Signals that an I/O exception has occurred.
     */
    public static void insert(String orginalFilePath, String content, int positionRow) throws IOException {
        File orginalFile = new File(orginalFilePath);
        BufferedReader br = new BufferedReader(new FileReader(orginalFile));

        File tmp1 = File.createTempFile("tmp1", null);
        BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tmp1), "UTF-8"));

        File tmp2 = File.createTempFile("tmp2", null);
        BufferedWriter bw2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tmp2), "UTF-8"));

        int countRows = 0;
        String line = "";
        while ((line = br.readLine()) != null) {
            if (++countRows >= positionRow) {
                if (countRows == positionRow) {
                    bw2.write(content);
                }
                bw2.write(line + "\n");
                bw2.flush();
            }
            else {
                bw1.write(line + "\n");
                bw1.flush();
            }

        }

        BufferedWriter targetBufferWriter = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(orginalFile), "UTF-8"));

        BufferedReader br1 = new BufferedReader(new FileReader(tmp1));
        line = "";
        while ((line = br1.readLine()) != null) {
            targetBufferWriter.write(line + "\n");
            targetBufferWriter.flush();
        }
        BufferedReader br2 = new BufferedReader(new FileReader(tmp2));
        line = "";
        while ((line = br2.readLine()) != null) {
            targetBufferWriter.write(line + "\n");
            targetBufferWriter.flush();
        }

        try {
            br.close();
            bw1.close();
            bw2.close();
            br1.close();
            br2.close();
            targetBufferWriter.close();
            tmp1.delete();
            tmp2.delete();
        }
        catch (Exception e) {
        }
    }

}
