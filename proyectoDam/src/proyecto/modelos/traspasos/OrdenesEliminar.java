package proyecto.modelos.traspasos;

import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import proyecto.modelos.EstadoOrden;

import java.time.LocalDate;

public class OrdenesEliminar extends Ordenes {
    private BooleanProperty borradoLogico;
    private CheckBox box=new CheckBox();

    public OrdenesEliminar(int id_orden, int centro_salida, int centro_destino, LocalDate localDate, EstadoOrden readyStatus,BooleanProperty borradoLogico) {
        super(id_orden, centro_salida, centro_destino, localDate, readyStatus);
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
