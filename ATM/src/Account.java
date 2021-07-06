public abstract class Account {
    private String name;
    private String surname;
    private double ballance;

    public Account(String name, String surname, double ballance) {
        this.name = name;
        this.surname = surname;
        this.ballance = ballance;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setBallance(double ballance) {
        this.ballance = ballance;
    }

    public double getBallance() {
        return this.ballance;
    }



}