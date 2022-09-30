// Implement the methods in PizzaService for Task 3
// Add appropriate parameter and return type

import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { firstValueFrom, Subject, tap } from "rxjs";
import { Order, OrderSummaries } from "./models";

@Injectable()
export class PizzaService {

  constructor(private httpClient : HttpClient) { }

  onNewOrder = new Subject<OrderSummaries[]>()

  // POST /api/order
  // Add any required parameters or return type
  createOrder(order : Order) {
    const postUrl = '/api/order'

    const headers = new HttpHeaders().set('Accept', 'application/json')

    return(
      firstValueFrom(
        this.httpClient.post<string>(postUrl, order, {headers})
      )
    )
  }

  // GET /api/order/<email>/all
  // Add any required parameters or return type
  getOrders(email : string) {
    const url = `/api/order/${email}/all`

    const params = new HttpParams();

    return(
      firstValueFrom(
        this.httpClient.get<OrderSummaries[]>(url, {params})
      )
    )
  }
}
