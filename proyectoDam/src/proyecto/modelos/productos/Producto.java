package proyecto.modelos.productos;

public class Producto {
    int id_producto;
    String nombre_producto;
    int id_proveedor;
    int cantidad;

    public Producto(int id_producto, String nombre_producto, int id_proveedor) {
        this.id_producto = id_producto;
        this.nombre_producto = nombre_producto;
        this.id_proveedor = id_proveedor;
    }

    public Producto(int id_producto, int cantidad,String nombre_producto) {
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

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
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

    public String verInfor(){
        return "Producto{" +
                "id_producto=" + id_producto +
                ", nombre_producto='" + nombre_producto + '\'' +
                ", id_proveedor=" + id_proveedor +
                '}';
    }
}
