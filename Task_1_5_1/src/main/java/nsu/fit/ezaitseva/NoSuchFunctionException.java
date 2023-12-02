package nsu.fit.ezaitseva;

/**
 * Exception if function is not included in realized functions
 */
public class NoSuchFunctionException extends Exception{
    public NoSuchFunctionException(String message) {
        super(message);

    }

}