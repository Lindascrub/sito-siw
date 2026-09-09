export interface Categoria {
  id: number;
  nome: string;
  descrizione?: string;
  numeroProdotti?: number;
}

export interface Prodotto {
  id: number;
  nome: string;
  descrizione?: string;
  prezzo: number;
  quantitaDisponibile?: number;
  urlImage?: string | null;
  categoriaNome?: string | null;
  categoriaId?: number | null;
  taglieDisponibili?: string[];
  coloriDisponibili?: string[];
  attivo?: boolean;
}

export interface ProdottoInput {
  nome: string;
  descrizione: string;
  prezzo: number;
  quantitaDisponibile: number;
  urlImage?: string;
  codiceModello?: string;
  attivo: boolean;
  categoriaId: number;
  taglie: string[];
  colori: string[];
}

export interface RigaOrdine {
  prodottoNome: string;
  quantita: number;
  taglia?: string;
  colore?: string;
  subtotale: number;
}

export type StatoOrdine = 'CREATO' | 'PAGATO' | 'SPEDITO' | 'CONSEGNATO' | 'ANNULLATO';

export interface Ordine {
  id: number;
  stato: StatoOrdine;
  totale: number;
  dataOrdine: string;
  dataPagamento?: string;
  metodoPagamento?: string;
  indirizzoSpedizione: string;
  cittaSpedizione: string;
  codPostaleSpedizione: string;
  utenteEmail?: string;
  righe: RigaOrdine[];
}

export interface ErroreApi {
  errore: string;
}
