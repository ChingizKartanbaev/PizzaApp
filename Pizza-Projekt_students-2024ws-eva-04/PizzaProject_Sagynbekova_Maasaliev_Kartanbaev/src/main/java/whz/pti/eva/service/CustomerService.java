package whz.pti.eva.service;

import java.util.Optional;

import whz.pti.eva.domain.Customer;
import whz.pti.eva.domain.request.CustomerAddressRequest;

public interface CustomerService extends BaseService<Customer>{
    
	Customer create(CustomerAddressRequest customerAddressRequest);
	
	Customer updateAddress(Long id, CustomerAddressRequest customerAddressRequest);

	Optional<Customer> findByLoginName(String loginName);
}
