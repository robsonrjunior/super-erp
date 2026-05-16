import dayjs from 'dayjs/esm';

import { ICompany } from 'app/entities/company/company.model';
import { ICustomer } from 'app/entities/customer/customer.model';
import { IPerson } from 'app/entities/person/person.model';
import { IProduct } from 'app/entities/product/product.model';
import { IRawMaterial } from 'app/entities/raw-material/raw-material.model';
import { ISale } from 'app/entities/sale/sale.model';
import { ISaleItem } from 'app/entities/sale-item/sale-item.model';
import { IStockMovement } from 'app/entities/stock-movement/stock-movement.model';
import { ISupplier } from 'app/entities/supplier/supplier.model';
import { IWarehouse } from 'app/entities/warehouse/warehouse.model';

export interface ITenant {
  id: number;
  name?: string | null;
  code?: string | null;
  active?: boolean | null;
  deletedAt?: dayjs.Dayjs | null;
  customers?: ICustomer | null;
  suppliers?: ISupplier | null;
  people?: IPerson | null;
  companies?: ICompany | null;
  products?: IProduct | null;
  rawMaterials?: IRawMaterial | null;
  warehouses?: IWarehouse | null;
  sales?: ISale | null;
  saleItems?: ISaleItem | null;
  stockMovements?: IStockMovement | null;
}

export type NewTenant = Omit<ITenant, 'id'> & { id: null };
