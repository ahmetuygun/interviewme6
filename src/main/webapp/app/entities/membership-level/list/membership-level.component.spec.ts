import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { MembershipLevelService } from '../service/membership-level.service';

import { MembershipLevelComponent } from './membership-level.component';

describe('MembershipLevel Management Component', () => {
  let comp: MembershipLevelComponent;
  let fixture: ComponentFixture<MembershipLevelComponent>;
  let service: MembershipLevelService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'membership-level', component: MembershipLevelComponent }]),
        HttpClientTestingModule,
        MembershipLevelComponent,
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
      .overrideTemplate(MembershipLevelComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MembershipLevelComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(MembershipLevelService);

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
    expect(comp.membershipLevels?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to membershipLevelService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getMembershipLevelIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getMembershipLevelIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
