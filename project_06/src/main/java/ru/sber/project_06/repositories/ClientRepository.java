package ru.sber.project_06.repositories;

import ru.sber.project_06.entities.Client;
import ru.sber.project_06.entities.ClientDTO;
import ru.sber.project_06.entities.Product;
import ru.sber.project_06.entities.ShoppingCart;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.core.RowMapper;


/**
 * Класс извлекающий данные о клиенте из бд для дальнейшей их обработки
 */
@Repository
public class ClientRepository implements ClientRepositoryInteface {

    private final JdbcTemplate jdbcTemplate;

    public static final String JDBC = "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=postgres";

    ShoppingCartRepository shoppingCartRepository;

    @Autowired
    public ClientRepository(ShoppingCartRepository shoppingCartRepository, JdbcTemplate jdbcTemplate) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    // @Override
    // public long registrate(Client client) {
    //     var insertCartSql = "INSERT INTO products_petrov_v.carts (promocode) VALUES (?);"; 

    //     try (var connection = DriverManager.getConnection(JDBC);
    //         var prepareStatement = connection.prepareStatement(insertCartSql, Statement.RETURN_GENERATED_KEYS)) {
    //         prepareStatement.setString(1, "");

    //         prepareStatement.executeUpdate();

    //         ResultSet rs = prepareStatement.getGeneratedKeys();
    //         if (rs.next()) {
    //         } else {
    //             throw new RuntimeException("Error when getting an identifier");
    //         }

    //         var insertClientSql = "INSERT INTO products_petrov_v.clients (name, username, password, cart_id, email) VALUES (?,?,?,?,?);";  

    //         try (var prepareNewStatement = connection.prepareStatement(insertClientSql, Statement.RETURN_GENERATED_KEYS)){
    //             prepareNewStatement.setString(1, client.getName());
    //             prepareNewStatement.setString(2, client.getLogin());
    //             prepareNewStatement.setString(3, client.getPassword());
    //             prepareNewStatement.setLong(4, rs.getInt(1));
    //             prepareNewStatement.setString(5, client.getEmail());

    //             prepareNewStatement.executeUpdate();

    //             rs = prepareNewStatement.getGeneratedKeys();
    //             if (rs.next()) {
    //                 return rs.getInt(1);
    //             } else {
    //                 throw new RuntimeException("Error when getting an identifier");
    //             }
    //         } catch (SQLException e) {
    //             throw new RuntimeException(e);
    //         }

    //     } catch (SQLException e) {
    //         throw new RuntimeException(e);
    //     }
    // }

    @Override
    public long registrate(Client client) {
        var insertCartSql = "INSERT INTO products_petrov_v.carts (promocode) VALUES (?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator cartPreparedStatementCreator = connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(insertCartSql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, "");
            return preparedStatement;
        };

        jdbcTemplate.update(cartPreparedStatementCreator, keyHolder);

        long cartId = (long) (int) keyHolder.getKeys().get("id");

        var insertClientSql = "INSERT INTO products_petrov_v.clients (name, username, password, cart_id, email) VALUES (?,?,?,?,?);";
        KeyHolder clientKeyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator clientPreparedStatementCreator = connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(insertClientSql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getLogin());
            preparedStatement.setString(3, client.getPassword());
            preparedStatement.setLong(4, cartId);
            preparedStatement.setString(5, client.getEmail());
            return preparedStatement;
        };

        jdbcTemplate.update(clientPreparedStatementCreator, clientKeyHolder);

        return (long) (int) clientKeyHolder.getKeys().get("id");
    }


    // @Override
    // public Optional<ClientDTO> findById(long id) {
    //     var selectSql = "SELECT * FROM products_petrov_v.clients where id = ?";
    //     var selectCartSql = "SELECT * FROM products_petrov_v.products_carts PC join products_petrov_v.products P on PC.id_product = P.id where PC.id_cart = ?";

    //     try (var connection = DriverManager.getConnection(JDBC);
    //          var prepareStatement = connection.prepareStatement(selectSql)) {
    //         prepareStatement.setLong(1, id);

    //         var resultSet = prepareStatement.executeQuery();

    //         if (resultSet.next()) {
    //             String name = resultSet.getString("name");
    //             String login = resultSet.getString("username");
    //             String email = resultSet.getString("email");
    //             int cartId = resultSet.getInt("cart_id");
    //             System.out.println(cartId);
    //             ShoppingCart cart = new ShoppingCart((long) cartId, "", new ArrayList<>(List.of()));
    //             List<Product> products = new ArrayList<>();
    //             try(var prepareNewStatement = connection.prepareStatement(selectCartSql)) {
    //                 prepareNewStatement.setLong(1, cartId);
    //                 var resultSetSet = prepareNewStatement.executeQuery();
    //                 while (resultSetSet.next()){
    //                     int newId = resultSetSet.getInt("id_product");
    //                     String newName = resultSetSet.getString("name");
    //                     BigDecimal price = BigDecimal.valueOf(resultSetSet.getDouble("price"));
    //                     int quantity = resultSetSet.getInt("count");
    //                     products.add(new Product(newId, newName, price, quantity));
    //                 }
    //                 cart.setProducts(products);
    //                 return Optional.of(new ClientDTO(name, login, email, cart));
    //             }
    //         }

    //         return Optional.empty();
    //     } catch (SQLException e) {
    //         throw new RuntimeException(e);
    //     }
    // }

    @Override
    public Optional<ClientDTO> findById(long id) {
        var selectSql = "SELECT * FROM products_petrov_v.clients WHERE id = ?";
        var selectCartSql = "SELECT * FROM products_petrov_v.products_carts PC JOIN products_petrov_v.products P ON PC.id_product = P.id WHERE PC.id_cart = ?";

        PreparedStatementCreator preparedStatementCreator = connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setLong(1, id);

            return preparedStatement;
        };

        RowMapper<ClientDTO> clientRowMapper = getClientRowMapper(selectCartSql);

        List<ClientDTO> clients = jdbcTemplate.query(preparedStatementCreator, clientRowMapper);

        return clients.stream().findFirst();
    }

    private RowMapper<ClientDTO> getClientRowMapper(String selectCartSql) {
        return (resultSet, rowNum) -> {
            String name = resultSet.getString("name");
            String login = resultSet.getString("username");
            String email = resultSet.getString("email");
            int cartId = resultSet.getInt("cart_id");

            ShoppingCart cart = fetchShoppingCart(cartId, selectCartSql);

            return new ClientDTO(name, login, email, cart);
        };
    }

    private ShoppingCart fetchShoppingCart(int cartId, String selectCartSql) {
        PreparedStatementCreator preparedStatementCreator = connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(selectCartSql);
            preparedStatement.setLong(1, cartId);

            return preparedStatement;
        };

        RowMapper<Product> productRowMapper = getProductRowMapper();

        List<Product> products = jdbcTemplate.query(preparedStatementCreator, productRowMapper);

        return new ShoppingCart((long) cartId, "", products);
    }

    private RowMapper<Product> getProductRowMapper() {
        return (resultSet, rowNum) -> {
            int id = resultSet.getInt("id_product");
            String name = resultSet.getString("name");
            BigDecimal price = BigDecimal.valueOf(resultSet.getDouble("price"));
            int quantity = resultSet.getInt("count");
            return new Product(id, name, price, quantity);
        };
    }

    
    // @Override
    // public boolean deleteById(long id) {
    //     var selectClientSql = "SELECT cart_id FROM products_petrov_v.clients where id = ?";
    //     var deleteProductClientSql = "delete from products_petrov_v.products_carts where id_cart = ?";
    //     var deleteCartSql = "delete from products_petrov_v.carts where id = ?";
    //     var deleteClientSql = " delete from products_petrov_v.clients where id = ? ";

    //     try (var connection = DriverManager.getConnection(JDBC);
    //          var prepareStatement = connection.prepareStatement(selectClientSql);
    //          var prepareDeleteProductClientStatement = connection.prepareStatement(deleteProductClientSql);
    //          var prepareDeleteCartStatement = connection.prepareStatement(deleteCartSql);
    //          var prepareDeleteClientStatement = connection.prepareStatement(deleteClientSql)) {


    //         prepareStatement.setLong(1, id);
    //         var resultSet = prepareStatement.executeQuery();

    //         if (resultSet.next()) {
    //             int cartId = resultSet.getInt("cart_id");
    //             prepareDeleteClientStatement.setLong(1, id);
    //             prepareDeleteProductClientStatement.setLong(1, cartId);
    //             prepareDeleteCartStatement.setLong(1, cartId);
    //             prepareDeleteProductClientStatement.executeUpdate();
    //             prepareDeleteClientStatement.executeUpdate();
    //             prepareDeleteCartStatement.executeUpdate();
    //             return true;

    //         } else {
    //             throw new RuntimeException();
    //         }

    //     } catch (SQLException e) {
    //         throw new RuntimeException(e);
    //     }
    // }

    @Override
    public boolean deleteById(long id) {
        var selectClientSql = "SELECT cart_id FROM products_petrov_v.clients where id = ?";
        var deleteProductClientSql = "DELETE FROM products_petrov_v.products_carts where id_cart = ?";
        var deleteCartSql = "DELETE FROM products_petrov_v.carts where id = ?";
        var deleteClientSql = "DELETE FROM products_petrov_v.clients where id = ?";

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(selectClientSql);
            prepareStatement.setLong(1, id);
            return prepareStatement;
        };

        RowMapper<Integer> rowMapper = (resultSet, rowNum) -> resultSet.getInt("cart_id");

        List<Integer> cartIds = jdbcTemplate.query(preparedStatementCreator, rowMapper);
        Optional<Integer> cartId = cartIds.stream().findFirst();

        if (cartId != null) {
            PreparedStatementCreator deleteProductClientStatementCreator = connection -> {
                var prepareStatement = connection.prepareStatement(deleteProductClientSql);
                prepareStatement.setLong(1, id);
                return prepareStatement;
            };

            PreparedStatementCreator deleteCartStatementCreator = connection -> {
                var prepareStatement = connection.prepareStatement(deleteCartSql);
                prepareStatement.setLong(1, id);
                return prepareStatement;
            };

            PreparedStatementCreator deleteClientStatementCreator = connection -> {
                var prepareStatement = connection.prepareStatement(deleteClientSql);
                prepareStatement.setLong(1, id);
                return prepareStatement;
            };

            jdbcTemplate.update(deleteProductClientStatementCreator);
            jdbcTemplate.update(deleteClientStatementCreator);
            jdbcTemplate.update(deleteCartStatementCreator);
            return true;
        } else {
            throw new RuntimeException();
        }
    }

    // @Override
    // public boolean deleteById(long id) {
    //     var selectClientSql = "SELECT cart_id FROM products_petrov_v.clients where id = ?";
    //     var deleteProductClientSql = "DELETE FROM products_petrov_v.products_carts WHERE id_cart = ?";
    //     var deleteCartSql = "DELETE FROM products_petrov_v.carts WHERE id = ?";
    //     var deleteClientSql = "DELETE FROM products_petrov_v.clients WHERE id = ?";

    //     PreparedStatementCreator selectClientStatementCreator = connection -> {
    //         var prepareStatement = connection.prepareStatement(selectClientSql);
    //         prepareStatement.setLong(1, id);
    //         return prepareStatement;
    //     };

    //     PreparedStatementCreator deleteProductClientStatementCreator = connection -> {
    //         var prepareStatement = connection.prepareStatement(deleteProductClientSql);
    //         prepareStatement.setLong(1, id);
    //         return prepareStatement;
    //     };

    //     PreparedStatementCreator deleteCartStatementCreator = connection -> {
    //         var prepareStatement = connection.prepareStatement(deleteCartSql);
    //         prepareStatement.setLong(1, id);
    //         return prepareStatement;
    //     };

    //     PreparedStatementCreator deleteClientStatementCreator = connection -> {
    //         var prepareStatement = connection.prepareStatement(deleteClientSql);
    //         prepareStatement.setLong(1, id);
    //         return prepareStatement;
    //     };

    //     RowMapper<Integer> cartIdRowMapper = (resultSet, rowNum) -> resultSet.getInt("cart_id");

    //     List<Integer> cartId = jdbcTemplate.query(selectClientStatementCreator, cartIdRowMapper);

    //     if (cartId != null) {
    //         jdbcTemplate.update(deleteProductClientStatementCreator);
    //         jdbcTemplate.update(deleteClientStatementCreator);
    //         jdbcTemplate.update(deleteCartStatementCreator);
    //         return true;
    //     } else {
    //         throw new RuntimeException("Client not found.");
    //     }
    // }


}
