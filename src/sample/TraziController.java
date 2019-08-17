package sample;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;

public class TraziController implements Initializable {

    public Button btnTrazi;
    public TextField textField;
    public ListView<String> listView;
    private FileListModel listModel;
    public File file = new File("/home");
    public Button btnStop;

    public TraziController(FileListModel model){
        this.listModel = model;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setItems(listModel.getPutanje());

        listView.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String>  c) {
               /* Parent root = null;
                try {
                    Stage myStage = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/posta.fxml"));
                    loader.setController(new SlanjePosteController());
                    root = loader.load();
                    myStage.setTitle("Podaci o primatelju");
                    myStage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                    myStage.initOwner(Main.m.getScene().getWindow());
                    myStage.initModality(Modality.APPLICATION_MODAL);
                    myStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                Stage x =new Stage();
                x.setTitle("Slanje pošte");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/posta.fxml"));
               // loader.setController(new SendWindowController());
                Parent root2 = null;
                try {
                    root2 = loader.load();
                    if(root2==null){
                        System.out.println("fakat jes null");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                x.setScene(new Scene( root2,USE_COMPUTED_SIZE,USE_COMPUTED_SIZE));
                x.initOwner(Main.m.getScene().getWindow());
                x.initModality(Modality.APPLICATION_MODAL);
                x.show();


            }
        });
    }

    public class Finder implements Runnable{

        @Override
        public void run() {
            find(textField.getText(),file.getAbsolutePath());
        }

        public void find(String name, String parent){
            if(btnStop.isDisabled()){
                try {
                    Thread.currentThread().join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            File[] child = new File(parent).listFiles();
            if (child != null) {
                if(child.length!=0){
                    for (File aChild : child) {
                        if (aChild.getName().contains(name) && aChild.isFile()) {
                            Platform.runLater(()-> {
                                listModel.dodajPutanju(aChild.getAbsolutePath());
                            });
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        if (aChild.isDirectory()) {
                            find(name, aChild.getAbsolutePath());
                        }
                    }
                }
            }
                if(parent.equals(file.getAbsolutePath())){
                btnTrazi.setDisable(false);
            }
        }
    }
    public void doFind(ActionEvent actionEvent) {
        listModel.izbrisiSvePutanje();
        btnTrazi.setDisable(true);
        btnStop.setDisable(false);
        Finder myFinder = new Finder();
        Thread myThread = new Thread(myFinder);
        myThread.start();
    }
    public void doAbort(ActionEvent actionEvent ){
        btnStop.setDisable(true);
        btnTrazi.setDisable(false);
    }

}
