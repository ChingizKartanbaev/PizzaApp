package whz.pti.eva.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import whz.pti.eva.domain.enums.Role;

@Entity
@FieldDefaults(level =  AccessLevel.PRIVATE)
public class Customer extends BaseEntity<Long> {

	String firstName;
	String lastName;
	String loginName;
	String passwordHash;
    @Enumerated(EnumType.STRING)
    private Role role;
	
    @OneToMany
    @JoinColumn(name = "address_id")
    DeliveryAddress deliveryAddress;
	
    public Customer() {}

    public Customer(Long id, String firstName, String lastName, DeliveryAddress addresses) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.deliveryAddress = addresses;
    }
    
    public Long getId() {
    	return id;
    }
    
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

	public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

    public DeliveryAddress getAddresses() {
        return deliveryAddress;
    }

    public void setAddresses(DeliveryAddress addresses) {
        this.deliveryAddress = addresses;
    }


}