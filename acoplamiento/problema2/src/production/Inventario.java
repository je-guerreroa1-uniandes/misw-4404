package production;


// import hashmap
import java.util.HashMap;

public class Inventario {
    public HashMap<String, Producto> productos;


    public Inventario() {
        this.productos = new HashMap<String, Producto>();
        init();
    }

    private void init() {
        setInitialProducts();
    }

    public void actualizarInventario(Carrito carrito) {
        for (Producto producto : carrito.productos) {
            this.productos.remove(producto.getNombre());
        }
    }

    public void setInitialProducts() {
        Producto producto1 = new Producto("Producto 1", 1000);
        Producto producto2 = new Producto("Producto 2", 2000);
        Producto producto3 = new Producto("Producto 3", 3000);

        this.productos.put(producto1.getNombre(), producto1);
        this.productos.put(producto2.getNombre(), producto2);
        this.productos.put(producto3.getNombre(), producto3);
    }

    public Producto obtenerProducto(String nombreProducto) {
        return productos.get(nombreProducto);
    }
}
