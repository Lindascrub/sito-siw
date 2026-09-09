import type { Direzione } from '../hooks/useSort';

interface Props {
  campo: string;
  etichetta: string;
  campoAttivo: string | null;
  direzione: Direzione;
  onClick: (campo: string) => void;
}

export function ThOrdinabile({ campo, etichetta, campoAttivo, direzione, onClick }: Props) {
  const attivo = campo === campoAttivo;
  return (
    <th className="admin__th-ordinabile" onClick={() => onClick(campo)}>
      {etichetta}
      <span className="admin__freccia">{attivo ? (direzione === 'asc' ? ' ▲' : ' ▼') : ''}</span>
    </th>
  );
}
