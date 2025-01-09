package whz.pti.eva.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import whz.pti.eva.domain.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID>{
}
