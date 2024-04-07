import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../membership-level.test-samples';

import { MembershipLevelFormService } from './membership-level-form.service';

describe('MembershipLevel Form Service', () => {
  let service: MembershipLevelFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MembershipLevelFormService);
  });

  describe('Service methods', () => {
    describe('createMembershipLevelFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createMembershipLevelFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            description: expect.any(Object),
            sessionAmount: expect.any(Object),
            price: expect.any(Object),
          }),
        );
      });

      it('passing IMembershipLevel should create a new form with FormGroup', () => {
        const formGroup = service.createMembershipLevelFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            description: expect.any(Object),
            sessionAmount: expect.any(Object),
            price: expect.any(Object),
          }),
        );
      });
    });

    describe('getMembershipLevel', () => {
      it('should return NewMembershipLevel for default MembershipLevel initial value', () => {
        const formGroup = service.createMembershipLevelFormGroup(sampleWithNewData);

        const membershipLevel = service.getMembershipLevel(formGroup) as any;

        expect(membershipLevel).toMatchObject(sampleWithNewData);
      });

      it('should return NewMembershipLevel for empty MembershipLevel initial value', () => {
        const formGroup = service.createMembershipLevelFormGroup();

        const membershipLevel = service.getMembershipLevel(formGroup) as any;

        expect(membershipLevel).toMatchObject({});
      });

      it('should return IMembershipLevel', () => {
        const formGroup = service.createMembershipLevelFormGroup(sampleWithRequiredData);

        const membershipLevel = service.getMembershipLevel(formGroup) as any;

        expect(membershipLevel).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IMembershipLevel should not enable id FormControl', () => {
        const formGroup = service.createMembershipLevelFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewMembershipLevel should disable id FormControl', () => {
        const formGroup = service.createMembershipLevelFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
