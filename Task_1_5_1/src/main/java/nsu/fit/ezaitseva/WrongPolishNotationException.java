package nsu.fit.ezaitseva;


/**
 * Exception if there are other elements in stack or no pop possibility (wrong Polish Notation)
 */
public class WrongPolishNotationException extends Exception {
    public WrongPolishNotationException(String message) {
        super(message);
    }
}