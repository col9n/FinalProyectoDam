package proyecto.controllers.stock;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import proyecto.Logica.Logica;
import proyecto.modelos.stock.Stock;
import proyecto.util.Util;

import java.net.URL;
import java.util.*;

public class ModStockController implements Initializable {


    private ObservableList<Stock> stockObservableList = Logica.getInstance().getDatabase().getStock(Logica.getInstance().getUsuario().getId_centro());
    private  List<Stock> listaActualizar = Collections.synchronizedList(new ArrayList());


    @FXML
    private TableView<Stock> tableViewStock;


    @FXML
    private TableColumn<Stock, String> cantidadColum;


    @FXML
    private ComboBox<String> combobox;


    @FXML
    private TextField textProveedor;

    @FXML
    private Button guardarCambios;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableViewStock.setItems(stockObservableList);

        textProveedor.textProperty().addListener((observable, oldValue, newValue) -> filtrarLista());

        cantidadColum.setCellFactory(TextFieldTableCell.forTableColumn());
        cantidadColum.setOnEditCommit(
                t -> {
                    try {
                        Integer.parseInt(t.getNewValue());
                        if(Integer.parseInt(t.getNewValue())>=0) {
                            Stock stock = t.getTableView().getSelectionModel().getSelectedItem();
                            stock.setCantidad((t.getNewValue()));
                            for (Stock st : listaActualizar) {
                                if (st.getId_stock() == stock.getId_stock())
                                    listaActualizar.remove(st);
                            }
                            listaActualizar.add(stock);
                        }
                        else
                        {
                            Util.alertaShow("Realizar cambios","Error introduzca un numero valido igual o mayor que 0 ", Alert.AlertType.WARNING);
                            tableViewStock.refresh();
                        }
                    }catch(Exception e){
                        Util.alertaShow("Realizar cambios","Error introduzca un numero ", Alert.AlertType.WARNING);
                        tableViewStock.refresh();
                    }
                }
        );




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

    @FXML
    void guardarCambio() {
        if(listaActualizar.size()>0){
        StringBuilder cambios= new StringBuilder("Estos son los cambio a guardar:\n\n");
        for (Stock st:listaActualizar) {
            cambios.append("-").append(st.getId_stock()).append(" nombre: ").append(st.getId_producto()).append(" dirrecion: ").append(st.getCantidad()).append("\n");
        }
        cambios.append("\nFilas afectadas :").append(listaActualizar.size());
        Alert alerta= Util.alertaGet("Realizar cambios", cambios.toString(), Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result = alerta.showAndWait();
        if(result.get() == ButtonType.OK) {
            int afectadas=Logica.getInstance().getDatabase().updateStock(listaActualizar);
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
        tableViewStock.setItems(Logica.getInstance().getDatabase().getStock(Logica.getInstance().getUsuario().getId_centro()));
        listaActualizar.clear();
        tableViewStock.refresh();

    }

}


