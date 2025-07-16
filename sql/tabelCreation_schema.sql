CREATE TABLE users (
                       student_number TEXT PRIMARY KEY NOT NULL,
                       email TEXT UNIQUE NOT NULL,
                       password TEXT NOT NULL,
                       phone VARCHAR(20),
                       name TEXT NOT NULL,
                       surname TEXT NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
);

CREATE TABLE Counselors(
                           name VARCHAR(20) NOT NULL,
                           specialization VARCHAR(20) NOT NULL,
                           availability VARCHAR(10) NOT NULL
);

CREATE TABLE Appointments(
                             student VARCHAR(20) NOT NULL,
                             counselor VARCHAR(20) NOT NULL,
                             date DATE NOT NULL,
                             time TIME NOT NULL,
                             status VARCHAR(20) NOT NULL
);

CREATE TABLE Feedback(
                         student VARCHAR(20) NOT NULL,
                         rating INT CHECK ( rating BETWEEN 1 and 10),
                         comments VARCHAR(225)
);