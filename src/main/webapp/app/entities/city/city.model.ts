import { ICompany } from 'app/entities/company/company.model';
import { ICustomer } from 'app/entities/customer/customer.model';
import { IPerson } from 'app/entities/person/person.model';
import { IState } from 'app/entities/state/state.model';
import { ISupplier } from 'app/entities/supplier/supplier.model';
import { IWarehouse } from 'app/entities/warehouse/warehouse.model';

export interface ICity {
  id: number;
  name?: string | null;
  suppliers?: ISupplier | null;
  customers?: ICustomer | null;
  people?: IPerson | null;
  companies?: ICompany | null;
  warehouses?: IWarehouse | null;
  state?: IState | null;
}

export type NewCity = Omit<ICity, 'id'> & { id: null };
