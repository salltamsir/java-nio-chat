import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        Server myClasse = new Server(256);
        myClasse.accept();

        //Handler handler = new Handler();
        //String [] words = handler.decoupage("Je suis la");



    }
}
