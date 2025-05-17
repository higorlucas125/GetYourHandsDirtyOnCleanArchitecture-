-- Create the countries table
CREATE TABLE IF NOT EXISTS countries (
                                         id INT PRIMARY KEY,
                                         name VARCHAR(50) NOT NULL
    );

-- Insert sample data into the countries table
INSERT INTO countries (id, name) VALUES (1, 'USA');
INSERT INTO countries (id, name) VALUES (2, 'France');
INSERT INTO countries (id, name) VALUES (3, 'Brazil');
INSERT INTO countries (id, name) VALUES (4, 'Italy');
INSERT INTO countries (id, name) VALUES (5, 'Canada');
