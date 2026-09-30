package Backend;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    // ==========================================
    // 1. ADD TRANSACTION
    // ==========================================

    public boolean addTransaction(Transaction transaction) {

        String sql = """
                INSERT INTO transactions
                (user_id, type, category, amount, description, transaction_date)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, transaction.getUserId());
            statement.setString(2, transaction.getType());
            statement.setString(3, transaction.getCategory());
            statement.setBigDecimal(4, transaction.getAmount());
            statement.setString(5, transaction.getDescription());
            statement.setDate(
                    6,
                    Date.valueOf(transaction.getTransactionDate())
            );

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }


    // ==========================================
    // 2. GET ALL TRANSACTIONS
    // ==========================================

    public List<Transaction> getAllTransactions() {

        List<Transaction> transactions = new ArrayList<>();

        String sql = """
                SELECT id, user_id, type, category,
                       amount, description, transaction_date
                FROM transactions
                ORDER BY transaction_date DESC
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                Transaction transaction = new Transaction(

                        result.getInt("id"),

                        result.getInt("user_id"),

                        result.getString("type"),

                        result.getString("category"),

                        result.getBigDecimal("amount"),

                        result.getString("description"),

                        result.getDate("transaction_date")
                              .toLocalDate()
                );

                transactions.add(transaction);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return transactions;
    }


    // ==========================================
    // 3. GET TRANSACTIONS OF A USER
    // ==========================================

    public List<Transaction> getTransactionsByUser(int userId) {

        List<Transaction> transactions = new ArrayList<>();

        String sql = """
                SELECT id, user_id, type, category,
                       amount, description, transaction_date
                FROM transactions
                WHERE user_id = ?
                ORDER BY transaction_date DESC
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet result = statement.executeQuery()) {

                while (result.next()) {

                    Transaction transaction = new Transaction(

                            result.getInt("id"),

                            result.getInt("user_id"),

                            result.getString("type"),

                            result.getString("category"),

                            result.getBigDecimal("amount"),

                            result.getString("description"),

                            result.getDate("transaction_date")
                                  .toLocalDate()
                    );

                    transactions.add(transaction);
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return transactions;
    }


    // ==========================================
    // 4. DELETE TRANSACTION
    // ==========================================

    public boolean deleteTransaction(int id) {

        String sql = """
                DELETE FROM transactions
                WHERE id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }


    // ==========================================
    // 5. UPDATE TRANSACTION
    // ==========================================

    public boolean updateTransaction(Transaction transaction) {

        String sql = """
                UPDATE transactions
                SET type = ?,
                    category = ?,
                    amount = ?,
                    description = ?,
                    transaction_date = ?
                WHERE id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, transaction.getType());
            statement.setString(2, transaction.getCategory());
            statement.setBigDecimal(3, transaction.getAmount());
            statement.setString(4, transaction.getDescription());

            statement.setDate(
                    5,
                    Date.valueOf(transaction.getTransactionDate())
            );

            statement.setInt(6, transaction.getId());

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }
}