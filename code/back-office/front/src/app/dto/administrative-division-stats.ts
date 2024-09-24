export class AdministrativeDivisionStats {
  id: number;
  provinceId: number;
  name: string;
  geojson: any;

  constructor(id: number, provinceId: number, name: string, geojson: any) {
    this.id = id;
    this.provinceId = provinceId;
    this.name = name;
    this.geojson = geojson;
  }
}
