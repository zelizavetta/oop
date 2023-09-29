package org.example;

import java.util.Arrays;

class Polynomial {
    int n;
    int[] coef;

    /**
     * init constructor for polynomial
     * @param inputCoef - array of the coefficients of the polynomial
     */
    Polynomial(int[] inputCoef) {
        coef = inputCoef.clone();
        n = coef.length;
    }

    /**
     * method equals for comparing two objects
     * @param obj - another object to compare
     * @return true if equal, false if not
     */
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

    /**
     * hashCode method depends on prime number and power of the polynomial
     * @return hash for key
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((coef.length == 0) ? 0 : coef.hashCode());
        return result;
    }

    /**
     * method oneTypePoly: adding zeros to start of the array to make the lengths of the both arrays equal
     * for example to make from [1, 2, 3] and [3] -> [1, 2, 3], [0, 0, 3]
     * @param p - polynomial
     * @param size - size of the array
     * @return new polynomial with zeros
     */
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

    /**
     * method for summing two polynomials
     * @param other - the other object for sum
     * @return sum polynomial of two polynomials
     */
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

    /**
     * method for sub two polynomials
     * @param other - the other object for sub
     * @return sub polynomial of two polynomials
     */
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

    /**
     * method for multiply two polynomials
     * @param other - the other object for multiply
     * @return multiply polynomial of two polynomials
     */
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

    /**
     * method for substitution value x in polynomial
     * @param x - int value to substitute in polynomial
     * @return int value - result of substitution
     */
    public int evaluate(int x) {
        int res = 0;
        for (int i = 0; i < this.n; i++) {
            res += this.coef[i] * Math.pow(x, this.n - 1 - i);
        }
        return res;
    }

    /**
     * method for differentiate the polynomial
     * @param powDiff - power for differentiate
     * @return new polynomial - the result of differentiation
     * @throws Exception if powDiff < 0
     */
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

    /**
     * method that turns the array of the coefficients to a formatted string of coefficients
     * @return formatted string of coefficients
     */
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

