-- Drop tables if they exist to avoid conflicts
DROP TABLE IF EXISTS players;
DROP TABLE IF EXISTS csk;
DROP TABLE IF EXISTS dc;
DROP TABLE IF EXISTS gt;
DROP TABLE IF EXISTS kkr;
DROP TABLE IF EXISTS lsg;
DROP TABLE IF EXISTS mi;
DROP TABLE IF EXISTS pbks;
DROP TABLE IF EXISTS rcb;
DROP TABLE IF EXISTS rr;
DROP TABLE IF EXISTS srh;

-- Create Players Table
CREATE TABLE players (
    pName VARCHAR(255) PRIMARY KEY,  -- Player Name as Primary Key
    Role VARCHAR(50),                -- Batsman, Bowler, WK, All Rounder
    basePrice DECIMAL(10,2),         -- Base price of the player
    rating INT,                      -- Player rating
    pot INT                           -- Player pool category (1, 2, etc.)
);

-- Create Team Tables (Same Structure for Each Team)
CREATE TABLE rcb (
    pName VARCHAR(255) PRIMARY KEY,  
    bidPrice DECIMAL(10,2),         -- Final bid price in auction
    rating INT,                     
    Role VARCHAR(50),               
    FOREIGN KEY (pName) REFERENCES players(pName) ON DELETE CASCADE
);

-- Duplicate the same structure for other teams (csk, mi, dc, etc.)
CREATE TABLE csk LIKE rcb;
CREATE TABLE dc LIKE rcb;
CREATE TABLE gt LIKE rcb;
CREATE TABLE kkr LIKE rcb;
CREATE TABLE lsg LIKE rcb;
CREATE TABLE mi LIKE rcb;
CREATE TABLE pbks LIKE rcb;
CREATE TABLE rr LIKE rcb;
CREATE TABLE srh LIKE rcb;
