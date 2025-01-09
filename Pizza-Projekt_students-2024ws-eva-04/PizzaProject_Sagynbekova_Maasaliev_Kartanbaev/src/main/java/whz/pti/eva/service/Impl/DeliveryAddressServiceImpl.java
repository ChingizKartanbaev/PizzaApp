package whz.pti.eva.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import whz.pti.eva.domain.DeliveryAddress;
import whz.pti.eva.repository.DeliveryAddressRepository;
import whz.pti.eva.service.DeliveryAddressService;

@Service
public class DeliveryAddressServiceImpl implements DeliveryAddressService {

	@Autowired
	private DeliveryAddressRepository deliveryAddressRepository;
	
	@Override
	public DeliveryAddress save(DeliveryAddress deliveryAddress) {
		return deliveryAddressRepository.save(deliveryAddress);
	}

	@Override
	public List<DeliveryAddress> getAll() {
		return deliveryAddressRepository.findAll();
	}

	@Override
	public DeliveryAddress getById(Long id) {
		Optional<DeliveryAddress> deliveryAddress = deliveryAddressRepository.findById(id);
        return deliveryAddress.orElse(null);	
	}

	@Override
	public DeliveryAddress update(Long id, DeliveryAddress deliveryAddressDetails) {
		Optional<DeliveryAddress> optionalDeliveryAddress = deliveryAddressRepository.findById(id);
        if (optionalDeliveryAddress.isPresent()) {
        	DeliveryAddress existingDeliveryAddress = optionalDeliveryAddress.get();
        	existingDeliveryAddress.setStreet(deliveryAddressDetails.getStreet());
        	existingDeliveryAddress.setHouseNumber(deliveryAddressDetails.getHouseNumber());
        	existingDeliveryAddress.setTown(deliveryAddressDetails.getTown());
        	existingDeliveryAddress.setPostalCode(deliveryAddressDetails.getPostalCode());
            return deliveryAddressRepository.save(existingDeliveryAddress);
        } else {
            return null;
        }
	}

	@Override
	public boolean deleteById(Long id) {
		if (deliveryAddressRepository.existsById(id)) {
			deliveryAddressRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
	}

}
