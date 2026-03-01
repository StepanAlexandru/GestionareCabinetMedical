# Medical Office Management System 

A robust Java desktop application designed for efficient patient data administration and secure medical record handling. This project focuses on data privacy and follows a clean, modular architecture.

## Key Features

* **Full CRUD Functionality:** Seamlessly add, view, edit, and delete patient records.
* **Advanced Security (AES-128):** Automatic encryption of sensitive data (National ID/CNP) before database storage to ensure GDPR compliance and data privacy.
* **Live Search System:** Real-time filtering of the patient database as you type, implemented via `DocumentListener`.
* **Automated Reporting:** Generate and export detailed medical reports in `.txt` format for each consultation.
* **Data Validation:** Built-in logic to ensure all required fields are correctly filled before database entry.

## Tech Stack

* **Language:** Java (JDK 17+)
* **GUI Framework:** Java Swing
* **Database:** MySQL
* **Connectivity:** JDBC (Java Database Connectivity)
* **Security:** AES Symmetric Encryption (Java Cryptography Architecture)
* **Version Control:** Git & GitHub

## Software Architecture

The project follows a **Layered Architecture** (Service-Repository pattern) to ensure a high degree of maintainability and scalability:

* **`model`**: Defines the data structure (POJOs).
* **`repository`**: Handles direct database interaction using SQL and PreparedStatements.
* **`controller` (Service)**: Contains the business logic, data validation, and bridges the UI with the Data Layer.
* **`util`**: Utility classes for encryption (`CryptoUtils`) and validation helpers.
* **`view`**: The Graphical User Interface components.

Database Setup:
Create a MySQL database named cabinet_medical.
Run the provided SQL script to create the pacienti table.

Configuration:
Update the DatabaseConnection class with your MySQL username and password.

Run:
Execute the Main class from your preferred IDE.
