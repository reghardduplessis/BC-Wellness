CREATE TABLE users (
                       student_number TEXT PRIMARY KEY NOT NULL,
                       email TEXT UNIQUE NOT NULL,
                       password TEXT NOT NULL,
                       phone VARCHAR(20),
                       name TEXT NOT NULL,
                       surname TEXT NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
);