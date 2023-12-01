package nsu.fit.ezaitseva;

public class Main {
    public static void main(String[] args) throws NoSuchFunctionException, WrongPolishNotationException {
        Calculator calculator = new Calculator();
        System.out.println(calculator.calculateResult("sin + - 1 2 1"));
    }
}