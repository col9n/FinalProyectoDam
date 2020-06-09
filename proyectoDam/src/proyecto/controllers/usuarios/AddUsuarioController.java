package proyecto.controllers.usuarios;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import proyecto.Logica.Logica;
import proyecto.modelos.Centro;
import proyecto.modelos.proveedores.Proveedor;
import proyecto.util.Util;

import java.net.URL;
import java.util.ResourceBundle;

import static proyecto.util.Util.*;


public class AddUsuarioController implements Initializable {

    @FXML
    private Button guardarProducto;

    @FXML
    private Button limpiarProducto;

    @FXML
    private ComboBox<Centro> centroEmpleado;

    @FXML
    private TextField nombreEmpleado;

    @FXML
    private TextField primerApellido;

    @FXML
    private TextField segundoApellido;

    @FXML
    private TextField userEmpleado;

    @FXML
    private PasswordField pass;

    @FXML
    private PasswordField passRep;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        centroEmpleado.setItems(Logica.getInstance().getDatabase().getTodosCentros());
        activacionBoton(userEmpleado,pass,passRep,nombreEmpleado,primerApellido,segundoApellido,centroEmpleado, guardarProducto);
    }

    @FXML
    private void limpiarAddUsuario() {
        nombreEmpleado.setText("");
        primerApellido.setText("");
        segundoApellido.setText("");
        userEmpleado.setText("");
        pass.setText("");
        passRep.setText("");
    }

    @FXML
    private void guardarUsuario() {
        Centro centro=  centroEmpleado.getSelectionModel().getSelectedItem();
        if( passEquals( pass.getText(), passRep.getText())) {
            boolean existe= Logica.getInstance().getDatabase().userExist(userEmpleado.getText());
            if(!existe)
            {
                int inserto = Logica.getInstance().getDatabase().addUser(userEmpleado.getText(),pass.getText(),nombreEmpleado.getText(),primerApellido.getText(),segundoApellido.getText(),centro.getId_centro());
                if (inserto==1)
                    Util.alertaShow("Consulta realizada", "El usuario fue guardado "+ nombreEmpleado.getText(), Alert.AlertType.INFORMATION);
                else
                    Util.alertaShow("Fallo de consulta","El usuario no se pudo guardar ", Alert.AlertType.ERROR);
            }
            else
                Util.alertaShow("Fallo en la creacion","Ese usuario ya existe ", Alert.AlertType.WARNING);
        limpiarProducto.fire();
    }
    }

}
