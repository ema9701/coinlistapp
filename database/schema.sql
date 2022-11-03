BEGIN TRANSACTION;
DROP TABLE IF EXISTS watchlist_coin, watchlist, coin;

CREATE TABLE coin (
    coin_id SERIAL PRIMARY KEY,
    symbol VARCHAR(20),
    coin_name VARCHAR(100),
    current_price NUMERIC(13, 2)
);

 CREATE TABLE watchlist (
 	list_id SERIAL PRIMARY KEY,
 	list_name VARCHAR(50)
 );

 CREATE TABLE watchlist_coin (
	watchlist_id INT, 
    coin_id INT,
    
	CONSTRAINT PK_coin_watchlist_id PRIMARY KEY (watchlist_id, coin_id),
	CONSTRAINT FK_coin_watchlist_watchlist_id FOREIGN KEY (watchlist_id) REFERENCES watchlist(list_id),
    CONSTRAINT FK_coin_watchlist_coin_id FOREIGN KEY (coin_id) REFERENCES coin(coin_id)
 );

INSERT INTO coin (symbol, coin_name, current_price)
VALUES ('btc', 'Bitcoin', 20522.72),
       ('eth', 'Ethereum', 1587.65),
       ('ada', 'Cardano', 0.40),
       ('sol', 'Solana', 32.20),
	   ('tst', 'TestingCoin', 30.00),
	   ('stf', 'StuffCoin', 20.00);
 
INSERT INTO watchlist (list_name)
VALUES ('ListOne'),('ListTwo');

INSERT INTO watchlist_coin (watchlist_id, coin_id)
VALUES ((SELECT list_id FROM watchlist WHERE list_name = 'ListOne'), (SELECT coin_id FROM coin WHERE coin_name = 'TestingCoin')),
       ((SELECT list_id FROM watchlist WHERE list_name = 'ListOne'), (SELECT coin_id FROM coin WHERE coin_name = 'StuffCoin'));

-- ROLLBACK;
COMMIT TRANSACTION;