import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.mindata.superheroes.dto.SuperHeroRequestDto;
import com.mindata.superheroes.exception.InvalidParameterException;
import com.mindata.superheroes.exception.NotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import com.mindata.superheroes.model.SuperHero;
import com.mindata.superheroes.repository.SuperHeroRepository;
import com.mindata.superheroes.service.SuperHeroService;
import org.springframework.boot.test.mock.mockito.MockBean;

public class SuperHeroServiceTest {

    private static SuperHeroService superHeroService;

    @MockBean
    static SuperHeroRepository superHeroRepository;

    @BeforeAll
    public static void setUp() {
        superHeroRepository = mock(SuperHeroRepository.class);
        superHeroService = new SuperHeroService(superHeroRepository);
    }

    @Test
    public void getAllSuperHeroesTest() {
        SuperHero batman = SuperHero.builder()
                .id(1L)
                .name("Batman")
                .description("The Dark Knight")
                .superPowers(Arrays.asList("Intelligence", "Combat Skills"))
                .vulnerabilities(List.of("No superpowers"))
                .build();
        SuperHero superman = SuperHero.builder()
                .id(2L)
                .name("Superman")
                .description("The Man of Steel")
                .superPowers(Arrays.asList("Flight", "Super strength"))
                .vulnerabilities(List.of("Kryptonite"))
                .build();
        List<SuperHero> superHeroes = List.of(batman,superman);
        when(superHeroRepository.findAll()).thenReturn(superHeroes);

        List<SuperHero> result = superHeroService.getAll();
        assertEquals(2, result.size());
        SuperHero first = result.getFirst();
        assertEquals("Batman", first.getName());
        assertEquals("The Dark Knight", first.getDescription());
        assertEquals(2, first.getSuperPowers().size());
        assertEquals("Intelligence", first.getSuperPowers().get(0));
        assertEquals("Combat Skills", first.getSuperPowers().get(1));
        assertEquals(1, first.getVulnerabilities().size());
        assertEquals("No superpowers", first.getVulnerabilities().getFirst());
        SuperHero second = result.get(1);
        assertEquals("Superman", second.getName());
        assertEquals("The Man of Steel", second.getDescription());
        assertEquals(2, second.getSuperPowers().size());
        assertEquals("Flight", second.getSuperPowers().get(0));
        assertEquals("Super strength", second.getSuperPowers().get(1));
        assertEquals(1, second.getVulnerabilities().size());
        assertEquals("Kryptonite", second.getVulnerabilities().getFirst());
    }

    @Test
    public void getByIdSuperHeroTest() {
        Long id = 1L;
        SuperHero batman = SuperHero.builder()
                .id(id)
                .name("Batman")
                .description("The Dark Knight")
                .superPowers(Arrays.asList("Intelligence", "Combat Skills"))
                .vulnerabilities(List.of("No superpowers"))
                .build();
        when(superHeroRepository.findById(anyLong())).thenReturn(Optional.of(batman));

        SuperHero result = superHeroService.getById(id);
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Batman", result.getName());
        assertEquals("The Dark Knight", result.getDescription());
        assertEquals(2, result.getSuperPowers().size());
        assertEquals("Intelligence", result.getSuperPowers().get(0));
        assertEquals("Combat Skills", result.getSuperPowers().get(1));
        assertEquals(1, result.getVulnerabilities().size());
        assertEquals("No superpowers", result.getVulnerabilities().getFirst());
    }

    @Test
    public void getByIdSuperHeroNotFoundTest() {
        when(superHeroRepository.findById(anyLong())).thenReturn(Optional.empty());
        NotFoundException exception = assertThrows(NotFoundException.class, () -> superHeroService.getById(1L));
        assertNotNull(exception);
        assertEquals("SuperHero not found", exception.getMessage());
    }

    @Test
    public void getAllByNameSuperHeroesTest() {
        SuperHero batman = SuperHero.builder()
                .id(1L)
                .name("Batman")
                .description("The Dark Knight")
                .superPowers(Arrays.asList("Intelligence", "Combat Skills"))
                .vulnerabilities(List.of("No superpowers"))
                .build();
        SuperHero superman = SuperHero.builder()
                .id(2L)
                .name("Superman")
                .description("The Man of Steel")
                .superPowers(Arrays.asList("Flight", "Super strength"))
                .vulnerabilities(List.of("Kryptonite"))
                .build();
        List<SuperHero> superHeroes = List.of(batman,superman);
        when(superHeroRepository.findAllByName(anyString())).thenReturn(superHeroes);

        List<SuperHero> result = superHeroService.getAllByName("man");
        assertEquals(2, result.size());
        SuperHero first = result.getFirst();
        assertEquals("Batman", first.getName());
        assertEquals("The Dark Knight", first.getDescription());
        assertEquals(2, first.getSuperPowers().size());
        assertEquals("Intelligence", first.getSuperPowers().get(0));
        assertEquals("Combat Skills", first.getSuperPowers().get(1));
        assertEquals(1, first.getVulnerabilities().size());
        assertEquals("No superpowers", first.getVulnerabilities().getFirst());
        SuperHero second = result.get(1);
        assertEquals("Superman", second.getName());
        assertEquals("The Man of Steel", second.getDescription());
        assertEquals(2, second.getSuperPowers().size());
        assertEquals("Flight", second.getSuperPowers().get(0));
        assertEquals("Super strength", second.getSuperPowers().get(1));
        assertEquals(1, second.getVulnerabilities().size());
        assertEquals("Kryptonite", second.getVulnerabilities().getFirst());
    }

    @Test
    public void createSuperHeroTest() {
        SuperHeroRequestDto newSuperHeroDto = SuperHeroRequestDto.builder()
                                    .name("Superman")
                                    .description("The Man of Steel")
                                    .superPowers(Arrays.asList("Flight", "Super strength"))
                                    .vulnerabilities(List.of("Kryptonite"))
                                    .build();
        SuperHero newSuperHero = SuperHero.builder()
                                    .id(1L)
                                    .name("Superman")
                                    .description("The Man of Steel")
                                    .superPowers(Arrays.asList("Flight", "Super strength"))
                                    .vulnerabilities(List.of("Kryptonite"))
                                    .build();
        when(superHeroRepository.existsByName(anyString())).thenReturn(false);
        when(superHeroRepository.save(any(SuperHero.class))).thenReturn(newSuperHero);

        SuperHero result = superHeroService.create(newSuperHeroDto);
        assertNotNull(result);
        assertEquals("Superman", result.getName());
        assertEquals("The Man of Steel", result.getDescription());
        assertEquals(2, result.getSuperPowers().size());
        assertEquals("Flight", result.getSuperPowers().get(0));
        assertEquals("Super strength", result.getSuperPowers().get(1));
        assertEquals(1, result.getVulnerabilities().size());
        assertEquals("Kryptonite", result.getVulnerabilities().getFirst());
    }

    @Test
    public void createSuperHeroEmptyNameTest() {
        SuperHeroRequestDto newSuperHeroDto = SuperHeroRequestDto.builder()
                .description("The Man of Steel")
                .superPowers(Arrays.asList("Flight", "Super strength"))
                .vulnerabilities(List.of("Kryptonite"))
                .build();

        InvalidParameterException exception = assertThrows(InvalidParameterException.class, () -> superHeroService.create(newSuperHeroDto));
        assertEquals("Superhero name cannot be null or empty.", exception.getMessage());
    }

    @Test
    public void createSuperHeroRepeatedNameTest() {
        SuperHeroRequestDto newSuperHeroDto = SuperHeroRequestDto.builder()
                .name("Superman")
                .description("The Man of Steel")
                .superPowers(Arrays.asList("Flight", "Super strength"))
                .vulnerabilities(List.of("Kryptonite"))
                .build();
        when(superHeroRepository.existsByName(anyString())).thenReturn(true);

        InvalidParameterException exception = assertThrows(InvalidParameterException.class, () -> superHeroService.create(newSuperHeroDto));
        assertEquals("Superhero with the same name already exists.", exception.getMessage());
    }

    @Test
    public void createSuperHeroEmptyDescriptionTest() {
        SuperHeroRequestDto newSuperHeroDto = SuperHeroRequestDto.builder()
                .name("Superman")
                .superPowers(Arrays.asList("Flight", "Super strength"))
                .vulnerabilities(List.of("Kryptonite"))
                .build();

        InvalidParameterException exception = assertThrows(InvalidParameterException.class, () -> superHeroService.create(newSuperHeroDto));
        assertEquals("The superhero description cannot be null or empty.", exception.getMessage());
    }

    @Test
    public void createSuperHeroEmptySuperPowersTest() {
        SuperHeroRequestDto newSuperHeroDto = SuperHeroRequestDto.builder()
                .name("Superman")
                .description("The Man of Steel")
                .superPowers(List.of())
                .vulnerabilities(List.of("Kryptonite"))
                .build();

        InvalidParameterException exception = assertThrows(InvalidParameterException.class, () -> superHeroService.create(newSuperHeroDto));
        assertEquals("The list of superhero superpowers cannot be null or empty.", exception.getMessage());
    }

    @Test
    public void createSuperHeroEmptyVulnerabilitiesTest() {
        SuperHeroRequestDto newSuperHeroDto = SuperHeroRequestDto.builder()
                .name("Superman")
                .description("The Man of Steel")
                .superPowers(Arrays.asList("Flight", "Super strength"))
                .vulnerabilities(List.of())
                .build();

        InvalidParameterException exception = assertThrows(InvalidParameterException.class, () -> superHeroService.create(newSuperHeroDto));
        assertEquals("The list of superhero vulnerabilities cannot be null or empty.", exception.getMessage());
    }

    @Test
    public void createSuperHeroRepeatSuperPowersTest() {
        SuperHeroRequestDto newSuperHeroDto = SuperHeroRequestDto.builder()
                .name("Superman")
                .description("The Man of Steel")
                .superPowers(Arrays.asList("Flight", "Flight"))
                .vulnerabilities(List.of("Kryptonite"))
                .build();

        InvalidParameterException exception = assertThrows(InvalidParameterException.class, () -> superHeroService.create(newSuperHeroDto));
        assertEquals("The list of superhero superpowers cannot contain duplicate elements.", exception.getMessage());
    }

    @Test
    public void createSuperHeroRepeatVulnerabilitiesTest() {
        SuperHeroRequestDto newSuperHeroDto = SuperHeroRequestDto.builder()
                .name("Superman")
                .description("The Man of Steel")
                .superPowers(Arrays.asList("Flight", "Super strength"))
                .vulnerabilities(List.of("Kryptonite","Kryptonite"))
                .build();

        InvalidParameterException exception = assertThrows(InvalidParameterException.class, () -> superHeroService.create(newSuperHeroDto));
        assertEquals("The list of superhero vulnerabilities cannot contain duplicate elements.", exception.getMessage());
    }

    @Test
    public void updateSuperHeroTest() {
        Long id = 1L;
        SuperHeroRequestDto updatedSuperHeroDto = SuperHeroRequestDto.builder()
                .name("The Superman")
                .description("The Man of Steel")
                .superPowers(Arrays.asList("Flight", "Super strength"))
                .vulnerabilities(List.of("Kryptonite"))
                .build();
        SuperHero existingHero = new SuperHero();
        existingHero.setId(id);
        when(superHeroRepository.existsByNameAndIdNot(anyString(),anyLong())).thenReturn(false);
        when(superHeroRepository.findById(anyLong())).thenReturn(Optional.of(existingHero));

        SuperHero result = superHeroService.update(updatedSuperHeroDto,id);
        assertNotNull(result);
        assertEquals("The Superman", result.getName());
        assertEquals("The Man of Steel", result.getDescription());
        assertEquals(2, result.getSuperPowers().size());
        assertEquals("Flight", result.getSuperPowers().get(0));
        assertEquals("Super strength", result.getSuperPowers().get(1));
        assertEquals(1, result.getVulnerabilities().size());
        assertEquals("Kryptonite", result.getVulnerabilities().getFirst());
    }

    @Test
    public void updateSuperHeroRepeatedNameTest() {
        SuperHeroRequestDto updatedSuperHeroDto = SuperHeroRequestDto.builder()
                .name("Superman")
                .description("The Man of Steel")
                .superPowers(Arrays.asList("Flight", "Super strength"))
                .vulnerabilities(List.of("Kryptonite"))
                .build();
        when(superHeroRepository.existsByNameAndIdNot(anyString(),anyLong())).thenReturn(true);

        InvalidParameterException exception = assertThrows(InvalidParameterException.class, () -> superHeroService.update(updatedSuperHeroDto,1L));
        assertEquals("Another superhero with the same name already exists.", exception.getMessage());
    }

    @Test
    public void deleteSuperHeroTest() {
        Long id = 1L;
        SuperHero existingHero = new SuperHero();
        existingHero.setId(id);
        when(superHeroRepository.findById(anyLong())).thenReturn(Optional.of(existingHero));

        superHeroService.delete(id);
        verify(superHeroRepository, times(1)).delete(existingHero);
    }
}
