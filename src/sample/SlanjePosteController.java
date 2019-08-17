package sample;


import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class SlanjePosteController implements Initializable {
    public Button btnPotvrdiSlanje;
    public TextField postanskiBroj;

    public SlanjePosteController(){}


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnPotvrdiSlanje.setDisable(true);

        postanskiBroj.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(oldValue && !newValue){
                    Validacija validacija = new Validacija(postanskiBroj.getText());
                    Thread thread = new Thread(validacija);
                    postanskiBroj.setDisable(true);
                    thread.start();
                }
            }
        });
    }

    public class Validacija implements Runnable {

         String provjeriPostanskiBroj;

        public Validacija(String x) {
            provjeriPostanskiBroj = x;
        }

        @Override
        public void run() {
            try {
                validiraj(provjeriPostanskiBroj);
            } catch (IOException e) {

            }
        }

        public void validiraj(String provjeriPostanskiBroj) throws IOException {
            URL url = new URL("http://c9.etf.unsa.ba/proba/postanskiBroj.php?postanskiBroj="+provjeriPostanskiBroj);
            InputStream tok = url.openStream();
            int size = tok.available();
            String rezultat = "";
            for(int i=0;i<size;i++){
                rezultat += (char)tok.read();
            }
            System.out.println(rezultat);

            if (rezultat.equals("OK")) {
                Platform.runLater(() -> {
                    btnPotvrdiSlanje.setDisable(false);
                    postanskiBroj.getStyleClass().removeAll("invalid");
                    postanskiBroj.getStyleClass().add("valid");

                });
            }
            else{
                Platform.runLater(() -> {
                    btnPotvrdiSlanje.setDisable(true);
                    postanskiBroj.getStyleClass().removeAll("valid");
                    postanskiBroj.getStyleClass().add("invalid");
                });
            }
            Platform.runLater(() -> {
                btnPotvrdiSlanje.setDisable(false);
            });

        }
    }

    public void posalji(ActionEvent actionEvent){
        Stage prozor = (Stage) postanskiBroj.getScene().getWindow();
        prozor.close();
    }
}
