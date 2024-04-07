import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPersonalDetail } from '../personal-detail.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../personal-detail.test-samples';

import { PersonalDetailService, RestPersonalDetail } from './personal-detail.service';

const requireRestSample: RestPersonalDetail = {
  ...sampleWithRequiredData,
  dateOfBirth: sampleWithRequiredData.dateOfBirth?.toJSON(),
};

describe('PersonalDetail Service', () => {
  let service: PersonalDetailService;
  let httpMock: HttpTestingController;
  let expectedResult: IPersonalDetail | IPersonalDetail[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PersonalDetailService);
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

    it('should create a PersonalDetail', () => {
      const personalDetail = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(personalDetail).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PersonalDetail', () => {
      const personalDetail = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(personalDetail).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PersonalDetail', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PersonalDetail', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PersonalDetail', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPersonalDetailToCollectionIfMissing', () => {
      it('should add a PersonalDetail to an empty array', () => {
        const personalDetail: IPersonalDetail = sampleWithRequiredData;
        expectedResult = service.addPersonalDetailToCollectionIfMissing([], personalDetail);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(personalDetail);
      });

      it('should not add a PersonalDetail to an array that contains it', () => {
        const personalDetail: IPersonalDetail = sampleWithRequiredData;
        const personalDetailCollection: IPersonalDetail[] = [
          {
            ...personalDetail,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPersonalDetailToCollectionIfMissing(personalDetailCollection, personalDetail);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PersonalDetail to an array that doesn't contain it", () => {
        const personalDetail: IPersonalDetail = sampleWithRequiredData;
        const personalDetailCollection: IPersonalDetail[] = [sampleWithPartialData];
        expectedResult = service.addPersonalDetailToCollectionIfMissing(personalDetailCollection, personalDetail);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(personalDetail);
      });

      it('should add only unique PersonalDetail to an array', () => {
        const personalDetailArray: IPersonalDetail[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const personalDetailCollection: IPersonalDetail[] = [sampleWithRequiredData];
        expectedResult = service.addPersonalDetailToCollectionIfMissing(personalDetailCollection, ...personalDetailArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const personalDetail: IPersonalDetail = sampleWithRequiredData;
        const personalDetail2: IPersonalDetail = sampleWithPartialData;
        expectedResult = service.addPersonalDetailToCollectionIfMissing([], personalDetail, personalDetail2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(personalDetail);
        expect(expectedResult).toContain(personalDetail2);
      });

      it('should accept null and undefined values', () => {
        const personalDetail: IPersonalDetail = sampleWithRequiredData;
        expectedResult = service.addPersonalDetailToCollectionIfMissing([], null, personalDetail, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(personalDetail);
      });

      it('should return initial array if no PersonalDetail is added', () => {
        const personalDetailCollection: IPersonalDetail[] = [sampleWithRequiredData];
        expectedResult = service.addPersonalDetailToCollectionIfMissing(personalDetailCollection, undefined, null);
        expect(expectedResult).toEqual(personalDetailCollection);
      });
    });

    describe('comparePersonalDetail', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePersonalDetail(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePersonalDetail(entity1, entity2);
        const compareResult2 = service.comparePersonalDetail(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePersonalDetail(entity1, entity2);
        const compareResult2 = service.comparePersonalDetail(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePersonalDetail(entity1, entity2);
        const compareResult2 = service.comparePersonalDetail(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
