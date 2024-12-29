CREATE TABLE users (
  id bigint NOT NULL PRIMARY KEY,
  username varchar(200) NOT NULL UNIQUE,
  password varchar(250) NOT NULL
);


CREATE TABLE expenses (
  id bigint NOT NULL PRIMARY KEY,
  user_id bigint NOT NULL,
  name varchar(100) NOT NULL,
  value double NOT NULL,
  amount bigint NOT NULL,
  record_date timestamp NOT NULL
);

ALTER TABLE expenses ADD CONSTRAINT expenses_user_id_fk FOREIGN KEY (user_id) REFERENCES users (id);
