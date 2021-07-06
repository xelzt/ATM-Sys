import java.io.*;

public class PersonalAccount extends Account implements Operation, Serializable{

    private String password;
    private int clientNumber;

    public PersonalAccount(String name, String surname, int clientNumber, String password, double ballance){
        super(name, surname, ballance);
        this.password = password;
        this.clientNumber = clientNumber;
    }

    public int getClientNumber() {
        return clientNumber;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public void addToBalance(double amount) throws badAmountException {
        if(amount%10 == 0)
            this.setBallance(this.getBallance() + amount);
        else
            throw new badAmountException();
    }

    @Override
    public void withdraw(double amount) throws tooLittleException, badAmountException {

        if(amount%10 == 0){
            if(this.getBallance() > amount) {
                this.setBallance(this.getBallance() - amount);
            }
            else
                throw new tooLittleException();
        }
        else
            throw new badAmountException();

    }



}

class noMatchException extends Exception {
    public noMatchException() {
        super("Błędny login lub hasło");
    }
}

class tooLittleException extends Exception {
    public tooLittleException() {
        super("Niewystarczajaca ilosc srodkow na koncie");
    }
}

class existingAccountException extends Exception {
    public existingAccountException() {
        super("Konto juz istnieje");
    }
}

class blankFieldException extends Exception {
    public blankFieldException() { super("Nie wszystkie pola zostały wypełnione!"); }
}

class badAmountException extends Exception{
    public badAmountException() { super("Kwota jest nieprawidłowa!");}
}