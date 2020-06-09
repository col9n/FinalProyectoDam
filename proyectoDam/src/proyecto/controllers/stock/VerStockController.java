package proyecto.controllers.stock;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import proyecto.Logica.Logica;
import proyecto.modelos.stock.Stock;
import proyecto.util.Util;

import java.net.URL;
import java.util.ResourceBundle;

public class VerStockController implements Initializable {
    private ObservableList<Stock> stockObservableList =Logica.getInstance().getDatabase().getStock(Logica.getInstance().getUsuario().getId_centro());

    @FXML
    private TableView<Stock> tableViewStock;

    @FXML
    private ComboBox<String> combobox;


    @FXML
    private TextField textProveedor;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableViewStock.setItems(stockObservableList);

        textProveedor.textProperty().addListener((observable, oldValue, newValue) -> filtrarLista());
    }


    private void filtrarLista() {
        String opcion=combobox.getSelectionModel().getSelectedItem();
        ObservableList<Stock> listaFiltrada= FXCollections.observableArrayList();
        if(opcion.equalsIgnoreCase("Todo"))
        {
            for (Stock stock: stockObservableList) {
                if(Util.stringToMayus(stock.toString()).contains(Util.stringToMayus(textProveedor.getText())))
                    listaFiltrada.add(stock);
            }
        }
        if(opcion.equalsIgnoreCase("ID stock"))
        {
            for (Stock stock: stockObservableList) {
                if(Util.stringToMayus(String.valueOf(stock.getId_stock())).contains(Util.stringToMayus(textProveedor.getText())))
                    listaFiltrada.add(stock);
            }
        }
        if(opcion.equalsIgnoreCase("ID producto"))
        {
            for (Stock stock: stockObservableList) {
                if(Util.stringToMayus(String.valueOf(stock.getId_producto())).contains(Util.stringToMayus(textProveedor.getText())))
                    listaFiltrada.add(stock);
            }
        }
        if(opcion.equalsIgnoreCase("Cantidad"))
        {
            for (Stock stock: stockObservableList) {
                if(Util.stringToMayus(String.valueOf(stock.getCantidad())).contains(Util.stringToMayus(textProveedor.getText())))
                    listaFiltrada.add(stock);
            }
        }
        tableViewStock.setItems(listaFiltrada);
    }

}
