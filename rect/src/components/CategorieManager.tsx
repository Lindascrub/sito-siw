import { useEffect, useState, type FormEvent } from 'react';
import { ApiError, adminCreaCategoria, adminEliminaCategoria, adminGetCategorie, adminModificaCategoria } from '../api';
import type { Categoria } from '../types';
import { useSort } from '../hooks/useSort';
import { ThOrdinabile } from './ThOrdinabile';

function CategoriaForm({
  categoria,
  onSalvato,
  onAnnulla,
}: {
  categoria: Categoria | null;
  onSalvato: () => void;
  onAnnulla: () => void;
}) {
  const [nome, setNome] = useState(categoria?.nome ?? '');
  const [descrizione, setDescrizione] = useState(categoria?.descrizione ?? '');
  const [invio, setInvio] = useState(false);
  const [errore, setErrore] = useState<string | null>(null);

  async function handleSubmit(e: FormEvent) {
    e.preventDefault();
    setInvio(true);
    setErrore(null);
    try {
      if (categoria) {
        await adminModificaCategoria(categoria.id, { nome, descrizione });
      } else {
        await adminCreaCategoria({ nome, descrizione });
      }
      onSalvato();
    } catch (err) {
      setErrore(err instanceof ApiError ? err.message : 'Errore imprevisto, riprova.');
    } finally {
      setInvio(false);
    }
  }

  return (
    <div className="admin__overlay" onClick={onAnnulla}>
      <aside className="admin__panel" onClick={(e) => e.stopPropagation()}>
        <div className="admin__panel-head">
          <h2>{categoria ? 'Modifica categoria' : 'Nuova categoria'}</h2>
          <button className="admin__panel-chiudi" onClick={onAnnulla} aria-label="Chiudi">
            ✕
          </button>
        </div>
        <form className="admin__form" onSubmit={handleSubmit}>
          <input type="text" placeholder="Nome" value={nome} onChange={(e) => setNome(e.target.value)} required />
          <textarea placeholder="Descrizione" value={descrizione} onChange={(e) => setDescrizione(e.target.value)} required />
          <button type="submit" disabled={invio}>
            {invio ? 'Salvataggio...' : 'Salva'}
          </button>
          {errore && <p className="admin__form-errore">{errore}</p>}
        </form>
      </aside>
    </div>
  );
}

export function CategorieManager() {
  const [categorie, setCategorie] = useState<Categoria[]>([]);
  const [caricamento, setCaricamento] = useState(true);
  const [errore, setErrore] = useState<string | null>(null);
  const [categoriaInModifica, setCategoriaInModifica] = useState<Categoria | null>(null);
  const [formAperto, setFormAperto] = useState(false);

  function carica() {
    setCaricamento(true);
    setErrore(null);
    adminGetCategorie()
      .then(setCategorie)
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
      await adminEliminaCategoria(id);
      carica();
    } catch (err) {
      if (err instanceof ApiError) setErrore(err.message);
    }
  }

  const { datiOrdinati, campo, direzione, ordinaPer } = useSort<Categoria>(categorie, (c, campo) => {
    switch (campo) {
      case 'nome':
        return c.nome;
      case 'descrizione':
        return c.descrizione;
      case 'prodotti':
        return c.numeroProdotti ?? 0;
      default:
        return null;
    }
  });

  return (
    <section className="admin-sezione">
      <div className="admin__head">
        <h2>Gestione categorie</h2>
        <button
          className="app__btn"
          onClick={() => {
            setCategoriaInModifica(null);
            setFormAperto(true);
          }}
        >
          + Nuova categoria
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
                <ThOrdinabile campo="descrizione" etichetta="Descrizione" campoAttivo={campo} direzione={direzione} onClick={ordinaPer} />
                <ThOrdinabile campo="prodotti" etichetta="Prodotti" campoAttivo={campo} direzione={direzione} onClick={ordinaPer} />
                <th></th>
              </tr>
            </thead>
            <tbody>
              {datiOrdinati.map((c) => (
                <tr key={c.id}>
                  <td>{c.nome}</td>
                  <td>{c.descrizione}</td>
                  <td>{c.numeroProdotti ?? 0}</td>
                  <td className="admin__azioni">
                    <button
                      onClick={() => {
                        setCategoriaInModifica(c);
                        setFormAperto(true);
                      }}
                    >
                      Modifica
                    </button>
                    <button className="admin__azione-pericolo" onClick={() => handleElimina(c.id)}>
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
        <CategoriaForm
          categoria={categoriaInModifica}
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
