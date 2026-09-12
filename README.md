# Lunna Lingerie

Progetto personale per l'esame di **Sistemi Informativi su Web (SIW)** — Roma Tre.

E-commerce di intimo femminile: sito pubblico in Spring Boot + Thymeleaf con catalogo, carrello e
checkout, più un pannello di amministrazione separato in React per la gestione di prodotti,
categorie e ordini.

## Stack tecnico

- **Backend**: Spring Boot 4.1, Spring Security, Spring Data JPA / Hibernate, PostgreSQL
- **Frontend sito**: Thymeleaf, CSS/JS statico
- **Frontend admin**: React 19 + TypeScript + Vite (cartella `rect/`)
- **Autenticazione**: login con form + Google OAuth2, ruoli `CLIENTE` / `ADMIN`

## Funzionalità principali

- Catalogo prodotti con ricerca testuale, filtri per categoria/taglia/colore/prezzo, ordinamento
  (rilevanza, novità, prezzo) e paginazione
- Carrello, checkout e storico ordini, con stati (`CREATO`, `PAGATO`, `SPEDITO`, `CONSEGNATO`,
  `ANNULLATO`)
- Recensioni prodotto e lista dei preferiti
- Pannello di amministrazione (React), raggiungibile dal sito tramite il pulsante "Admin"
  visibile solo agli utenti con ruolo `ADMIN`: CRUD prodotti (con upload immagini) e categorie,
  gestione stato ordini

## Struttura del progetto

```
src/main/java/...              backend Spring Boot (controller, service, repository, model)
src/main/resources/templates   pagine Thymeleaf del sito pubblico
src/main/resources/static      CSS e immagini statiche
rect/                          applicazione React del pannello admin
uploads/                       immagini prodotto caricate dall'admin
```

## Avvio in locale

Il backend (Spring Boot) gira su **http://localhost:8080**, il pannello admin (React/Vite) su
**http://localhost:5173**. Il pannello admin richiede di aver effettuato il login sul sito
principale con un account che ha ruolo `ADMIN` (stessa sessione/cookie).

## Note

- Pagamento PayPal: ancora da attivare. Servono credenziali sandbox reali in
  `application.properties` (`paypal.client-id`, `paypal.client-secret`) — finché restano il
  valore placeholder, il bottone PayPal non compare in checkout.
- `spring.jpa.hibernate.ddl-auto=none`: lo schema del database non viene generato
  automaticamente da Hibernate, va creato/aggiornato manualmente (vedi `import.sql` per i dati
  di esempio).
