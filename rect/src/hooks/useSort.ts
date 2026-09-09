import { useMemo, useState } from 'react';

export type Direzione = 'asc' | 'desc';

/**
 * Ordinamento generico per tabelle: clic su una colonna ordina asc, un secondo clic
 * sulla stessa colonna inverte la direzione.
 */
export function useSort<T>(dati: T[], estraiValore: (item: T, campo: string) => string | number | null | undefined) {
  const [campo, setCampo] = useState<string | null>(null);
  const [direzione, setDirezione] = useState<Direzione>('asc');

  function ordinaPer(nuovoCampo: string) {
    if (nuovoCampo === campo) {
      setDirezione((d) => (d === 'asc' ? 'desc' : 'asc'));
    } else {
      setCampo(nuovoCampo);
      setDirezione('asc');
    }
  }

  const datiOrdinati = useMemo(() => {
    if (!campo) return dati;
    const copia = [...dati];
    copia.sort((a, b) => {
      const va = estraiValore(a, campo) ?? '';
      const vb = estraiValore(b, campo) ?? '';
      let confronto: number;
      if (typeof va === 'number' && typeof vb === 'number') {
        confronto = va - vb;
      } else {
        confronto = String(va).localeCompare(String(vb), 'it', { sensitivity: 'base' });
      }
      return direzione === 'asc' ? confronto : -confronto;
    });
    return copia;
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [dati, campo, direzione]);

  return { datiOrdinati, campo, direzione, ordinaPer };
}
