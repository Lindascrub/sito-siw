import { useEffect, useState } from 'react';
import { ApiError, adminAggiornaStatoOrdine, adminAnnullaOrdine, adminGetOrdine, adminGetOrdini } from '../api';
import type { Ordine, StatoOrdine } from '../types';
import { useSort } from '../hooks/useSort';
import { ThOrdinabile } from './ThOrdinabile';

const STATI: StatoOrdine[] = ['CREATO', 'PAGATO', 'SPEDITO', 'CONSEGNATO', 'ANNULLATO'];

function OrdineDettaglio({ id, onChiudi, onAggiornato }: { id: number; onChiudi: () => void; onAggiornato: () => void }) {
  const [ordine, setOrdine] = useState<Ordine | null>(null);
  const [statoSelezionato, setStatoSelezionato] = useState<StatoOrdine>('CREATO');
  const [errore, setErrore] = useState<string | null>(null);
  const [invio, setInvio] = useState(false);

  function carica() {
    adminGetOrdine(id).then((o) => {
      setOrdine(o);
      setStatoSelezionato(o.stato);
    });
  }

  useEffect(() => {
    carica();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [id]);

  async function handleAggiornaStato() {
    setInvio(true);
    setErrore(null);
    try {
      await adminAggiornaStatoOrdine(id, statoSelezionato);
      carica();
      onAggiornato();
    } catch (err) {
      setErrore(err instanceof ApiError ? err.message : 'Errore imprevisto');
    } finally {
      setInvio(false);
    }
  }

  async function handleAnnulla() {
    setInvio(true);
    setErrore(null);
    try {
      await adminAnnullaOrdine(id);
      carica();
      onAggiornato();
    } catch (err) {
      setErrore(err instanceof ApiError ? err.message : 'Errore imprevisto');
    } finally {
      setInvio(false);
    }
  }

  if (!ordine) return null;

  const puoAnnullare = ordine.stato !== 'ANNULLATO' && ordine.stato !== 'CONSEGNATO';

  return (
    <div className="admin__overlay" onClick={onChiudi}>
      <aside className="admin__panel" onClick={(e) => e.stopPropagation()}>
        <div className="admin__panel-head">
          <h2>Ordine #{ordine.id}</h2>
          <button className="admin__panel-chiudi" onClick={onChiudi} aria-label="Chiudi">
            ✕
          </button>
        </div>

        <p>
          <strong>{ordine.utenteEmail}</strong> — {new Date(ordine.dataOrdine).toLocaleString('it-IT')}
        </p>

        <ul className="admin__lista-righe">
          {ordine.righe.map((r, i) => (
            <li key={i}>
              {r.prodottoNome} × {r.quantita} {r.taglia && `(${r.taglia})`} {r.colore && `— ${r.colore}`} — € {r.subtotale.toFixed(2)}
            </li>
          ))}
        </ul>

        <p>
          <strong>Totale:</strong> € {ordine.totale.toFixed(2)}
        </p>
        <p>
          {ordine.indirizzoSpedizione} — {ordine.cittaSpedizione} ({ordine.codPostaleSpedizione})
        </p>
        <p>
          {ordine.dataPagamento
            ? `Pagato il ${new Date(ordine.dataPagamento).toLocaleString('it-IT')} (${ordine.metodoPagamento})`
            : 'Non ancora pagato'}
        </p>

        <div className="admin__form" style={{ marginTop: 16 }}>
          <label>
            Stato ordine
            <select value={statoSelezionato} onChange={(e) => setStatoSelezionato(e.target.value as StatoOrdine)}>
              {STATI.map((s) => (
                <option key={s} value={s}>
                  {s}
                </option>
              ))}
            </select>
          </label>
          <button onClick={handleAggiornaStato} disabled={invio}>
            Aggiorna stato
          </button>
          {puoAnnullare && (
            <button className="admin__azione-pericolo" onClick={handleAnnulla} disabled={invio}>
              Annulla ordine
            </button>
          )}
          {errore && <p className="admin__form-errore">{errore}</p>}
        </div>
      </aside>
    </div>
  );
}

export function OrdiniManager() {
  const [ordini, setOrdini] = useState<Ordine[]>([]);
  const [filtroStato, setFiltroStato] = useState<StatoOrdine | ''>('');
  const [caricamento, setCaricamento] = useState(true);
  const [errore, setErrore] = useState<string | null>(null);
  const [ordineSelezionato, setOrdineSelezionato] = useState<number | null>(null);

  function carica() {
    setCaricamento(true);
    setErrore(null);
    adminGetOrdini(filtroStato || undefined)
      .then(setOrdini)
      .catch((err) => {
        if (err instanceof ApiError) setErrore(err.message);
      })
      .finally(() => setCaricamento(false));
  }

  useEffect(() => {
    carica();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [filtroStato]);

  const { datiOrdinati, campo, direzione, ordinaPer } = useSort<Ordine>(ordini, (o, campo) => {
    switch (campo) {
      case 'id':
        return o.id;
      case 'cliente':
        return o.utenteEmail;
      case 'data':
        return o.dataOrdine;
      case 'totale':
        return o.totale;
      case 'stato':
        return o.stato;
      default:
        return null;
    }
  });

  return (
    <section className="admin-sezione">
      <div className="admin__head">
        <h2>Gestione ordini</h2>
        <select value={filtroStato} onChange={(e) => setFiltroStato(e.target.value as StatoOrdine | '')}>
          <option value="">Tutti gli stati</option>
          {STATI.map((s) => (
            <option key={s} value={s}>
              {s}
            </option>
          ))}
        </select>
      </div>

      {errore && <p className="admin__errore">{errore}</p>}
      {caricamento && <p className="admin__stato">Caricamento...</p>}

      {!caricamento && ordini.length === 0 && <p className="admin__stato">Nessun ordine trovato.</p>}

      {!caricamento && ordini.length > 0 && (
        <div className="admin__tabella-wrap">
          <table className="admin__tabella">
            <thead>
              <tr>
                <ThOrdinabile campo="id" etichetta="ID" campoAttivo={campo} direzione={direzione} onClick={ordinaPer} />
                <ThOrdinabile campo="cliente" etichetta="Cliente" campoAttivo={campo} direzione={direzione} onClick={ordinaPer} />
                <ThOrdinabile campo="data" etichetta="Data" campoAttivo={campo} direzione={direzione} onClick={ordinaPer} />
                <ThOrdinabile campo="totale" etichetta="Totale" campoAttivo={campo} direzione={direzione} onClick={ordinaPer} />
                <ThOrdinabile campo="stato" etichetta="Stato" campoAttivo={campo} direzione={direzione} onClick={ordinaPer} />
                <th></th>
              </tr>
            </thead>
            <tbody>
              {datiOrdinati.map((o) => (
                <tr key={o.id}>
                  <td>{o.id}</td>
                  <td>{o.utenteEmail}</td>
                  <td>{new Date(o.dataOrdine).toLocaleDateString('it-IT')}</td>
                  <td>€ {o.totale.toFixed(2)}</td>
                  <td>{o.stato}</td>
                  <td className="admin__azioni">
                    <button onClick={() => setOrdineSelezionato(o.id)}>Dettaglio</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {ordineSelezionato !== null && (
        <OrdineDettaglio id={ordineSelezionato} onChiudi={() => setOrdineSelezionato(null)} onAggiornato={carica} />
      )}
    </section>
  );
}
