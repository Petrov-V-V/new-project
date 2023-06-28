package ru.sber.project_06.repositories;

import ru.sber.project_06.entities.Client;
import ru.sber.project_06.entities.ClientDTO;
import ru.sber.project_06.entities.Product;
import ru.sber.project_06.entities.ShoppingCart;
import ru.sber.project_06.repositories.ShoppingCartRepository;

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
 * Класс извлекающий данные о клиенте из бд для дальнейшей их обработки
 */
@Repository
public class ClientRepository implements ClientRepositoryInteface {

    public static final String JDBC = "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=postgres";

    private List<Client> clients = new ArrayList<>(List.of(
            new Client(1l, "Миша", "misha", "11111", "misha@w.e", new ShoppingCart(1L, "2jo4h9dfss", new ArrayList<>(List.of(new Product(1l, "Яблоко", BigDecimal.valueOf(50), 0)))))
    ));

    ShoppingCartRepository shoppingCartRepository;

    public ClientRepository(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    // @Override
    // public long registrate(Client client) {
    //     try (var connection = DriverManager.getConnection(JDBC)){System.out.println(")_)");} catch (SQLException e) {
    //         throw new RuntimeException(e);
    //     };
    //     long id = generateId();
    //     client.setId(id);
    //     client.setShoppingCart(shoppingCartRepository.generate(id));

    //     clients.add(client);
    //     return id;
    // }

    //
    //NEW CODE-NEW CODE-NEW CODE-NEW CODE-NEW CODE-NEW CODE-NEW CODE-NEW CODE-NEW CODE-NEW CODE-
    //
    @Override
    public long registrate(Client client) {
        var insertCartSql = "INSERT INTO petrov.cart (promocode) VALUES (?);"; 

        try (var connection = DriverManager.getConnection(JDBC);
            var prepareStatement = connection.prepareStatement(insertCartSql, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setString(1, "");

            prepareStatement.executeUpdate();

            ResultSet rs = prepareStatement.getGeneratedKeys();
            if (rs.next()) {
            } else {
                throw new RuntimeException("Error when getting an identifier");
            }

            var insertClientSql = "INSERT INTO petrov.client (name, username, password, cart_id, email) VALUES (?,?,?,?,?);";  

            try (var prepareNewStatement = connection.prepareStatement(insertClientSql, Statement.RETURN_GENERATED_KEYS)){
                prepareNewStatement.setString(1, client.getName());
                prepareNewStatement.setString(2, client.getLogin());
                prepareNewStatement.setString(3, client.getPassword());
                prepareNewStatement.setLong(4, rs.getInt(1));
                prepareNewStatement.setString(5, client.getEmail());

                prepareNewStatement.executeUpdate();

                rs = prepareNewStatement.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new RuntimeException("Error when getting an identifier");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // private long generateId() {
    //     Random random = new Random();
    //     int low = 1;
    //     int high = 1_000_000;
    //     return random.nextLong(high - low) + low;
    // }

    // public Optional<Client> findByIdClient(long id) {
    //     return clients.stream()
    //             .filter(client -> client.getId() == id)
    //             .findAny();
    // }

    @Override
    public Optional<ClientDTO> findById(long id) {
        var selectSql = "SELECT * FROM petrov.client where id = ?";
        var selectCartSql = "SELECT * FROM petrov.product_client PC join petrov.product P on PC.id_product = P.id where PC.id_cart = ?";

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setLong(1, id);

            var resultSet = prepareStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String login = resultSet.getString("username");
                String email = resultSet.getString("email");
                int cartId = resultSet.getInt("cart_id");
                System.out.println(cartId);
                ShoppingCart cart = new ShoppingCart((long) cartId, "", new ArrayList<>(List.of()));
                List<Product> products = new ArrayList<>();
                try(var prepareNewStatement = connection.prepareStatement(selectCartSql)) {
                    prepareNewStatement.setLong(1, cartId);
                    var resultSetSet = prepareNewStatement.executeQuery();
                    while (resultSetSet.next()){
                        int newId = resultSetSet.getInt("id_product");
                        String newName = resultSetSet.getString("name");
                        BigDecimal price = BigDecimal.valueOf(resultSetSet.getDouble("price"));
                        int quantity = resultSetSet.getInt("count");
                        products.add(new Product(newId, newName, price, quantity));
                    }
                    cart.setProducts(products);
                    return Optional.of(new ClientDTO(name, login, email, cart));
                }
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // @Override
    // public boolean deleteById(long id) {
    //     return clients.removeIf(client -> client.getId() == id);
    // }

    @Override
    public boolean deleteById(long id) {
        var selectClientSql = "SELECT cart_id FROM petrov.client where id = ?";
        var deleteProductClientSql = "delete from petrov.product_client where id_cart = ?";
        var deleteCartSql = "delete from petrov.cart where id = ?";
        var deleteClientSql = " delete from petrov.client where id = ? ";

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectClientSql);
             var prepareDeleteProductClientStatement = connection.prepareStatement(deleteProductClientSql);
             var prepareDeleteCartStatement = connection.prepareStatement(deleteCartSql);
             var prepareDeleteClientStatement = connection.prepareStatement(deleteClientSql)) {


            prepareStatement.setLong(1, id);
            var resultSet = prepareStatement.executeQuery();

            if (resultSet.next()) {
                int cartId = resultSet.getInt("cart_id");
                prepareDeleteClientStatement.setLong(1, id);
                prepareDeleteProductClientStatement.setLong(1, cartId);
                prepareDeleteCartStatement.setLong(1, cartId);
                prepareDeleteProductClientStatement.executeUpdate();
                prepareDeleteClientStatement.executeUpdate();
                prepareDeleteCartStatement.executeUpdate();
                return true;

            } else {
                throw new RuntimeException();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
