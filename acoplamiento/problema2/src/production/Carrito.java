package production;

import java.util.ArrayList;

public class Carrito {
    public ArrayList<Producto> productos;

    public Carrito() {
        this.productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public double calcularPrecioTotal() {
        double precio = 0;
        for (Producto producto : this.productos) {
            precio += producto.getPrecio();
        }
        return precio;
    }
}
