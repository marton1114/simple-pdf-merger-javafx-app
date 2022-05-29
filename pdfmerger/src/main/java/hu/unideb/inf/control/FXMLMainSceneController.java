package hu.unideb.inf.control;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class FXMLMainSceneController {

    List<File> f0;
    List<File> f1;

    @FXML
    private Button saveButton;

    @FXML
    void chooseFiles0(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF fájlok", "*.pdf"));
        f0 = fc.showOpenMultipleDialog(null);
    }

    @FXML
    void chooseFiles1(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF fájlok", "*.pdf"));
        f1 = fc.showOpenMultipleDialog(null);

    }

    @FXML
    void generateFile(ActionEvent event) throws IOException {
        if (f0 == null && f1 == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hiba!");
            alert.setContentText("Nem választottál ki egyesítendő pdf fájlokat!");
            alert.setHeaderText(null);
            alert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            alert.showAndWait();
        } else if (f0 != null && f1 == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hiba!");
            alert.setContentText("Nem választottad ki a páratlan oldalakat!");
            alert.setHeaderText(null);
            alert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            alert.showAndWait();
        } else if (f0 == null && f1 != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hiba!");
            alert.setContentText("Nem választottad ki a páros oldalakat!");
            alert.setHeaderText(null);
            alert.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            alert.showAndWait();
        }

        PDFMergerUtility obj = new PDFMergerUtility();

        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF fájlok", "*.pdf"));
        File file = fc.showSaveDialog(new Stage());

        obj.setDestinationFileName(file.getPath());

        int minim = min(f0.size(),f1.size());

        for (int i = 0; i < minim; i++) {
            obj.addSource(f0.get(i));
            obj.addSource(f1.get(i));
        }

        for (int i = minim; i < f0.size(); i++) {
            obj.addSource(f0.get(i));
        }

        for (int i = minim; i < f1.size(); i++) {
            obj.addSource(f1.get(i));
        }

        obj.mergeDocuments();
    }
}
