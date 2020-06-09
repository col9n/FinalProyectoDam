package jasperReport;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrdenesImprimir {
    private int id_orden;
    private  int centro_salida;
    private  int centro_destino;
    private LocalDate fecha;
    private int id_usuario;
    private List<ProductoImprimir> listaProductos = new ArrayList<>();


    public OrdenesImprimir(int id_orden, int centro_salida, int centro_destino, LocalDate fecha, int id_usuario) {
        this.id_orden = id_orden;
        this.centro_salida = centro_salida;
        this.centro_destino = centro_destino;
        this.fecha = fecha;
        this.id_usuario = id_usuario;
    }

    public List<ProductoImprimir> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<ProductoImprimir> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public int getId_orden() {
        return id_orden;
    }

    public void setId_orden(int id_orden) {
        this.id_orden = id_orden;
    }

    public int getCentro_salida() {
        return centro_salida;
    }

    public void setCentro_salida(int centro_salida) {
        this.centro_salida = centro_salida;
    }

    public int getCentro_destino() {
        return centro_destino;
    }

    public void setCentro_destino(int centro_destino) {
        this.centro_destino = centro_destino;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }



    @Override
    public String toString() {
        return "Ordenes{" +
                "id_orden=" + id_orden +
                ", centro_salida=" + centro_salida +
                ", centro_destino=" + centro_destino +
                ", fecha=" + fecha +
                ", id_usuario=" + id_usuario+
                '}';
    }
}
