export const administrativeDivisions: {
  value: {
    name: 'region' | 'district' | 'commune' | 'fokontany';
    zoom: number;
  };
  label: string;
}[] = [
  {
    value: { name: 'region', zoom: 6 },
    label: 'Région',
  },
  {
    value: { name: 'district', zoom: 7 },
    label: 'District',
  },
  {
    value: { name: 'commune', zoom: 8 },
    label: 'Commune',
  },
  {
    value: { name: 'fokontany', zoom: 9 },
    label: 'Fokontany',
  },
];
