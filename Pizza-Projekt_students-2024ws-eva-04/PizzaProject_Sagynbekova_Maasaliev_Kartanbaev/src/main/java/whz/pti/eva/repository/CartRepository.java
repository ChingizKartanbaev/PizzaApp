package whz.pti.eva.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import whz.pti.eva.domain.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByCustomer_Id(Long customerId);
    
    @Modifying
    @Query(value = "DELETE FROM cart_item WHERE cart_id = :cartId AND item_id = :itemId", nativeQuery = true)
    void deleteItemFromCart(@Param("cartId") Long cartId, @Param("itemId") UUID itemId);
    
    Optional<Cart> findBySessionId(String sessionId);

    @Query("SELECT c FROM Cart c WHERE c.customer IS NULL AND c.createdAt < :cutoff")
    List<Cart> findAllByCustomerIsNullAndCreatedAtBefore(@Param("cutoff") LocalDateTime cutoff);

}

