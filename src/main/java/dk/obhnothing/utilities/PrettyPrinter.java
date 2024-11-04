package dk.obhnothing.utilities;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Predicate;

/*
 * Cph Business School....
 * Datamatiker 3. sem.....
 * -----------------------
 * Oskar Bahner Hansen....
 * cph-oh82@cphbusiness.dk
 * 2024-11-04.............
 * -----------------------
 */

public class PrettyPrinter
{

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void withColor(String str, String col)
    {
        System.out.print(col + str + ANSI_RESET);
    }

    public static void arr2d(int[][] arr, Predicate<Integer> highlightCond)
    {
        int maxelem = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++)
            for (int j = 0; j < arr.length; j++)
                maxelem = (arr[i][j] > maxelem) ? arr[i][j] : maxelem;
        int digits = (int)Math.log10(Math.abs(maxelem == 0 ? 1 : maxelem)) + 1;
        System.out.printf("[");
        for (int i = 0; i < arr.length; i++) {
            if (i > 0)
                System.out.printf(" ");
            for (int j = 0; j < arr[0].length; j++) {
                int element = arr[i][j];
                if (highlightCond.test(element))
                    System.out.printf("%s", ANSI_RED);

                String eleStr = String.valueOf(element);
                eleStr = " ".repeat(digits - eleStr.length()) + eleStr;
                System.out.printf("%s", eleStr);

                if (highlightCond.test(element))
                    System.out.printf("%s", ANSI_RESET);
                if (j < arr[0].length - 1)
                    System.out.printf(",");
            }
            if (i < arr.length - 1)
                System.out.printf("%n");
            else
                System.out.printf("]");
        }
    }

    public static void PrintObjectTable(List<?> emps)
    {
        if (emps == null || emps.size() < 1)
            return;
        Class<?> cl = emps.get(0).getClass();
        String nameFull = cl.getName().substring(cl.getName().lastIndexOf(".") + 1, cl.getName().length());
        String nameSpace = cl.getName().substring(0, cl.getName().lastIndexOf("."));
        System.out.println();
        PrettyPrinter.withColor("*** Printing " + nameFull + " table (from: " + nameSpace + " )"    + " ***", ANSI_YELLOW);
        System.out.println();

        Field fields[] = cl.getFields();
        if (fields.length < 1) {
            System.out.println("this class has no public fields...");
        }
        for (int i = 0; i < fields.length; i++) {
            PrettyPrinter.withColor(fields[i].getName() + "\t", ANSI_BLUE);
        }
        for (Object e : emps) {
            System.out.println();
            for (int i = 0; i < fields.length; i++) {
                try {
                    if (fields[i].get(e) == null)
                        continue;
                    String fValStr = fields[i].get(e).toString();
                    int maxLen = fields[i].getName().length();
                    int nPads = maxLen - fValStr.length();
                    String padStr = " ".repeat(Math.max(nPads, 0));
                    System.out.print(fValStr.substring(0, Math.min(fValStr.length(), maxLen)) + padStr + "\t");
                } catch (org.hibernate.LazyInitializationException | IllegalArgumentException | IllegalAccessException e1) {
                }
            }
        }
        System.out.println();
    }
}
