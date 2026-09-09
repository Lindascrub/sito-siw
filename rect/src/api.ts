import type { Categoria, ErroreApi, Ordine, Prodotto, ProdottoInput, StatoOrdine } from './types';

const API_BASE = import.meta.env.VITE_API_BASE_URL ?? 'http://localhost:8080';

export class ApiError extends Error {
  status: number;

  constructor(message: string, status: number) {
    super(message);
    this.status = status;
  }
}

/** Legge il cookie XSRF-TOKEN che Spring Security espone per le SPA cross-origin. */
function leggiCsrfToken(): string | null {
  const match = document.cookie.match(/(?:^|; )XSRF-TOKEN=([^;]*)/);
  return match ? decodeURIComponent(match[1]) : null;
}

async function request<T>(method: 'GET' | 'POST' | 'PUT' | 'PATCH' | 'DELETE', path: string, body?: unknown): Promise<T> {
  const csrf = leggiCsrfToken();
  const risposta = await fetch(`${API_BASE}${path}`, {
    method,
    credentials: 'include',
    headers: {
      ...(body !== undefined ? { 'Content-Type': 'application/json' } : {}),
      ...(csrf && method !== 'GET' ? { 'X-XSRF-TOKEN': csrf } : {}),
    },
    body: body !== undefined ? JSON.stringify(body) : undefined,
  });

  if (risposta.status === 204) {
    return undefined as T;
  }

  const dati = await risposta.json().catch(() => null);
  if (!risposta.ok) {
    const messaggio = (dati as ErroreApi | null)?.errore ?? `Richiesta fallita (${risposta.status})`;
    throw new ApiError(messaggio, risposta.status);
  }
  return dati as T;
}

export function getCategorie(): Promise<Categoria[]> {
  return request<Categoria[]>('GET', '/api/categorie');
}

export interface AdminInfo {
  nome: string;
  cognome: string;
  email: string;
}

export function adminWhoAmI(): Promise<AdminInfo> {
  return request<AdminInfo>('GET', '/api/admin/whoami');
}

// --- Prodotti (admin) ---

export function adminGetProdotti(): Promise<Prodotto[]> {
  return request<Prodotto[]>('GET', '/api/admin/prodotti');
}

export function adminCreaProdotto(dati: ProdottoInput): Promise<Prodotto> {
  return request<Prodotto>('POST', '/api/admin/prodotti', dati);
}

export function adminModificaProdotto(id: number, dati: ProdottoInput): Promise<Prodotto> {
  return request<Prodotto>('PUT', `/api/admin/prodotti/${id}`, dati);
}

export function adminEliminaProdotto(id: number): Promise<void> {
  return request<void>('DELETE', `/api/admin/prodotti/${id}`);
}

export function adminAggiornaAttivo(id: number, attivo: boolean): Promise<Prodotto> {
  return request<Prodotto>('PATCH', `/api/admin/prodotti/${id}/attivo`, { attivo });
}

/** Carica un'immagine prodotto e ritorna il percorso (relativo) da salvare in urlImage. */
export async function adminCaricaImmagine(file: File): Promise<string> {
  const csrf = leggiCsrfToken();
  const formData = new FormData();
  formData.append('file', file);

  const risposta = await fetch(`${API_BASE}/api/admin/upload`, {
    method: 'POST',
    credentials: 'include',
    headers: csrf ? { 'X-XSRF-TOKEN': csrf } : {},
    body: formData,
  });

  const dati = await risposta.json().catch(() => null);
  if (!risposta.ok) {
    const messaggio = (dati as ErroreApi | null)?.errore ?? `Richiesta fallita (${risposta.status})`;
    throw new ApiError(messaggio, risposta.status);
  }
  return (dati as { url: string }).url;
}

/** Risolve un urlImage (assoluto o relativo tipo /uploads/xxx.png) in un URL mostrabile. */
export function risolviUrlImmagine(urlImage: string | undefined | null): string {
  if (!urlImage) return '';
  return urlImage.startsWith('http') ? urlImage : `${API_BASE}${urlImage}`;
}

// --- Categorie (admin) ---

export function adminGetCategorie(): Promise<Categoria[]> {
  return request<Categoria[]>('GET', '/api/admin/categorie');
}

export function adminCreaCategoria(dati: { nome: string; descrizione: string }): Promise<Categoria> {
  return request<Categoria>('POST', '/api/admin/categorie', dati);
}

export function adminModificaCategoria(id: number, dati: { nome: string; descrizione: string }): Promise<Categoria> {
  return request<Categoria>('PUT', `/api/admin/categorie/${id}`, dati);
}

export function adminEliminaCategoria(id: number): Promise<void> {
  return request<void>('DELETE', `/api/admin/categorie/${id}`);
}

// --- Ordini (admin) ---

export function adminGetOrdini(stato?: StatoOrdine): Promise<Ordine[]> {
  const query = stato ? `?stato=${stato}` : '';
  return request<Ordine[]>('GET', `/api/admin/ordini${query}`);
}

export function adminGetOrdine(id: number): Promise<Ordine> {
  return request<Ordine>('GET', `/api/admin/ordini/${id}`);
}

export function adminAggiornaStatoOrdine(id: number, stato: StatoOrdine): Promise<Ordine> {
  return request<Ordine>('PATCH', `/api/admin/ordini/${id}/stato`, { stato });
}

export function adminAnnullaOrdine(id: number): Promise<Ordine> {
  return request<Ordine>('POST', `/api/admin/ordini/${id}/annulla`);
}

export { API_BASE };
