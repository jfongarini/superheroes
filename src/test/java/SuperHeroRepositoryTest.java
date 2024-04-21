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
        SuperHero batman = SuperHero.builder()
                .name("Batman")
                .description("The Dark Knight")
                .build();
        superHeroRepository.save(batman);
        List<SuperHero> superHeroes = superHeroRepository.findAllByName("atm");

        assertFalse(superHeroes.isEmpty());
        assertEquals("Batman", superHeroes.getFirst().getName());
        assertEquals("The Dark Knight", superHeroes.getFirst().getDescription());
    }
}
