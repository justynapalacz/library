CREATE TABLE IF NOT EXISTS books (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  title VARCHAR (100),
  author VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS readers (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  first_name VARCHAR (100),
  last_name VARCHAR(100),
  date_of_birth DATE NOT NULL
);
