package ru.sber.project_06.repositories;

import ru.sber.project_06.entities.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.core.RowMapper;

/**
 * Класс извлекающий данные о продукте из бд для дальнейшей их обработки
 */
@Repository
public class ProductRepository implements ProductRepositoryInteface{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static final String JDBC = "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=postgres";


    // @Override
    // public long save(Product product) {
    //     var insertSql = "INSERT INTO products_petrov_v.products (name, price, count) VALUES (?,?,?);";  

    //     try (var connection = DriverManager.getConnection(JDBC);
    //         var prepareStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
    //         prepareStatement.setString(1, product.getName());
    //         prepareStatement.setDouble(2, product.getPrice().doubleValue());
    //         prepareStatement.setInt(3, product.getQuantity());

    //         prepareStatement.executeUpdate();

    //         ResultSet rs = prepareStatement.getGeneratedKeys();
    //         if (rs.next()) {
    //             return rs.getInt(1);
    //         } else {
    //             throw new RuntimeException("Error when getting an identifier");
    //         }
    //     } catch (SQLException e) {
    //         throw new RuntimeException(e);
    //     }
    // }

    @Override
    public long save(Product product) {
        var insertSql = "INSERT INTO products_petrov_v.products (name, price, count) VALUES (?,?,?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator preparedStatementCreator = connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice().doubleValue());
            preparedStatement.setInt(3, product.getQuantity());

            return preparedStatement;
        };

        jdbcTemplate.update(preparedStatementCreator, keyHolder);

        return (long) (int) keyHolder.getKeys().get("id");
    }

    // @Override
    // public Optional<Product> findById(long productId) {
    //     var selectSql = "SELECT * FROM products_petrov_v.products where id = ?";

    //     try (var connection = DriverManager.getConnection(JDBC);
    //          var prepareStatement = connection.prepareStatement(selectSql)) {
    //         prepareStatement.setLong(1, productId);

    //         var resultSet = prepareStatement.executeQuery();

    //         if (resultSet.next()) {
    //             int id = resultSet.getInt("id");
    //             String name = resultSet.getString("name");
    //             double price = resultSet.getDouble("price");
    //             int count = resultSet.getInt("count");
    //             Product product = new Product(id, name, BigDecimal.valueOf(price), count);

    //             return Optional.of(product);
    //         }

    //         return Optional.empty();
    //     } catch (SQLException e) {
    //         throw new RuntimeException(e);
    //     }
    // }

    @Override
    public Optional<Product> findById(long productId) {
        var selectSql = "SELECT * FROM products_petrov_v.products WHERE id = ?";

        PreparedStatementCreator preparedStatementCreator = connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setLong(1, productId);

            return preparedStatement;
        };

        RowMapper<Product> productRowMapper = getProductRowMapper();

        List<Product> products = jdbcTemplate.query(preparedStatementCreator, productRowMapper);

        return products.stream().findFirst();
    }

    @Override
    public List<Product> findAll(String productName) {
        var selectSql = "SELECT * FROM products_petrov_v.products where name like ?";

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(selectSql);
            prepareStatement.setString(1, "%" + (productName == null ? "" : productName) + "%");

            return prepareStatement;
        };

        RowMapper<Product> productRowMapper = getProductRowMapper();

        return jdbcTemplate.query(preparedStatementCreator, productRowMapper);
    }

    private static RowMapper<Product> getProductRowMapper() {
        return (resultSet, rowNum) -> {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            double price = resultSet.getDouble("price");
            int count = resultSet.getInt("count");
            return new Product(id, name, BigDecimal.valueOf(price), count);
        };
    }

    // @Override
    // public List<Product> findAll(String productName) {
    //     var selectSql = "SELECT * FROM products_petrov_v.products where name like ?";
    //     List<Product> products = new ArrayList<>();

    //     try (var connection = DriverManager.getConnection(JDBC);
    //          var prepareStatement = connection.prepareStatement(selectSql)) {
    //         prepareStatement.setString(1, "%" + (productName == null ? "" : productName) + "%");

    //         var resultSet = prepareStatement.executeQuery();
    //         while (resultSet.next()) {
    //             int id = resultSet.getInt("id");
    //             String name = resultSet.getString("name");
    //             double price = resultSet.getDouble("price");
    //             int count = resultSet.getInt("count");
    //             Product product = new Product(id, name, BigDecimal.valueOf(price), count);

    //             products.add(product);
    //         }

    //         return products;
    //     } catch (SQLException e) {
    //         throw new RuntimeException(e);
    //     }
    // }

    // @Override
    // public boolean update(Product product) {
    //     var selectSql = """
    //             UPDATE products_petrov_v.products
    //             SET 
    //             name = ?,
    //             price = ?,
    //             count = ?
    //             where id = ?;
    //             """;

    //     try (var connection = DriverManager.getConnection(JDBC);
    //          var prepareStatement = connection.prepareStatement(selectSql)) {
    //         prepareStatement.setString(1, product.getName());
    //         prepareStatement.setDouble(2, product.getPrice().doubleValue());
    //         prepareStatement.setInt(3, product.getQuantity());
    //         prepareStatement.setLong(4, product.getId());

    //         var rows = prepareStatement.executeUpdate();

    //         return rows > 0;
    //     } catch (SQLException e) {
    //         throw new RuntimeException(e);
    //     }
    // }

    // @Override
    // public boolean deleteById(long id) {
    //     var selectSql = "DELETE FROM products_petrov_v.products where id = ?";

    //     try (var connection = DriverManager.getConnection(JDBC);
    //          var prepareStatement = connection.prepareStatement(selectSql)) {
    //         prepareStatement.setLong(1, id);

    //         var rows = prepareStatement.executeUpdate();

    //         return rows > 0;
    //     } catch (SQLException e) {
    //         throw new RuntimeException(e);
    //     }
    // }

    @Override
    public boolean deleteById(long id) {
        var deleteSql = "DELETE FROM products where id = ?";

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(deleteSql);
            prepareStatement.setLong(1, id);

            return prepareStatement;
        };

        int rows = jdbcTemplate.update(preparedStatementCreator);

        return rows > 0;
    }

    @Override
    public boolean update(Product product) {
        var updateSql = """
                UPDATE products
                SET 
                name = ?,
                price = ?,
                count = ?
                where id = ?;
                """;

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(updateSql);
            prepareStatement.setString(1, product.getName());
            prepareStatement.setDouble(2, product.getPrice().doubleValue());
            prepareStatement.setInt(3, product.getQuantity());
            prepareStatement.setLong(4, product.getId());

            return prepareStatement;
        };

        int rows = jdbcTemplate.update(preparedStatementCreator);

        return rows > 0;
    }
}