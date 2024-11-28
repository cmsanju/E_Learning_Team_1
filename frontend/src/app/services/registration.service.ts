import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { Professor } from '../models/professor';
import { User } from '../models/user';

const NAV_URL = environment.apiURL;

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {
  user = new User();
  professor = new Professor();

  constructor(private _http : HttpClient) { }

public registerUserFromRemote(user : User):Observable<any>
{
    return this._http.post<any>(`${NAV_URL}/registeruser`,user)
}

public registerProfessorFromRemote(professor : Professor):Observable<any>
{
    return this._http.post<any>(`${NAV_URL}/registerprofessor`,professor)
}

public sendWelcomeEmail(userId: string): Observable<any> {
    console.log(`Attempting to send welcome email for user ID: ${userId}`);
    return this._http.post<any>(`${NAV_URL}/api/sendmail/welcome`, { userId: userId })
        .pipe(
            catchError(error => {
                console.error('Email service error:', error);
                return throwError(() => error);
            })
        );
}

}
