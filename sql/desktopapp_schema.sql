CREATE TABLE users (
                       student_number VARCHAR(10) PRIMARY KEY,
                       email VARCHAR(100),
                       password VARCHAR(100),
                       name VARCHAR(50),
                       surname VARCHAR(50),
                       created_at TIMESTAMP
);

CREATE TABLE Feedback (
                          id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,  -- auto-incrementing
                          student VARCHAR(255) NOT NULL,
                          rating INT NOT NULL,
                          comments VARCHAR(500)
);

CREATE TABLE Counselors (
                            id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                            name VARCHAR(100),
                            specialization VARCHAR(100),
                            availability VARCHAR(100)
);

CREATE TABLE Appointments (
                              id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                              student VARCHAR(100),
                              counselor VARCHAR(100),
                              date VARCHAR(20),
                              time VARCHAR(20),
                              status VARCHAR(20)
);