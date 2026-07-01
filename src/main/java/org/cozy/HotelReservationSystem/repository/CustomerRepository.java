package org.cozy.HotelReservationSystem.repository;

import org.cozy.HotelReservationSystem.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // Add this
import org.springframework.data.repository.query.Param; // Add this
import org.springframework.stereotype.Repository;
import java.util.List; // Add this

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	@Query("SELECT c FROM Customer c WHERE " +
		       "LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
		       "LOWER(c.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
		       "c.phone LIKE CONCAT('%', :keyword, '%')") // <-- Only one ')' here
		List<Customer> searchCustomers(@Param("keyword") String keyword);
}