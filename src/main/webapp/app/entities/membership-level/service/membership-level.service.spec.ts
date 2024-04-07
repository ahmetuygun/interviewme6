import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IMembershipLevel } from '../membership-level.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../membership-level.test-samples';

import { MembershipLevelService } from './membership-level.service';

const requireRestSample: IMembershipLevel = {
  ...sampleWithRequiredData,
};

describe('MembershipLevel Service', () => {
  let service: MembershipLevelService;
  let httpMock: HttpTestingController;
  let expectedResult: IMembershipLevel | IMembershipLevel[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MembershipLevelService);
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

    it('should create a MembershipLevel', () => {
      const membershipLevel = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(membershipLevel).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MembershipLevel', () => {
      const membershipLevel = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(membershipLevel).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MembershipLevel', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MembershipLevel', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a MembershipLevel', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addMembershipLevelToCollectionIfMissing', () => {
      it('should add a MembershipLevel to an empty array', () => {
        const membershipLevel: IMembershipLevel = sampleWithRequiredData;
        expectedResult = service.addMembershipLevelToCollectionIfMissing([], membershipLevel);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(membershipLevel);
      });

      it('should not add a MembershipLevel to an array that contains it', () => {
        const membershipLevel: IMembershipLevel = sampleWithRequiredData;
        const membershipLevelCollection: IMembershipLevel[] = [
          {
            ...membershipLevel,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addMembershipLevelToCollectionIfMissing(membershipLevelCollection, membershipLevel);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MembershipLevel to an array that doesn't contain it", () => {
        const membershipLevel: IMembershipLevel = sampleWithRequiredData;
        const membershipLevelCollection: IMembershipLevel[] = [sampleWithPartialData];
        expectedResult = service.addMembershipLevelToCollectionIfMissing(membershipLevelCollection, membershipLevel);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(membershipLevel);
      });

      it('should add only unique MembershipLevel to an array', () => {
        const membershipLevelArray: IMembershipLevel[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const membershipLevelCollection: IMembershipLevel[] = [sampleWithRequiredData];
        expectedResult = service.addMembershipLevelToCollectionIfMissing(membershipLevelCollection, ...membershipLevelArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const membershipLevel: IMembershipLevel = sampleWithRequiredData;
        const membershipLevel2: IMembershipLevel = sampleWithPartialData;
        expectedResult = service.addMembershipLevelToCollectionIfMissing([], membershipLevel, membershipLevel2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(membershipLevel);
        expect(expectedResult).toContain(membershipLevel2);
      });

      it('should accept null and undefined values', () => {
        const membershipLevel: IMembershipLevel = sampleWithRequiredData;
        expectedResult = service.addMembershipLevelToCollectionIfMissing([], null, membershipLevel, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(membershipLevel);
      });

      it('should return initial array if no MembershipLevel is added', () => {
        const membershipLevelCollection: IMembershipLevel[] = [sampleWithRequiredData];
        expectedResult = service.addMembershipLevelToCollectionIfMissing(membershipLevelCollection, undefined, null);
        expect(expectedResult).toEqual(membershipLevelCollection);
      });
    });

    describe('compareMembershipLevel', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareMembershipLevel(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareMembershipLevel(entity1, entity2);
        const compareResult2 = service.compareMembershipLevel(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareMembershipLevel(entity1, entity2);
        const compareResult2 = service.compareMembershipLevel(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareMembershipLevel(entity1, entity2);
        const compareResult2 = service.compareMembershipLevel(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
