import dayjs from 'dayjs/esm';

import { IStockMovement, NewStockMovement } from './stock-movement.model';

export const sampleWithRequiredData: IStockMovement = {
  id: 16284,
  movementDate: dayjs('2026-05-15T18:45'),
  movementType: 'TRANSFER',
  quantity: 18206.41,
};

export const sampleWithPartialData: IStockMovement = {
  id: 16345,
  movementDate: dayjs('2026-05-15T15:00'),
  movementType: 'TRANSFER',
  quantity: 2021.85,
  notes: 'gosh representar largo',
};

export const sampleWithFullData: IStockMovement = {
  id: 31835,
  movementDate: dayjs('2026-05-15T04:27'),
  movementType: 'ADJUSTMENT',
  quantity: 28519.27,
  unitCost: 14163.43,
  referenceNumber: 'onto mmm clearly',
  notes: 'shrilly',
  deletedAt: dayjs('2026-05-15T12:31'),
};

export const sampleWithNewData: NewStockMovement = {
  movementDate: dayjs('2026-05-15T00:31'),
  movementType: 'ADJUSTMENT',
  quantity: 8701.2,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
