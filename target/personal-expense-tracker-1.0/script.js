// Store transactions temporarily for displaying them
let transactions = [];


// Get HTML elements
const form = document.getElementById("expense-form");

const transactionList =
    document.getElementById("transaction-list");

const totalIncome =
    document.getElementById("total-income");

const totalExpense =
    document.getElementById("total-expense");

const balance =
    document.getElementById("balance");


// Add transaction
form.addEventListener("submit", async function(event) {

    // Prevent page refresh
    event.preventDefault();

    // Get values from form
    const type =
        document.getElementById("type").value;

    const category =
        document.getElementById("category").value;

    const amount =
        document.getElementById("amount").value;

    const description =
        document.getElementById("description").value;

    const date =
        document.getElementById("date").value;


    // Temporary user ID for testing
    const userId = 1;


    // Create form data
    const formData = new URLSearchParams();

    formData.append("userId", userId);
    formData.append("type", type);
    formData.append("category", category);
    formData.append("amount", amount);
    formData.append("description", description);
    formData.append("date", date);


    try {

        // Send data to Java Servlet
        const response = await fetch("addTransaction", {

            method: "POST",

            headers: {
                "Content-Type":
                    "application/x-www-form-urlencoded"
            },

            body: formData
        });


        // Get response from servlet
        const result = await response.text();

        console.log(result);


        if (response.ok) {

            alert("Transaction added successfully!");


            // Add transaction to frontend temporarily
            const transaction = {

                id: Date.now(),

                type: type,

                category: category,

                amount: Number(amount),

                description: description,

                date: date
            };


            transactions.push(transaction);

            displayTransactions();

            updateSummary();

            form.reset();


            // Set today's date again
            document.getElementById("date").valueAsDate =
                new Date();

        } else {

            alert("Failed to add transaction.");

        }

    } catch (error) {

        console.error(error);

        alert(
            "Could not connect to the Java backend."
        );

    }

});


// Display transactions
function displayTransactions() {

    transactionList.innerHTML = "";


    transactions.forEach(function(transaction) {

        const row =
            document.createElement("tr");


        const amountClass =
            transaction.type === "income"
                ? "income-text"
                : "expense-text";


        const amountSymbol =
            transaction.type === "income"
                ? "+"
                : "-";


        row.innerHTML = `

            <td>${transaction.type}</td>

            <td>${transaction.category}</td>

            <td class="${amountClass}">
                ${amountSymbol} ₹${transaction.amount}
            </td>

            <td>${transaction.description}</td>

            <td>${transaction.date}</td>

            <td>
                <button
                    class="delete-btn"
                    onclick="deleteTransaction(${transaction.id})">
                    Delete
                </button>
            </td>
        `;


        transactionList.appendChild(row);

    });

}


// Delete transaction
function deleteTransaction(id) {

    transactions =
        transactions.filter(function(transaction) {

            return transaction.id !== id;

        });


    displayTransactions();

    updateSummary();

}


// Update dashboard
function updateSummary() {

    let income = 0;

    let expense = 0;


    transactions.forEach(function(transaction) {

        if (transaction.type === "income") {

            income += transaction.amount;

        } else {

            expense += transaction.amount;

        }

    });


    const currentBalance =
        income - expense;


    totalIncome.innerText =
        "₹" + income;

    totalExpense.innerText =
        "₹" + expense;

    balance.innerText =
        "₹" + currentBalance;

}


// Set today's date automatically
document.getElementById("date").valueAsDate =
    new Date();