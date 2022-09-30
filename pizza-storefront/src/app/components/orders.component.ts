import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { OrderSummaries } from 'app/models';
import { PizzaService } from 'app/pizza.service';
import { Subscription } from 'dexie';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {

  constructor(private activatedRoute : ActivatedRoute, private pizzaSvc : PizzaService, private router : Router) { }

  email! : string
  respOrder : OrderSummaries[] = []

  ngOnInit(): void {
    this.email = this.activatedRoute.snapshot.params['email']
    this.pizzaSvc.getOrders(this.email)
    .then(data =>{
      this.respOrder = data
      console.info("data received >>>>", data)

    })
    .catch(error => {
      console.info("error received >>>>", error)
    })
  }

  homepage(){
    this.router.navigate(['/'])
  }

}
