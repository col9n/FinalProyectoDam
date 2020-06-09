package proyecto.controllers.centros;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import proyecto.Logica.Logica;
import proyecto.modelos.Centro;
import proyecto.modelos.productos.Producto;
import proyecto.util.Util;

import java.net.URL;
import java.util.ResourceBundle;

public class VerCentroController implements Initializable {
    private ObservableList<Centro> productosObservableList =Logica.getInstance().getDatabase().getTodosCentrosNoPropios(Logica.getInstance().getUsuario().getId_centro());

    @FXML
    private TableView<Centro> tableViewCentro;

    @FXML
    private ComboBox<String> combobox;


    @FXML
    private TextField textCentro;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableViewCentro.setItems(productosObservableList);

        textCentro.textProperty().addListener((observable, oldValue, newValue) -> filtrarLista());
    }


    private void filtrarLista() {
        String opcion=combobox.getSelectionModel().getSelectedItem();
        ObservableList<Centro> listaFiltrada= FXCollections.observableArrayList();
        if(opcion.equalsIgnoreCase("Todo"))
        {
            for (Centro centro: productosObservableList) {
                if(Util.stringToMayus(centro.verInfor()).contains(Util.stringToMayus(textCentro.getText())))
                    listaFiltrada.add(centro);
            }
        }
        if(opcion.equalsIgnoreCase("ID centro"))
        {
            for (Centro centro: productosObservableList) {
                if(Util.stringToMayus(String.valueOf(centro.getId_centro())).contains(Util.stringToMayus(textCentro.getText())))
                    listaFiltrada.add(centro);
            }
        }
        if(opcion.equalsIgnoreCase("Direccion"))
        {
            for (Centro centro: productosObservableList) {
                if(Util.stringToMayus(centro.getDireccion()).contains(Util.stringToMayus(textCentro.getText())))
                    listaFiltrada.add(centro);
            }
        }


        tableViewCentro.setItems(listaFiltrada);
    }

}
