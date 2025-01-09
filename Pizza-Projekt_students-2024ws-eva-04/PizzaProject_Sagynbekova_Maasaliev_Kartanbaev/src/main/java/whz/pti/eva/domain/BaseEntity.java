package whz.pti.eva.domain;

import java.io.Serializable;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@MappedSuperclass
@FieldDefaults(level =  AccessLevel.PRIVATE)
public class BaseEntity<PK extends Serializable> {

	@Id
	@GeneratedValue
	PK id;
	
	public PK getId() {
		return id;
	}
	
	public void setId(PK id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		if (getId() != null) {
			return getId().hashCode();
		}
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(!(obj instanceof BaseEntity)) return false;
		BaseEntity other = (BaseEntity) obj;
		return this.getClass() != null && this.getId().equals(other.getId());
	}
	
}
