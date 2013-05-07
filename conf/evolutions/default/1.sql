
# --- !Ups

CREATE TABLE game (
    id 				serial PRIMARY KEY,
    address 		varchar(255) NOT NULL,
	maxPlayers 		integer NOT NULL,
	currentPlayers 	integer NOT NULL
);

# --- !Downs
