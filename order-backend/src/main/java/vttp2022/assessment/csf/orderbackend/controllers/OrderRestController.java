package vttp2022.assessment.csf.orderbackend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import vttp2022.assessment.csf.orderbackend.models.Order;
import vttp2022.assessment.csf.orderbackend.models.OrderSummary;
import vttp2022.assessment.csf.orderbackend.services.OrderService;

@RestController
@RequestMapping(path="/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderRestController {

    @Autowired
    private OrderService orderSvc;

    @PostMapping("/order")
    public ResponseEntity<String> postOrder(@RequestBody String payload){

        Order order = Order.createFromJson(payload);
        orderSvc.createOrder(order);

        JsonObject respJson = Json.createObjectBuilder()
        .add("Status", "Pizza successfully added")
        .build();

        return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(respJson.toString());
    }

    @GetMapping("/order/{email}/all")
    public ResponseEntity<String> getOrder(@PathVariable String email){

         List<OrderSummary> orderList = orderSvc.getOrdersByEmail(email);

         JsonArrayBuilder respArr = Json.createArrayBuilder();
         for(OrderSummary o : orderList){
             JsonObject obj = Json.createObjectBuilder()
             .add("id", o.getOrderId())
             .add("name", o.getName())
             .add("email", o.getEmail())
             .add("totalcost", o.getAmount())
             .build();
             respArr.add(obj);
        }

         return ResponseEntity
         .status(HttpStatus.OK)
         .body(respArr.build().toString()); 

    }

}
