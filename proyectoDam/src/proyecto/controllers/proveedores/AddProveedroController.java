package proyecto.controllers.proveedores;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import proyecto.Logica.Logica;
import proyecto.util.Util;

import java.net.URL;
import java.util.ResourceBundle;

import static proyecto.util.Util.activacionBoton;

public class AddProveedroController implements Initializable {

    @FXML
    private Button guardarProveedor;

    @FXML
    private Button limpiarProveedor;


    @FXML
    private TextField nombreProveedor;

    @FXML
    private TextField direccionProveedor;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        activacionBoton(nombreProveedor,direccionProveedor,guardarProveedor);
    }

    @FXML
    private void limpiarAddProveedor() {
        nombreProveedor.setText("");
        direccionProveedor.setText("");

    }

    @FXML
    private void guardarProveedor() {
        String nombre =Util.stringToMayus(nombreProveedor.getText());
        String direecion= Util.stringToMayus(direccionProveedor.getText());
            boolean existe= Logica.getInstance().getDatabase().proveedorExists(nombre);
            if(!existe)
            {
                int inserto = Logica.getInstance().getDatabase().addProveedor(nombre, direecion);
                if (inserto != 0)
                    Util.alertaShow("Consulta realizada", "El proveedor fue guardado con nombre: "+nombre+ " y direccion: "+direecion, Alert.AlertType.INFORMATION);
                else
                    Util.alertaShow("Fallo de consulta","El proveedor no se pudo guardar", Alert.AlertType.ERROR);
            }
            else
                Util.alertaShow("Campos vacios","El proveedor :"+nombre+" ya esta creado", Alert.AlertType.WARNING);
        limpiarProveedor.fire();

    }

}
