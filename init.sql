USE master;
GO

-- Create the database
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'bookingDate')
BEGIN
    CREATE DATABASE [your-database-name];
END