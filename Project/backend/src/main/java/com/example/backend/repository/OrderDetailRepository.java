package com.example.backend.repository;

import com.example.backend.model.OrderDetail;
import com.example.backend.model.OrderDetailKey;
import com.example.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailKey> {
    @Query(value = "SELECT od FROM OrderDetail od WHERE od.orderDetailID.orderID = :orderID")
    List<OrderDetail> findOrderDetailByOrderId(@Param("orderID") Long orderID);
    Long deleteByOrderOrderID(@Param("orderID") Long orderID);
    List<OrderDetail> findByOrderOrderID(Long orderID);

    @Query(value = "SELECT od.product FROM OrderDetail od GROUP BY od.product.productID " +
            "ORDER BY SUM(od.orderQuantity) DESC LIMIT 5")
    List<Product> findTop5Product();

    @Query(value = "SELECT SUM(p.product_price*od.order_quantity) \n" +
            "FROM project.order_detail od INNER JOIN project.order_product o ON o.orderid = od.orderid\n" +
            "INNER JOIN project.product p ON p.productid = od.productid\n" +
            "WHERE year(o.date) = YEAR(curdate()) AND month(o.date) = :month",nativeQuery = true)
    Double revenueMonth(@Param(value = "month") int month);
}
