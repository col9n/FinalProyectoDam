package proyecto.modelos.proveedores;

import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;

public class ProveedorEliminar extends Proveedor {
    private BooleanProperty borradoLogico;
    private CheckBox box=new CheckBox();

    public ProveedorEliminar(int id_proveedor, String nombre_proveedor, String direccion_proveedor, BooleanProperty borradoLogico) {
      super(id_proveedor,nombre_proveedor,direccion_proveedor);
        this.borradoLogico = borradoLogico;
        box.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue)
                {
                    setBorradoLogico(false);
                }
                if (!newValue)
                {
                    setBorradoLogico(true);
                }
            }
        });
    }


    public boolean isBorradoLogico() {
        return borradoLogico.get();
    }

    public BooleanProperty borradoLogicoProperty() {
        return borradoLogico;
    }

    public void setBorradoLogico(boolean borradoLogico) {
        this.borradoLogico.set(borradoLogico);
    }


    public CheckBox getBox() {
        return box;
    }

    public void setBox(CheckBox box) {
        this.box = box;
    }

    @Override
    public String toString() {
        return super.toString() +
                "{ borradoLogico=" + borradoLogico +
                '}';
    }
}
