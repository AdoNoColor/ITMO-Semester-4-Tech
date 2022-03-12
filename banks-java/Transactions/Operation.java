package Transactions;

public interface Operation {
    String id = null;
    boolean isCancelled = false;
    void cancelOperation();
}
