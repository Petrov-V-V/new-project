package ru.sber.project_06.repositories;

import ru.sber.project_06.entities.ShoppingCart;
import ru.sber.project_06.entities.Product;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Класс извлекающий данные о тележке из бд для дальнейшей их обработки
 */
@Repository
public class ShoppingCartRepository implements ShoppingCartRepositoryInteface {
    
    public static final String JDBC = "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=postgres";

    List<ShoppingCart> shoppingCarts = new ArrayList<>(List.of(new ShoppingCart(1L, "324ffds", new ArrayList<>(List.of(new Product(1l, "Яблоко", BigDecimal.valueOf(50), 0))))));

    // @Override
    // public Optional<ShoppingCart> addProduct(long id, Product product) {
    //     Optional<ShoppingCart> shoppingCart = shoppingCarts.stream().filter(c -> c.getId()==id).findFirst();
    //     if(shoppingCart.isPresent()){
    //         shoppingCart.get().getProducts().add(product);
    //     }
    //     return shoppingCart;
    // }

    @Override
    public Optional<ShoppingCart> addProduct(long cartId, Product product) {
        List<Product> products = new ArrayList<>();
        var selectSql = "SELECT * from petrov.product_client PC join petrov.product P on PC.id_product = P.id where PC.id_cart = ?";
        var insertSql = "insert into petrov.product_client(id_product, id_cart, count) values (?,?,?);";
        try (var connection = DriverManager.getConnection(JDBC);
             var prepareInsertStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
             var prepareSelectStatement = connection.prepareStatement(selectSql)){
            prepareInsertStatement.setLong(1, product.getId());
            prepareInsertStatement.setLong(2, cartId);
            prepareInsertStatement.setLong(3, product.getQuantity());
            prepareInsertStatement.executeUpdate();
            prepareSelectStatement.setLong(1, cartId);
            var resultProducts = prepareSelectStatement.executeQuery();
            while (resultProducts.next()) {
                int id = resultProducts.getInt("id");
                String name = resultProducts.getString("name");
                BigDecimal price = BigDecimal.valueOf(resultProducts.getDouble("price"));
                int quantity = resultProducts.getInt("count");
                products.add(new Product((long) id, name, price, quantity));
            }
            return Optional.of(new ShoppingCart((long) cartId, "", products));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // @Override
    // public boolean deleteProduct(long id, long productId) {
    //     Optional<ShoppingCart> shoppingCart = shoppingCarts.stream().filter(c -> c.getId() == id).findFirst();
    //     if (shoppingCart.isPresent()) {
    //         return shoppingCart.get().getProducts().removeIf(product -> product.getId() == productId);
    //     }
    //     return false;
    // }

    @Override
    public boolean deleteProduct(long cartId, long productId) {
        var deleteSql = "DELETE from petrov.product_client where id_cart = ? and id_product = ?;";
        
        try (var connection = DriverManager.getConnection(JDBC);
            var prepareDeleteStatement = connection.prepareStatement(deleteSql);
        ) {
            prepareDeleteStatement.setLong(1, cartId);
            prepareDeleteStatement.setLong(2, productId);
            int rowsDeleted = prepareDeleteStatement.executeUpdate();
            
            return rowsDeleted > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // @Override
    // public Optional<ShoppingCart> updateQuantityOfProduct(long id, long productId, int quantity) {
    //     Optional<ShoppingCart> shoppingCart = shoppingCarts.stream().filter(c -> c.getId() == id).findFirst();
    //     if (shoppingCart.isPresent()) {
    //         Optional<Product> product = shoppingCart.get().getProducts().stream().filter(p -> p.getId() == productId).findFirst();
    //         product.ifPresent(value -> value.setQuantity(quantity));
    //     }
    //     return shoppingCart;
    // }

    @Override
    public Optional<ShoppingCart> updateQuantityOfProduct(long cartId, long productId, int quantity) {
        var updateSql = "UPDATE petrov.product_client SET count = ? where id_product = ? AND id_cart = ?;";
        var selectSql = "SELECT * from petrov.product_client PC join petrov.product P ON PC.id_product = P.id where PC.id_cart = ?";
        List<Product> products = new ArrayList<>();
        
        try (var connection = DriverManager.getConnection(JDBC);
            var prepareUpdateStatement = connection.prepareStatement(updateSql);
            var prepareSelectStatement = connection.prepareStatement(selectSql);
        ){
            prepareUpdateStatement.setInt(1, quantity);
            prepareUpdateStatement.setLong(2, productId);
            prepareUpdateStatement.setLong(3, cartId);
            int rowsUpdated = prepareUpdateStatement.executeUpdate();
            
            if (rowsUpdated > 0) {
                prepareSelectStatement.setLong(1, cartId);
                var resultProducts = prepareSelectStatement.executeQuery();
                
                while (resultProducts.next()) {
                    int id = resultProducts.getInt("id_product");
                    String name = resultProducts.getString("name");
                    BigDecimal price = BigDecimal.valueOf(resultProducts.getDouble("price"));
                    int productQuantity = resultProducts.getInt("count");
                    products.add(new Product((long) id, name, price, productQuantity));
                }
                
                return Optional.of(new ShoppingCart(cartId, "", products));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
        return Optional.empty();
    }


    public ShoppingCart generate(long id){
        ShoppingCart shoppingCart = new ShoppingCart(id, "g45wrewg", new ArrayList<>());
        shoppingCarts.add(shoppingCart);
        return shoppingCart;
    }
    
}