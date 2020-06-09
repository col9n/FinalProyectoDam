package proyecto.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import proyecto.Logica.Logica;
import proyecto.util.Util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.StandardOpenOption;
import java.util.ResourceBundle;


public class LoginController  {
    private Stage stage=new Stage();

    @FXML
    private PasswordField pass;

    @FXML
    private TextField text;

    @FXML
    private Button button;


    @FXML
    private Button buttonCon;


    @FXML
    private void entrar(ActionEvent event) {

        boolean existe= Logica.getInstance().getDatabase().loginUser(text.getText(),pass.getText());

        if(existe)
        {
            changeScene();
        }
        else
        {
            Util.alertaShow("Error de acceso","Revise usuario  y contraseña", Alert.AlertType.INFORMATION);
        }
    }

    private void changeScene() {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/proyecto/views/Principal.fxml"));
                    Parent root = fxmlLoader.load();
                    PrincipalController principalController = (PrincipalController) fxmlLoader.getController();
                    stage.setTitle("Control Inventario");
                    stage.setResizable(true);
                    stage.setMinWidth(1000);
                    stage.setMinHeight(600);
                    stage.setMaxWidth(1200);
                    stage.setMaxHeight(900);
                    stage.setScene(new Scene(root));

                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage.show();
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void configuracion(ActionEvent actionEvent) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/proyecto/views/configuracion.fxml"));
            Parent root = fxmlLoader.load();
            ConfiguracionController configuracionController = (ConfiguracionController) fxmlLoader.getController();
            configuracionController.setStage(stage);
            stage.setResizable(false);
            stage.setTitle("Configuracíon ");
            stage.setScene(new Scene(root));

        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.showAndWait();
    }



}
