package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FileListModel {
    private ObservableList<String> putanje = FXCollections.observableArrayList();
    private SimpleStringProperty trenutniPut = new SimpleStringProperty();

    public FileListModel(){

    }

    public ObservableList<String> getPutanje() {
        return putanje;
    }

    public void setPutanje(ObservableList<String> putanje) {
        this.putanje = putanje;
    }

    public String getTrenutniPut() {
        return trenutniPut.get();
    }

    public SimpleStringProperty trenutniPutProperty() {
        return trenutniPut;
    }

    public void setTrenutniPut(String trenutniPut) {
        this.trenutniPut.set(trenutniPut);
    }

    public void dodajPutanju(String putanja){
        putanje.add(putanja);
    }

    public void izbrisiSvePutanje(){
        putanje.remove(0, putanje.size());
        trenutniPut.set("");
    }
}

