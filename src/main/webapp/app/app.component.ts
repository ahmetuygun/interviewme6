import { Component } from '@angular/core';
import { registerLocaleData } from '@angular/common';
import locale from '@angular/common/locales/en';
import dayjs from 'dayjs/esm';
import {FaIconLibrary, FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import { NgbDatepickerConfig } from '@ng-bootstrap/ng-bootstrap';
// jhipster-needle-angular-add-module-import JHipster will add new module here

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { fontAwesomeIcons } from './config/font-awesome-icons';
import MainComponent from './layouts/main/main.component';
import {MaterialModule} from "./shared/material/material.module";

@Component({
  selector: 'jhi-app',
  standalone: true,
  template: '<jhi-main></jhi-main>',
  imports: [
    MainComponent,
    MaterialModule,FontAwesomeModule
    // jhipster-needle-angular-add-module JHipster will add new module here
  ],
})
export default class AppComponent {
  constructor(applicationConfigService: ApplicationConfigService, iconLibrary: FaIconLibrary, dpConfig: NgbDatepickerConfig) {
    applicationConfigService.setEndpointPrefix(SERVER_API_URL);
    registerLocaleData(locale);
    iconLibrary.addIcons(...fontAwesomeIcons);
    dpConfig.minDate = { year: dayjs().subtract(100, 'year').year(), month: 1, day: 1 };
  }
}
