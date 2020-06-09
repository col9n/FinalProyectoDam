package proyecto.controllers;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import proyecto.Logica.Logica;
import proyecto.util.Util;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static proyecto.util.Util.activacionBotonConfiguracion;

public class ConfiguracionController implements Initializable {
    private Stage stage;

    @FXML
    private TextField conexion;

    @FXML
    private TextField user;

    @FXML
    private PasswordField pass;

    @FXML
    private Button guardar;

    @FXML
    private ChoiceBox<String> choice;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        conexion.setText(Logica.getInstance().getDatabase().getDbConnection());
        user.setText(Logica.getInstance().getDatabase().getDbUser());
        pass.setText(Logica.getInstance().getDatabase().getDbPassword());

        choice.getItems().add("Modena");
        choice.getItems().add("Caspian");
        choice.getSelectionModel().selectFirst();

        activacionBotonConfiguracion(conexion,user,pass,guardar);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void cargarCaspian() {
        Application.setUserAgentStylesheet(Application.STYLESHEET_CASPIAN);
    }

    private void cargarModena() {

        Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
    }

    public void guardar(ActionEvent actionEvent) {
        Alert alerta= Util.alertaGet("Guardar cambios","Desea guardar los cambio realizados", Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result = alerta.showAndWait();
        if(result.get() == ButtonType.OK) {
            Logica.getInstance().getDatabase().setDbConnection(conexion.getText());
            Logica.getInstance().getDatabase().setDbUser(user.getText());
            Logica.getInstance().getDatabase().setDbPassword(pass.getText());
             cambiarEstilo(choice.getSelectionModel().getSelectedItem());

            alerta= Util.alertaGet("Guardar cambios","Cambio guardados", Alert.AlertType.INFORMATION);
            alerta.showAndWait();

        }
    }

    private void cambiarEstilo(String opcion){
        if(opcion.equals("Caspian"))
            cargarCaspian();
        if(opcion.equals("Modena"))
            cargarModena();
    }
}
