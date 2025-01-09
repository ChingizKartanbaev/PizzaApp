package whz.pti.eva.domain.request;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerAddressRequest {

	String firstName;
	String lastName;
	String loginName;
	String passwordHash;
	String street;
	String houseNumber;
	String town;
	String postalCode;

	public String getFirstName() {
	    return firstName;
	}

	public void setFirstName(String firstName) {
	    this.firstName = firstName;
	}

	public String getLastName() {
	    return lastName;
	}

	public void setLastName(String lastName) {
	    this.lastName = lastName;
	}

	public String getLoginName() {
	    return loginName;
	}

	public void setLoginName(String loginName) {
	    this.loginName = loginName;
	}

	public String getPasswordHash() {
	    return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
	    this.passwordHash = passwordHash;
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
