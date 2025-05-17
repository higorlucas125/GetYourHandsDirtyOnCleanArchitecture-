
-- Create the account table with a foreign key reference to the countries table
CREATE TABLE IF NOT EXISTS account (
    id INT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    country_id INT NOT NULL,
    FOREIGN KEY (country_id) REFERENCES countries(id)
)