package proyecto.modelos;

public class Centro {
    private int id_centro;
    private String direccion;

    public Centro(int id_centro, String direccion) {
        this.id_centro = id_centro;
        this.direccion = direccion;
    }

    public int getId_centro() {
        return id_centro;
    }

    public void setId_centro(int id_centro) {
        this.id_centro = id_centro;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }


    public String verInfor() {
        return "Centro{" +
                "id_centro=" + id_centro +
                ", direccion='" + direccion + '\'' +
                '}';
    }

    @Override
    public String toString() {
        return "ID "+this.id_centro+" direccion "+this.direccion;
    }

}
