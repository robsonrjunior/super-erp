import dayjs from 'dayjs/esm';

import { ICompany } from 'app/entities/company/company.model';
import { PartyType } from 'app/entities/enumerations/party-type.model';
import { IPerson } from 'app/entities/person/person.model';
import { IRawMaterial } from 'app/entities/raw-material/raw-material.model';

export interface ISupplier {
  id: number;
  legalName?: string | null;
  tradeName?: string | null;
  taxId?: string | null;
  partyType?: keyof typeof PartyType | null;
  email?: string | null;
  phone?: string | null;
  active?: boolean | null;
  deletedAt?: dayjs.Dayjs | null;
  person?: IPerson | null;
  company?: ICompany | null;
  rawMaterials?: IRawMaterial | null;
}

export type NewSupplier = Omit<ISupplier, 'id'> & { id: null };
