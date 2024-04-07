jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { InterviewNoteService } from '../service/interview-note.service';

import { InterviewNoteDeleteDialogComponent } from './interview-note-delete-dialog.component';

describe('InterviewNote Management Delete Component', () => {
  let comp: InterviewNoteDeleteDialogComponent;
  let fixture: ComponentFixture<InterviewNoteDeleteDialogComponent>;
  let service: InterviewNoteService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, InterviewNoteDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(InterviewNoteDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(InterviewNoteDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(InterviewNoteService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      }),
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
