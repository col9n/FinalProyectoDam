package jasperReport;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrdenesFactory {
    public static List<OrdenesImprimir> createListOrdenes()
    {

        List<OrdenesImprimir> listaOrdenes = new ArrayList<>();
        List<ProductoImprimir> productos= new ArrayList<>();
        productos.add(new ProductoImprimir(1,23,"Boli"));
        productos.add(new ProductoImprimir(2,8,"Casa"));
        productos.add(new ProductoImprimir(3,7,"Moto"));
        productos.add(new ProductoImprimir(4,3,"Coche"));
        productos.add(new ProductoImprimir(5,3,"Avion"));
        OrdenesImprimir ordenes =new OrdenesImprimir(1,1,2,java.time.LocalDate.now(),4);
        ordenes.setListaProductos(productos);
        listaOrdenes.add(ordenes);



        return listaOrdenes;
    }
}
