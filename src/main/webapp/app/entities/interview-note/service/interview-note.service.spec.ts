import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IInterviewNote } from '../interview-note.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../interview-note.test-samples';

import { InterviewNoteService } from './interview-note.service';

const requireRestSample: IInterviewNote = {
  ...sampleWithRequiredData,
};

describe('InterviewNote Service', () => {
  let service: InterviewNoteService;
  let httpMock: HttpTestingController;
  let expectedResult: IInterviewNote | IInterviewNote[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(InterviewNoteService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a InterviewNote', () => {
      const interviewNote = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(interviewNote).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a InterviewNote', () => {
      const interviewNote = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(interviewNote).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a InterviewNote', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of InterviewNote', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a InterviewNote', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addInterviewNoteToCollectionIfMissing', () => {
      it('should add a InterviewNote to an empty array', () => {
        const interviewNote: IInterviewNote = sampleWithRequiredData;
        expectedResult = service.addInterviewNoteToCollectionIfMissing([], interviewNote);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(interviewNote);
      });

      it('should not add a InterviewNote to an array that contains it', () => {
        const interviewNote: IInterviewNote = sampleWithRequiredData;
        const interviewNoteCollection: IInterviewNote[] = [
          {
            ...interviewNote,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addInterviewNoteToCollectionIfMissing(interviewNoteCollection, interviewNote);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a InterviewNote to an array that doesn't contain it", () => {
        const interviewNote: IInterviewNote = sampleWithRequiredData;
        const interviewNoteCollection: IInterviewNote[] = [sampleWithPartialData];
        expectedResult = service.addInterviewNoteToCollectionIfMissing(interviewNoteCollection, interviewNote);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(interviewNote);
      });

      it('should add only unique InterviewNote to an array', () => {
        const interviewNoteArray: IInterviewNote[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const interviewNoteCollection: IInterviewNote[] = [sampleWithRequiredData];
        expectedResult = service.addInterviewNoteToCollectionIfMissing(interviewNoteCollection, ...interviewNoteArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const interviewNote: IInterviewNote = sampleWithRequiredData;
        const interviewNote2: IInterviewNote = sampleWithPartialData;
        expectedResult = service.addInterviewNoteToCollectionIfMissing([], interviewNote, interviewNote2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(interviewNote);
        expect(expectedResult).toContain(interviewNote2);
      });

      it('should accept null and undefined values', () => {
        const interviewNote: IInterviewNote = sampleWithRequiredData;
        expectedResult = service.addInterviewNoteToCollectionIfMissing([], null, interviewNote, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(interviewNote);
      });

      it('should return initial array if no InterviewNote is added', () => {
        const interviewNoteCollection: IInterviewNote[] = [sampleWithRequiredData];
        expectedResult = service.addInterviewNoteToCollectionIfMissing(interviewNoteCollection, undefined, null);
        expect(expectedResult).toEqual(interviewNoteCollection);
      });
    });

    describe('compareInterviewNote', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareInterviewNote(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareInterviewNote(entity1, entity2);
        const compareResult2 = service.compareInterviewNote(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareInterviewNote(entity1, entity2);
        const compareResult2 = service.compareInterviewNote(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareInterviewNote(entity1, entity2);
        const compareResult2 = service.compareInterviewNote(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
