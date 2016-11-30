package app.component;

/**
 * Created by Ejdems on 26/11/2016.
 */
public class BootstrapAlert {
    private final String type;
    private final String message;

    public BootstrapAlert(String type, String message) {
        this.type = type;
        this.message = message;
    }

    @Override
    public String toString() {
        return "<div class='alert alert-"+type+"' role='alert'>"+message+"</div>";
    }
}
