package whz.pti.eva.domain;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import whz.pti.eva.domain.enums.Role;

public class CurrentUser extends User{
	
	private Customer customer;
	
	public CurrentUser(Customer customer) {
		super(customer.getLoginName(), customer.getPasswordHash(),
			AuthorityUtils.createAuthorityList(customer.getRole().toString()));
		this.customer = customer;
	}

	
	public Customer getCustomer() {
		return customer;
	}
	
	public Long getId() {
		return customer.getId();
	}
	
	public String getLoginName() {
		return customer.getLoginName();
	}
	
	public Role getRole() {
		return customer.getRole();
	}
	
}