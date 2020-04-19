import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'file-info',
        loadChildren: () => import('./file-info/file-info.module').then(m => m.PdfEditorFileInfoModule)
      },
      {
        path: 'box',
        loadChildren: () => import('./box/box.module').then(m => m.PdfEditorBoxModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class PdfEditorEntityModule {}
