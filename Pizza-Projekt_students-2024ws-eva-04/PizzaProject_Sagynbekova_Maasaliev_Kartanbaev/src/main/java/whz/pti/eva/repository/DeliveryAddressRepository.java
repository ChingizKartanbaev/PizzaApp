package whz.pti.eva.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import whz.pti.eva.domain.DeliveryAddress;

@Repository 
public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Long>{

}
