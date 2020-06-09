package proyecto.controllers.productos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import proyecto.Logica.Logica;
import proyecto.modelos.productos.Producto;
import proyecto.util.Util;

import java.net.URL;
import java.util.ResourceBundle;

public class VerProductoController implements Initializable {
    private ObservableList<Producto> productosObservableList =Logica.getInstance().getDatabase().getTodosProductos();

    @FXML
    private TableView<Producto> tableViewProveedor;

    @FXML
    private ComboBox<String> combobox;


    @FXML
    private TextField textProveedor;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableViewProveedor.setItems(productosObservableList);

        textProveedor.textProperty().addListener((observable, oldValue, newValue) -> filtrarLista());
    }


    private void filtrarLista() {
        String opcion=combobox.getSelectionModel().getSelectedItem();
        ObservableList<Producto> listaFiltrada= FXCollections.observableArrayList();
        if(opcion.equalsIgnoreCase("Todo"))
        {
            for (Producto producto: productosObservableList) {
                if(Util.stringToMayus(producto.toString()).contains(Util.stringToMayus(textProveedor.getText())))
                    listaFiltrada.add(producto);
            }
        }
        if(opcion.equalsIgnoreCase("ID producto"))
        {
            for (Producto producto: productosObservableList) {
                if(Util.stringToMayus(String.valueOf(producto.getId_producto())).contains(Util.stringToMayus(textProveedor.getText())))
                    listaFiltrada.add(producto);
            }
        }
        if(opcion.equalsIgnoreCase("Nombre"))
        {
            for (Producto producto: productosObservableList) {
                if(Util.stringToMayus(producto.getNombre_producto()).contains(Util.stringToMayus(textProveedor.getText())))
                    listaFiltrada.add(producto);
            }
        }

        if(opcion.equalsIgnoreCase("ID proveedor"))
        {
            for (Producto producto: productosObservableList) {
                if(Util.stringToMayus(String.valueOf(producto.getId_proveedor())).contains(Util.stringToMayus(textProveedor.getText())))
                    listaFiltrada.add(producto);
            }
        }
        tableViewProveedor.setItems(listaFiltrada);
    }

}
