import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TesterSharedModule } from 'app/shared/shared.module';
import { PotatoComponent } from './potato.component';
import { PotatoDetailComponent } from './potato-detail.component';
import { PotatoUpdateComponent } from './potato-update.component';
import { PotatoDeletePopupComponent, PotatoDeleteDialogComponent } from './potato-delete-dialog.component';
import { potatoRoute, potatoPopupRoute } from './potato.route';

const ENTITY_STATES = [...potatoRoute, ...potatoPopupRoute];

@NgModule({
  imports: [TesterSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [PotatoComponent, PotatoDetailComponent, PotatoUpdateComponent, PotatoDeleteDialogComponent, PotatoDeletePopupComponent],
  entryComponents: [PotatoDeleteDialogComponent]
})
export class TesterPotatoModule {}
