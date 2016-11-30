package app.exception;

/**
 * Created by Ejdems on 26/11/2016.
 */
public abstract class ParameterException extends Exception{
    public ParameterException(String message) {
        super("Parameter "+message);
    }
}
