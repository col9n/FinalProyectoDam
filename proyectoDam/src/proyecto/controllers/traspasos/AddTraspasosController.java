package proyecto.controllers.traspasos;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import proyecto.Logica.Logica;
import proyecto.modelos.Centro;
import proyecto.modelos.EstadoOrden;
import proyecto.modelos.productos.Producto;
import proyecto.modelos.proveedores.Proveedor;
import proyecto.modelos.traspasos.Ordenes;
import proyecto.util.*;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import static proyecto.util.Util.activacionBotonComboBoxTraspasoAdd;
import static proyecto.util.Util.activacionBotonComboBoxTraspasoEli;

public class AddTraspasosController implements Initializable {

    private ObservableList<Producto> listaProductos = Logica.getInstance().getDatabase().getTodosProductosCantidad(Logica.getInstance().getUsuario().getId_centro());
    private ObservableList<Centro> listaCentro = Logica.getInstance().getDatabase().getTodosCentrosNoPropios(Logica.getInstance().getUsuario().getId_centro());

    @FXML
    private TableView<Producto> tableViewTraspaso;

    @FXML
    private TextField textCantidad;
    @FXML
    private ComboBox<Producto> comboProducto;


    @FXML
    private Button guardar;

    @FXML
    private Button eliminarProducto;

    @FXML
    private Button generarTraspaso;

    @FXML
    private ComboBox<Centro> comboDestino;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        comboProducto.setItems(listaProductos);
        comboDestino.setItems(listaCentro);

        comboProducto.valueProperty().addListener(new ChangeListener<Producto>() {
            @Override
            public void changed(ObservableValue<? extends Producto> observableValue, Producto producto, Producto t1) {
                if(t1!=null)
                textCantidad.setPromptText("Stock disponible: " + t1.getCantidad());
            }
        });





        activacionBotonComboBoxTraspasoAdd(textCantidad, comboProducto, comboDestino, guardar);
        activacionBotonComboBoxTraspasoEli(tableViewTraspaso, generarTraspaso);
        activacionBotonComboBoxTraspasoEli(tableViewTraspaso, eliminarProducto);
    }

    @FXML
    private void addItem(ActionEvent actionEvent) {
        try {
            int cantidad = Integer.parseInt(textCantidad.getText());
            if (cantidad <= comboProducto.getSelectionModel().getSelectedItem().getCantidad() && cantidad > 0) {
                Producto prod = comboProducto.getSelectionModel().getSelectedItem();
                tableViewTraspaso.getItems().remove(prod);
                tableViewTraspaso.refresh();
                prod.setCantidad(cantidad);
                tableViewTraspaso.getItems().add(prod);
                activacionBotonComboBoxTraspasoEli(tableViewTraspaso, generarTraspaso);
                activacionBotonComboBoxTraspasoEli(tableViewTraspaso, eliminarProducto);
            } else {
                Util.alertaShow("Realizar cambios", "Error introduzca un numero  mayor que 0  y menor igual que el stock", Alert.AlertType.WARNING);
            }
        } catch (Exception e) {
            Util.alertaShow("Realizar cambios", "Error introduzca un numero  mayor que 0  y menor igual que el stock", Alert.AlertType.WARNING);
        }
        textCantidad.clear();
    }

    @FXML
    private void eliItem(ActionEvent actionEvent) {
        try {
            Producto producto = tableViewTraspaso.getSelectionModel().getSelectedItem();
            if (producto != null) {
                String borrados = "Eliminar " + producto.getNombre_producto() + " con id " + producto.getId_producto();
                Alert alerta = Util.alertaGet("Realizar borrado", borrados, Alert.AlertType.CONFIRMATION);
                Optional<ButtonType> result = alerta.showAndWait();
                if (result.get() == ButtonType.OK) {
                    tableViewTraspaso.getItems().remove(producto);
                    activacionBotonComboBoxTraspasoEli(tableViewTraspaso, generarTraspaso);
                    activacionBotonComboBoxTraspasoEli(tableViewTraspaso, eliminarProducto);
                }
            }
        } catch (Exception e) {

        }

    }

    @FXML
    private void addTraspasos(ActionEvent actionEvent) {
        int centroDesitno=comboDestino.getSelectionModel().getSelectedItem().getId_centro();
        ObservableList<Producto> listaContenidoOrden=tableViewTraspaso.getItems();

        StringBuilder contenido = new StringBuilder("Estos son los pruductos del traspasos:\n\n");
        for (Producto pro: listaContenidoOrden) {
            contenido.append("-").append(pro.getId_producto()).append(" nombre: ").append(pro.getNombre_producto()).append("\n");
        }
        String listaPor = contenido.toString();
        listaPor=listaPor+"\nLista productos :"+ listaContenidoOrden.size();
        Alert alerta= Util.alertaGet("Realizar traspasos",listaPor, Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result = alerta.showAndWait();
        if(result.get() == ButtonType.OK) {
            int centroOrigen=Logica.getInstance().getUsuario().getId_centro();
            int centroDestino=comboDestino.getSelectionModel().getSelectedItem().getId_centro();
            int creador=Logica.getInstance().getUsuario().getId_usuario();
            LocalDate fecha=java.time.LocalDate.now();
            boolean resultado=Logica.getInstance().getDatabase().generarOrden(new Ordenes(centroOrigen,centroDesitno,fecha,creador,EstadoOrden.EN_CURSO),tableViewTraspaso.getItems());
            if(resultado)
                Util.alertaShow("Crear traspaso", "Traspaso generado", Alert.AlertType.INFORMATION);
            }
            else
                Util.alertaShow("Crear traspaso","Se produjo un error al generar los traspasos", Alert.AlertType.ERROR);
            limpiar();
    }

    private void limpiar(){
        comboProducto.getItems().removeAll(comboProducto.getItems());
        listaProductos=Logica.getInstance().getDatabase().getTodosProductosCantidad(Logica.getInstance().getUsuario().getId_centro());
        comboProducto.setValue(null);
        if(listaProductos.size()>0)
        comboProducto.getItems().setAll(listaProductos);
        tableViewTraspaso.getItems().clear();
        tableViewTraspaso.refresh();
        textCantidad.setPromptText("No puede estar vacio");
    }



}
