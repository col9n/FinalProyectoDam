package proyecto.controllers.traspasos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import proyecto.Logica.Logica;
import proyecto.modelos.proveedores.Proveedor;
import proyecto.modelos.traspasos.Ordenes;
import proyecto.modelos.traspasos.OrdenesImprimir;
import proyecto.util.Util;

import java.io.File;
import java.net.URL;
import java.util.*;

public class VerTraspasosController implements Initializable {
    private ObservableList<Ordenes> proveedorObservableList=Logica.getInstance().getDatabase().getTodasOrdenes(Logica.getInstance().getUsuario().getId_centro());

    @FXML
    private TableView<Ordenes> tableViewProveedor;

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
        ObservableList<Ordenes> listaFiltrada= FXCollections.observableArrayList();
        if(opcion.equalsIgnoreCase("Todo"))
        {
            for (Ordenes orden:proveedorObservableList) {
                if(Util.stringToMayus(orden.toString()).contains(Util.stringToMayus(textProveedor.getText())))
                    listaFiltrada.add(orden);
            }
        }
        if(opcion.equalsIgnoreCase("ID Traspaso"))
        {
            for (Ordenes orden:proveedorObservableList) {
                if(Util.stringToMayus(String.valueOf(orden.getId_orden())).contains(Util.stringToMayus(textProveedor.getText())))
                    listaFiltrada.add(orden);
            }
        }
        if(opcion.equalsIgnoreCase("ID Centro origen"))
        {
            for (Ordenes orden:proveedorObservableList) {
                if(Util.stringToMayus(String.valueOf(orden.getCentro_salida())).contains(Util.stringToMayus(textProveedor.getText())))
                    listaFiltrada.add(orden);
            }
        }
        if(opcion.equalsIgnoreCase("ID Centro destino"))
        {
            for (Ordenes orden:proveedorObservableList) {
                if(Util.stringToMayus(String.valueOf(orden.getCentro_destino())).contains(Util.stringToMayus(textProveedor.getText())))
                    listaFiltrada.add(orden);
            }
        }
        if(opcion.equalsIgnoreCase("Estado"))
        {
            for (Ordenes orden:proveedorObservableList) {
                if(Util.stringToMayus(String.valueOf(orden.getEstado())).contains(Util.stringToMayus(textProveedor.getText())))
                    listaFiltrada.add(orden);
            }
        }
        if(opcion.equalsIgnoreCase("Fecha"))
        {
            for (Ordenes orden:proveedorObservableList) {
                if(Util.stringToMayus(String.valueOf(orden.getFecha())).contains(Util.stringToMayus(textProveedor.getText())))
                    listaFiltrada.add(orden);
            }
        }
        tableViewProveedor.setItems(listaFiltrada);


    }

    @FXML
    void imprimir(ActionEvent event) {
        File file=Util.getFile();
        try {
            if (tableViewProveedor.getSelectionModel().getSelectedItem() instanceof Ordenes) {
                Ordenes orden = tableViewProveedor.getSelectionModel().getSelectedItem();

                List<OrdenesImprimir> lista =Logica.getInstance().getDatabase().getOrdenesImprimir(orden.getId_orden());

                JRBeanCollectionDataSource jr = new JRBeanCollectionDataSource(lista); //lista sería la colección a mostrar. Típicamente saldría de la lógica de nuestra aplicación
                Map<String, Object> parametros = new HashMap<>(); //En este caso no hay parámetros, aunque podría haberlos
                JasperPrint print = null;


                print = JasperFillManager.fillReport("reportes/informeProyecto.jasper", parametros, jr);

                JasperExportManager.exportReportToPdfFile(print, file.getPath());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Informe");
                alert.setHeaderText(null);
                alert.setContentText("Informe del traspaso generado");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}
