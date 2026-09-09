import { useState, type ChangeEvent, type FormEvent } from 'react';
import { ApiError, adminCaricaImmagine, adminCreaProdotto, adminModificaProdotto, risolviUrlImmagine } from '../api';
import type { Categoria, Prodotto, ProdottoInput } from '../types';

interface Props {
  categorie: Categoria[];
  prodotto: Prodotto | null;
  onSalvato: () => void;
  onAnnulla: () => void;
}

export function ProductForm({ categorie, prodotto, onSalvato, onAnnulla }: Props) {
  const [nome, setNome] = useState(prodotto?.nome ?? '');
  const [descrizione, setDescrizione] = useState(prodotto?.descrizione ?? '');
  const [prezzo, setPrezzo] = useState(String(prodotto?.prezzo ?? ''));
  const [quantita, setQuantita] = useState(String(prodotto?.quantitaDisponibile ?? '0'));
  const [urlImage, setUrlImage] = useState(prodotto?.urlImage ?? '');
  const [caricamentoImmagine, setCaricamentoImmagine] = useState(false);
  const [categoriaId, setCategoriaId] = useState(String(prodotto?.categoriaId ?? categorie[0]?.id ?? ''));
  const [taglie, setTaglie] = useState((prodotto?.taglieDisponibili ?? []).join(', '));
  const [colori, setColori] = useState((prodotto?.coloriDisponibili ?? []).join(', '));
  const [invio, setInvio] = useState(false);
  const [errore, setErrore] = useState<string | null>(null);

  function daListaCsv(valore: string): string[] {
    return valore
      .split(',')
      .map((v) => v.trim())
      .filter((v) => v.length > 0);
  }

  async function handleFile(e: ChangeEvent<HTMLInputElement>) {
    const file = e.target.files?.[0];
    if (!file) return;
    setCaricamentoImmagine(true);
    setErrore(null);
    try {
      setUrlImage(await adminCaricaImmagine(file));
    } catch (err) {
      setErrore(err instanceof ApiError ? err.message : 'Errore nel caricamento immagine');
    } finally {
      setCaricamentoImmagine(false);
      e.target.value = '';
    }
  }

  async function handleSubmit(e: FormEvent) {
    e.preventDefault();
    setInvio(true);
    setErrore(null);

    const dati: ProdottoInput = {
      nome,
      descrizione,
      prezzo: Number(prezzo),
      quantitaDisponibile: Number(quantita),
      urlImage: urlImage || undefined,
      attivo: prodotto?.attivo ?? true,
      categoriaId: Number(categoriaId),
      taglie: daListaCsv(taglie),
      colori: daListaCsv(colori),
    };

    try {
      if (prodotto) {
        await adminModificaProdotto(prodotto.id, dati);
      } else {
        await adminCreaProdotto(dati);
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
          <h2>{prodotto ? 'Modifica prodotto' : 'Nuovo prodotto'}</h2>
          <button className="admin__panel-chiudi" onClick={onAnnulla} aria-label="Chiudi">
            ✕
          </button>
        </div>

        <form className="admin__form" onSubmit={handleSubmit}>
          <input type="text" placeholder="Nome" value={nome} onChange={(e) => setNome(e.target.value)} required />
          <textarea placeholder="Descrizione" value={descrizione} onChange={(e) => setDescrizione(e.target.value)} required />
          <input type="number" step="0.01" min="0" placeholder="Prezzo" value={prezzo} onChange={(e) => setPrezzo(e.target.value)} required />
          <input type="number" min="0" placeholder="Quantità disponibile" value={quantita} onChange={(e) => setQuantita(e.target.value)} required />

          <label>
            Immagine prodotto
            <input type="file" accept="image/png,image/jpeg,image/webp,image/gif" onChange={handleFile} />
          </label>
          {caricamentoImmagine && <p className="admin__stato">Caricamento immagine...</p>}
          {urlImage && !caricamentoImmagine && (
            <img src={risolviUrlImmagine(urlImage)} alt="Anteprima prodotto" className="admin__anteprima-immagine" />
          )}

          <label>
            Categoria
            <select value={categoriaId} onChange={(e) => setCategoriaId(e.target.value)} required>
              {categorie.map((c) => (
                <option key={c.id} value={c.id}>
                  {c.nome}
                </option>
              ))}
            </select>
          </label>
          <input type="text" placeholder="Taglie (separate da virgola)" value={taglie} onChange={(e) => setTaglie(e.target.value)} />
          <input type="text" placeholder="Colori (separati da virgola)" value={colori} onChange={(e) => setColori(e.target.value)} />

          <button type="submit" disabled={invio || caricamentoImmagine}>
            {invio ? 'Salvataggio...' : 'Salva'}
          </button>
          {errore && <p className="admin__form-errore">{errore}</p>}
        </form>
      </aside>
    </div>
  );
}
