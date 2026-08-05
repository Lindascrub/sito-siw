-- ============================================
-- 🔹 CATEGORIE
-- ============================================
INSERT INTO categoria (id, nome, descrizione) VALUES (nextval('categoria_seq'), 'Intimo Donna', 'Abiti intimi per donna, comodi e di alta qualità');
INSERT INTO categoria (id, nome, descrizione) VALUES (nextval('categoria_seq'), 'Intimo Uomo', 'Abiti intimi per uomo, adatti a ogni esigenza');
INSERT INTO categoria (id, nome, descrizione) VALUES (nextval('categoria_seq'), 'Pigiami', 'Pigiami in seta e cotone per un riposo confortevole');
INSERT INTO categoria (id, nome, descrizione) VALUES (nextval('categoria_seq'), 'Sport', 'Intimo tecnico per attività sportiva');
INSERT INTO categoria (id, nome, descrizione) VALUES (nextval('categoria_seq'), 'Accessori', 'Calze, guanti e altri accessori');

-- ============================================
-- 🔹 TAGLIE
-- ============================================
INSERT INTO taglia (id, nome, ordine, descrizione) VALUES (nextval('taglia_seq'), 'XS', 1, 'Extra Small');
INSERT INTO taglia (id, nome, ordine, descrizione) VALUES (nextval('taglia_seq'), 'S', 2, 'Small');
INSERT INTO taglia (id, nome, ordine, descrizione) VALUES (nextval('taglia_seq'), 'M', 3, 'Medium');
INSERT INTO taglia (id, nome, ordine, descrizione) VALUES (nextval('taglia_seq'), 'L', 4, 'Large');
INSERT INTO taglia (id, nome, ordine, descrizione) VALUES (nextval('taglia_seq'), 'XL', 5, 'Extra Large');

-- ============================================
-- 🔹 PRODOTTI
-- ============================================
INSERT INTO prodotto (id, nome, descrizione, prezzo, quantita_disponibile, url_image, attivo, categoria_id, codice_modello) VALUES (nextval('prodotto_seq'), 'Bralette in Pizzo Nero', 'Bralette in pizzo di alta qualità, regolabile e molto confortevole. Perfetta per ogni giorno.', 24.99, 50, 'https://via.placeholder.com/400x400/000000/FFFFFF?text=Bralette+Nera', true, (SELECT id FROM categoria WHERE nome = 'Intimo Donna'), 'INT-D-001');
INSERT INTO prodotto (id, nome, descrizione, prezzo, quantita_disponibile, url_image, attivo, categoria_id, codice_modello) VALUES (nextval('prodotto_seq'), 'Slip in Seta Blu', 'Slip in seta naturale, morbidissimo e traspirante. Disponibile in diverse taglie.', 19.99, 35, 'https://via.placeholder.com/400x400/0000FF/FFFFFF?text=Slip+Blu', true, (SELECT id FROM categoria WHERE nome = 'Intimo Donna'), 'INT-D-002');
INSERT INTO prodotto (id, nome, descrizione, prezzo, quantita_disponibile, url_image, attivo, categoria_id, codice_modello) VALUES (nextval('prodotto_seq'), 'Reggiseno Push-Up Rosa', 'Reggiseno push-up con imbottitura rimovibile. Sostegno e morbidezza.', 29.99, 25, 'https://via.placeholder.com/400x400/FF69B4/FFFFFF?text=Push-Up+Rosa', true, (SELECT id FROM categoria WHERE nome = 'Intimo Donna'), 'INT-D-003');
INSERT INTO prodotto (id, nome, descrizione, prezzo, quantita_disponibile, url_image, attivo, categoria_id, codice_modello) VALUES (nextval('prodotto_seq'), 'Boxer in Cotone Grigio', 'Boxer in cotone biologico, elasticizzato e traspirante. Fascia elastica confortevole.', 14.99, 60, 'https://via.placeholder.com/400x400/808080/FFFFFF?text=Boxer+Grigio', true, (SELECT id FROM categoria WHERE nome = 'Intimo Uomo'), 'INT-U-001');
INSERT INTO prodotto (id, nome, descrizione, prezzo, quantita_disponibile, url_image, attivo, categoria_id, codice_modello) VALUES (nextval('prodotto_seq'), 'Slip in Microfibra Nero', 'Slip in microfibra, ultraleggero e ad asciugatura rapida. Perfetto per lo sport.', 16.99, 40, 'https://via.placeholder.com/400x400/000000/FFFFFF?text=Slip+Nero', true, (SELECT id FROM categoria WHERE nome = 'Intimo Uomo'), 'INT-U-002');
INSERT INTO prodotto (id, nome, descrizione, prezzo, quantita_disponibile, url_image, attivo, categoria_id, codice_modello) VALUES (nextval('prodotto_seq'), 'T-Shirt Intima Bianca', 'T-shirt intima in cotone elasticizzato, senza cuciture laterali. Massimo comfort.', 12.99, 45, 'https://via.placeholder.com/400x400/FFFFFF/000000?text=T-Shirt+Bianca', true, (SELECT id FROM categoria WHERE nome = 'Intimo Uomo'), 'INT-U-003');
INSERT INTO prodotto (id, nome, descrizione, prezzo, quantita_disponibile, url_image, attivo, categoria_id, codice_modello) VALUES (nextval('prodotto_seq'), 'Pigiama in Seta Champagne', 'Pigiama a due pezzi in seta pregiata. Giacca con revers e pantaloni a vita elastica.', 89.99, 15, 'https://via.placeholder.com/400x400/F5E6D3/FFFFFF?text=Pigiama+Champagne', true, (SELECT id FROM categoria WHERE nome = 'Pigiami'), 'INT-P-001');
INSERT INTO prodotto (id, nome, descrizione, prezzo, quantita_disponibile, url_image, attivo, categoria_id, codice_modello) VALUES (nextval('prodotto_seq'), 'Pigiama in Flanella Rossa', 'Pigiama in flanella di cotone, ideale per l''inverno. Motivo a quadri.', 49.99, 20, 'https://via.placeholder.com/400x400/8B0000/FFFFFF?text=Pigiama+Rossa', true, (SELECT id FROM categoria WHERE nome = 'Pigiami'), 'INT-P-002');
INSERT INTO prodotto (id, nome, descrizione, prezzo, quantita_disponibile, url_image, attivo, categoria_id, codice_modello) VALUES (nextval('prodotto_seq'), 'Top Sportivo Aria', 'Top sportivo in tessuto tecnico traspirante. Ideale per running e fitness.', 34.99, 30, 'https://via.placeholder.com/400x400/00CED1/FFFFFF?text=Top+Sportivo', true, (SELECT id FROM categoria WHERE nome = 'Sport'), 'INT-S-001');
INSERT INTO prodotto (id, nome, descrizione, prezzo, quantita_disponibile, url_image, attivo, categoria_id, codice_modello) VALUES (nextval('prodotto_seq'), 'Leggins Sportivi Nero', 'Leggins ad alta compressione, con tasche laterali. Perfetti per ogni allenamento.', 39.99, 25, 'https://via.placeholder.com/400x400/000000/FFFFFF?text=Leggins+Nero', true, (SELECT id FROM categoria WHERE nome = 'Sport'), 'INT-S-002');
INSERT INTO prodotto (id, nome, descrizione, prezzo, quantita_disponibile, url_image, attivo, categoria_id, codice_modello) VALUES (nextval('prodotto_seq'), 'Calze in Microfibra', 'Calze in microfibra, traspiranti e resistenti. Confezione da 5 paia.', 9.99, 100, 'https://via.placeholder.com/400x400/8B008B/FFFFFF?text=Calze+Microfibra', true, (SELECT id FROM categoria WHERE nome = 'Accessori'), 'INT-A-001');
INSERT INTO prodotto (id, nome, descrizione, prezzo, quantita_disponibile, url_image, attivo, categoria_id, codice_modello) VALUES (nextval('prodotto_seq'), 'Guanti in Lana Merino', 'Guanti in lana merino, morbidissimi e caldi. Ideali per l''inverno.', 19.99, 30, 'https://via.placeholder.com/400x400/8B4513/FFFFFF?text=Guanti+Lana', true, (SELECT id FROM categoria WHERE nome = 'Accessori'), 'INT-A-002');

-- ============================================
-- 🔹 PRODOTTO_TAGLIE
-- ============================================
INSERT INTO prodotto_taglie (prodotto_id, taglia) VALUES ((SELECT id FROM prodotto WHERE codice_modello = 'INT-D-001'), 'XS');
INSERT INTO prodotto_taglie (prodotto_id, taglia) VALUES ((SELECT id FROM prodotto WHERE codice_modello = 'INT-D-001'), 'S');
INSERT INTO prodotto_taglie (prodotto_id, taglia) VALUES ((SELECT id FROM prodotto WHERE codice_modello = 'INT-D-001'), 'M');
INSERT INTO prodotto_taglie (prodotto_id, taglia) VALUES ((SELECT id FROM prodotto WHERE codice_modello = 'INT-D-001'), 'L');
INSERT INTO prodotto_taglie (prodotto_id, taglia) VALUES ((SELECT id FROM prodotto WHERE codice_modello = 'INT-D-001'), 'XL');
INSERT INTO prodotto_taglie (prodotto_id, taglia) VALUES ((SELECT id FROM prodotto WHERE codice_modello = 'INT-D-002'), 'S');
INSERT INTO prodotto_taglie (prodotto_id, taglia) VALUES ((SELECT id FROM prodotto WHERE codice_modello = 'INT-D-002'), 'M');
INSERT INTO prodotto_taglie (prodotto_id, taglia) VALUES ((SELECT id FROM prodotto WHERE codice_modello = 'INT-D-002'), 'L');
INSERT INTO prodotto_taglie (prodotto_id, taglia) VALUES ((SELECT id FROM prodotto WHERE codice_modello = 'INT-U-001'), 'S');
INSERT INTO prodotto_taglie (prodotto_id, taglia) VALUES ((SELECT id FROM prodotto WHERE codice_modello = 'INT-U-001'), 'M');
INSERT INTO prodotto_taglie (prodotto_id, taglia) VALUES ((SELECT id FROM prodotto WHERE codice_modello = 'INT-U-001'), 'L');
INSERT INTO prodotto_taglie (prodotto_id, taglia) VALUES ((SELECT id FROM prodotto WHERE codice_modello = 'INT-U-001'), 'XL');
INSERT INTO prodotto_taglie (prodotto_id, taglia) VALUES ((SELECT id FROM prodotto WHERE codice_modello = 'INT-P-001'), 'S');
INSERT INTO prodotto_taglie (prodotto_id, taglia) VALUES ((SELECT id FROM prodotto WHERE codice_modello = 'INT-P-001'), 'M');
INSERT INTO prodotto_taglie (prodotto_id, taglia) VALUES ((SELECT id FROM prodotto WHERE codice_modello = 'INT-P-001'), 'L');
INSERT INTO prodotto_taglie (prodotto_id, taglia) VALUES ((SELECT id FROM prodotto WHERE codice_modello = 'INT-S-001'), 'XS');
INSERT INTO prodotto_taglie (prodotto_id, taglia) VALUES ((SELECT id FROM prodotto WHERE codice_modello = 'INT-S-001'), 'S');
INSERT INTO prodotto_taglie (prodotto_id, taglia) VALUES ((SELECT id FROM prodotto WHERE codice_modello = 'INT-S-001'), 'M');
INSERT INTO prodotto_taglie (prodotto_id, taglia) VALUES ((SELECT id FROM prodotto WHERE codice_modello = 'INT-S-001'), 'L');
INSERT INTO prodotto_taglie (prodotto_id, taglia) VALUES ((SELECT id FROM prodotto WHERE codice_modello = 'INT-S-002'), 'S');
INSERT INTO prodotto_taglie (prodotto_id, taglia) VALUES ((SELECT id FROM prodotto WHERE codice_modello = 'INT-S-002'), 'M');
INSERT INTO prodotto_taglie (prodotto_id, taglia) VALUES ((SELECT id FROM prodotto WHERE codice_modello = 'INT-S-002'), 'L');
INSERT INTO prodotto_taglie (prodotto_id, taglia) VALUES ((SELECT id FROM prodotto WHERE codice_modello = 'INT-S-002'), 'XL');

-- ============================================
-- 🔹 PRODOTTO_COLORI
-- ============================================
INSERT INTO prodotto_colori (prodotto_id, colori) VALUES ((SELECT id FROM prodotto WHERE codice_modello = 'INT-D-001'), 'Nero');
INSERT INTO prodotto_colori (prodotto_id, colori) VALUES ((SELECT id FROM prodotto WHERE codice_modello = 'INT-D-001'), 'Bianco');
INSERT INTO prodotto_colori (prodotto_id, colori) VALUES ((SELECT id FROM prodotto WHERE codice_modello = 'INT-D-001'), 'Rosa');
INSERT INTO prodotto_colori (prodotto_id, colori) VALUES ((SELECT id FROM prodotto WHERE codice_modello = 'INT-D-002'), 'Blu');
INSERT INTO prodotto_colori (prodotto_id, colori) VALUES ((SELECT id FROM prodotto WHERE codice_modello = 'INT-D-002'), 'Viola');
INSERT INTO prodotto_colori (prodotto_id, colori) VALUES ((SELECT id FROM prodotto WHERE codice_modello = 'INT-D-002'), 'Verde');
INSERT INTO prodotto_colori (prodotto_id, colori) VALUES ((SELECT id FROM prodotto WHERE codice_modello = 'INT-U-001'), 'Grigio');
INSERT INTO prodotto_colori (prodotto_id, colori) VALUES ((SELECT id FROM prodotto WHERE codice_modello = 'INT-U-001'), 'Nero');
INSERT INTO prodotto_colori (prodotto_id, colori) VALUES ((SELECT id FROM prodotto WHERE codice_modello = 'INT-U-001'), 'Blu');
INSERT INTO prodotto_colori (prodotto_id, colori) VALUES ((SELECT id FROM prodotto WHERE codice_modello = 'INT-P-001'), 'Champagne');
INSERT INTO prodotto_colori (prodotto_id, colori) VALUES ((SELECT id FROM prodotto WHERE codice_modello = 'INT-P-001'), 'Avorio');
INSERT INTO prodotto_colori (prodotto_id, colori) VALUES ((SELECT id FROM prodotto WHERE codice_modello = 'INT-P-002'), 'Rosso');
INSERT INTO prodotto_colori (prodotto_id, colori) VALUES ((SELECT id FROM prodotto WHERE codice_modello = 'INT-P-002'), 'Blu notte');

-- ============================================
-- 🔹 CREDENZIALI
-- ============================================
INSERT INTO credenziali (id, username, password, ruolo) VALUES (nextval('credenziali_seq'), 'admin', '$2a$10$3qYwM6G8q5Y5W5W5W5W5W5O5O5O5O5O5O5O5O', 'ADMIN');
INSERT INTO credenziali (id, username, password, ruolo) VALUES (nextval('credenziali_seq'), 'cliente', '$2a$10$4qYwM6G8q5Y5W5W5W5W5W5O5O5O5O5O5O5O5O', 'CLIENTE');

-- ============================================
-- 🔹 UTENTI
-- ============================================
INSERT INTO users (id, nome, cognome, email, telefono, indirizzo, credenziali_id) VALUES (nextval('users_seq'), 'Admin', 'Admin', 'admin@intimistore.com', '1234567890', 'Via Roma, 1', (SELECT id FROM credenziali WHERE username = 'admin'));
INSERT INTO users (id, nome, cognome, email, telefono, indirizzo, credenziali_id) VALUES (nextval('users_seq'), 'Mario', 'Rossi', 'mario@email.com', '9876543210', 'Via Milano, 20', (SELECT id FROM credenziali WHERE username = 'cliente'));

-- ============================================
-- 🔹 CARRELLI
-- ============================================
INSERT INTO carrello (id, utente_id) VALUES (nextval('carrello_seq'), (SELECT id FROM users WHERE email = 'admin@intimistore.com'));
INSERT INTO carrello (id, utente_id) VALUES (nextval('carrello_seq'), (SELECT id FROM users WHERE email = 'mario@email.com'));