package proyecto.controllers.proveedores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import javafx.scene.control.*;


import proyecto.Logica.Logica;
import proyecto.modelos.proveedores.Proveedor;
import proyecto.util.Util;


import java.net.URL;

import java.util.ResourceBundle;

public class VerProveedroController implements Initializable {
    private ObservableList<Proveedor> proveedorObservableList=Logica.getInstance().getDatabase().getTodosProveedores();

    @FXML
    private TableView<Proveedor> tableViewProveedor;

    @FXML
    private ComboBox<String> combobox;


    @FXML
    private TextField textProveedor;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableViewProveedor.setItems(proveedorObservableList);

        textProveedor.textProperty().addListener((observable, oldValue, newValue) -> filtrarLista());
    }


    private void filtrarLista() {
        String opcion=combobox.getSelectionModel().getSelectedItem();
        ObservableList<Proveedor> listaFiltrada= FXCollections.observableArrayList();
        if(opcion.equalsIgnoreCase("Todo"))
        {
            for (Proveedor proveedor:proveedorObservableList) {
                if(Util.stringToMayus(proveedor.toString()).contains(Util.stringToMayus(textProveedor.getText())))
                    listaFiltrada.add(proveedor);
            }
        }
        if(opcion.equalsIgnoreCase("ID"))
        {
            for (Proveedor proveedor:proveedorObservableList) {
                if(Util.stringToMayus(String.valueOf(proveedor.getId_proveedor())).contains(Util.stringToMayus(textProveedor.getText())))
                    listaFiltrada.add(proveedor);
            }
        }
        if(opcion.equalsIgnoreCase("Nombre"))
        {
            for (Proveedor proveedor:proveedorObservableList) {
                if(Util.stringToMayus(proveedor.getNombre_proveedor()).contains(Util.stringToMayus(textProveedor.getText())))
                    listaFiltrada.add(proveedor);
            }
        }
        if(opcion.equalsIgnoreCase("Direccion"))
        {
            for (Proveedor proveedor:proveedorObservableList) {
                if(Util.stringToMayus(proveedor.getDireccion_proveedor()).contains(Util.stringToMayus(textProveedor.getText())))
                    listaFiltrada.add(proveedor);
            }
        }
        tableViewProveedor.setItems(listaFiltrada);
    }

}
