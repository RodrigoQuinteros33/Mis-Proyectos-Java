package zona_fit.datos;

import zona_fit.dominio.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static zona_fit.conexion.Conexion.getConexion;

public class ClienteDAO implements IClienteDAO{

    @Override
    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        var sql = "SELECT * FROM cliente ORDER BY id";
        try{
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                var cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setMembresia(rs.getInt("membresia"));
                clientes.add(cliente);
            }
        }catch(Exception e){
            System.out.println("Error al listar clientes: " + e.getMessage());
        }
        finally{
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar conexion: " + e.getMessage());
            }
        }
        return clientes;
    }

    @Override
    public boolean buscarClientePorId(Cliente cliente) {
        PreparedStatement ps;
        ResultSet rs;
        var con = getConexion();
        var sql = "SELECT * FROM cliente WHERE id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, cliente.getId());
            rs = ps.executeQuery();
            if(rs.next()){
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setMembresia(rs.getInt("membresia"));
                return true;
            }
        }catch(Exception e){
            System.out.println("Error al recuperar cliente por id: " + e.getMessage());
        }
        finally {
            try{
                con.close();
            }catch(Exception e){
                System.out.println("Error al cerrar conexion: " + e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean agregarCliente(Cliente cliente) {
        PreparedStatement ps;
        Connection con = getConexion();
        String sql = "INSERT INTO cliente(nombre, apellido, membresia)"+
                " VALUES(?, ?, ?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setInt(3, cliente.getMembresia());
            ps.execute();
            return true;
        }catch(Exception e){
            System.out.println("Error al agregar cliente: " + e.getMessage());
        }
        finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Error al cerrar conexion: " + e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean modificarCliente(Cliente cliente) {
        PreparedStatement ps;
        Connection con = getConexion();
        var sql = "UPDATE cliente SET nombre=?, apellido=?, membresia=? "
                + "WHERE id = ?";
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setInt(3, cliente.getMembresia());
            ps.setInt(4, cliente.getId());
            ps.execute();
            return true;
        }catch(Exception e){
            System.out.println("Error al modificar cliente: " + e.getMessage());
        }
        finally {
            try{
            con.close();
            }catch(Exception e){
                System.out.println("Error al cerrar conexion: " + e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean eliminarCliente(Cliente cliente) {
        PreparedStatement ps;
        Connection con = getConexion();
        String sql = "DELETE FROM cliente WHERE id = ?";
        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1, cliente.getId());
            ps.execute();
            return true;
        }catch(Exception e){
            System.out.println("Error al eliminar cliente: " + e.getMessage());
        }
        finally{
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("error al cerrar conexion: " + e.getMessage());
            }
        }
        return false;
    }

//    public boolean buscarClienteSinId(Cliente usuario){
//        PreparedStatement ps;
//        ResultSet rs;
//        var con = getConexion();
//        var sql = "SELECT id FROM cliente WHERE nombre = ?";
//        try{
//            ps  = con.prepareStatement(sql);
//            ps.setString(1, usuario.getNombre());
//            rs = ps.executeQuery();
//
//            int id;
//            if (rs.next()) {
//                id = rs.getInt("id");
//                usuario.setId(id);
//                buscarClientePorId(usuario);
//                return true;
//            } else {
//                System.out.println("no se encontro el registro");
//            }
//        }catch(Exception e){
//            System.out.println("Se ha producido un error: " + e.getMessage());
//        }finally {
//            try{
//                con.close();
//            }catch(Exception e){
//                System.out.println("Error al cerrar conexion: " + e.getMessage());
//            }
//        }
//        return false;//cambiar esto
//    }

    public static void main(String[] args) {

        IClienteDAO clienteDao = new ClienteDAO();

        //buscar por id
        var cliente1 = new Cliente(2);
        System.out.println("Cliente antes de la busqueda: " + cliente1);
        var encontrado = clienteDao.buscarClientePorId(cliente1);
        if(encontrado){
            System.out.println("cliente encontrado: " + cliente1);
        }else {
            System.out.println("cliente no encontrado: " + cliente1.getId());
        }

        //agregar cliente
//        var nuevoCliente = new Cliente("Lavinia", "Falcone", 300);
//        var agregado = clienteDao.agregarCliente(nuevoCliente);
//        if(agregado){
//            System.out.println("cliente agregado: " + nuevoCliente);
//        }else{
//            System.out.println("no se agreglo el cliente: " + nuevoCliente);
//        }
        //modificar cliente

//        var modificarCliente = new Cliente(5, "Angelina" ,"Ajimu" ,300);
//        var modificado = clienteDao.modificarCliente(modificarCliente);
//        if(modificado){
//            System.out.println("cliente modificado: " + modificarCliente);
//        }else{
//            System.out.println("no se ha modificado el cliente: " + modificarCliente);
//        }

        //eliminar cliente
//        var clienteEliminar = new Cliente(5);
//        var eliminado = clienteDao.eliminarCliente(clienteEliminar);
//        if(eliminado){
//            System.out.println("cliente eliminado: " + clienteEliminar);
//        }else{
//            System.out.println("no se elimino cliente: " + clienteEliminar);
//        }


        //listar clientes
        System.out.println("*** Listar clientes ***");

        var clientes = clienteDao.listarClientes();
        clientes.forEach(System.out::println);


    }

}
