package vttp2022.assessment.csf.orderbackend.services;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp2022.assessment.csf.orderbackend.models.Order;
import vttp2022.assessment.csf.orderbackend.models.OrderSummary;
import vttp2022.assessment.csf.orderbackend.repositories.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private PricingService priceSvc;

	@Autowired
	private OrderRepository orderRepo;

	// POST /api/order
	// Create a new order by inserting into orders table in pizzafactory database
	// IMPORTANT: Do not change the method's signature
	public void createOrder(Order order) {
		orderRepo.createOrder(order);
	}

	// GET /api/order/<email>/all
	// Get a list of orders for email from orders table in pizzafactory database
	// IMPORTANT: Do not change the method's signature
	public List<OrderSummary> getOrdersByEmail(String email) {
		// Use priceSvc to calculate the total cost of an order
		List<OrderSummary> summaries = new LinkedList<>();
		List<Order> orders = orderRepo.getOrderByEmail(email);
		for(Order o : orders){
			Float totalprice = 0f;
			totalprice += priceSvc.size(o.getSize());
			totalprice += priceSvc.sauce(o.getSauce());
			for(String s : o.getToppings()){
				totalprice += priceSvc.topping(s);
			}
			if(o.isThickCrust()){
				totalprice += priceSvc.thickCrust();
			}
			else{
				totalprice += priceSvc.thinCrust();
			}

			OrderSummary summary = new OrderSummary();
			summary.setOrderId(o.getOrderId());
			summary.setName(o.getName());
			summary.setEmail(o.getEmail());
			summary.setAmount(totalprice);
			summaries.add(summary);
		}
		return summaries;
	}
}
