import com.mindata.superheroes.dto.SuperHeroRequestDto;
import com.mindata.superheroes.model.SuperHero;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SuperHeroRequestDtoTest {

    @Test
    public void createASuperheroFromDtoTest() {
        SuperHeroRequestDto superHeroRequestDto = SuperHeroRequestDto.builder()
                .name("Superman")
                .description("Man of Steel")
                .superPowers(Arrays.asList("Flight", "Super strength"))
                .vulnerabilities(List.of("Kryptonite"))
                .build();

        SuperHero superHero = SuperHeroRequestDto.toSuperHero(superHeroRequestDto);

        assertEquals("Superman", superHero.getName());
        assertEquals("Man of Steel", superHero.getDescription());
        assertEquals(2, superHero.getSuperPowers().size());
        assertEquals("Flight", superHero.getSuperPowers().get(0));
        assertEquals("Super strength", superHero.getSuperPowers().get(1));
        assertEquals(1, superHero.getVulnerabilities().size());
        assertEquals("Kryptonite", superHero.getVulnerabilities().getFirst());
    }
}
