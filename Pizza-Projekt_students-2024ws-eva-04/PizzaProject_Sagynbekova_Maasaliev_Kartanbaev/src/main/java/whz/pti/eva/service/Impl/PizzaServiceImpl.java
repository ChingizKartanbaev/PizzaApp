package whz.pti.eva.service.Impl;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import whz.pti.eva.domain.Pizza;
import whz.pti.eva.repository.PizzaRepository;
import whz.pti.eva.service.PizzaService;

import java.util.List;
import java.util.Optional;

@Service
public class PizzaServiceImpl implements PizzaService {

	@Autowired
    private PizzaRepository pizzaRepository;

    @Override
    public Pizza save(Pizza pizza) {
        return pizzaRepository.save(pizza);
    }

    @Override
    public List<Pizza> getAll() {
        return pizzaRepository.findAll();
    }

    @Override
    public Pizza getById(Long id) {
        Optional<Pizza> pizza = pizzaRepository.findById(id);
        return pizza.orElse(null);
    }
    
    @Override
    public Pizza update(Long id, Pizza pizzaDetails) {
        Optional<Pizza> optionalPizza = pizzaRepository.findById(id);
        if (optionalPizza.isPresent()) {
            Pizza existingPizza = optionalPizza.get();
            existingPizza.setName(pizzaDetails.getName());
            existingPizza.setPriceLarge(pizzaDetails.getPriceLarge());
            existingPizza.setPriceMedium(pizzaDetails.getPriceMedium());
            existingPizza.setPriceSmall(pizzaDetails.getPriceSmall());
            return pizzaRepository.save(existingPizza);
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteById(Long id) {
        if (pizzaRepository.existsById(id)) {
            pizzaRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

}
