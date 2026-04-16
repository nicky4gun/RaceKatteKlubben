DROP TABLE IF EXISTS catawards;
DROP TABLE IF EXISTS awards;
DROP TABLE IF EXISTS cats;
DROP TABLE IF EXISTS members;

CREATE TABLE IF NOT EXISTS members (
    id INT AUTO_INCREMENT PRIMARY KEY,
    member_name VARCHAR(50) not null,
    email VARCHAR(100) not null,
    password VARCHAR(200) not null,
    admin TINYINT
);

CREATE TABLE IF NOT EXISTS cats (
    id INT AUTO_INCREMENT PRIMARY KEY,
    image VARCHAR(255),
    cat_name VARCHAR(30) not null,
    race VARCHAR(50),
    age  INT,
    year_or_month VARCHAR(10),
    gender VARCHAR(10),
    member_id int,

    FOREIGN KEY (member_id) REFERENCES members(id)
);

CREATE TABLE IF NOT EXISTS awards (
    id INT AUTO_INCREMENT PRIMARY KEY,
    awardName VARCHAR(40),
    Standing int
);

CREATE TABLE IF NOT EXISTS catawards (
    id INT AUTO_INCREMENT PRIMARY KEY,
    award_id int,
    cat_id int,

    FOREIGN KEY (award_id) REFERENCES awards(id),
    FOREIGN KEY (cat_id) REFERENCES cats(id)
);