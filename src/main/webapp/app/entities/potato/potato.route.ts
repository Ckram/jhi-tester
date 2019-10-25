import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Potato } from 'app/shared/model/potato.model';
import { PotatoService } from './potato.service';
import { PotatoComponent } from './potato.component';
import { PotatoDetailComponent } from './potato-detail.component';
import { PotatoUpdateComponent } from './potato-update.component';
import { PotatoDeletePopupComponent } from './potato-delete-dialog.component';
import { IPotato } from 'app/shared/model/potato.model';

@Injectable({ providedIn: 'root' })
export class PotatoResolve implements Resolve<IPotato> {
  constructor(private service: PotatoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPotato> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Potato>) => response.ok),
        map((potato: HttpResponse<Potato>) => potato.body)
      );
    }
    return of(new Potato());
  }
}

export const potatoRoute: Routes = [
  {
    path: '',
    component: PotatoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testerApp.potato.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PotatoDetailComponent,
    resolve: {
      potato: PotatoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testerApp.potato.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PotatoUpdateComponent,
    resolve: {
      potato: PotatoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testerApp.potato.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PotatoUpdateComponent,
    resolve: {
      potato: PotatoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testerApp.potato.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const potatoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PotatoDeletePopupComponent,
    resolve: {
      potato: PotatoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'testerApp.potato.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
