import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager } from 'ng-jhipster';

import { IPotato } from 'app/shared/model/potato.model';
import { AccountService } from 'app/core/auth/account.service';
import { PotatoService } from './potato.service';

@Component({
  selector: 'jhi-potato',
  templateUrl: './potato.component.html'
})
export class PotatoComponent implements OnInit, OnDestroy {
  potatoes: IPotato[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(protected potatoService: PotatoService, protected eventManager: JhiEventManager, protected accountService: AccountService) {}

  loadAll() {
    this.potatoService
      .query()
      .pipe(
        filter((res: HttpResponse<IPotato[]>) => res.ok),
        map((res: HttpResponse<IPotato[]>) => res.body)
      )
      .subscribe((res: IPotato[]) => {
        this.potatoes = res;
      });
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().subscribe(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPotatoes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPotato) {
    return item.id;
  }

  registerChangeInPotatoes() {
    this.eventSubscriber = this.eventManager.subscribe('potatoListModification', response => this.loadAll());
  }
}
