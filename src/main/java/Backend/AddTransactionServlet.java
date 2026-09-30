package Backend;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

@WebServlet("/addTransaction")
public class AddTransactionServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // Get data sent by frontend
        int userId = Integer.parseInt(request.getParameter("userId"));
        String type = request.getParameter("type");
        String category = request.getParameter("category");
        BigDecimal amount =
                new BigDecimal(request.getParameter("amount"));
        String description = request.getParameter("description");
        LocalDate date =
                LocalDate.parse(request.getParameter("date"));

        // Create Transaction object
        Transaction transaction = new Transaction(
                userId,
                type,
                category,
                amount,
                description,
                date
        );

        // Send transaction to database
        TransactionDAO dao = new TransactionDAO();

        boolean success = dao.addTransaction(transaction);

        // Send response
        if (success) {
            response.getWriter().println(
                    "Transaction added successfully!"
            );
        } else {
            response.getWriter().println(
                    "Failed to add transaction."
            );
        }
    }
}