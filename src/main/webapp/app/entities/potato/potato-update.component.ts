import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IPotato, Potato } from 'app/shared/model/potato.model';
import { PotatoService } from './potato.service';

@Component({
  selector: 'jhi-potato-update',
  templateUrl: './potato-update.component.html'
})
export class PotatoUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    shape: [],
    size: []
  });

  constructor(protected potatoService: PotatoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ potato }) => {
      this.updateForm(potato);
    });
  }

  updateForm(potato: IPotato) {
    this.editForm.patchValue({
      id: potato.id,
      shape: potato.shape,
      size: potato.size
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const potato = this.createFromForm();
    if (potato.id !== undefined) {
      this.subscribeToSaveResponse(this.potatoService.update(potato));
    } else {
      this.subscribeToSaveResponse(this.potatoService.create(potato));
    }
  }

  private createFromForm(): IPotato {
    return {
      ...new Potato(),
      id: this.editForm.get(['id']).value,
      shape: this.editForm.get(['shape']).value,
      size: this.editForm.get(['size']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPotato>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
