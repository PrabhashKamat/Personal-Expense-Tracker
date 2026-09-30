package Backend;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction{
    private int id;
    private int userId;
    private String type;
    private String category;
    private BigDecimal amount;
    private String description;
    private LocalDate TransactionDate; 

    // Constructor withoout ID
    // Used When adding a new transaction
    public Transaction(int userId, String type, String category, BigDecimal amount, String description, LocalDate transactionDate){
            this.userId = userId;
            this.type = type;
            this.category = category;
            this.amount = amount;
            this.description = description;
            this.TransactionDate = transactionDate;
    }

    // Constructor with ID
    // Used When reading a  transaction from database
    public Transaction(int id,int userId, String type, String category, BigDecimal amount, String description, LocalDate transactionDate){
            this.id = id;
            this.userId = userId;
            this.type = type;
            this.category = category;
            this.amount = amount;
            this.description = description;
            this.TransactionDate = transactionDate;
    }

    // Getters and Setters to access private data
    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getUserId(){
        return userId;
    }

    public void setUserId(int user_id){
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    
    }
 
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return  description;
    }

    public LocalDate getTransactionDate() {
        return TransactionDate;
    }
    public void setTransactionDate(LocalDate transcationDate) {
        this.TransactionDate = transcationDate;
    }
}
