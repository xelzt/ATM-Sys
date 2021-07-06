
public interface Operation {
    void addToBalance(double amount) throws badAmountException;
    void withdraw(double amount) throws tooLittleException, badAmountException;
}
