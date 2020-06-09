package proyecto.modelos.stock;

public class Stock {
    private int id_stock;
    private int id_centro;
    private int id_producto;
    private String cantidad;

    public Stock(int id_stock, int id_centro, int id_producto, String cantidad) {
        this.id_stock = id_stock;
        this.id_centro = id_centro;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
    }

    public int getId_stock() {
        return id_stock;
    }

    public void setId_stock(int id_stock) {
        this.id_stock = id_stock;
    }

    public int getId_centro() {
        return id_centro;
    }

    public void setId_centro(int id_centro) {
        this.id_centro = id_centro;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id_stock=" + id_stock +
                ", id_producto=" + id_producto +
                ", cantidad=" + cantidad +
                '}';
    }
}
