import java.awt.*;
import java.io.IOException;

public class MainClass {

    private static MainFrame mf;

    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                try {
                    mf = new MainFrame("ATM");
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
    }


}


