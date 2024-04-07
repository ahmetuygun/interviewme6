import {Component, ViewChild, OnInit, AfterViewInit, ElementRef} from '@angular/core';
import {FormGroup, FormControl, Validators, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {Router, RouterModule} from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import {LoginService} from 'app/login/login.service';
import {AccountService} from 'app/core/auth/account.service';
import {Account} from "../core/auth/account.model";
import {InterviewerService} from "../entities/interviewer/service/interviewer.service";
import {IntervieweeService} from "../entities/interviewee/service/interviewee.service";

@Component({
  selector: 'jhi-login',
  standalone: true,
  imports: [SharedModule, FormsModule, ReactiveFormsModule, RouterModule],
  templateUrl: './login.component.html',
})
export default class LoginComponent implements OnInit, AfterViewInit {
  @ViewChild('username', {static: false})
  username!: ElementRef;
  userId!: number | null | undefined;
  account: Account | null = null;

  authenticationError = false;

  loginForm = new FormGroup({
    username: new FormControl('', {nonNullable: true, validators: [Validators.required]}),
    password: new FormControl('', {nonNullable: true, validators: [Validators.required]}),
    rememberMe: new FormControl(false, {nonNullable: true, validators: [Validators.required]}),
  });

  constructor(
    private accountService: AccountService,
    private interviewerService: InterviewerService,
    private intervieweeService: IntervieweeService,
    private loginService: LoginService,
    private router: Router,
  ) {
  }

  ngOnInit(): void {
    // if already authenticated then navigate to home page
    this.accountService.identity().subscribe(() => {
      if (this.accountService.isAuthenticated()) {
        this.router.navigate(['']);
      }
    });
  }

  ngAfterViewInit(): void {
    this.username.nativeElement.focus();
  }

  login(): void {
    this.loginService.login(this.loginForm.getRawValue()).subscribe({
      next: () => {
        this.accountService.identity().subscribe(account => {
          if (account?.id) {
            if (account.type === 'INTERVIEWER') {
              this.handleInterviewerLogin(account.id);
            } else if (account.type === 'INTERVIEWEE') {
              this.handleIntervieweeLogin(account.id);
            }
          }
          this.authenticationError = false;
          this.handlePostLoginRouting();
        });
      },
      error: () => {
        this.authenticationError = true;
      }
    });
  }

  private handleInterviewerLogin(userId: number): void {
    this.interviewerService.findByInternalUserId(userId).subscribe(
      interviewer => {
        if (interviewer.body && interviewer.body.length > 0) {
          this.router.navigate(['/interviewer', interviewer.body[0].id, 'view']);
        } else {
          this.router.navigate(['/interviewer/new']);
        }
      },
      error => this.handleLoginError(error, '/interviewer/new')
    );
  }

  private handleIntervieweeLogin(userId: number): void {
    this.intervieweeService.findByInternalUserId(userId).subscribe(
      interviewee => {
        if (interviewee.body && interviewee.body.length > 0) {
          this.router.navigate(['/interviewee', interviewee.body[0].id, 'view']);
        } else {
          this.router.navigate(['/interviewee/new']);
        }
      },
      error => this.handleLoginError(error, '/interviewee/new')
    );
  }

  private handleLoginError(error: any, fallbackRoute: string): void {
    if (error.status === 404) {
      this.router.navigate([fallbackRoute]);
    } else {
      console.error('An error occurred:', error);
      // Handle other errors here
    }
  }

  private handlePostLoginRouting(): void {
    if (!this.router.getCurrentNavigation()) {
      this.router.navigate(['']);
    }
  }
}

