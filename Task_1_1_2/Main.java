package Task_1_1_2;

import java.util.Scanner;
import java.util.Arrays;

class Polynomial {
    int n;
    int[] coef;

    Polynomial(int[] inputCoef) {
        coef = inputCoef.clone();
        n = coef.length;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Polynomial poly = (Polynomial) obj;
        return this.n == poly.n && Arrays.equals(this.coef, poly.coef);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((coef.length == 0) ? 0 : coef.hashCode());
        return result;
    }

    public Polynomial oneTypePoly(Polynomial p, int size) {
        int[] newArr = new int[size];
        for (int i = 0; i < size - p.n; i++) {
            newArr[i] = 0;
        }
        for (int i = 0; i < p.n; i++) {
            newArr[i + size - p.n] = p.coef[i];
        }
        Polynomial newP = new Polynomial(newArr);
        return newP;
    }

    public Polynomial plus(Polynomial other) {
        int maxN = Math.max(this.n, other.n);
        Polynomial newP1 = oneTypePoly(this, maxN);
        Polynomial newP2 = oneTypePoly(other, maxN);
        int[] newCoef = new int[maxN];
        for (int i = 0; i < maxN; i++) {
            newCoef[i] = newP1.coef[i] + newP2.coef[i];
        }
        Polynomial sumPol = new Polynomial(newCoef);
        return sumPol;
    }

    public Polynomial minus(Polynomial other) {
        int maxN = Math.max(this.n, other.n);
        Polynomial newP1 = oneTypePoly(this, maxN);
        Polynomial newP2 = oneTypePoly(other, maxN);
        int[] newCoef = new int[maxN];
        for (int i = 0; i < maxN; i++) {
            newCoef[i] = newP1.coef[i] - newP2.coef[i];
        }
        Polynomial subPol = new Polynomial(newCoef);
        return subPol;
    }

    public Polynomial times(Polynomial other) {
        if (this.n == 0) {
            return new Polynomial(new int[]{});
        }
        int[] multCoef = new int[this.n + other.n - 1];
        Arrays.fill(multCoef, 0);
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < other.n; j++) {
                multCoef[i + j] += this.coef[i] * other.coef[j];
            }
        }
        Polynomial multPol = new Polynomial(multCoef);
        return multPol;
    }

    public int evaluate(int x) {
        int res = 0;
        for (int i = 0; i < this.n; i++) {
            res += this.coef[i] * Math.pow(x, this.n - 1 - i);
        }
        return res;
    }

    public Polynomial differentiate(int powDiff) throws Exception{
        if (powDiff < 0) {
            throw new Exception("pow of differentiate is < 0");
        }
        if (this.n == 0) {
            return new Polynomial(new int[]{});
        }
        int[] diffCoef = Arrays.copyOfRange(this.coef, 0, this.n - powDiff);
        for (int i = 0; i < powDiff; i++) {
            for (int j = 0; j < this.n - powDiff; j++) {
                diffCoef[j] *= (this.n - 1 - j - i);
            }
        }
        Polynomial diffPol = new Polynomial(diffCoef);
        return diffPol;
    }

    public String turnToString() {
        String strPoly = "";
        for (int i = 0; i < this.n; i++) {
            int elem = this.coef[i];
            int pow = this.n - 1 - i;
            String xPow = "";
            if (pow == 1) {
                xPow += "x";
            } else if (pow > 1) {
                xPow += "x^" + pow;
            }
            if (strPoly != "") {
                if (elem > 1 | elem < -1) {
                    strPoly += (elem > 0) ? " + " + elem + xPow : " - " + Math.abs(elem) + xPow;
                } else if (elem != 0) {
                    strPoly += (elem > 0) ? " + " + xPow: " - " + xPow;
                }
            } else if (strPoly == "") {
                if (elem > 1 | elem < -1) {
                    strPoly += (elem > 0) ? elem + xPow : "-" + (-elem) + xPow;
                } else if (elem != 0) {
                    strPoly += (elem > 0) ? xPow: "-" + xPow;
                }
            }
            if (i == this.n - 1 && Math.abs(elem) == 1) {
                strPoly += Math.abs(elem);
            }
        }

        return strPoly;
    }
}

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