package zona_fit.presentacion;

import zona_fit.datos.ClienteDAO;
import zona_fit.datos.IClienteDAO;
import zona_fit.dominio.Cliente;

import java.util.Scanner;

public class zonaFitApp {
    public static void main(String[] args) {
        zonaFitApp();
    }

    private static void zonaFitApp() {

        var salir = false;
        var scanner = new Scanner(System.in);
        IClienteDAO clienteDao = new ClienteDAO();

        while(!salir) {
            try {
                var opcion = mostrarMenu(scanner);
                salir = ejecutarOpciones(scanner, opcion, clienteDao);
            }catch(Exception e){
                System.out.println("Error al ejecutar opciones: " + e.getMessage());
            }
            System.out.println();
        }
    }

    private static int mostrarMenu(Scanner scanner) {

        System.out.print("""
                        *** ZonaFit ***
                        1. Listar cliente
                        2. Buscar cliente
                        3. Agregar cliente
                        4. Modificar cliente
                        5. Eliminar cliente
                        6. Salir
                        Eliga una opcion:\s""");

        return Integer.parseInt(scanner.nextLine());
    }
    private static boolean ejecutarOpciones(Scanner scanner,int opcion, IClienteDAO clienteDAO){
        var salir = false;
        switch (opcion) {
            case 1 -> { //Listar cliente
                System.out.println("--- Listado de clientes ---");
                listarCliente();
            }

            case 2 -> {// Buscar cliente
                System.out.print("Ingrese el id de la persona: ");
                var cliente = Integer.parseInt(scanner.nextLine());
                buscarCliente(cliente);
            }

            case 3 -> { //agregar cliente
                System.out.println("--- agregar cliente ---");
                System.out.print("Ingrese nombre: ");
                var nombre = scanner.nextLine();
                System.out.print("Ingrese apellido: ");
                var apellido = scanner.nextLine();
                System.out.print("Ingrese membresia: ");
                var membresia = scanner.nextInt();
                agregarCliente(nombre, apellido, membresia);
            }

            case 4 -> { // modificar cliente
                System.out.println("--- Modificar cliente ---");

                System.out.print("Ingrese id: ");
                var id = Integer.parseInt(scanner.nextLine());

                System.out.println("Ingrese nombre: ");
                var nombre = scanner.nextLine();

                System.out.println("Ingrese apellido: ");
                var apellido = scanner.nextLine();

                System.out.println("Ingrese membresia: ");
                var membresia = Integer.parseInt(scanner.nextLine());

                modificarCliente(id, nombre, apellido, membresia);
            }

            case 5 -> { //eliminar cliente
                System.out.print("Ingrese id: ");
                int usuario = Integer.parseInt(scanner.nextLine());
                eliminarCliente(usuario);
            }

            case 6 ->{//salir
                System.out.println("Hasta luego...");
                salir = true;
            }

            default -> System.out.print("Opcion invalida," +
                    "\nEliga otra opcion: ");
        }
        return salir;//arreglar
    }

    private static void listarCliente() {
        ClienteDAO clienteDao = new ClienteDAO();
        var clientes = clienteDao.listarClientes();
        clientes.forEach(System.out::println);
    }

    private static void buscarCliente(int id) {
        ClienteDAO clienteDao = new ClienteDAO();
        Cliente cliente = new Cliente(id);
        var encontrado =  clienteDao.buscarClientePorId(cliente);

        if(encontrado){
            System.out.println("CLiente encontrado: " + cliente);
        }else{
            System.out.println("cliente no encontrado: " + cliente);
        }
    }

    private static void agregarCliente(String nombre, String apellido, int membresia) {

        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente = new Cliente(nombre, apellido, membresia);

        var agregado = clienteDAO.agregarCliente(cliente);
        if(agregado) {
            System.out.println("Se ha agregado el cliente con exito");
        }else{
            System.out.println("Error al agregar persona");
        }
    }

    private static void modificarCliente(int id, String nombre, String apellido, int membresia) {
        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente = new Cliente(id, nombre, apellido, membresia);

        var modificado = clienteDAO.modificarCliente(cliente);

        if(modificado){
            System.out.println("Cliente modificado: "+ cliente);
        }else{
            System.out.println("Cliente no modificado: " + cliente);
        }
    }

    private static void eliminarCliente(int id) {
        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente = new Cliente(id);
        var eliminado = clienteDAO.eliminarCliente(cliente);
        if(eliminado){
            System.out.println("Cliente eliminado con exito: "+ cliente);
        }else{
            System.out.println("Error al eliminar cliente: " + cliente);
        }
    }
}
