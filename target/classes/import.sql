-- CATEGORIE
INSERT INTO categoria (id, nome) VALUES (nextval('categoria_seq'), 'Reggiseni');
INSERT INTO categoria (id, nome) VALUES (nextval('categoria_seq'), 'Guaine modellanti');
INSERT INTO categoria (id, nome) VALUES (nextval('categoria_seq'), 'Bodysuits');
INSERT INTO categoria (id, nome) VALUES (nextval('categoria_seq'), 'Lingerie');

-- TAGLIE
INSERT INTO taglia (id, codice, descrizione) VALUES (nextval('taglia_seq'), 'XS', 'Extra Small');
INSERT INTO taglia (id, codice, descrizione) VALUES (nextval('taglia_seq'), 'S', 'Small');
INSERT INTO taglia (id, codice, descrizione) VALUES (nextval('taglia_seq'), 'M', 'Medium');
INSERT INTO taglia (id, codice, descrizione) VALUES (nextval('taglia_seq'), 'L', 'Large');
INSERT INTO taglia (id, codice, descrizione) VALUES (nextval('taglia_seq'), 'XL', 'Extra Large');

-- PRODOTTI (usando SELECT per trovare l'ID della categoria)
INSERT INTO prodotto (id, nome, descrizione, prezzo, quantita_disponibile, url_immagine, categoria_id) VALUES (nextval('prodotto_seq'), 'Reggiseno Triangolo Fanciful Flowers', 'Reggiseno', 35.90, 50, '/images/triangolofiore.jpg', (SELECT id FROM categoria WHERE nome = 'Reggiseni'));
INSERT INTO prodotto (id, nome, descrizione, prezzo, quantita_disponibile, url_immagine, categoria_id) VALUES (nextval('prodotto_seq'), 'Guaina modellante nera', 'Guaina modellante in microfibra', 39.99, 30, '/images/guaina-nera.jpg', (SELECT id FROM categoria WHERE nome = 'Guaine modellanti'));
INSERT INTO prodotto (id, nome, descrizione, prezzo, quantita_disponibile, url_immagine, categoria_id) VALUES (nextval('prodotto_seq'), 'Bodysuit in pizzo bianco', 'Bodysuit elegante in pizzo', 34.99, 25, '/images/bodysuit-bianco.jpg', (SELECT id FROM categoria WHERE nome = 'Bodysuits'));
INSERT INTO prodotto (id, nome, descrizione, prezzo, quantita_disponibile, url_immagine, categoria_id) VALUES (nextval('prodotto_seq'), 'Set lingerie in seta', 'Set completo in seta naturale', 59.99, 15, '/images/lingerie-seta.jpg', (SELECT id FROM categoria WHERE nome = 'Lingerie'));
-- NIENTE INSERT SU prodotto_taglie - JPA se ne occuperà!
-- ... (categorie, taglie, prodotti come già hai) ...

-- Credenziali per admin (password: admin)
INSERT INTO credenziali (id, username, password, ruolo) VALUES (nextval('credenziali_seq'), 'admin', '$2a$10$N9qo8uLOickgx2ZMRZoMy.Mr4J5XaJQlP7FQ/fnKqK7Wq3wFq8Twe', 'ADMIN');

-- Utente admin
INSERT INTO users (id, nome, cognome, email, credenziali_id) VALUES (nextval('users_seq'), 'Admin', 'Admin', 'admin@intimostore.it', (SELECT currval('credenziali_seq')));