import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPotato } from 'app/shared/model/potato.model';

type EntityResponseType = HttpResponse<IPotato>;
type EntityArrayResponseType = HttpResponse<IPotato[]>;

@Injectable({ providedIn: 'root' })
export class PotatoService {
  public resourceUrl = SERVER_API_URL + 'api/potatoes';

  constructor(protected http: HttpClient) {}

  create(potato: IPotato): Observable<EntityResponseType> {
    return this.http.post<IPotato>(this.resourceUrl, potato, { observe: 'response' });
  }

  update(potato: IPotato): Observable<EntityResponseType> {
    return this.http.put<IPotato>(this.resourceUrl, potato, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPotato>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPotato[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
