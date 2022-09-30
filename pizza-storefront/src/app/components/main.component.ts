import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Order } from 'app/models';
import { PizzaService } from 'app/pizza.service';

const SIZES: string[] = [
  "Personal - 6 inches",
  "Regular - 9 inches",
  "Large - 12 inches",
  "Extra Large - 15 inches"
]

const PizzaToppings: string[] = [
    'chicken', 'seafood', 'beef', 'vegetables',
    'cheese', 'arugula', 'pineapple'
]

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  pizzaSize = SIZES[0]
  pizzaForm!: FormGroup
  toppingsArray! : FormArray

  constructor(private fb : FormBuilder, private router : Router, private pizzaSvc : PizzaService) {}

  ngOnInit(): void {
    this.toppingsArray = this.fb.array([])
    this.pizzaForm = this.fb.group({
      name: this.fb.control<string>('',[Validators.required]),
      email: this.fb.control<string>('',[Validators.required, Validators.email]),
      size: this.fb.control<number>(0,[Validators.required]),
      base: this.fb.control<string>('thin',[Validators.required]),
      sauce: this.fb.control<string>('classic',[Validators.required]),
      comments: this.fb.control<string>(''),
      toppings: this.toppingsArray
      // TODO FIND A WAY TO MAKE FORMARRAY 1 OR MORE VALIDATOR
    })
  }


  updateToppingsArray(event : any){
    if(event.target.checked){
      this.toppingsArray.push(new FormControl(event.target.value))
    }else{
      const index = this.toppingsArray.controls
      .findIndex(x => x.value == event.target.value)
      this.toppingsArray.removeAt(index)
    }
  }

  listOrder(){
    this.router.navigate(['/orders',this.pizzaForm.value.email])
  }

  updateSize(size: string) {
    this.pizzaSize = SIZES[parseInt(size)]
  }

  processForm(){
    const order = this.pizzaForm.value as Order
    console.info('>>>> order:',order)
    this.pizzaSvc.createOrder(order)
    .then(value =>{
      console.info(value)
    }).catch(error =>{
      console.info(error)
    })
    this.router.navigate(['/orders',this.pizzaForm.value.email])
  }

}
