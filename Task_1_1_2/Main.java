package Task_1_1_2;

import java.util.Scanner;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws Exception{
        Scanner scan = new Scanner(System.in);
        String strCoef1 = scan.nextLine();
        int[] polyCoef1 = (strCoef1 != "") ?
                Arrays.stream(strCoef1.split(", ")).mapToInt(Integer::parseInt).toArray() : new int[]{};
        Polynomial polynomial1 = new Polynomial(polyCoef1);

        String strCoef2 = scan.nextLine();
        int[] polyCoef2 = (strCoef2 != "") ?
                Arrays.stream(strCoef2.split(", ")).mapToInt(Integer::parseInt).toArray() : new int[]{};
        Polynomial polynomial2 = new Polynomial(polyCoef2);

        System.out.println(polynomial2.differentiate(1).turnToString());
        System.out.println(polynomial1.times(polynomial2).evaluate(2));
    }
}