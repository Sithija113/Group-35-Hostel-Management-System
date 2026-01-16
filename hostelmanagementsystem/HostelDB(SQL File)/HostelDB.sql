CREATE DATABASE HostelDB;

USE HostelDB;


IF OBJECT_ID('students','U') IS NULL
CREATE TABLE students (
    student_id INT IDENTITY(1,1) PRIMARY KEY,
    first_name NVARCHAR(50) NOT NULL,
    last_name NVARCHAR(50) NOT NULL,
    gender NVARCHAR(10),
    contact NVARCHAR(20),
    email NVARCHAR(50),
    room_number NVARCHAR(10),
    join_date DATE DEFAULT GETDATE()
);

IF OBJECT_ID('rooms','U') IS NULL
CREATE TABLE rooms (
    room_id INT IDENTITY(1,1) PRIMARY KEY,
    room_number NVARCHAR(10) NOT NULL UNIQUE,
    type NVARCHAR(20),
    capacity INT NOT NULL,
    occupied INT DEFAULT 0
);

CREATE TABLE payments (
    payment_id INT IDENTITY(1,1) PRIMARY KEY,
    student_name NVARCHAR(100) NOT NULL,
    room_number NVARCHAR(10),
    amount DECIMAL(10,2),
    payment_date DATE DEFAULT GETDATE()
);
