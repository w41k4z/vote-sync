import { Region } from '../../region';

export class RegionStatPayload {
  regions: Region[];

  constructor(regions: Region[]) {
    this.regions = regions;
  }
}
