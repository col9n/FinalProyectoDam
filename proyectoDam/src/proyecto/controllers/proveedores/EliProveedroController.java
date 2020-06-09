package proyecto.controllers.proveedores;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import proyecto.Logica.Logica;
import proyecto.modelos.proveedores.Proveedor;
import proyecto.modelos.proveedores.ProveedorEliminar;
import proyecto.util.Util;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static proyecto.util.Util.enableButtonListaContine;

public class EliProveedroController implements Initializable {
    private ObservableList<ProveedorEliminar> proveedorObservableList=Logica.getInstance().getDatabase().getTodosProveedoresEliminar();
    private List <ProveedorEliminar> listaBorrar = new <Proveedor> ArrayList();

    @FXML
    private TableView<ProveedorEliminar> tableViewProveedor;

    @FXML
    private ComboBox<String> combobox;

    @FXML
    private TextField textProveedor;

    @FXML
    private Button eliminar;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableViewProveedor.setItems(proveedorObservableList);

        textProveedor.textProperty().addListener((observable, oldValue, newValue) -> filtrarLista());

        proveedorObservableList.addListener((ListChangeListener<Proveedor>) c -> {
            while (c.next()) {
                if (c.wasUpdated()) {
                    for (int i = c.getFrom(); i < c.getTo(); ++i) {
                        ProveedorEliminar prov =proveedorObservableList.get(i);
                        listaBorrar.remove(prov);
                        if(!prov.isBorradoLogico()) {
                            listaBorrar.add(prov);
                        }
                }
            }
        }
            enableButtonListaContine(listaBorrar,eliminar);
    });
      enableButtonListaContine(listaBorrar,eliminar);


    }


    private void filtrarLista() {
        String opcion=combobox.getSelectionModel().getSelectedItem();
        ObservableList<ProveedorEliminar> listaFiltrada= FXCollections.observableArrayList();
        if(opcion.equalsIgnoreCase("Todo"))
        {
            for (ProveedorEliminar proveedor:proveedorObservableList) {
                if(proveedor.verInfor().toUpperCase().contains(textProveedor.getText().toUpperCase()))
                    listaFiltrada.add(proveedor);
            }
        }
        if(opcion.equalsIgnoreCase("ID"))
        {
            for (ProveedorEliminar proveedor:proveedorObservableList) {
                if(String.valueOf(proveedor.getId_proveedor()).contains(textProveedor.getText()))
                    listaFiltrada.add(proveedor);
            }
        }
        if(opcion.equalsIgnoreCase("Nombre"))
        {
            for (ProveedorEliminar proveedor:proveedorObservableList) {
                if(proveedor.getNombre_proveedor().toUpperCase().contains(textProveedor.getText().toUpperCase()))
                    listaFiltrada.add(proveedor);
            }
        }
        if(opcion.equalsIgnoreCase("Direccion"))
        {
            for (ProveedorEliminar proveedor:proveedorObservableList) {
                if(proveedor.getDireccion_proveedor().toUpperCase().contains(textProveedor.getText().toUpperCase()))
                    listaFiltrada.add(proveedor);
            }
        }
        tableViewProveedor.setItems(listaFiltrada);
    }


    @FXML
    void eliminar() {
        StringBuilder borradosBuilder = new StringBuilder("Estos son los proveedores a borrar:\n\n");
        for (Proveedor pro: listaBorrar) {
                borradosBuilder.append("-").append(pro.getId_proveedor()).append(" nombre: ").append(pro.getNombre_proveedor()).append(" dirrecion: ").append(pro.getDireccion_proveedor()).append("\n");
            }
        String borrados = borradosBuilder.toString();
        borrados=borrados+"\nFilas afectadas :"+ listaBorrar.size();
            Alert alerta= Util.alertaGet("Realizar borrado",borrados, Alert.AlertType.CONFIRMATION);
            Optional<ButtonType> result = alerta.showAndWait();
            if(result.get() == ButtonType.OK) {
                int afectadas=Logica.getInstance().getDatabase().deleteProveedores(listaBorrar);
                if(afectadas!=listaBorrar.size())
                {
                    Util.alertaShow("Realizar borrado","Se produjo un error en el borrado", Alert.AlertType.ERROR);
                }
                else
                    Util.alertaShow("Realizar borrado","Numero de borrados realizados: "+ listaBorrar.size()+" ", Alert.AlertType.INFORMATION);
                actualizarTableView();
            }
    }

    private void actualizarTableView(){
        for(ProveedorEliminar prov:listaBorrar)
        {
            proveedorObservableList.remove(prov);
        }
        listaBorrar.clear();
       enableButtonListaContine(listaBorrar,eliminar);
    }

}
