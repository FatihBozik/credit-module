# Credit Module

This module is responsible for managing the credit of the user. It includes functionalities for creating, listing, and
paying loans. The following endpoints are provided:

## Endpoints

### Create Loan

- **Description:** Create a new loan for a given customer.
- **URL:** `/loans`
- **Method:** `POST`
- **Request Body:**
  ```json
  {
    "customerId": "string",
    "amount": "number",
    "interestRate": "number",
    "numberOfInstallments": "number"
  }
  ```

- **Validation:**
    - Check if the customer has enough credit limit.
    - Number of installments can only be 6, 9, 12, or 24.
    - Interest rate must be between 0.1 and 0.5.
    - All installments should have the same amount.
    - The due date of installments should be the first day of the month.

- **Response:**

```json
{
  "loanId": "string",
  "customerId": "string",
  "amount": "number",
  "interestRate": "number",
  "numberOfInstallments": "number",
  "installments": [
    {
      "installmentId": "string",
      "amount": "number",
      "dueDate": "string"
    }
  ]
}
```

### List Loans

- **Description:** List all loans for a given customer.
- **URL:** /loans/{customerId}
- **Method:** GET
- **Response:**

```json
[
  {
    "loanId": "string",
    "customerId": "string",
    "amount": "number",
    "interestRate": "number",
    "numberOfInstallments": "number",
    "installments": [
      {
        "installmentId": "string",
        "amount": "number",
        "dueDate": "string"
      }
    ]
  }
]
```

### List Installments

- **Description:** List all installments for a given loan.
- **URL:** /loans/{loanId}/installments
- **Method:** GET
- **Response:**

```json
[
  {
    "installmentId": "string",
    "amount": "number",
    "dueDate": "string",
    "isPaid": "boolean"
  }
]
```

### Pay Loan

- **Description:** Pay installment for a given loan and amount.ü
- **URL:** /loans/{loanId}/pay
- **Method:** POST
- **Request Body:**

```json
{
  "amount": "number"
}
```

- **Validation:**
    - Installments should be paid wholly or not at all.
    - Pay the earliest installment first.
    - Installments due more than 3 months in the future cannot be paid.

- **Response:**

```json
{
  "installmentsPaid": "number",
  "totalAmountSpent": "number",
  "isLoanPaidCompletely": "boolean"
}
```

## Authorization

All endpoints should be authorized with an admin user and password.

## Database Structure

- Customer:
    - id
    - name
    - surname
    - creditLimit
    - usedCreditLimit

- Loan:
    - id
    - customerId
    - loanAmount
    - numberOfInstallments
    - createDate
    - isPaid

- LoanInstallment:
    - id
    - loanId
    - amount
    - paidAmount
    - dueDate
    - paymentDate
    - isPaid

## Installation and Running the Project

1. Clone the repository.

  ```bash
  git clone https://github.com/FatihBozik/credit-module.git
  ```

2. Navigate to the project directory:

  ```bash
  cd credit-module
  ```

3. Build the project:

 ```bash
 ./gradlew clean build
 ```

4. Run the application:

   ```bash
   ./gradlew bootRun
   ```

### Unit Tests

Unit tests are provided to ensure the functionality of the application. To run the tests, use the following command:

```bash
./gradlew test
```

## Notes

- The application uses Postgres SQL database for simplicity.
- Documentation is important. Ensure to update this README for any changes in the application.

## Bonus Features

### Authorization Roles

- Admin users can operate for all customers.
- Customer role users can operate for themselves.

### Reward and Penalty Logic

- If an installment is paid before the due date, a discount is applied.
- If an installment is paid after the due date, a penalty is added.

# Deployment

The application is deployed and can be accessed at:

**Host:** `api.credit-module.fatihbozik.com`

**Port:** `8080`

**Environment:** `Test`

---

This `README` provides a comprehensive overview of the Credit Module project,
its endpoints, and how to use the application.

Feel free to modify the README further based on any additional project-specific details or requirements.
