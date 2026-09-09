import { useEffect, useState } from 'react';
import './App.css';
import { API_BASE, adminWhoAmI, type AdminInfo } from './api';
import { CategorieManager } from './components/CategorieManager';
import { Dashboard } from './components/Dashboard';
import { OrdiniManager } from './components/OrdiniManager';
import { ProdottiManager } from './components/ProdottiManager';

type Sezione = 'dashboard' | 'prodotti' | 'categorie' | 'ordini';
type StatoAccesso = 'verifica' | 'consentito' | 'negato';

const TAB: { chiave: Sezione; etichetta: string }[] = [
  { chiave: 'dashboard', etichetta: 'Dashboard' },
  { chiave: 'prodotti', etichetta: 'Prodotti' },
  { chiave: 'categorie', etichetta: 'Categorie' },
  { chiave: 'ordini', etichetta: 'Ordini' },
];

function App() {
  const [sezione, setSezione] = useState<Sezione>('dashboard');
  const [statoAccesso, setStatoAccesso] = useState<StatoAccesso>('verifica');
  const [admin, setAdmin] = useState<AdminInfo | null>(null);

  useEffect(() => {
    adminWhoAmI()
      .then((info) => {
        setAdmin(info);
        setStatoAccesso('consentito');
      })
      .catch(() => setStatoAccesso('negato'));
  }, []);

  if (statoAccesso === 'verifica') {
    return (
      <div className="app">
        <p className="admin__stato">Verifica accesso...</p>
      </div>
    );
  }

  if (statoAccesso === 'negato') {
    return (
      <div className="app admin__accesso-negato">
        <div className="logo">
          Lunna <em>Lingerie</em> <span className="app__badge-admin">Admin</span>
        </div>
        <h1>Area riservata</h1>
        <p>Questo pannello è riservato agli amministratori. Accedi con un account admin sul sito principale.</p>
        <a className="app__btn app__btn--grande" href={`${API_BASE}/login`}>
          Vai al login
        </a>
      </div>
    );
  }

  return (
    <div className="app">
      <header className="app__header">
        <div className="app__header-top">
          <div className="logo">
            Lunna <em>Lingerie</em> <span className="app__badge-admin">Admin</span>
          </div>
          <div className="app__header-azioni">
            {admin && <span className="admin__utente">{admin.nome} {admin.cognome}</span>}
            <a className="app__btn" href={`${API_BASE}/profilo`}>
              Il mio profilo
            </a>
            <a className="app__btn" href={API_BASE}>
              ← Torna al sito
            </a>
          </div>
        </div>
        <p className="app__sottotitolo">Pannello di amministrazione — collegato via REST a Spring Boot</p>

        <nav className="app__tabs">
          {TAB.map((t) => (
            <button
              key={t.chiave}
              className={`app__tab${sezione === t.chiave ? ' app__tab--attivo' : ''}`}
              onClick={() => setSezione(t.chiave)}
            >
              {t.etichetta}
            </button>
          ))}
        </nav>
      </header>

      <main className="app__main">
        {sezione === 'dashboard' && <Dashboard onNavigate={setSezione} />}
        {sezione === 'prodotti' && <ProdottiManager />}
        {sezione === 'categorie' && <CategorieManager />}
        {sezione === 'ordini' && <OrdiniManager />}
      </main>
    </div>
  );
}

export default App;
