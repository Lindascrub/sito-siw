import { useEffect, useState } from 'react';
import { ApiError, adminAggiornaAttivo, adminEliminaProdotto, adminGetProdotti, getCategorie } from '../api';
import type { Categoria, Prodotto } from '../types';
import { ProductForm } from './ProductForm';
import { useSort } from '../hooks/useSort';
import { ThOrdinabile } from './ThOrdinabile';

export function ProdottiManager() {
  const [prodotti, setProdotti] = useState<Prodotto[]>([]);
  const [categorie, setCategorie] = useState<Categoria[]>([]);
  const [caricamento, setCaricamento] = useState(true);
  const [errore, setErrore] = useState<string | null>(null);
  const [prodottoInModifica, setProdottoInModifica] = useState<Prodotto | null>(null);
  const [formAperto, setFormAperto] = useState(false);

  useEffect(() => {
    getCategorie().then(setCategorie).catch(() => setCategorie([]));
  }, []);

  function carica() {
    setCaricamento(true);
    setErrore(null);
    adminGetProdotti()
      .then(setProdotti)
      .catch((err) => {
        if (err instanceof ApiError) setErrore(err.message);
      })
      .finally(() => setCaricamento(false));
  }

  useEffect(() => {
    carica();
  }, []);

  async function handleElimina(id: number) {
    setErrore(null);
    try {
      await adminEliminaProdotto(id);
      carica();
    } catch (err) {
      if (err instanceof ApiError) setErrore(err.message);
    }
  }

  async function handleToggleAttivo(p: Prodotto) {
    await adminAggiornaAttivo(p.id, !p.attivo);
    carica();
  }

  const { datiOrdinati, campo, direzione, ordinaPer } = useSort<Prodotto>(prodotti, (p, campo) => {
    switch (campo) {
      case 'nome':
        return p.nome;
      case 'categoria':
        return p.categoriaNome;
      case 'prezzo':
        return p.prezzo;
      case 'stock':
        return p.quantitaDisponibile;
      case 'stato':
        return p.attivo ? 1 : 0;
      default:
        return null;
    }
  });

  return (
    <section className="admin-sezione">
      <div className="admin__head">
        <h2>Gestione prodotti</h2>
        <button
          className="app__btn"
          onClick={() => {
            setProdottoInModifica(null);
            setFormAperto(true);
          }}
        >
          + Nuovo prodotto
        </button>
      </div>

      {errore && <p className="admin__errore">{errore}</p>}
      {caricamento && <p className="admin__stato">Caricamento...</p>}

      {!caricamento && (
        <div className="admin__tabella-wrap">
          <table className="admin__tabella">
            <thead>
              <tr>
                <ThOrdinabile campo="nome" etichetta="Nome" campoAttivo={campo} direzione={direzione} onClick={ordinaPer} />
                <ThOrdinabile campo="categoria" etichetta="Categoria" campoAttivo={campo} direzione={direzione} onClick={ordinaPer} />
                <ThOrdinabile campo="prezzo" etichetta="Prezzo" campoAttivo={campo} direzione={direzione} onClick={ordinaPer} />
                <ThOrdinabile campo="stock" etichetta="Stock" campoAttivo={campo} direzione={direzione} onClick={ordinaPer} />
                <ThOrdinabile campo="stato" etichetta="Stato" campoAttivo={campo} direzione={direzione} onClick={ordinaPer} />
                <th></th>
              </tr>
            </thead>
            <tbody>
              {datiOrdinati.map((p) => (
                <tr key={p.id}>
                  <td>{p.nome}</td>
                  <td>{p.categoriaNome}</td>
                  <td>€ {p.prezzo.toFixed(2)}</td>
                  <td>{p.quantitaDisponibile}</td>
                  <td>{p.attivo ? 'Attivo' : 'Disattivato'}</td>
                  <td className="admin__azioni">
                    <button
                      onClick={() => {
                        setProdottoInModifica(p);
                        setFormAperto(true);
                      }}
                    >
                      Modifica
                    </button>
                    <button onClick={() => handleToggleAttivo(p)}>{p.attivo ? 'Disattiva' : 'Attiva'}</button>
                    <button className="admin__azione-pericolo" onClick={() => handleElimina(p.id)}>
                      Elimina
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {formAperto && (
        <ProductForm
          categorie={categorie}
          prodotto={prodottoInModifica}
          onSalvato={() => {
            setFormAperto(false);
            carica();
          }}
          onAnnulla={() => setFormAperto(false)}
        />
      )}
    </section>
  );
}
