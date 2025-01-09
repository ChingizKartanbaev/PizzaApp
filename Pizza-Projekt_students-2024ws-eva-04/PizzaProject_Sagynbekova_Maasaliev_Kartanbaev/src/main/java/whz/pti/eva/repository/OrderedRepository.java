package whz.pti.eva.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import whz.pti.eva.domain.Ordered;

@Repository
public interface OrderedRepository extends JpaRepository<Ordered, Long>{

}
