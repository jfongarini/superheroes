import com.mindata.superheroes.dto.SuperHeroDto;
import com.mindata.superheroes.model.SuperHero;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SuperHeroDtoTest {

    @Test
    public void convertListOfSuperheroesToSuperHeroesDtosTest() {
        SuperHero superman = SuperHero.builder()
                .name("Superman")
                .description("Man of Steel")
                .superPowers(Arrays.asList("Flight", "Super strength"))
                .vulnerabilities(List.of("Kryptonite"))
                .build();
        SuperHero batman = SuperHero.builder()
                .name("Batman")
                .description("The Dark Knight")
                .superPowers(Arrays.asList("Money", "Technology"))
                .vulnerabilities(List.of("Daylight"))
                .build();
        List<SuperHero> superHeroes = List.of(superman, batman);

        List<SuperHeroDto> superHeroDtos = SuperHeroDto.toDtos(superHeroes);

        assertEquals(2, superHeroDtos.size());
        SuperHeroDto supermanDto = superHeroDtos.getFirst();
        assertEquals("Superman", supermanDto.getName());
        assertEquals("Man of Steel", supermanDto.getDescription());
        assertEquals(2, supermanDto.getSuperPowers().size());
        assertEquals("Flight", supermanDto.getSuperPowers().get(0));
        assertEquals("Super strength", supermanDto.getSuperPowers().get(1));
        assertEquals(1, supermanDto.getVulnerabilities().size());
        assertEquals("Kryptonite", supermanDto.getVulnerabilities().getFirst());
        SuperHeroDto batmanDto = superHeroDtos.get(1);
        assertEquals("Batman", batmanDto.getName());
        assertEquals("The Dark Knight", batmanDto.getDescription());
        assertEquals(2, batmanDto.getSuperPowers().size());
        assertEquals("Money", batmanDto.getSuperPowers().get(0));
        assertEquals("Technology", batmanDto.getSuperPowers().get(1));
        assertEquals(1, batmanDto.getVulnerabilities().size());
        assertEquals("Daylight", batmanDto.getVulnerabilities().getFirst());
    }

    @Test
    public void createASuperHeroDtoFromASuperheroTest() {
        SuperHero superman = SuperHero.builder()
                .name("Superman")
                .description("Man of Steel")
                .superPowers(Arrays.asList("Flight", "Super strength"))
                .vulnerabilities(List.of("Kryptonite"))
                .build();

        SuperHeroDto superHeroDto = SuperHeroDto.toDto(superman);

        assertEquals("Superman", superHeroDto.getName());
        assertEquals("Man of Steel", superHeroDto.getDescription());
        assertEquals(2, superHeroDto.getSuperPowers().size());
        assertEquals("Flight", superHeroDto.getSuperPowers().get(0));
        assertEquals("Super strength", superHeroDto.getSuperPowers().get(1));
        assertEquals(1, superHeroDto.getVulnerabilities().size());
        assertEquals("Kryptonite", superHeroDto.getVulnerabilities().getFirst());
    }

    @Test
    public void createASuperheroFromDtoTest() {
        SuperHeroDto superHeroDto = SuperHeroDto.builder()
                .name("Superman")
                .description("Man of Steel")
                .superPowers(Arrays.asList("Flight", "Super strength"))
                .vulnerabilities(List.of("Kryptonite"))
                .build();

        SuperHero superHero = SuperHeroDto.toSuperHero(superHeroDto);

        assertEquals("Superman", superHero.getName());
        assertEquals("Man of Steel", superHero.getDescription());
        assertEquals(2, superHero.getSuperPowers().size());
        assertEquals("Flight", superHero.getSuperPowers().get(0));
        assertEquals("Super strength", superHero.getSuperPowers().get(1));
        assertEquals(1, superHero.getVulnerabilities().size());
        assertEquals("Kryptonite", superHero.getVulnerabilities().getFirst());
    }

}
