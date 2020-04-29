DROP TABLE IF EXISTS user;

CREATE TABLE user (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL,
  address VARCHAR(250) DEFAULT NULL
);

INSERT INTO user (first_name, last_name, address) VALUES
  ('John', 'Doe', 'Test Address 1'),
  ('Jane', 'Doe', 'Test Address 2'),
  ('Jason', 'Doe', 'Test Address 3'),
  ('kim', 'Doe', 'Test Address 4');