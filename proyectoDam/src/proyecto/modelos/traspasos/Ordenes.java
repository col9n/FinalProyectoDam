package proyecto.modelos.traspasos;

import proyecto.modelos.EstadoOrden;

import java.time.LocalDate;
import java.util.Date;

public class Ordenes {
    private int id_orden;
    private  int centro_salida;
    private  int centro_destino;
    private LocalDate fecha;
    private int id_usuario;
    private EstadoOrden estado;

    public Ordenes(int id_orden, int centro_salida, int centro_destino, LocalDate fecha, int id_usuario, EstadoOrden estado) {
        this.id_orden = id_orden;
        this.centro_salida = centro_salida;
        this.centro_destino = centro_destino;
        this.fecha = fecha;
        this.id_usuario = id_usuario;
        this.estado = estado;
    }

    public Ordenes(int centro_salida, int centro_destino, LocalDate fecha, int id_usuario, EstadoOrden estado) {
        this.centro_salida = centro_salida;
        this.centro_destino = centro_destino;
        this.fecha = fecha;
        this.id_usuario = id_usuario;
        this.estado = estado;
    }

    public Ordenes(int id_orden, int centro_salida, int centro_destino, LocalDate localDate, EstadoOrden readyStatus) {
        this.id_orden=id_orden;
        this.centro_salida = centro_salida;
        this.centro_destino = centro_destino;
        this.fecha = localDate;
        this.estado = readyStatus;
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

    public EstadoOrden getEstado() {
        return estado;
    }

    public void setEstado(EstadoOrden estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Ordenes{" +
                "id_orden=" + id_orden +
                ", centro_salida=" + centro_salida +
                ", centro_destino=" + centro_destino +
                ", fecha=" + fecha +
                ", id_usuario=" + id_usuario +
                ", estado=" + estado +
                '}';
    }
}
