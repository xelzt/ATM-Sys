import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public class MainFrame extends JFrame {

    JPanel cards;
    final static String LOGINPANEL = "Card with login panel";
    final static String REGISTERPANEL = "Card with register panel";
    final static String MENUPANEL = "Card with menu panel";
    final static String PAYMENTPANEL = "Card with payment panel";
    final static String PAYOFFPANEL = "Card with payoff panel";
    final static String ADMINLOGINPANEL = "Card with admin login panel";
    final static String ADPANEL = "Card with admin panel";

    private JLabel lLogin, lPassword;
    private JTextField login, tfRegName, tfRegSurname, tfpayment, tfpayoff, tfAdmin;
    private JPasswordField password, pfRegPassword;
    private JButton bLog, bReg, bAccReg, paymentOButton, checkBallance,bAdmin, paymentButton, payoffButton,backNButton, paymentJButton, backButton, backToLoginButton, adminLoginButton;
    private ButtonListener buttonListener;
    private String name, surname, passwd;
    private double ballance;
    private PersonalAccount pa;

    private Date date;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    private String allOperations = "";

    public MainFrame(String title) throws IOException {
        super(title);
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dim = tk.getScreenSize();
        setSize(dim.width/2, dim.height/2);
        setLocation(dim.width/4, dim.height/4);
        addWindowListener(new WindowClosingAdapter());
        addComponentToPane(getContentPane());
        setVisible(true);
    }

    //PRZY ZAMYKANIU OKNA ZAPISUJE WSZYSTKIE ZMIANY DO PLIKU
    class WindowClosingAdapter extends WindowAdapter{
        @Override
        public void windowClosing(WindowEvent e) {
            if(JOptionPane.showOptionDialog(e.getWindow(),"Czy chcesz zamknąć aplikację ?","Potwierdzenie",
                    JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,new String[] {"Tak","Nie"},"Nie")==0){
                new saveToFile();
                System.exit(0);
            }

        }
    }

    //ZMIANA JPANELÓW NA INNE
    public void addComponentToPane(Container pane) {

        //PANEL LOGOWANIA (WSTĘPNY PANEL)
        JPanel panel = new JPanel();
        JPanel loginPanel = new JPanel();
        lLogin = new JLabel("Login");
        login = new JTextField(20);
        lPassword = new JLabel("Hasło");
        password = new JPasswordField(20);
        bLog = new JButton(new LoginLogic());
        bReg = new JButton("Zarejestruj się");
        bAdmin = new JButton("Panel administratora");
        loginPanel.add(lLogin);
        loginPanel.add(login);
        loginPanel.add(lPassword);
        loginPanel.add(password);
        loginPanel.add(bLog);
        loginPanel.add(bReg);
        buttonListener = new ButtonListener();
        bReg.addActionListener(buttonListener);
        bAdmin.addActionListener(buttonListener);


        //PANEL REJESTRACJI
        JPanel registerPanel = new JPanel();
        tfRegName = new JTextField(20);
        tfRegSurname = new JTextField(20);
        pfRegPassword = new JPasswordField(20);
        bAccReg = new JButton(new RegisterLogic());
        registerPanel.add(new JLabel("Imię"));
        registerPanel.add(tfRegName);
        registerPanel.add(new JLabel("Nazwisko"));
        registerPanel.add(tfRegSurname);
        registerPanel.add(new JLabel("Hasło"));
        registerPanel.add(pfRegPassword);
        registerPanel.add(bAccReg);

        //PANEL MENU
        JPanel menuPanel = new JPanel();
        checkBallance = new JButton(new checkBallance());
        menuPanel.add(checkBallance);
        paymentButton = new JButton("Wpłać pieniądze");
        menuPanel.add(paymentButton);
        payoffButton = new JButton("Wypłać pieniądze");
        menuPanel.add(payoffButton);
        backToLoginButton= new JButton("Powrót do logowania");
        menuPanel.add(backToLoginButton);
        paymentButton.addActionListener(buttonListener);
        payoffButton.addActionListener(buttonListener);
        backToLoginButton.addActionListener(buttonListener);

        //PANEL WPŁACANIA
        JPanel paymentPanel = new JPanel();
        tfpayment = new JTextField(5);
        paymentPanel.add(new JLabel("Kwota:"));
        paymentPanel.add(tfpayment);
        paymentJButton = new JButton(new paymentLogic());
        paymentPanel.add(paymentJButton);
        backButton = new JButton("Powrót");
        paymentPanel.add(backButton);
        paymentJButton.addActionListener(buttonListener);
        backButton.addActionListener(buttonListener);
        bAdmin = new JButton("Panel administratora");

        //PANEL WYPŁACANIA
        JPanel payoffPanel = new JPanel();
        tfpayoff = new JTextField(5);
        payoffPanel.add(new JLabel("Kwota:"));
        payoffPanel.add(tfpayoff);
        paymentOButton = new JButton(new payoffLogic());
        payoffPanel.add(paymentOButton);
        backNButton = new JButton("Powrót");
        payoffPanel.add(backNButton);
        paymentOButton.addActionListener(buttonListener);
        backNButton.addActionListener(buttonListener);

        JPanel adminLoginPanel = new JPanel();
        tfAdmin = new JPasswordField(15);
        adminLoginPanel.add(new JLabel("Hasło Administratora"));
        adminLoginPanel.add(tfAdmin);
        adminLoginButton = new JButton(new LoginAdmin());
        adminLoginPanel.add(adminLoginButton);
        adminLoginButton.addActionListener(buttonListener);

        JPanel adPanel = new JPanel();

        //DODAWANIE PANELI
        cards = new JPanel(new CardLayout());
        cards.add(loginPanel, LOGINPANEL);
        cards.add(registerPanel, REGISTERPANEL);
        cards.add(menuPanel, MENUPANEL);
        cards.add(paymentPanel, PAYMENTPANEL);
        cards.add(payoffPanel, PAYOFFPANEL);
        cards.add(adminLoginPanel, ADMINLOGINPANEL);
        cards.add(adPanel, ADPANEL);

        pane.add(panel, BorderLayout.PAGE_START);
        pane.add(cards, BorderLayout.CENTER);


    }

    //W ZALEŻNOŚCI OD KLIKNIĘTEGO PRZYCISKU ZMIANA PANELU
    class ButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = (JButton) e.getSource();
            if(clickedButton == bReg){
                CardLayout c = (CardLayout) (cards.getLayout());
                c.show(cards, REGISTERPANEL);
            }else if(clickedButton == paymentButton){
                CardLayout c = (CardLayout) (cards.getLayout());
                c.show(cards, PAYMENTPANEL);
            }else if(clickedButton == payoffButton){
                CardLayout c = (CardLayout) (cards.getLayout());
                c.show(cards, PAYOFFPANEL);
            }else if(clickedButton == backButton){
                CardLayout c = (CardLayout) (cards.getLayout());
                c.show(cards, MENUPANEL);
            }else if(clickedButton == backNButton){
                CardLayout c = (CardLayout) (cards.getLayout());
                c.show(cards, MENUPANEL);
            }else if(clickedButton == backToLoginButton){
                new saveToFile();
                try {
                    new saveTransaction();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                CardLayout c = (CardLayout) (cards.getLayout());
                c.show(cards, LOGINPANEL);
            }
        }
    }

    class RegisterLogic extends AbstractAction{
        public RegisterLogic(){
            putValue(Action.NAME, "Zarejestruj się");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Random random = new Random();
            String gName, gSurname, gClientNumber;
            String pass;
            gName = tfRegName.getText();
            gSurname = tfRegSurname.getText();
            pass = String.valueOf(pfRegPassword.getPassword());
            //GENEROWANIE I SPRAWDZANIE CZY NUMER KONTA ZNAJDUJE SIĘ W BAZIE DANYCH
            int clientNumber = random.nextInt((9999 - 100) + 1) + 10;
            gClientNumber = String.valueOf(clientNumber);
            try{
                File f = new File("Accounts.txt");
                FileReader in = new FileReader("Accounts.txt");

                if(gName.length() < 3 || gSurname.length() < 3 || pass.length() < 3)
                    throw new blankFieldException();

                FileOutputStream out = new FileOutputStream(f, true);
                PrintWriter pw = new PrintWriter(out);
                pw.append("\n" + gName + "," + gSurname + "," + gClientNumber + "," + pass + "," + "0");
                JOptionPane.showMessageDialog(((JButton)e.getSource()).getParent(), "Zarejestrowano pomyślnie. Twój numer konta (login) to: " + gClientNumber);
                pw.close();
                in.close();
                out.close();
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (blankFieldException blankFieldException) {
                JOptionPane.showMessageDialog(((JButton)e.getSource()).getParent(), "Dane nie zostały wypełnione lub hasło jest za krótkie");
            }

            CardLayout c = (CardLayout) (cards.getLayout());
            c.show(cards, LOGINPANEL);
        }
    }

    class LoginLogic extends AbstractAction{
        public LoginLogic() {
            putValue(Action.NAME, "Zaloguj się");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String line = null;
            String log = login.getText();
            FileReader in = null;
            try {
                in = new FileReader("Accounts.txt");
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
            int clientNumber = 0;
            boolean isLogged = false;

            BufferedReader br = new BufferedReader(in);

            while (true) {
                try {
                    if (((line = br.readLine()) == null))
                        break;
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                String[] split = line.split(",");
                char[] pass = split[3].toCharArray();
                if (log.equals(split[2]) && Arrays.equals(password.getPassword(), pass)) {
                    isLogged = true;
                    name = split[0];
                    surname = split[1];
                    clientNumber = Integer.parseInt(split[2]);
                    passwd = split[3];
                    ballance = Double.parseDouble(split[4]);
                }

            }
            if (!isLogged){
                try {
                    throw new noMatchException();
                } catch (noMatchException p) {
                    JOptionPane.showMessageDialog(((JButton)e.getSource()).getParent(), "Logowanie nieudane, spróbuj ponownie!");
                }
            }
            else{
                try {
                    in.close();
                    br.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                pa = new PersonalAccount(name, surname, clientNumber, passwd, ballance);
                CardLayout c = (CardLayout) (cards.getLayout());
                c.show(cards, MENUPANEL);
                date = new Date();
                allOperations += "Logowanie," + pa.getClientNumber() + "," + formatter.format(date) + "," + 0 + "\n";
                JOptionPane.showMessageDialog(((JButton)e.getSource()).getParent(), "Logowanie pomyślne. Witaj " + pa.getName() + " " + pa.getSurname() + "!");
            }

        }
    }

    class checkBallance extends AbstractAction {

        public checkBallance() { putValue(Action.NAME, "Sprawdź informacje o użytkowniku");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            date = new Date();
            allOperations += "Sprawdzenie stanu konta," + pa.getClientNumber() + "," + formatter.format(date) + "," + 0 + "\n";
            JOptionPane.showMessageDialog(((JButton)e.getSource()).getParent(), "Imie: " + pa.getName() + "\nNazwisko: " + pa.getSurname() + "\nNr konta: " + pa.getClientNumber() + "\nDostępne saldo: " + pa.getBallance() + " zł" );
        }
    }

    class paymentLogic extends AbstractAction {

        public paymentLogic () { putValue(Action.NAME, "Wpłać pieniendze!");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            double ballance;
            ballance = Double.parseDouble(tfpayment.getText());
            try {
                date = new Date();
                pa.addToBalance(ballance);
                allOperations += "Wpłata," + pa.getClientNumber() + "," + formatter.format(date) + "," + ballance + "\n";
                JOptionPane.showMessageDialog(((JButton)e.getSource()).getParent(), pa.getBallance());
            } catch (badAmountException badAmountException) {
                JOptionPane.showMessageDialog(((JButton)e.getSource()).getParent(), "Kwota jest nieprawidłowa!");
            }

        }
    }

    class payoffLogic extends AbstractAction {

        public payoffLogic () { putValue(Action.NAME, "Wypłać pieniendze!");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            double oballance;
            oballance = Double.parseDouble(tfpayoff.getText());

            try {
                date = new Date();
                pa.withdraw(oballance);
                allOperations += "Wypłata," + pa.getClientNumber() + "," + formatter.format(date) + "," + oballance + "\n";
                JOptionPane.showMessageDialog(((JButton)e.getSource()).getParent(), "Pomyślnie wypłacono pieniądze! Stan konta: " + pa.getBallance());
            } catch (tooLittleException tooLittleException) {
                JOptionPane.showMessageDialog(((JButton)e.getSource()).getParent(), "Za mało pieniędzy na koncie! Posiadana kwota: " + pa.getBallance());
            } catch (badAmountException badAmountException) {
                JOptionPane.showMessageDialog(((JButton)e.getSource()).getParent(), "Kwota jest nieprawidłowa!");
            }



        }
    }

    class saveToFile {
        public saveToFile(){
            try{
                FileReader read = new FileReader("Accounts.txt");
                BufferedReader file = new BufferedReader(read);
                StringBuffer inputBuffer = new StringBuffer();
                String line;

                while((line = file.readLine()) != null)
                    inputBuffer.append(line + "\n");

                read.close();
                file.close();
                inputBuffer.deleteCharAt(inputBuffer.length() - 1);
                String input = inputBuffer.toString();

                String oldText = pa.getName()+","+pa.getSurname()+","+pa.getClientNumber()+","+pa.getPassword()+","+ballance;
                String newText = pa.getName()+","+pa.getSurname()+","+pa.getClientNumber()+","+pa.getPassword()+","+pa.getBallance();

                if(!oldText.equals(newText)){
                    input = input.replace(oldText, newText);
                    FileOutputStream fileOut = new FileOutputStream("Accounts.txt");
                    fileOut.write(input.getBytes());
                    fileOut.flush();
                    fileOut.close();
                }

            }
            catch (Exception k){
                System.out.println(k);
            }
        }
    }

    class saveTransaction {
        public saveTransaction() throws IOException {
            FileOutputStream fileOut = new FileOutputStream("transaction.txt", true);
            fileOut.write(allOperations.getBytes());
            fileOut.flush();
            fileOut.close();
        }
    }

    class LoginAdmin extends  AbstractAction {
        public LoginAdmin() { putValue(Action.NAME, "Zaloguj się jak administrator");}

        @Override
        public void actionPerformed(ActionEvent e) {
            String passA = tfAdmin.getText();
            if(passA.equals("admin123")){
                JOptionPane.showMessageDialog(((JButton)e.getSource()).getParent(), "Witamy Pana Prezesa!");
                CardLayout c = (CardLayout) (cards.getLayout());
                c.show(cards, ADPANEL);
            }else{
                JOptionPane.showMessageDialog(((JButton)e.getSource()).getParent(), "Złe hasło ;/");
            }

        }
    }


}
