package proyecto.controllers.proveedores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import proyecto.Logica.Logica;
import proyecto.modelos.proveedores.Proveedor;
import proyecto.util.Util;

import java.net.URL;
import java.util.*;

public class ModProveedorController implements Initializable {
    private ObservableList<Proveedor> proveedorObservableList = Logica.getInstance().getDatabase().getTodosProveedores();
    private  List<Proveedor> listaActualizar = Collections.synchronizedList(new ArrayList());


    @FXML
    private TableView<Proveedor> tableViewProveedor;


    @FXML
    private TableColumn<Proveedor, String> nombreColum;

    @FXML
    private TableColumn<Proveedor, String> dirreccionColum;

    @FXML
    private ComboBox<String> combobox;


    @FXML
    private TextField textProveedor;

    @FXML
    private Button guardarCambios;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableViewProveedor.setItems(proveedorObservableList);

        textProveedor.textProperty().addListener((observable, oldValue, newValue) -> filtrarLista());

        nombreColum.setCellFactory(TextFieldTableCell.forTableColumn());
        nombreColum.setOnEditCommit(
                t -> {
                    Proveedor prov = t.getTableView().getSelectionModel().getSelectedItem();
                    prov.setNombre_proveedor(Util.stringToMayus(t.getNewValue()));
                    for (Proveedor pro : listaActualizar) {
                        if (pro.getId_proveedor() == prov.getId_proveedor())
                            listaActualizar.remove(pro);
                    }
                    listaActualizar.add(prov);
                }
        );

        dirreccionColum.setCellFactory(TextFieldTableCell.forTableColumn());
        dirreccionColum.setOnEditCommit(
                t -> {
                    Proveedor prov = t.getTableView().getSelectionModel().getSelectedItem();
                    prov.setDireccion_proveedor(Util.stringToMayus(t.getNewValue()));
                    for (Proveedor pro : listaActualizar) {
                        if (pro.getId_proveedor() == prov.getId_proveedor())
                            listaActualizar.remove(pro);
                    }
                    listaActualizar.add(prov);
                }
        );


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

    @FXML
    void guardarCambio() {
        if(listaActualizar.size()>0){
        StringBuilder cambios= new StringBuilder("Estos son los cambio a guardar:\n\n");
        for (Proveedor pro:listaActualizar) {
            cambios.append("-").append(pro.getId_proveedor()).append(" nombre: ").append(pro.getNombre_proveedor()).append(" dirrecion: ").append(pro.getDireccion_proveedor()).append("\n");
        }
        cambios.append("\nFilas afectadas :").append(listaActualizar.size());
        Alert alerta= Util.alertaGet("Realizar cambios", cambios.toString(), Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result = alerta.showAndWait();
        if(result.get() == ButtonType.OK) {
            int afectadas=Logica.getInstance().getDatabase().updateProveedor(listaActualizar);
            if(afectadas!=listaActualizar.size())
            {
                Util.alertaShow("Realizar cambios","Se produjo un error en el guardado", Alert.AlertType.ERROR);
            }
            else {
                Util.alertaShow("Realizar cambios", "Numero de cambios realizados: " + listaActualizar.size() + " ", Alert.AlertType.INFORMATION);
            }
        }

        }
       else
            Util.alertaShow("Realizar cambios","Ningun cambio realizado", Alert.AlertType.INFORMATION);
        tableViewProveedor.setItems(Logica.getInstance().getDatabase().getTodosProveedores());
        listaActualizar.clear();
        tableViewProveedor.refresh();

    }

}


