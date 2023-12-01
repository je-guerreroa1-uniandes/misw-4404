import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Inventory {

    private static final String CSV_SPLIT_BY = ",";

    public static void main(String[] args) {
        String csvFileProducts = "./refactoring/problema3/data/products.csv";
        String csvFileSales = "./refactoring/problema3/data/sales.csv";
        String csvFileOrders = "./refactoring/problema3/data/orders.csv";

        Map<Integer, Product> products = readProducts(csvFileProducts);
        List<Sale> sales = readSales(csvFileSales);
        List<Order> orders = readOrders(csvFileOrders);

        updateInventory(products, orders, sales);

        products.values().forEach(product -> 
            System.out.println(product.getItem() + " " + product.getQuantity()));
    }

    private static Map<Integer, Product> readProducts(String fileName) {
        Map<Integer, Product> products = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(CSV_SPLIT_BY);
                int itemId = Integer.parseInt(data[0].trim());
                products.put(itemId, new Product(itemId, data[1].trim(), Integer.parseInt(data[2].trim())));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }

    // Similar methods for readSales and readOrders

    private static void updateInventory(Map<Integer, Product> products, List<Order> orders, List<Sale> sales) {
        for (Order order : orders) {
            Product item = products.get(order.getItemId());
            item.setQuantity(item.getQuantity() + order.getQuantity());
        }

        for (Sale sale : sales) {
            Product item = products.get(sale.getItemId());
            item.setQuantity(item.getQuantity() - sale.getQuantity());
        }
    }
}

// Product, Order, and Sale classes remain the same
