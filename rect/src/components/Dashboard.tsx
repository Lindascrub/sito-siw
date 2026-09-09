import { useEffect, useState } from 'react';
import { adminGetCategorie, adminGetOrdini, adminGetProdotti } from '../api';
import type { Ordine, Prodotto } from '../types';

type Sezione = 'prodotti' | 'categorie' | 'ordini';

interface Props {
  onNavigate: (sezione: Sezione) => void;
}

export function Dashboard({ onNavigate }: Props) {
  const [prodotti, setProdotti] = useState<Prodotto[]>([]);
  const [numeroCategorie, setNumeroCategorie] = useState(0);
  const [ordiniInAttesa, setOrdiniInAttesa] = useState<Ordine[]>([]);

  useEffect(() => {
    adminGetProdotti().then(setProdotti).catch(() => setProdotti([]));
    adminGetCategorie().then((c) => setNumeroCategorie(c.length)).catch(() => setNumeroCategorie(0));
    adminGetOrdini('CREATO').then(setOrdiniInAttesa).catch(() => setOrdiniInAttesa([]));
  }, []);

  const prodottiAttivi = prodotti.filter((p) => p.attivo).length;

  return (
    <section className="admin-sezione">
      <h2>Dashboard</h2>
      <div className="dashboard__grid">
        <button className="dashboard__tile" onClick={() => onNavigate('prodotti')}>
          <span className="dashboard__tile-valore">{prodotti.length}</span>
          <span className="dashboard__tile-label">Prodotti totali</span>
        </button>
        <button className="dashboard__tile" onClick={() => onNavigate('prodotti')}>
          <span className="dashboard__tile-valore">{prodottiAttivi}</span>
          <span className="dashboard__tile-label">Prodotti attivi</span>
        </button>
        <button className="dashboard__tile" onClick={() => onNavigate('categorie')}>
          <span className="dashboard__tile-valore">{numeroCategorie}</span>
          <span className="dashboard__tile-label">Categorie</span>
        </button>
        <button className="dashboard__tile" onClick={() => onNavigate('ordini')}>
          <span className="dashboard__tile-valore">{ordiniInAttesa.length}</span>
          <span className="dashboard__tile-label">Ordini in attesa</span>
        </button>
      </div>

      {ordiniInAttesa.length > 0 && (
        <>
          <h2 style={{ marginTop: 32 }}>Ordini in attesa</h2>
          <div className="admin__tabella-wrap">
            <table className="admin__tabella">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Cliente</th>
                  <th>Data</th>
                  <th>Totale</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                {ordiniInAttesa.map((o) => (
                  <tr key={o.id}>
                    <td>{o.id}</td>
                    <td>{o.utenteEmail}</td>
                    <td>{new Date(o.dataOrdine).toLocaleDateString('it-IT')}</td>
                    <td>€ {o.totale.toFixed(2)}</td>
                    <td className="admin__azioni">
                      <button onClick={() => onNavigate('ordini')}>Vai agli ordini</button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </>
      )}
    </section>
  );
}
