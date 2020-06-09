package proyecto.controllers.productos;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import proyecto.Logica.Logica;
import proyecto.modelos.productos.Producto;
import proyecto.modelos.productos.ProductoEliminar;
import proyecto.modelos.proveedores.Proveedor;
import proyecto.util.Util;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static proyecto.util.Util.enableButtonListaContine;

public class EliProductoController implements Initializable {
    private ObservableList<ProductoEliminar> productosObservableList =Logica.getInstance().getDatabase().getTodosProductosEliminar();
    private List <ProductoEliminar> listaBorrar = new <Proveedor> ArrayList();

    @FXML
    private TableView<ProductoEliminar> tableViewProveedor;

    @FXML
    private ComboBox<String> combobox;

    @FXML
    private TextField textProveedor;

    @FXML
    private Button eliminar;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableViewProveedor.setItems(productosObservableList);

        textProveedor.textProperty().addListener((observable, oldValue, newValue) -> filtrarLista());

        productosObservableList.addListener((ListChangeListener<Producto>) c -> {
            while (c.next()) {
                if (c.wasUpdated()) {
                    for (int i = c.getFrom(); i < c.getTo(); ++i) {
                        ProductoEliminar prov = productosObservableList.get(i);
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
        ObservableList<ProductoEliminar> listaFiltrada= FXCollections.observableArrayList();
        if(opcion.equalsIgnoreCase("Todo"))
        {
            for (ProductoEliminar producto: productosObservableList) {
                if(Util.stringToMayus(producto.toString()).contains(Util.stringToMayus(textProveedor.getText())))
                    listaFiltrada.add(producto);
            }
        }
        if(opcion.equalsIgnoreCase("ID producto"))
        {
            for (ProductoEliminar producto: productosObservableList) {
                if(Util.stringToMayus(String.valueOf(producto.getId_producto())).contains(Util.stringToMayus(textProveedor.getText())))
                    listaFiltrada.add(producto);
            }
        }
        if(opcion.equalsIgnoreCase("Nombre"))
        {
            for (ProductoEliminar producto: productosObservableList) {
                if(Util.stringToMayus(producto.getNombre_producto()).contains(Util.stringToMayus(textProveedor.getText())))
                    listaFiltrada.add(producto);
            }
        }

        if(opcion.equalsIgnoreCase("ID proveedor"))
        {
            for (ProductoEliminar producto: productosObservableList) {
                if(Util.stringToMayus(String.valueOf(producto.getId_proveedor())).contains(Util.stringToMayus(textProveedor.getText())))
                    listaFiltrada.add(producto);
            }
        }
        tableViewProveedor.setItems(listaFiltrada);
    }


    @FXML
    void eliminar() {
        StringBuilder borradosBuilder = new StringBuilder("Estos son los proveedores a borrar:\n\n");
        for (Producto pro: listaBorrar) {
            borradosBuilder.append("-").append(pro.getId_producto()).append(" nombre: ").append(Util.stringToMayus(pro.getNombre_producto())).append(" id proveedor: ").append(pro.getId_proveedor()).append("\n");
            }
        String borrados = borradosBuilder.toString();
        borrados=borrados+"\nFilas afectadas :"+ listaBorrar.size();
            Alert alerta= Util.alertaGet("Realizar borrado",borrados, Alert.AlertType.CONFIRMATION);
            Optional<ButtonType> result = alerta.showAndWait();
            if(result.get() == ButtonType.OK) {
                int afectadas=Logica.getInstance().getDatabase().deleteProductos(listaBorrar);
                if(afectadas!= listaBorrar.size())
                {
                    Util.alertaShow("Realizar borrado","Se produjo un error en el borrado", Alert.AlertType.ERROR);
                }
                else
                    Util.alertaShow("Realizar borrado","Numero de borrados realizados: "+ listaBorrar.size()+" ", Alert.AlertType.INFORMATION);
                actualizarTableView();
            }
    }

    private void actualizarTableView(){
        for(ProductoEliminar prov:listaBorrar)
        {
            productosObservableList.remove(prov);
        }
        listaBorrar.clear();
       enableButtonListaContine(listaBorrar,eliminar);
    }

}
