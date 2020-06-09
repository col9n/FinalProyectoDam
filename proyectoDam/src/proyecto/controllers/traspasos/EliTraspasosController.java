package proyecto.controllers.traspasos;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import proyecto.Logica.Logica;
import proyecto.modelos.proveedores.Proveedor;
import proyecto.modelos.proveedores.ProveedorEliminar;
import proyecto.modelos.traspasos.Ordenes;
import proyecto.modelos.traspasos.OrdenesEliminar;
import proyecto.util.Util;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static proyecto.util.Util.enableButtonListaContine;

public class EliTraspasosController implements Initializable {
    private ObservableList<OrdenesEliminar> proveedorObservableList=Logica.getInstance().getDatabase().getTodasOrdenesEliminar(Logica.getInstance().getUsuario().getId_centro());
    private List <OrdenesEliminar> listaRecibir = new <OrdenesEliminar> ArrayList();

    @FXML
    private TableView<OrdenesEliminar> tableViewProveedor;

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

        proveedorObservableList.addListener((ListChangeListener<Ordenes>) c -> {
            while (c.next()) {
                if (c.wasUpdated()) {
                    for (int i = c.getFrom(); i < c.getTo(); ++i) {
                        OrdenesEliminar prov =proveedorObservableList.get(i);
                        listaRecibir.remove(prov);
                        if(!prov.isBorradoLogico()) {
                            listaRecibir.add(prov);
                        }
                    }
                }
            }
            enableButtonListaContine(listaRecibir,eliminar);
        });
        enableButtonListaContine(listaRecibir,eliminar);


    }


    private void filtrarLista() {

        String opcion=combobox.getSelectionModel().getSelectedItem();
        ObservableList<OrdenesEliminar> listaFiltrada= FXCollections.observableArrayList();
        if(opcion.equalsIgnoreCase("Todo"))
        {
            for (OrdenesEliminar orden:proveedorObservableList) {
                if(Util.stringToMayus(orden.toString()).contains(Util.stringToMayus(textProveedor.getText())))
                    listaFiltrada.add(orden);
            }
        }
        if(opcion.equalsIgnoreCase("ID Traspaso"))
        {
            for (OrdenesEliminar orden:proveedorObservableList) {
                if(Util.stringToMayus(String.valueOf(orden.getId_orden())).contains(Util.stringToMayus(textProveedor.getText())))
                    listaFiltrada.add(orden);
            }
        }
        if(opcion.equalsIgnoreCase("ID Centro origen"))
        {
            for (OrdenesEliminar orden:proveedorObservableList) {
                if(Util.stringToMayus(String.valueOf(orden.getCentro_salida())).contains(Util.stringToMayus(textProveedor.getText())))
                    listaFiltrada.add(orden);
            }
        }
        if(opcion.equalsIgnoreCase("ID Centro destino"))
        {
            for (OrdenesEliminar orden:proveedorObservableList) {
                if(Util.stringToMayus(String.valueOf(orden.getCentro_destino())).contains(Util.stringToMayus(textProveedor.getText())))
                    listaFiltrada.add(orden);
            }
        }
        if(opcion.equalsIgnoreCase("Estado"))
        {
            for (OrdenesEliminar orden:proveedorObservableList) {
                if(Util.stringToMayus(String.valueOf(orden.getEstado())).contains(Util.stringToMayus(textProveedor.getText())))
                    listaFiltrada.add(orden);
            }
        }
        if(opcion.equalsIgnoreCase("Fecha"))
        {
            for (OrdenesEliminar orden:proveedorObservableList) {
                if(Util.stringToMayus(String.valueOf(orden.getFecha())).contains(Util.stringToMayus(textProveedor.getText())))
                    listaFiltrada.add(orden);
            }
        }
        tableViewProveedor.setItems(listaFiltrada);


    }


    @FXML
    void recibir() {

        StringBuilder borradosBuilder = new StringBuilder("Estos son los traspasos a recibir:\n\n");
        for (Ordenes orden: listaRecibir) {
                borradosBuilder.append("-").append(orden.getId_orden()).append(" orgien: ").append(orden.getCentro_salida()).append(" fecha: ").append(orden.getFecha()).append("\n");
            }
        String borrados = borradosBuilder.toString();
        borrados=borrados+"\nFilas afectadas :"+ listaRecibir.size();
            Alert alerta= Util.alertaGet("Recibir  traspaso",borrados, Alert.AlertType.CONFIRMATION);
            Optional<ButtonType> result = alerta.showAndWait();
            if(result.get() == ButtonType.OK) {
                boolean resultado=Logica.getInstance().getDatabase().recibirTraspasos(listaRecibir);

                if(!resultado)
                {
                    Util.alertaShow("Recibir  traspaso","Se produjo un error en el recibir el traspaso", Alert.AlertType.ERROR);
                }
                else
                    Util.alertaShow("Recibir  traspaso","Se recibio este cantidad de traspasos: "+ listaRecibir.size()+" ", Alert.AlertType.INFORMATION);
                actualizarTableView();
            }


    }

    private void actualizarTableView(){

        for(OrdenesEliminar orden:listaRecibir)
        {
            proveedorObservableList.remove(orden);
        }
        listaRecibir.clear();
       enableButtonListaContine(listaRecibir,eliminar);


    }

}
