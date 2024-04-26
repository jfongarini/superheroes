import static org.junit.jupiter.api.Assertions.*;

import com.mindata.superheroes.Application;
import com.mindata.superheroes.model.SuperHero;
import com.mindata.superheroes.repository.SuperHeroRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = Application.class)
public class SuperHeroRepositoryTest {

    @Autowired
    private SuperHeroRepository superHeroRepository;

    @Test
    public void findAllByNameTest() {
        SuperHero flash = SuperHero.builder()
                .name("Flash")
                .description("The fastest man alive")
                .build();
        superHeroRepository.save(flash);
        List<SuperHero> superHeroes = superHeroRepository.findAllByName("ash");

        assertFalse(superHeroes.isEmpty());
        assertEquals("Flash", superHeroes.getFirst().getName());
        assertEquals("The fastest man alive", superHeroes.getFirst().getDescription());
    }

    @Test
    public void existsByName(){
        SuperHero aquaman = SuperHero.builder()
                .name("Aquaman")
                .description("King of Atlantis")
                .build();
        superHeroRepository.save(aquaman);
        boolean exist = superHeroRepository.existsByName("Aquaman");

        assertTrue(exist);
    }

    @Test
    public void existsByNameAndIdNot(){
        SuperHero aquawoman = SuperHero.builder()
                .name("Aquawoman")
                .description("Queen of Atlantis")
                .build();
        superHeroRepository.save(aquawoman);
        boolean exist = superHeroRepository.existsByNameAndIdNot("Aquawoman",1L);

        assertTrue(exist);
    }
}
