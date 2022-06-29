package Tools;

public class BanksException extends RuntimeException {
    public BanksException(String message) {
        super(message);
    }
    public BanksException(String message, Exception innerException) { super(message, innerException); }

}
