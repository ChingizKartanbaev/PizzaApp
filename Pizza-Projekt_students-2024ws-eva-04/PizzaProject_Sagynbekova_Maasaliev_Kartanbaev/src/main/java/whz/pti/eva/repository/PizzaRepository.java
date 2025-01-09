package whz.pti.eva.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import whz.pti.eva.domain.Pizza;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Long> {

}
