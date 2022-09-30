// Add your models here if you have any
export interface Order{
  name : string
  email : string
  size : string
  base : string
  sauce : string
  comments : string
  toppings : string[]
}

export interface OrderSummaries{
  id : string
  name: string
  email: string
  totalcost : string
}
