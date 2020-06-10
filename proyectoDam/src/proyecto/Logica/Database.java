package proyecto.Logica;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.util.Callback;
import proyecto.modelos.*;
import proyecto.modelos.productos.Producto;
import proyecto.modelos.productos.ProductoEliminar;
import proyecto.modelos.productos.ProductoImprimir;
import proyecto.modelos.proveedores.Proveedor;
import proyecto.modelos.proveedores.ProveedorEliminar;
import proyecto.modelos.stock.Stock;
import proyecto.modelos.traspasos.Ordenes;
import proyecto.modelos.traspasos.OrdenesEliminar;
import proyecto.modelos.traspasos.OrdenesImprimir;
import proyecto.util.Util;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Database
 * @author Eduardo
 *
 */
public class Database {


  private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
  private static  String DB_CONNECTION = "jdbc:mysql://ec2-3-86-143-17.compute-1.amazonaws.com:3306/proyectodam?autoReconnect=true&useSSL=false";
  //private static  String DB_CONNECTION = "jdbc:mysql://localhost/proyectodam?autoReconnect=true&useSSL=false";
  private static  String DB_USER = "root";
  private static  String DB_PASSWORD = "mypass123";
 //private static  String DB_PASSWORD = "root";

  public Database() {

  }

  private static Connection getDBConnection() {
    Connection connection = null;
    try {
      Class.forName(DB_DRIVER);
    } catch (ClassNotFoundException exception) {
    }

    try {
      connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
      return connection;
    } catch (SQLException exception) {
      Util.alertaShow("Error de conexion a la base de datos", "No se puede conectar con la base de datos", Alert.AlertType.ERROR);
    }

    return connection;
  }

  public boolean userExist(String user) {
    Connection connection = null;
    PreparedStatement psSQL = null;
    try {
      connection = Database.getDBConnection();
      connection.setAutoCommit(false);
      psSQL = connection.prepareStatement("SELECT `id_usuario`,`nombre_usuario`,`apellido1`,`apellido2`,`usuario`,`id_centro`,`borradoLogico` FROM `usuarios` where `usuario` = ? ");
      psSQL.setString(1, user);
      ResultSet rs = psSQL.executeQuery();
      while (rs.next()) {
        if (rs.getBoolean("borradoLogico") == true)
          return false;
        int id_usuario = rs.getInt("id_usuario");
        String nombre_usuario = rs.getString("nombre_usuario");
        String apellido1 = rs.getString("apellido1");
        String apellido2 = rs.getString("apellido2");
        String usuario = rs.getString("usuario");
        int id_centro = rs.getInt("id_centro");

        return true;
      }
      return false;
    } catch (SQLException exception) {
    } finally {
      if (null != psSQL) {
        try {
          psSQL.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      if (null != connection) {
        try {
          connection.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    return false;
  }

  public boolean loginUser(String user, String pass) {
    Connection connection = null;
    PreparedStatement psSQL = null;
    try {
      connection = Database.getDBConnection();
      connection.setAutoCommit(false);
      psSQL = connection.prepareStatement("SELECT `id_usuario`,`nombre_usuario`,`apellido1`,`apellido2`,`usuario`,`id_centro`,`borradoLogico` FROM `usuarios` where `usuario` = ? and pass=MD5(?);");
      psSQL.setString(1, user);
      psSQL.setString(2, pass);
      ResultSet rs = psSQL.executeQuery();
      while (rs.next()) {
        if (rs.getBoolean("borradoLogico") == true)
          return false;
        int id_usuario = rs.getInt("id_usuario");
        String nombre_usuario = rs.getString("nombre_usuario");
        String apellido1 = rs.getString("apellido1");
        String apellido2 = rs.getString("apellido2");
        String usuario = rs.getString("usuario");
        int id_centro = rs.getInt("id_centro");

        Usuario usuario1 = new Usuario(id_usuario, nombre_usuario, apellido1, apellido2, usuario, id_centro);
        Logica.getInstance().setUsuario(usuario1);
        return true;
      }
      return false;
    } catch (SQLException exception) {
    } finally {
      if (null != psSQL) {
        try {
          psSQL.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      if (null != connection) {
        try {
          connection.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    return false;
  }

  public int addUser(String user, String pass, String nombre, String apellido1, String apellido2, int centro) {
    Connection connection = null;
    PreparedStatement psInsertar = null;
    int rs = 0;
    try {
      connection = Database.getDBConnection();
      connection.setAutoCommit(false);
      psInsertar = connection.prepareStatement("INSERT INTO `usuarios` ( `nombre_usuario`, `apellido1`, `apellido2`, `usuario`, `pass`, `id_centro`, `borradoLogico`) VALUES (?, ?, ?, ?, MD5(?), ?,0)");
      psInsertar.setString(1, nombre);
      psInsertar.setString(2, apellido1);
      psInsertar.setString(3, apellido2);
      psInsertar.setString(4, user);
      psInsertar.setString(5, pass);
      psInsertar.setInt(6, centro);
      rs = psInsertar.executeUpdate();
      connection.commit();
      return rs;
    } catch (SQLException exception) {
    } finally {
      if (null != psInsertar) {
        try {
          psInsertar.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      if (null != connection) {
        try {
          connection.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    return rs;
  }


  public boolean proveedorExists(String proveedor) {
    Connection connection = null;
    PreparedStatement psSQL = null;
    try {
      connection = Database.getDBConnection();
      connection.setAutoCommit(false);
      psSQL = connection.prepareStatement("SELECT `borradoLogico` FROM `proveedores` where upper(nombre_proveedor)=upper(?)");
      psSQL.setString(1, proveedor);

      ResultSet rs = psSQL.executeQuery();
      while (rs.next()) {
        if (rs.getBoolean("borradoLogico") == true)
          return false;
        return true;
      }
      return false;
    } catch (SQLException exception) {
    } finally {
      if (null != psSQL) {
        try {
          psSQL.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      if (null != connection) {
        try {
          connection.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    return false;
  }

  public ObservableList<Proveedor> getTodosProveedores() {
    Connection connection = null;
    PreparedStatement statement = null;
    ObservableList<Proveedor> listaProveedores = FXCollections.observableArrayList();
    try {
      connection = Database.getDBConnection();
      connection.setAutoCommit(false);
      Statement stmt = connection.createStatement();
      String sql = "SELECT `id_proveedor`,`nombre_proveedor`,`direccion_proveedor`,`borradoLogico` FROM `proveedores`  where  borradoLogico=0 order by id_proveedor";

      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        if (rs.getBoolean("borradoLogico") == false) {
          int id_proveedor = rs.getInt("id_proveedor");
          String nombre_proveedor = rs.getString("nombre_proveedor");
          String direccion_proveedor = rs.getString("direccion_proveedor");
          listaProveedores.add(new Proveedor(id_proveedor, nombre_proveedor, direccion_proveedor));
        }
      }
      return listaProveedores;
    } catch (SQLException exception) {
    } finally {
      if (null != statement) {
        try {
          statement.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      if (null != connection) {
        try {
          connection.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    return listaProveedores;
  }

  public ObservableList<ProveedorEliminar> getTodosProveedoresEliminar() {
    Connection connection = null;
    PreparedStatement statement = null;
    ObservableList<ProveedorEliminar> listaProveedores = FXCollections.observableArrayList(
            new Callback<ProveedorEliminar, Observable[]>() {
              @Override
              public Observable[] call(ProveedorEliminar param) {
                return new Observable[]{
                        param.borradoLogicoProperty()
                };
              }
            }
    );
    try {
      connection = Database.getDBConnection();
      connection.setAutoCommit(false);
      Statement stmt = connection.createStatement();
      String sql = "SELECT `id_proveedor`,`nombre_proveedor`,`direccion_proveedor`,`borradoLogico` FROM `proveedores` where  borradoLogico=0 order by id_proveedor";

      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        int id_proveedor = rs.getInt("id_proveedor");
        String nombre_proveedor = rs.getString("nombre_proveedor");
        String direccion_proveedor = rs.getString("direccion_proveedor");
        BooleanProperty borradoLogico = new SimpleBooleanProperty(true);
        listaProveedores.add(new ProveedorEliminar(id_proveedor, nombre_proveedor, direccion_proveedor, borradoLogico));
      }
      return listaProveedores;
    } catch (SQLException exception) {
    } finally {
      if (null != statement) {
        try {
          statement.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      if (null != connection) {
        try {
          connection.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    return listaProveedores;
  }

  public int addProveedor(String nombre, String direccion) {
    Connection connection = null;
    PreparedStatement psInsertar = null;
    int rs = 0;
    try {
      connection = Database.getDBConnection();
      connection.setAutoCommit(false);
      psInsertar = connection.prepareStatement("INSERT INTO `proveedores` (`nombre_proveedor`, `direccion_proveedor`, `borradoLogico`) VALUES (?, ?, '0')");
      psInsertar.setString(1, nombre);
      psInsertar.setString(2, direccion);
      rs = psInsertar.executeUpdate();
      connection.commit();
      return rs;
    } catch (SQLException exception) {
    } finally {
      if (null != psInsertar) {
        try {
          psInsertar.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      if (null != connection) {
        try {
          connection.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    return rs;
  }

  public int updateProveedor(List<Proveedor> listaActualizar) {
    Connection connection = null;
    PreparedStatement update = null;
    int rs = 0;
    try {
      connection = Database.getDBConnection();
      connection.setAutoCommit(false);
      for (Proveedor prov : listaActualizar) {
        String consulta = "UPDATE `proveedores` SET `nombre_proveedor` = ? ,direccion_proveedor= ? WHERE upper(`id_proveedor`) = upper(?)";
        update = connection.prepareStatement(consulta);
        update.setString(1, prov.getNombre_proveedor());
        update.setString(2, prov.getDireccion_proveedor());
        update.setInt(3, prov.getId_proveedor());

        rs = rs + update.executeUpdate();

      }
      connection.commit();
      return rs;
    } catch (SQLException exception) {
    } finally {
      if (null != update) {
        try {
          update.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      if (null != connection) {
        try {
          connection.rollback();
          connection.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    return rs;
  }

  public int deleteProveedores(List<ProveedorEliminar> listaBorrados) {
    Connection connection = null;
    PreparedStatement update = null;
    int rs = 0;
    try {
      connection = Database.getDBConnection();
      connection.setAutoCommit(false);
      for (Proveedor prov : listaBorrados) {

        String consulta = "UPDATE `proveedores` SET `borradoLogico`=1 WHERE `id_proveedor`=?";
        update = connection.prepareStatement(consulta);
        update.setInt(1, prov.getId_proveedor());

        rs = rs + update.executeUpdate();

      }
      connection.commit();
      return rs;
    } catch (SQLException exception) {
    } finally {
      if (null != update) {
        try {
          update.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      if (null != connection) {
        try {
          connection.rollback();
          connection.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    return rs;
  }


  public boolean productoExists(String producto) {
    Connection connection = null;
    PreparedStatement psSQL = null;
    try {
      connection = Database.getDBConnection();
      connection.setAutoCommit(false);
      psSQL = connection.prepareStatement("SELECT `borradoLogico` FROM `productos` where upper(nombre_producto)=upper(?) ");
      psSQL.setString(1, producto);

      ResultSet rs = psSQL.executeQuery();
      while (rs.next()) {
        if (rs.getBoolean("borradoLogico") == true)
          return false;
        return true;
      }
      return false;
    } catch (SQLException exception) {
    } finally {
      if (null != psSQL) {
        try {
          psSQL.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      if (null != connection) {
        try {
          connection.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    return false;
  }

  public ObservableList<Producto> getTodosProductos() {
    Connection connection = null;
    PreparedStatement statement = null;
    ObservableList<Producto> listaProveedores = FXCollections.observableArrayList();
    try {
      connection = Database.getDBConnection();
      connection.setAutoCommit(false);
      Statement stmt = connection.createStatement();
      String sql = "SELECT `id_producto`,`nombre_producto`,`id_proveedor`,`borradoLogico` FROM `productos`  where  borradoLogico=0 order by id_producto";

      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        if (rs.getBoolean("borradoLogico") == false) {
          int id_producto = rs.getInt("id_producto");
          String nombre_producto = rs.getString("nombre_producto");
          int id_proveedor = rs.getInt("id_proveedor");
          listaProveedores.add(new Producto(id_producto, nombre_producto, id_proveedor));
        }
      }
      return listaProveedores;
    } catch (SQLException exception) {
    } finally {
      if (null != statement) {
        try {
          statement.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      if (null != connection) {
        try {
          connection.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    return listaProveedores;
  }

  public ObservableList<ProductoEliminar> getTodosProductosEliminar() {
    Connection connection = null;
    PreparedStatement statement = null;
    ObservableList<ProductoEliminar> listaProveedores = FXCollections.observableArrayList(
            new Callback<ProductoEliminar, Observable[]>() {
              @Override
              public Observable[] call(ProductoEliminar param) {
                return new Observable[]{
                        param.borradoLogicoProperty()
                };
              }
            }
    );
    try {
      connection = Database.getDBConnection();
      connection.setAutoCommit(false);
      Statement stmt = connection.createStatement();
      String sql = "SELECT `id_producto`,`nombre_producto`,`id_proveedor`,`borradoLogico` FROM `productos` where  borradoLogico=0 order by id_producto";

      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        int id_producto = rs.getInt("id_producto");
        String nombre_producto = rs.getString("nombre_producto");
        int id_proveedor = rs.getInt("id_proveedor");
        BooleanProperty borradoLogico = new SimpleBooleanProperty(true);
        listaProveedores.add(new ProductoEliminar(id_producto, nombre_producto, id_proveedor, borradoLogico));
      }
      return listaProveedores;
    } catch (SQLException exception) {
    } finally {
      if (null != statement) {
        try {
          statement.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      if (null != connection) {
        try {
          connection.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    return listaProveedores;
  }

  public int addProducto(String nombre, int id_proveedor) {
    Connection connection = null;
    PreparedStatement psInsertar = null;
    int rs = 0;
    try {
      connection = Database.getDBConnection();
      connection.setAutoCommit(false);
      psInsertar = connection.prepareStatement("INSERT INTO `productos` (`nombre_producto`, `id_proveedor`,`borradoLogico`) VALUES (?, ?, '0')");
      psInsertar.setString(1, nombre);
      psInsertar.setInt(2, id_proveedor);
      rs = psInsertar.executeUpdate();

      connection.commit();
      psInsertar = connection.prepareStatement("select * from `productos` where( upper(`nombre_producto`)=upper(?) and  `id_proveedor`=?  and borradoLogico=0) ");
      psInsertar.setString(1, nombre);
      psInsertar.setInt(2, id_proveedor);

      ResultSet rst = psInsertar.executeQuery();

      int id_producto = -1;
      while (rst.next()) {
        if (rst.getBoolean("borradoLogico") == false) {
          id_producto = rst.getInt("id_producto");
        }
        insertStock(id_producto, 0);
        return rs;

      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return rs;
  }

  public int updateProducto(List<Producto> listaActualizar) {
    Connection connection = null;
    PreparedStatement update = null;
    int rs = 0;
    try {
      connection = Database.getDBConnection();
      connection.setAutoCommit(false);
      for (Producto prov : listaActualizar) {
        String consulta = "UPDATE `productos` SET `nombre_producto` = upper(?)  WHERE `id_producto` = ?";
        update = connection.prepareStatement(consulta);
        update.setString(1, prov.getNombre_producto());
        update.setInt(2, prov.getId_producto());

        rs = rs + update.executeUpdate();

      }
      connection.commit();
      return rs;
    } catch (SQLException exception) {
    } finally {
      if (null != update) {
        try {
          update.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      if (null != connection) {
        try {
          connection.rollback();
          connection.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    return rs;
  }

  public int deleteProductos(List<ProductoEliminar> listaBorrados) {
    Connection connection = null;
    PreparedStatement update = null;
    int rs = 0;
    try {
      connection = Database.getDBConnection();
      connection.setAutoCommit(false);
      for (Producto prov : listaBorrados) {

        String consulta = "UPDATE `productos` SET `borradoLogico`=1 WHERE `id_producto` = ?";
        update = connection.prepareStatement(consulta);
        update.setInt(1, prov.getId_producto());

        rs = rs + update.executeUpdate();

      }
      connection.commit();
      return rs;
    } catch (SQLException exception) {
    } finally {
      if (null != update) {
        try {
          update.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      if (null != connection) {
        try {
          connection.rollback();
          connection.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    return rs;
  }


  public ObservableList<Stock> getStock(int centro) {
    ObservableList<Stock> listaProveedores = FXCollections.observableArrayList();
    Connection connection = null;
    PreparedStatement psSQL = null;
    try {
      connection = Database.getDBConnection();
      connection.setAutoCommit(false);
      psSQL = connection.prepareStatement("SELECT `id_stock`, `id_centro`, `id_producto`, `cantidad`, `borradoLogico` FROM `stock` WHERE id_centro=? and (borradoLogico=0) order by id_stock");
      psSQL.setInt(1, centro);
      ResultSet rs = psSQL.executeQuery();
      while (rs.next()) {
        int id_stock = rs.getInt("id_stock");
        int id_centro = rs.getInt("id_centro");
        int id_producto = rs.getInt("id_producto");
        String cantidad = rs.getString("cantidad");
        BooleanProperty borradoLogico = new SimpleBooleanProperty(true);
        listaProveedores.add(new Stock(id_stock, id_centro, id_producto, cantidad));
      }
      return listaProveedores;
    } catch (SQLException exception) {
    } finally {
      if (null != psSQL) {
        try {
          psSQL.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      if (null != connection) {
        try {
          connection.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    return listaProveedores;
  }

  public int updateStock(List<Stock> listaActualizar) {
    Connection connection = null;
    PreparedStatement update = null;
    int rs = 0;
    try {
      connection = Database.getDBConnection();
      connection.setAutoCommit(false);
      for (Stock stock : listaActualizar) {
        String consulta = "UPDATE `stock` SET `cantidad` = ? WHERE upper(`id_stock`) = upper(?)";
        update = connection.prepareStatement(consulta);
        update.setString(1, stock.getCantidad());
        update.setInt(2, stock.getId_stock());


        rs = rs + update.executeUpdate();

      }
      connection.commit();
      return rs;
    } catch (SQLException exception) {
    } finally {
      if (null != update) {
        try {
          update.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      if (null != connection) {
        try {
          connection.rollback();
          connection.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    return rs;
  }


  public ObservableList<Producto> getTodosProductosCantidad(int centro) {
    ObservableList<Producto> listaProductos = FXCollections.observableArrayList();
    Connection connection = null;
    PreparedStatement psSQL = null;
    try {
      connection = Database.getDBConnection();
      connection.setAutoCommit(false);
      psSQL = connection.prepareStatement(" select a.id_producto,a.nombre_producto,b.cantidad from productos a,stock b where (a.id_producto=b.id_producto) and (b.id_centro=?) and (a.borradoLogico=0) and (b.borradoLogico=0) and (b.cantidad>0) order by a.id_producto");
      psSQL.setInt(1, centro);
      ResultSet rs = psSQL.executeQuery();
      while (rs.next()) {
        int id_producto = rs.getInt("a.id_producto");
        String nombre_producto = rs.getString("a.nombre_producto");
        int cantidad = rs.getInt("b.cantidad");
        listaProductos.add(new Producto(id_producto, cantidad, nombre_producto));
      }
      return listaProductos;
    } catch (SQLException exception) {
    } finally {
      if (null != psSQL) {
        try {
          psSQL.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      if (null != connection) {
        try {
          connection.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    return listaProductos;
  }

  public ObservableList<Centro> getTodosCentrosNoPropios(int centro) {
    ObservableList<Centro> listaCentro = FXCollections.observableArrayList();
    Connection connection = null;
    PreparedStatement psSQL = null;
    try {
      connection = Database.getDBConnection();
      connection.setAutoCommit(false);
      psSQL = connection.prepareStatement("select id_centro,direccion_centro from centros a where (id_centro!=?) and (borradoLogico=0) order by id_centro");
      psSQL.setInt(1, centro);
      ResultSet rs = psSQL.executeQuery();
      while (rs.next()) {
        int id_centro = rs.getInt("id_centro");
        String direccion = rs.getString("direccion_centro");
        listaCentro.add(new Centro(id_centro, direccion));
      }
      return listaCentro;
    } catch (SQLException exception) {
    } finally {
      if (null != psSQL) {
        try {
          psSQL.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      if (null != connection) {
        try {
          connection.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    return listaCentro;
  }

  public ObservableList<Centro> getTodosCentros() {
    ObservableList<Centro> listaCentro = FXCollections.observableArrayList();
    Connection connection = null;
    PreparedStatement psSQL = null;
    try {
      connection = Database.getDBConnection();
      connection.setAutoCommit(false);
      psSQL = connection.prepareStatement("select id_centro,direccion_centro from centros a where (borradoLogico=0) order by id_centro");
      ResultSet rs = psSQL.executeQuery();
      while (rs.next()) {
        int id_centro = rs.getInt("id_centro");
        String direccion = rs.getString("direccion_centro");
        listaCentro.add(new Centro(id_centro, direccion));
      }
      return listaCentro;
    } catch (SQLException exception) {
    } finally {
      if (null != psSQL) {
        try {
          psSQL.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      if (null != connection) {
        try {
          connection.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    return listaCentro;
  }


  public boolean generarOrden(Ordenes orden, ObservableList<Producto> lista) {
    Connection connection = Database.getDBConnection();
    Savepoint savepoint = null;
    int ordenContenido = -1;
    try {
      savepoint = connection.setSavepoint();

      PreparedStatement psInsertar = null;
      try {
        connection.setAutoCommit(false);
        psInsertar = connection.prepareStatement("INSERT INTO `ordenes` ( `centro_salida`, `centro_destino`, `fecha`, `id_usuario`, `estado`, `borradoLogico`) VALUES ( ?, ?, ?, ?, ?, '0')");
        psInsertar.setInt(1, orden.getCentro_salida());
        psInsertar.setInt(2, orden.getCentro_destino());
        psInsertar.setDate(3, Date.valueOf(orden.getFecha()));
        psInsertar.setInt(4, orden.getId_usuario());
        psInsertar.setString(5, orden.getEstado().toString());
        psInsertar.executeUpdate();
        connection.commit();
      } finally {
        if (psInsertar != null) {
          psInsertar.close();
        }
      }

      PreparedStatement psSQL = null;
      try {
        connection = Database.getDBConnection();
        connection.setAutoCommit(false);
        psSQL = connection.prepareStatement("Select max(id_orden) from ordenes WHERE id_usuario=" + Logica.getInstance().getUsuario().getId_usuario());

        ResultSet rs = psSQL.executeQuery();
        while (rs.next()) {
          ordenContenido = rs.getInt(1);
        }
        connection.commit();
      } finally {
        if (psSQL != null) {
          psSQL.close();
        }
      }
      PreparedStatement psInsertarContenido = null;
      try {
        connection = Database.getDBConnection();
        connection.setAutoCommit(false);
        for (Producto prod : lista) {
          psInsertarContenido = connection.prepareStatement("INSERT INTO `contenidoorden` ( `id_orden`, `id_producto`, `cantidadorden`, `borradoLogico`) VALUES (?, ?, ?, '0')");
          psInsertarContenido.setInt(1, ordenContenido);
          psInsertarContenido.setInt(2, prod.getId_producto());
          psInsertarContenido.setInt(3, prod.getCantidad());
        }
        psInsertarContenido.executeUpdate();
        connection.commit();
      } finally {
        if (psInsertarContenido != null) {
          psInsertarContenido.close();
        }
      }

      PreparedStatement updateStock = null;
      try {
        connection = Database.getDBConnection();
        connection.setAutoCommit(false);
        for (Producto prod : lista) {
          String consulta = "UPDATE `stock` SET `cantidad` = cantidad-? WHERE (upper(`id_producto`) = upper(?)) and (upper(`id_centro`)=upper(?))";
          updateStock = connection.prepareStatement(consulta);
          updateStock.setInt(1, prod.getCantidad());
          updateStock.setInt(2, prod.getId_producto());
          updateStock.setInt(3, orden.getCentro_salida());
          updateStock.executeUpdate();
          connection.commit();
        }
      } finally {
        if (updateStock != null) {
          updateStock.close();
        }
      }


      return true;
    } catch (SQLException e) {
      try {
        connection.rollback(savepoint);
      } catch (SQLException ex) {
      }
    } finally {
      try {
        connection.close();
      } catch (SQLException e) {
      }
    }
    return false;
  }

  public ObservableList<Ordenes> getTodasOrdenes(int centroUsuario) {
    Connection connection = null;
    PreparedStatement statement = null;
    ObservableList<Ordenes> listaOrdenes = FXCollections.observableArrayList();
    try {
      connection = Database.getDBConnection();
      connection.setAutoCommit(false);
      Statement stmt = connection.createStatement();
      String sql = "SELECT * FROM `ordenes` WHERE ((`centro_salida`=" + centroUsuario + ") or (`centro_destino`=" + centroUsuario + ") and borradoLogico=0)";

      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        if (rs.getBoolean("borradoLogico") == false) {
          int id_orden = rs.getInt("id_orden");
          int centro_salida = rs.getInt("centro_salida");
          int centro_destino = rs.getInt("centro_destino");

          LocalDate date = rs.getDate("fecha").toLocalDate();
          EstadoOrden readyStatus = EstadoOrden.valueOf(rs.getString("estado"));
          listaOrdenes.add(new Ordenes(id_orden, centro_salida, centro_destino, date, readyStatus));
        }
      }
      return listaOrdenes;
    } catch (SQLException exception) {
    } finally {
      if (null != statement) {
        try {
          statement.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      if (null != connection) {
        try {
          connection.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    return listaOrdenes;
  }

  public ObservableList<OrdenesEliminar> getTodasOrdenesEliminar(int id_centro) {
    Connection connection = null;
    PreparedStatement psSQL = null;
    ObservableList<OrdenesEliminar> listaOrdenes = FXCollections.observableArrayList(
            new Callback<OrdenesEliminar, Observable[]>() {
              @Override
              public Observable[] call(OrdenesEliminar param) {
                return new Observable[]{
                        param.borradoLogicoProperty()
                };
              }
            }
    );
    try {
      connection = Database.getDBConnection();
      connection.setAutoCommit(false);


      psSQL = connection.prepareStatement("SELECT * FROM `ordenes`  where `estado`!='FINALIZADO' and borradoLogico=0 and centro_destino=?");
      psSQL.setInt(1, id_centro);
      ResultSet rs = psSQL.executeQuery();
      while (rs.next()) {
        if (rs.getBoolean("borradoLogico") == false) {
          int id_orden = rs.getInt("id_orden");
          int centro_salida = rs.getInt("centro_salida");
          int centro_destino = rs.getInt("centro_destino");
          LocalDate date = rs.getDate("fecha").toLocalDate();
          EstadoOrden readyStatus = EstadoOrden.valueOf(rs.getString("estado"));
          BooleanProperty borradoLogico = new SimpleBooleanProperty(true);
          listaOrdenes.add(new OrdenesEliminar(id_orden, centro_salida, centro_destino, date, readyStatus, borradoLogico));
        }
      }
      return listaOrdenes;
    } catch (SQLException exception) {
    } finally {
      if (null != psSQL) {
        try {
          psSQL.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      if (null != connection) {
        try {
          connection.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    return listaOrdenes;
  }

  public boolean recibirTraspasos(List<OrdenesEliminar> listaRecibir) {
    Connection connection = Database.getDBConnection();
    Savepoint savepoint = null;
    int centroActual = Logica.getInstance().getUsuario().getId_centro();
    int ordenContenido = -1;
    int productoExiste = 0;

    try {

      savepoint = connection.setSavepoint();

      PreparedStatement psSQL = null;
      try {
        connection = Database.getDBConnection();
        connection.setAutoCommit(false);
        for (OrdenesEliminar orden : listaRecibir) {
          String consulta = ("select * from contenidoorden  where `id_orden`=?");
          psSQL = connection.prepareStatement(consulta);
          psSQL.setInt(1, orden.getId_orden());
          ResultSet rs = psSQL.executeQuery();
          while (rs.next()) {
            ordenContenido = (rs.getInt(2));
            int idprod = (rs.getInt(3));
            int cantidad = rs.getInt(4);


            String existe = "select EXISTS(select 1 from `stock` where  ((`id_producto` = " + idprod + ") and (upper(`id_centro`)=upper(" + centroActual + "))))";
            psSQL = connection.prepareStatement(existe);
            ResultSet rsexite = psSQL.executeQuery();
            while (rsexite.next()) {
              productoExiste = (rsexite.getInt(1));
            }

            if (productoExiste == 1) {

              String update = "UPDATE `stock` SET `cantidad` = cantidad+? WHERE (upper(`id_producto`) = upper(?)) and (upper(`id_centro`)=upper(" + centroActual + "))";
              psSQL = connection.prepareStatement(update);
              psSQL.setInt(1, idprod);
              psSQL.setInt(2, cantidad);
              psSQL.executeUpdate();

            } else {
              String update = " INSERT INTO `stock` (`id_centro`, `id_producto`, `cantidad`, `borradoLogico`) VALUES (" + centroActual + ", ?, ?, '0')";
              psSQL = connection.prepareStatement(update);
              psSQL.setInt(1, idprod);
              psSQL.setInt(2, cantidad);
              psSQL.executeUpdate();
            }


            String update = "UPDATE `ordenes` SET `estado` = '" + EstadoOrden.FINALIZADO + "' WHERE upper(`id_orden`) = ?";
            psSQL = connection.prepareStatement(update);
            psSQL.setInt(1, ordenContenido);
            psSQL.executeUpdate();

          }
          connection.commit();
        }

        return true;


      } catch (SQLException e) {
        try {
          connection.rollback(savepoint);
        } catch (SQLException ex) {
        }
      } finally {
        try {
          connection.close();
        } catch (SQLException e) {
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }

  public void insertStock(int id_prod, int cantidad) {
    Connection connection = Database.getDBConnection();
    int centroActual = Logica.getInstance().getUsuario().getId_centro();

    PreparedStatement psSQL = null;
    try {
      connection.setAutoCommit(true);

      String update = " INSERT INTO `stock` (`id_centro`, `id_producto`, `cantidad`, `borradoLogico`) VALUES (" + centroActual + ", ?, ?, '0')";
      psSQL = connection.prepareStatement(update);
      psSQL.setInt(1, id_prod);
      psSQL.setInt(2, cantidad);
      psSQL.executeUpdate();
      connection.commit();
    } catch (SQLException exception) {
    } finally {
      if (null != psSQL) {
        try {
          psSQL.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      if (null != connection) {
        try {
          connection.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public int centroExsist(String nombre) {
    Connection connection = null;
    PreparedStatement psSQL = null;
    int existe=0;
    try {
      connection = Database.getDBConnection();
      connection.setAutoCommit(false);
      psSQL = connection.prepareStatement("select EXISTS(SELECT 1 FROM `centros` where upper(`direccion_centro`) = upper(?))");
      psSQL.setString(1, nombre);
      ResultSet rsexite = psSQL.executeQuery();
      while (rsexite.next()) {
        existe=(rsexite.getInt(1));
      }
      return existe;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return existe;

  }

  public int addCentro(String nombre) {
    Connection connection = null;
    PreparedStatement psInsertar = null;
    int rs = 0;
    try {
      connection = Database.getDBConnection();
      connection.setAutoCommit(false);
      psInsertar = connection.prepareStatement("INSERT INTO `centros` (`direccion_centro`, `borradoLogico`) VALUES (?,'0')");
      psInsertar.setString(1, nombre);
      rs = psInsertar.executeUpdate();
      connection.commit();
      return rs;
    } catch (SQLException exception) {
    } finally {
      if (null != psInsertar) {
        try {
          psInsertar.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      if (null != connection) {
        try {
          connection.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    return rs;
  }

  public ObservableList<Usuario> getTodosUsuario() {
    ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();
    Connection connection = null;
    PreparedStatement psSQL = null;
    try {
      connection = Database.getDBConnection();
      connection.setAutoCommit(false);
      psSQL = connection.prepareStatement("select id_usuario,nombre_usuario,apellido1,apellido2 from usuarios a where (borradoLogico=0) order by id_centro");
      ResultSet rs = psSQL.executeQuery();
      while (rs.next()) {
        int id_usuario = rs.getInt("id_usuario");
        String nombre = rs.getString("nombre_usuario");
        String ape1 = rs.getString("apellido1");
        String ape2 = rs.getString("apellido2");
        listaUsuarios.add(new Usuario(id_usuario, nombre, ape1, ape2));
      }
      return listaUsuarios;
    } catch (SQLException exception) {
    } finally {
      if (null != psSQL) {
        try {
          psSQL.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      if (null != connection) {
        try {
          connection.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    return listaUsuarios;
  }

  public ArrayList<OrdenesImprimir> getOrdenesImprimir(int id_orden) {

    Connection connection = null;
    PreparedStatement statement = null;
    ArrayList<OrdenesImprimir> listaOrdenes = new ArrayList<>();
    ArrayList<ProductoImprimir> listaProductos = new ArrayList<>();
    try {
      connection = Database.getDBConnection();
      connection.setAutoCommit(false);
      Statement stmt = connection.createStatement();
      String sql = "SELECT * FROM `ordenes` WHERE (`id_orden`=" + id_orden + ") ";
      int orden = -1;
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        if (rs.getBoolean("borradoLogico") == false) {
          orden = rs.getInt("id_orden");
          int centro_salida = rs.getInt("centro_salida");
          int centro_destino = rs.getInt("centro_destino");
          LocalDate date = rs.getDate("fecha").toLocalDate();
          int id_usuario = rs.getInt("id_usuario");
          OrdenesImprimir ordenesImprimir = new OrdenesImprimir(orden, centro_salida, centro_destino, date, id_usuario);
          listaOrdenes.add(ordenesImprimir);
        }
      }
      rs = stmt.executeQuery("select a.id_producto,cantidadorden,nombre_producto from contenidoorden a,productos b where a.id_producto=b.id_producto and id_orden=" + orden);
      while (rs.next()) {
        int id_producto = rs.getInt("id_producto");
        int cantidad = rs.getInt("cantidadorden");
        String nombre = rs.getString("nombre_producto");
        ProductoImprimir producto = new ProductoImprimir(id_producto, cantidad, nombre);
        listaProductos.add(producto);
      }
      listaOrdenes.get(0).setListaProductos(listaProductos);


      return listaOrdenes;
    } catch (SQLException exception) {
    } finally {
      if (null != statement) {
        try {
          statement.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      if (null != connection) {
        try {
          connection.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    return listaOrdenes;


  }


  public static String getDbConnection() {
    return DB_CONNECTION;
  }

  public static void setDbConnection(String dbConnection) {
    DB_CONNECTION = dbConnection;
  }

  public static String getDbUser() {
    return DB_USER;
  }

  public static void setDbUser(String dbUser) {
    DB_USER = dbUser;
  }

  public static String getDbPassword() {
    return DB_PASSWORD;
  }

  public static void setDbPassword(String dbPassword) {
    DB_PASSWORD = dbPassword;
  }
}

