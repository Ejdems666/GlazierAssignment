package app.exception;

/**
 * Created by Ejdems on 26/11/2016.
 */
public class NumberParameterException extends ParameterException {
    public NumberParameterException(String name) {
        super(name+" must be a number!");
    }
}
