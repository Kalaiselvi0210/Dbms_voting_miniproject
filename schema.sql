-- Voting Database Management System (MySQL)
CREATE DATABASE IF NOT EXISTS voting_dbms;
USE voting_dbms;

CREATE TABLE IF NOT EXISTS admin (
    admin_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS voter_booth (
    booth_id INT PRIMARY KEY AUTO_INCREMENT,
    booth_name VARCHAR(100) NOT NULL,
    location VARCHAR(150) NOT NULL
);

CREATE TABLE IF NOT EXISTS voter (
    voter_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(20) NOT NULL,
    address VARCHAR(200) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    has_voted TINYINT(1) NOT NULL DEFAULT 0,
    booth_id INT NOT NULL,
    CONSTRAINT fk_voter_booth FOREIGN KEY (booth_id) REFERENCES voter_booth(booth_id)
);

CREATE TABLE IF NOT EXISTS candidate (
    candidate_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    party VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    symbol VARCHAR(50) NOT NULL,
    booth_id INT NOT NULL,
    CONSTRAINT fk_candidate_booth FOREIGN KEY (booth_id) REFERENCES voter_booth(booth_id)
);

CREATE TABLE IF NOT EXISTS result (
    result_id INT PRIMARY KEY AUTO_INCREMENT,
    candidate_id INT NOT NULL,
    total_votes INT NOT NULL DEFAULT 0,
    booth_id INT NOT NULL,
    CONSTRAINT fk_result_candidate FOREIGN KEY (candidate_id) REFERENCES candidate(candidate_id) ON DELETE CASCADE,
    CONSTRAINT fk_result_booth FOREIGN KEY (booth_id) REFERENCES voter_booth(booth_id),
    CONSTRAINT uq_result UNIQUE (candidate_id, booth_id)
);

-- Default admin and booths
INSERT INTO admin (username, password, email)
VALUES ('admin', 'admin123', 'admin@vote.com')
ON DUPLICATE KEY UPDATE email = VALUES(email);

INSERT INTO voter_booth (booth_name, location)
VALUES
('Booth A', 'City School'),
('Booth B', 'Community Hall'),
('Booth C', 'Town Library')
ON DUPLICATE KEY UPDATE location = VALUES(location);
