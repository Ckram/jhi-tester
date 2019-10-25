import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPotato } from 'app/shared/model/potato.model';

@Component({
  selector: 'jhi-potato-detail',
  templateUrl: './potato-detail.component.html'
})
export class PotatoDetailComponent implements OnInit {
  potato: IPotato;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ potato }) => {
      this.potato = potato;
    });
  }

  previousState() {
    window.history.back();
  }
}
