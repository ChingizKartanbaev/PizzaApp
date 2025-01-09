package whz.pti.eva.domain;

import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Entity
@FieldDefaults(level =  AccessLevel.PRIVATE)
public class DeliveryAddress extends BaseEntity<Long> {
	
	String street;
	String houseNumber;
	String town;
	String postalCode;
	
    public DeliveryAddress() {}

    public DeliveryAddress(String street, String houseNumber, String town, String postalCode) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.town = town;
        this.postalCode = postalCode;
    }
    
    public Long getId() {
	    return id;
	}
    
	public String getStreet() {
	    return street;
	}

	public void setStreet(String street) {
	    this.street = street;
	}

	public String getHouseNumber() {
	    return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
	    this.houseNumber = houseNumber;
	}

	public String getTown() {
	    return town;
	}

	public void setTown(String town) {
	    this.town = town;
	}

	public String getPostalCode() {
	    return postalCode;
	}

	public void setPostalCode(String postalCode) {
	    this.postalCode = postalCode;
	}

}
