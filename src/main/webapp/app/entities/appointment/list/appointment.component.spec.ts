import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { AppointmentService } from '../service/appointment.service';

import { AppointmentComponent } from './appointment.component';

describe('Appointment Management Component', () => {
  let comp: AppointmentComponent;
  let fixture: ComponentFixture<AppointmentComponent>;
  let service: AppointmentService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'appointment', component: AppointmentComponent }]),
        HttpClientTestingModule,
        AppointmentComponent,
      ],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              }),
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(AppointmentComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AppointmentComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AppointmentService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        }),
      ),
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.appointments?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to appointmentService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getAppointmentIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getAppointmentIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
