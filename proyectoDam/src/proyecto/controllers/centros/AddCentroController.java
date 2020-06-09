package proyecto.controllers.centros;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import proyecto.Logica.Logica;
import proyecto.modelos.proveedores.Proveedor;
import proyecto.util.Util;

import java.net.URL;
import java.util.ResourceBundle;

import static proyecto.util.Util.activacionBotonComboBoxProduct;


public class AddCentroController implements Initializable {

    @FXML
    private TextField direccionCentro;

    @FXML
    private Button guardarProducto;

    @FXML
    private Button limpiarProducto;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        activacionBotonComboBoxProduct(direccionCentro, guardarProducto);
    }

    @FXML
    private void limpiarAddProveedor() {
        direccionCentro.setText("");
    }

    @FXML
    private void guardarProveedor() {
        String nombre =Util.stringToMayus(direccionCentro.getText().toString());
        System.out.println(nombre);
            int existe= Logica.getInstance().getDatabase().centroExsist(nombre);
            if(existe==0)
            {
                int inserto = Logica.getInstance().getDatabase().addCentro(nombre);
                if (inserto != 0)
                    Util.alertaShow("Consulta realizada", "El centro fue guardado con la siguiente dirrecion: "+nombre, Alert.AlertType.INFORMATION);
                else
                    Util.alertaShow("Fallo de consulta","El proveedor no se pudo guardar", Alert.AlertType.ERROR);
            }
            else
                Util.alertaShow("Campos vacios","El centro :"+nombre+" ya esta creado", Alert.AlertType.WARNING);
        limpiarProducto.fire();

    }

}
