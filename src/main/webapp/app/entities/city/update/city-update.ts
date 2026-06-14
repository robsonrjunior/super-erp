import { HttpResponse } from '@angular/common/http';
import { Component, OnInit, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { TranslateModule } from '@ngx-translate/core';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ICompany } from 'app/entities/company/company.model';
import { CompanyService } from 'app/entities/company/service/company.service';
import { ICustomer } from 'app/entities/customer/customer.model';
import { CustomerService } from 'app/entities/customer/service/customer.service';
import { SupplierService } from 'app/entities/supplier/service/supplier.service';
import { ISupplier } from 'app/entities/supplier/supplier.model';
import { AlertError } from 'app/shared/alert/alert-error';
import { TranslateDirective } from 'app/shared/language';

import { ICity, NewCity } from '../city.model';
import { CityService } from '../service/city.service';
import { IPerson } from 'app/entities/person/person.model';
import { PersonService } from 'app/entities/person/service/person.service';
import { IWarehouse } from 'app/entities/warehouse/warehouse.model';
import { WarehouseService } from 'app/entities/warehouse/service/warehouse.service';
import { IState } from 'app/entities/state/state.model';
import { StateService } from 'app/entities/state/service/state.service';

@Component({
  selector: 'jhi-city-update',
  templateUrl: './city-update.html',
  imports: [TranslateDirective, TranslateModule, FontAwesomeModule, AlertError, FormsModule],
})
export class CityUpdate implements OnInit {
  readonly isSaving = signal(false);
  city: ICity | NewCity = { id: null };

  suppliersSharedCollection = signal<ISupplier[]>([]);
  customersSharedCollection = signal<ICustomer[]>([]);
  peopleSharedCollection = signal<IPerson[]>([]);
  companiesSharedCollection = signal<ICompany[]>([]);
  warehousesSharedCollection = signal<IWarehouse[]>([]);
  statesSharedCollection = signal<IState[]>([]);

  protected cityService = inject(CityService);
  protected supplierService = inject(SupplierService);
  protected customerService = inject(CustomerService);
  protected personService = inject(PersonService);
  protected companyService = inject(CompanyService);
  protected warehouseService = inject(WarehouseService);
  protected stateService = inject(StateService);
  protected activatedRoute = inject(ActivatedRoute);

  compareSupplier = (o1: ISupplier | null, o2: ISupplier | null): boolean => this.supplierService.compareSupplier(o1, o2);

  compareCustomer = (o1: ICustomer | null, o2: ICustomer | null): boolean => this.customerService.compareCustomer(o1, o2);

  comparePerson = (o1: IPerson | null, o2: IPerson | null): boolean => this.personService.comparePerson(o1, o2);

  compareCompany = (o1: ICompany | null, o2: ICompany | null): boolean => this.companyService.compareCompany(o1, o2);

  compareWarehouse = (o1: IWarehouse | null, o2: IWarehouse | null): boolean => this.warehouseService.compareWarehouse(o1, o2);

  compareState = (o1: IState | null, o2: IState | null): boolean => this.stateService.compareState(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ city }) => {
      if (city) {
        this.city = city;
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    globalThis.history.back();
  }

  save(): void {
    this.isSaving.set(true);
    if (this.city.id === null) {
      this.subscribeToSaveResponse(this.cityService.create(this.city));
    } else {
      this.subscribeToSaveResponse(this.cityService.update(this.city));
    }
  }

  protected subscribeToSaveResponse(result: Observable<ICity | null>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving.set(false);
  }

  protected loadRelationshipsOptions(): void {
    this.supplierService
      .query()
      .pipe(map((res: HttpResponse<ISupplier[]>) => res.body ?? []))
      .pipe(
        map((suppliers: ISupplier[]) => this.supplierService.addSupplierToCollectionIfMissing<ISupplier>(suppliers, this.city.suppliers)),
      )
      .subscribe((suppliers: ISupplier[]) => this.suppliersSharedCollection.set(suppliers));

    this.customerService
      .query()
      .pipe(map((res: HttpResponse<ICustomer[]>) => res.body ?? []))
      .pipe(
        map((customers: ICustomer[]) => this.customerService.addCustomerToCollectionIfMissing<ICustomer>(customers, this.city.customers)),
      )
      .subscribe((customers: ICustomer[]) => this.customersSharedCollection.set(customers));

    this.personService
      .query()
      .pipe(map((res: HttpResponse<IPerson[]>) => res.body ?? []))
      .pipe(map((people: IPerson[]) => this.personService.addPersonToCollectionIfMissing<IPerson>(people, this.city.people)))
      .subscribe((people: IPerson[]) => this.peopleSharedCollection.set(people));

    this.companyService
      .query()
      .pipe(map((res: HttpResponse<ICompany[]>) => res.body ?? []))
      .pipe(map((companies: ICompany[]) => this.companyService.addCompanyToCollectionIfMissing<ICompany>(companies, this.city.companies)))
      .subscribe((companies: ICompany[]) => this.companiesSharedCollection.set(companies));

    this.warehouseService
      .query()
      .pipe(map((res: HttpResponse<IWarehouse[]>) => res.body ?? []))
      .pipe(
        map((warehouses: IWarehouse[]) =>
          this.warehouseService.addWarehouseToCollectionIfMissing<IWarehouse>(warehouses, this.city.warehouses),
        ),
      )
      .subscribe((warehouses: IWarehouse[]) => this.warehousesSharedCollection.set(warehouses));

    this.stateService
      .query()
      .pipe(map((res: HttpResponse<IState[]>) => res.body ?? []))
      .pipe(map((states: IState[]) => this.stateService.addStateToCollectionIfMissing<IState>(states, this.city.state)))
      .subscribe((states: IState[]) => this.statesSharedCollection.set(states));
  }
}
