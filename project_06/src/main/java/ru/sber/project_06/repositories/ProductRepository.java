package ru.sber.project_06.repositories;

import ru.sber.project_06.entities.Product;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Класс извлекающий данные о продукте из бд для дальнейшей их обработки
 */
@Repository
public class ProductRepository implements ProductRepositoryInteface{

    public static final String JDBC = "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=postgres";

    private List<Product> products = new ArrayList<>(List.of(
            // new Product(1l, "Яблоко", BigDecimal.valueOf(50), 0),
            // new Product(2l, "Арбуз", BigDecimal.valueOf(150), 0),
            // new Product(3l, "Персик", BigDecimal.valueOf(30), 0)
    ));

    // @Override
    // public long save(Product product) {
    //     long id = generateId();
    //     product.setId(id);
    //     products.add(product);
    //     return id;
    // }

    @Override
    public long save(Product product) {
        var insertSql = "INSERT INTO petrov.product (name, price) VALUES (?,?);";  

        try (var connection = DriverManager.getConnection(JDBC);
            var prepareStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setString(1, product.getName());
            prepareStatement.setDouble(2, product.getPrice().doubleValue());
            //prepareStatement.setInt(3, product.getQuantity());

            prepareStatement.executeUpdate();

            ResultSet rs = prepareStatement.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new RuntimeException("Error when getting an identifier");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // @Override
    // public Optional<Product> findById(long id) {
    //     return products.stream()
    //             .filter(product -> product.getId() == id)
    //             .findAny();
    // }

    @Override
    public Optional<Product> findById(long productId) {
        var selectSql = "SELECT * FROM petrov.PRODUCT where id = ?";

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setLong(1, productId);

            var resultSet = prepareStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                //int quantity = resultSet.getInt("count");
                Product product = new Product(id, name, BigDecimal.valueOf(price), 0);

                return Optional.of(product);
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // @Override
    // public List<Product> findAll(String name) {
    //     if (name == null) {
    //         return products;
    //     }

    //     return products.stream()
    //             .filter(product -> product.getName().equals(name))
    //             .toList();
    // }

    @Override
    public List<Product> findAll(String productName) {
        var selectSql = "SELECT * FROM petrov.PRODUCT where name like ?";
        List<Product> products = new ArrayList<>();

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setString(1, "%" + (productName == null ? "" : productName) + "%");

            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                //int quantity = resultSet.getInt("count");
                Product product = new Product(id, name, BigDecimal.valueOf(price), 0);

                products.add(product);
            }

            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // @Override
    // public boolean update(Product product) {
    //     for (Product p : products) {
    //         if (p.getId() == product.getId()) {
    //             p.setName(product.getName());
    //             p.setPrice(product.getPrice());

    //             return true;
    //         }
    //     }

    //     return false;
    // }

    @Override
    public boolean update(Product product) {
        var selectSql = """
                UPDATE petrov.PRODUCT
                SET 
                name = ?,
                price = ?
                where id = ?;
                """;

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setString(1, product.getName());
            prepareStatement.setDouble(2, product.getPrice().doubleValue());
            prepareStatement.setLong(3, product.getId());

            var rows = prepareStatement.executeUpdate();

            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // @Override
    // public boolean deleteById(long id) {
    //     return products.removeIf(product -> product.getId() == id);
    // }

    // private long generateId() {
    //     Random random = new Random();
    //     int low = 1;
    //     int high = 1_000_000;
    //     return random.nextLong(high - low) + low;
    // }

    @Override
    public boolean deleteById(long id) {
        var selectSql = "DELETE FROM petrov.PRODUCT where id = ?";

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setLong(1, id);

            var rows = prepareStatement.executeUpdate();

            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}