package proyecto.modelos.productos;

public class ProductoImprimir {
    int id_producto;
    String nombre_producto;
    int cantidad;


    public ProductoImprimir(int id_producto, int cantidad, String nombre_producto) {
        this.id_producto = id_producto;
        this.nombre_producto = nombre_producto;
        this.cantidad = cantidad;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }



    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "ID "+this.id_producto+" nombre "+this.nombre_producto;
    }


}
