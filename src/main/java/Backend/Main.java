package Backend;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        TransactionDAO dao = new TransactionDAO();
        // Create a test transaction
        Transaction transaction = new Transaction(
                1,                          // user_id
                "expense",                  // type
                "Food",                     // category
                new BigDecimal("250.00"),    // amount
                "Lunch",                    // description
                LocalDate.now()             // date
        );

        // Add transaction
        boolean added = dao.addTransaction(transaction);

        if (added) {
            System.out.println("Transaction added successfully!");
        } else {
            System.out.println("Failed to add transaction.");
        }


        // Display all transactions
        System.out.println("\nAll Transactions:");

        List<Transaction> transactions =
                dao.getAllTransactions();

        for (Transaction t : transactions) {

            System.out.println(
                    t.getId() + " | " +
                    t.getType() + " | " +
                    t.getCategory() + " | " +
                    t.getAmount() + " | " +
                    t.getDescription() + " | " +
                    t.getTransactionDate()
            );
        }
    }
}