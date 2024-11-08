import { Election } from '../../dto/election';

export const electionData: Election[] = [
  {
    id: 50,
    name: 'Election legislative 2024',
    startDate: '2024-05-29',
    endDate: '2024-06-12',
    pollingStationCount: 0,
    totalPollingStationCount: 0,
    type: {
      id: -1,
      type: 'Legislative',
    },
  },
  {
    id: 51,
    name: 'Election presidentielle 2023',
    startDate: '2023-11-16',
    endDate: '2024-12-15',
    pollingStationCount: 0,
    totalPollingStationCount: 0,
    type: {
      id: -1,
      type: 'Presidentielle',
    },
  },
];
