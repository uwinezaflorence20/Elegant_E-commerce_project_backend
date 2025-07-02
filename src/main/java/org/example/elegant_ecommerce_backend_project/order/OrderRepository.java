package org.example.elegant_ecommerce_backend_project.order;

import org.example.elegant_ecommerce_backend_project.Dto.OrderDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);

    @Query("SELECT new org.example.elegant_ecommerce_backend_project.Dto.OrderDTO(o.id, o.status, o.createdAt, o.user.id) " +
            "FROM Order o WHERE o.user.id = :userId")
    List<OrderDTO> findOrderDTOsByUserId(Long userId);

    @Query("SELECT new org.example.elegant_ecommerce_backend_project.Dto.OrderDTO(o.id, o.status, o.createdAt, o.user.id) " +
            "FROM Order o WHERE o.id = :orderId")
    OrderDTO findOrderDTOById(Long orderId);
}
