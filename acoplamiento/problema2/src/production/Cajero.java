package production;

public class Cajero {
    private Inventario inventario;

    public Cajero(Inventario inventario) {
        this.setInventario(inventario);
    }

    public void procesarTransaccion(Carrito carrito) {
        double precio = carrito.calcularPrecioTotal();
        System.out.println("El precio total es: " + precio);
        getInventario().actualizarInventario(carrito);
    }

    public static void main(String[] args) {
        Inventario inventario = new Inventario();
        Cajero cajero = new Cajero(inventario);

        Carrito carrito = new Carrito();

        carrito.agregarProducto(inventario.obtenerProducto("Producto 1"));
        carrito.agregarProducto(inventario.obtenerProducto("Producto 2"));

        cajero.procesarTransaccion(carrito);

    }


    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }
}
