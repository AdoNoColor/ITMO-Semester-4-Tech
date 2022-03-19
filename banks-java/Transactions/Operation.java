package Transactions;

public interface Operation {
    String id = null;
    Boolean isCancelled = false;
    void cancelOperation();
}
