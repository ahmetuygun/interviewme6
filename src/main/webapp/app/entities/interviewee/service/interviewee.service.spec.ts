import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IInterviewee } from '../interviewee.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../interviewee.test-samples';

import { IntervieweeService } from './interviewee.service';

const requireRestSample: IInterviewee = {
  ...sampleWithRequiredData,
};

describe('Interviewee Service', () => {
  let service: IntervieweeService;
  let httpMock: HttpTestingController;
  let expectedResult: IInterviewee | IInterviewee[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(IntervieweeService);
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

    it('should create a Interviewee', () => {
      const interviewee = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(interviewee).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Interviewee', () => {
      const interviewee = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(interviewee).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Interviewee', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Interviewee', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Interviewee', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addIntervieweeToCollectionIfMissing', () => {
      it('should add a Interviewee to an empty array', () => {
        const interviewee: IInterviewee = sampleWithRequiredData;
        expectedResult = service.addIntervieweeToCollectionIfMissing([], interviewee);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(interviewee);
      });

      it('should not add a Interviewee to an array that contains it', () => {
        const interviewee: IInterviewee = sampleWithRequiredData;
        const intervieweeCollection: IInterviewee[] = [
          {
            ...interviewee,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addIntervieweeToCollectionIfMissing(intervieweeCollection, interviewee);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Interviewee to an array that doesn't contain it", () => {
        const interviewee: IInterviewee = sampleWithRequiredData;
        const intervieweeCollection: IInterviewee[] = [sampleWithPartialData];
        expectedResult = service.addIntervieweeToCollectionIfMissing(intervieweeCollection, interviewee);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(interviewee);
      });

      it('should add only unique Interviewee to an array', () => {
        const intervieweeArray: IInterviewee[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const intervieweeCollection: IInterviewee[] = [sampleWithRequiredData];
        expectedResult = service.addIntervieweeToCollectionIfMissing(intervieweeCollection, ...intervieweeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const interviewee: IInterviewee = sampleWithRequiredData;
        const interviewee2: IInterviewee = sampleWithPartialData;
        expectedResult = service.addIntervieweeToCollectionIfMissing([], interviewee, interviewee2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(interviewee);
        expect(expectedResult).toContain(interviewee2);
      });

      it('should accept null and undefined values', () => {
        const interviewee: IInterviewee = sampleWithRequiredData;
        expectedResult = service.addIntervieweeToCollectionIfMissing([], null, interviewee, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(interviewee);
      });

      it('should return initial array if no Interviewee is added', () => {
        const intervieweeCollection: IInterviewee[] = [sampleWithRequiredData];
        expectedResult = service.addIntervieweeToCollectionIfMissing(intervieweeCollection, undefined, null);
        expect(expectedResult).toEqual(intervieweeCollection);
      });
    });

    describe('compareInterviewee', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareInterviewee(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareInterviewee(entity1, entity2);
        const compareResult2 = service.compareInterviewee(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareInterviewee(entity1, entity2);
        const compareResult2 = service.compareInterviewee(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareInterviewee(entity1, entity2);
        const compareResult2 = service.compareInterviewee(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
