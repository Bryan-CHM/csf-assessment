package vttp2022.assessment.csf.orderbackend.repositories;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022.assessment.csf.orderbackend.models.Order;
import vttp2022.assessment.csf.orderbackend.models.OrderSummary;

@Repository
public class OrderRepository {

    private static final String SQL_CREATE_ORDER = "insert into orders(name, email, comments, pizza_size, thick_crust, sauce, toppings) values(?,?,?,?,?,?,?)";
    private static final String SQL_GET_ORDERS_BY_EMAIL = "select * from orders where email = ?";
    
    @Autowired
    private JdbcTemplate template;

    public boolean createOrder(Order order){
        return 1 == template.update(
            SQL_CREATE_ORDER, 
            order.getName(), 
            order.getEmail(), 
            order.getComments(), 
            order.getSize(), 
            order.isThickCrust(),
            order.getSauce(), 
            order.getToppings().toString().replace("[", "").replace("]", ""));
    }

    public List<Order> getOrderByEmail(String email){
        List<Order>  orders = new LinkedList<>();
        SqlRowSet rs = template.queryForRowSet(SQL_GET_ORDERS_BY_EMAIL, email);
       while(rs.next()){
        Order order = Order.createFromResultSet(rs);
        orders.add(order);
       }
       return orders;
    }
    
}
