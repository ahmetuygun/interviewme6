import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IInterviewer } from '../interviewer.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../interviewer.test-samples';

import { InterviewerService } from './interviewer.service';

const requireRestSample: IInterviewer = {
  ...sampleWithRequiredData,
};

describe('Interviewer Service', () => {
  let service: InterviewerService;
  let httpMock: HttpTestingController;
  let expectedResult: IInterviewer | IInterviewer[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(InterviewerService);
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

    it('should create a Interviewer', () => {
      const interviewer = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(interviewer).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Interviewer', () => {
      const interviewer = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(interviewer).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Interviewer', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Interviewer', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Interviewer', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addInterviewerToCollectionIfMissing', () => {
      it('should add a Interviewer to an empty array', () => {
        const interviewer: IInterviewer = sampleWithRequiredData;
        expectedResult = service.addInterviewerToCollectionIfMissing([], interviewer);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(interviewer);
      });

      it('should not add a Interviewer to an array that contains it', () => {
        const interviewer: IInterviewer = sampleWithRequiredData;
        const interviewerCollection: IInterviewer[] = [
          {
            ...interviewer,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addInterviewerToCollectionIfMissing(interviewerCollection, interviewer);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Interviewer to an array that doesn't contain it", () => {
        const interviewer: IInterviewer = sampleWithRequiredData;
        const interviewerCollection: IInterviewer[] = [sampleWithPartialData];
        expectedResult = service.addInterviewerToCollectionIfMissing(interviewerCollection, interviewer);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(interviewer);
      });

      it('should add only unique Interviewer to an array', () => {
        const interviewerArray: IInterviewer[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const interviewerCollection: IInterviewer[] = [sampleWithRequiredData];
        expectedResult = service.addInterviewerToCollectionIfMissing(interviewerCollection, ...interviewerArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const interviewer: IInterviewer = sampleWithRequiredData;
        const interviewer2: IInterviewer = sampleWithPartialData;
        expectedResult = service.addInterviewerToCollectionIfMissing([], interviewer, interviewer2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(interviewer);
        expect(expectedResult).toContain(interviewer2);
      });

      it('should accept null and undefined values', () => {
        const interviewer: IInterviewer = sampleWithRequiredData;
        expectedResult = service.addInterviewerToCollectionIfMissing([], null, interviewer, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(interviewer);
      });

      it('should return initial array if no Interviewer is added', () => {
        const interviewerCollection: IInterviewer[] = [sampleWithRequiredData];
        expectedResult = service.addInterviewerToCollectionIfMissing(interviewerCollection, undefined, null);
        expect(expectedResult).toEqual(interviewerCollection);
      });
    });

    describe('compareInterviewer', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareInterviewer(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareInterviewer(entity1, entity2);
        const compareResult2 = service.compareInterviewer(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareInterviewer(entity1, entity2);
        const compareResult2 = service.compareInterviewer(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareInterviewer(entity1, entity2);
        const compareResult2 = service.compareInterviewer(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
