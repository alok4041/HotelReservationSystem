package org.cozy.HotelReservationSystem.repository;

import java.util.List;
import java.util.Optional;

import org.cozy.HotelReservationSystem.model.Customer; // Add this
import org.springframework.data.jpa.repository.JpaRepository; // Add this
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param; // Add this
import org.springframework.stereotype.Repository; // Add this

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	@Query("SELECT c FROM Customer c WHERE " +
		       "LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
		       "LOWER(c.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
		       "c.phone LIKE CONCAT('%', :keyword, '%')") // <-- Only one ')' here
		List<Customer> searchCustomers(@Param("keyword") String keyword);
	
	// Find customer by email
	Optional<Customer> findByEmail(String email);
}