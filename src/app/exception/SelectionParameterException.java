package app.exception;

/**
 * Created by Ejdems on 26/11/2016.
 */
public class SelectionParameterException extends ParameterException {
    public SelectionParameterException(String name) {
        super(name + " has been submited with incorect value!");
    }
}
