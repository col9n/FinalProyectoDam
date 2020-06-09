package proyecto.controllers.usuarios;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import proyecto.Logica.Logica;
import proyecto.modelos.Usuario;
import proyecto.modelos.productos.Producto;
import proyecto.util.Util;

import java.net.URL;
import java.util.ResourceBundle;

public class VerUsuarioController implements Initializable {
    private ObservableList<Usuario> productosObservableList =Logica.getInstance().getDatabase().getTodosUsuario();

    @FXML
    private TableView<Usuario> tableViewProveedor;

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
        ObservableList<Usuario> listaFiltrada= FXCollections.observableArrayList();
        if(opcion.equalsIgnoreCase("Todo"))
        {
            for (Usuario user: productosObservableList) {
                if(Util.stringToMayus(user.toString()).contains(Util.stringToMayus(textProveedor.getText())))
                    listaFiltrada.add(user);
            }
        }
        if(opcion.equalsIgnoreCase("ID usuario"))
        {
            for (Usuario user: productosObservableList) {
                if(Util.stringToMayus(String.valueOf(user.getId_usuario())).contains(Util.stringToMayus(textProveedor.getText())))
                    listaFiltrada.add(user);
            }
        }
        if(opcion.equalsIgnoreCase("Nombre"))
        {
            for (Usuario user: productosObservableList) {
                if(Util.stringToMayus(user.getNombre_usuario()).contains(Util.stringToMayus(textProveedor.getText())))
                    listaFiltrada.add(user);
            }
        }

        if(opcion.equalsIgnoreCase("Primer apellido"))
        {
            for (Usuario user: productosObservableList) {
                if(Util.stringToMayus(String.valueOf(user.getApellido1())).contains(Util.stringToMayus(textProveedor.getText())))
                    listaFiltrada.add(user);
            }
        }

        if(opcion.equalsIgnoreCase("Segundo apellido"))
        {
            for (Usuario user: productosObservableList) {
                if(Util.stringToMayus(String.valueOf(user.getApellido2())).contains(Util.stringToMayus(textProveedor.getText())))
                    listaFiltrada.add(user);
            }
        }
        tableViewProveedor.setItems(listaFiltrada);
    }

}
