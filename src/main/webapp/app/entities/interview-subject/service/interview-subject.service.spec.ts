import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IInterviewSubject } from '../interview-subject.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../interview-subject.test-samples';

import { InterviewSubjectService } from './interview-subject.service';

const requireRestSample: IInterviewSubject = {
  ...sampleWithRequiredData,
};

describe('InterviewSubject Service', () => {
  let service: InterviewSubjectService;
  let httpMock: HttpTestingController;
  let expectedResult: IInterviewSubject | IInterviewSubject[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(InterviewSubjectService);
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

    it('should create a InterviewSubject', () => {
      const interviewSubject = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(interviewSubject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a InterviewSubject', () => {
      const interviewSubject = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(interviewSubject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a InterviewSubject', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of InterviewSubject', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a InterviewSubject', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addInterviewSubjectToCollectionIfMissing', () => {
      it('should add a InterviewSubject to an empty array', () => {
        const interviewSubject: IInterviewSubject = sampleWithRequiredData;
        expectedResult = service.addInterviewSubjectToCollectionIfMissing([], interviewSubject);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(interviewSubject);
      });

      it('should not add a InterviewSubject to an array that contains it', () => {
        const interviewSubject: IInterviewSubject = sampleWithRequiredData;
        const interviewSubjectCollection: IInterviewSubject[] = [
          {
            ...interviewSubject,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addInterviewSubjectToCollectionIfMissing(interviewSubjectCollection, interviewSubject);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a InterviewSubject to an array that doesn't contain it", () => {
        const interviewSubject: IInterviewSubject = sampleWithRequiredData;
        const interviewSubjectCollection: IInterviewSubject[] = [sampleWithPartialData];
        expectedResult = service.addInterviewSubjectToCollectionIfMissing(interviewSubjectCollection, interviewSubject);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(interviewSubject);
      });

      it('should add only unique InterviewSubject to an array', () => {
        const interviewSubjectArray: IInterviewSubject[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const interviewSubjectCollection: IInterviewSubject[] = [sampleWithRequiredData];
        expectedResult = service.addInterviewSubjectToCollectionIfMissing(interviewSubjectCollection, ...interviewSubjectArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const interviewSubject: IInterviewSubject = sampleWithRequiredData;
        const interviewSubject2: IInterviewSubject = sampleWithPartialData;
        expectedResult = service.addInterviewSubjectToCollectionIfMissing([], interviewSubject, interviewSubject2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(interviewSubject);
        expect(expectedResult).toContain(interviewSubject2);
      });

      it('should accept null and undefined values', () => {
        const interviewSubject: IInterviewSubject = sampleWithRequiredData;
        expectedResult = service.addInterviewSubjectToCollectionIfMissing([], null, interviewSubject, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(interviewSubject);
      });

      it('should return initial array if no InterviewSubject is added', () => {
        const interviewSubjectCollection: IInterviewSubject[] = [sampleWithRequiredData];
        expectedResult = service.addInterviewSubjectToCollectionIfMissing(interviewSubjectCollection, undefined, null);
        expect(expectedResult).toEqual(interviewSubjectCollection);
      });
    });

    describe('compareInterviewSubject', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareInterviewSubject(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareInterviewSubject(entity1, entity2);
        const compareResult2 = service.compareInterviewSubject(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareInterviewSubject(entity1, entity2);
        const compareResult2 = service.compareInterviewSubject(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareInterviewSubject(entity1, entity2);
        const compareResult2 = service.compareInterviewSubject(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
