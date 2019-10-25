import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPotato } from 'app/shared/model/potato.model';
import { PotatoService } from './potato.service';

@Component({
  selector: 'jhi-potato-delete-dialog',
  templateUrl: './potato-delete-dialog.component.html'
})
export class PotatoDeleteDialogComponent {
  potato: IPotato;

  constructor(protected potatoService: PotatoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.potatoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'potatoListModification',
        content: 'Deleted an potato'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-potato-delete-popup',
  template: ''
})
export class PotatoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ potato }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PotatoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.potato = potato;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/potato', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/potato', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
