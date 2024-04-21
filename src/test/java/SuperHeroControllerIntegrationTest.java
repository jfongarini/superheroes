import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindata.superheroes.Application;
import com.mindata.superheroes.dto.SuperHeroDto;
import com.mindata.superheroes.model.SuperHero;
import com.mindata.superheroes.repository.SuperHeroRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SuperHeroControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SuperHeroRepository superHeroRepository;

    @BeforeAll
    public void setUp() {
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
        SuperHero spiderman = SuperHero.builder()
                .id(3L)
                .name("Spiderman")
                .description("Friendly neighborhood spider")
                .superPowers(Arrays.asList("Spider sense", "Super strength"))
                .vulnerabilities(List.of("Guilty over uncle Ben's death"))
                .build();
        SuperHero iroman = SuperHero.builder()
                .id(4L)
                .name("Ironman")
                .description("Genius billionaire")
                .superPowers(Arrays.asList("Power armor suit", "Intelligence"))
                .vulnerabilities(List.of("Hearth condition"))
                .build();
        superHeroRepository.saveAll(List.of(batman,superman,spiderman,iroman));
    }

    @Test
    public void getAllSuperHeroesIntegrationTest() throws Exception {
        mockMvc.perform(get("/superheroes/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Batman"))
                .andExpect(jsonPath("$[0].description").value("The Dark Knight"))
                .andExpect(jsonPath("$[0].superPowers[0]").value("Intelligence"))
                .andExpect(jsonPath("$[0].superPowers[1]").value("Combat Skills"))
                .andExpect(jsonPath("$[0].vulnerabilities[0]").value("No superpowers"))
                .andExpect(jsonPath("$[1].name").value("Superman"))
                .andExpect(jsonPath("$[1].description").value("The Man of Steel"))
                .andExpect(jsonPath("$[1].superPowers[0]").value("Flight"))
                .andExpect(jsonPath("$[1].superPowers[1]").value("Super strength"))
                .andExpect(jsonPath("$[1].vulnerabilities[0]").value("Kryptonite"));
    }
    @Test
    public void getAllByNameSuperHeroesIntegrationTest() throws Exception {
        mockMvc.perform(get("/superheroes/search")
                        .param("name", "man")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Batman"))
                .andExpect(jsonPath("$[0].description").value("The Dark Knight"))
                .andExpect(jsonPath("$[0].superPowers[0]").value("Intelligence"))
                .andExpect(jsonPath("$[0].superPowers[1]").value("Combat Skills"))
                .andExpect(jsonPath("$[0].vulnerabilities[0]").value("No superpowers"))
                .andExpect(jsonPath("$[1].name").value("Superman"))
                .andExpect(jsonPath("$[1].description").value("The Man of Steel"))
                .andExpect(jsonPath("$[1].superPowers[0]").value("Flight"))
                .andExpect(jsonPath("$[1].superPowers[1]").value("Super strength"))
                .andExpect(jsonPath("$[1].vulnerabilities[0]").value("Kryptonite"));
    }

    @Test
    public void getByIdSuperHeroIntegrationTest() throws Exception {
        mockMvc.perform(get("/superheroes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Batman"))
                .andExpect(jsonPath("$.description").value("The Dark Knight"))
                .andExpect(jsonPath("$.superPowers[0]").value("Intelligence"))
                .andExpect(jsonPath("$.superPowers[1]").value("Combat Skills"))
                .andExpect(jsonPath("$.vulnerabilities[0]").value("No superpowers"));
    }

    @Test
    public void createSuperHeroIntegrationTest() throws Exception {
        SuperHeroDto superman = SuperHeroDto.builder()
                .id(5L)
                .name("Superman")
                .description("The Man of Steel")
                .superPowers(Arrays.asList("Flight", "Super strength"))
                .vulnerabilities(List.of("Kryptonite"))
                .build();

        mockMvc.perform(post("/superheroes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(superman)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Superman"))
                .andExpect(jsonPath("$.description").value("The Man of Steel"))
                .andExpect(jsonPath("$.superPowers[0]").value("Flight"))
                .andExpect(jsonPath("$.superPowers[1]").value("Super strength"))
                .andExpect(jsonPath("$.vulnerabilities[0]").value("Kryptonite"));
    }

    @Test
    public void updateSuperHeroIntegrationTest() throws Exception {
        Long superHeroId = 3L;
        SuperHeroDto batman = SuperHeroDto.builder()
                .id(superHeroId)
                .name("Spiderman")
                .description("Friendly neighborhood spider")
                .superPowers(Arrays.asList("Spider sense", "Super strength"))
                .vulnerabilities(List.of("Guilty over uncle Ben's death","Limited web fluid"))
                .build();

        mockMvc.perform(put("/superheroes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(batman)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Spiderman"))
                .andExpect(jsonPath("$.description").value("Friendly neighborhood spider"))
                .andExpect(jsonPath("$.superPowers[0]").value("Spider sense"))
                .andExpect(jsonPath("$.superPowers[1]").value("Super strength"))
                .andExpect(jsonPath("$.vulnerabilities[0]").value("Guilty over uncle Ben's death"))
                .andExpect(jsonPath("$.vulnerabilities[1]").value("Limited web fluid"));
    }

    @Test
    public void deleteSuperHeroIntegrationTest() throws Exception {
        mockMvc.perform(delete("/superheroes/{id}", 4L))
                .andExpect(status().isOk());
    }

    @Test
    public void getByIdSuperHeroNotFoundIntegrationTest() throws Exception {
        mockMvc.perform(get("/superheroes/{id}", 100L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("SuperHero not found"));
    }

    @Test
    public void createSuperHeroEmptyNameIntegrationTest() throws Exception {
        SuperHeroDto superHeroDto = SuperHeroDto.builder()
                .id(6L)
                .description("The Man of Steel")
                .superPowers(Arrays.asList("Flight", "Super strength"))
                .vulnerabilities(List.of("Kryptonite"))
                .build();

        mockMvc.perform(post("/superheroes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(superHeroDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Superhero name cannot be null or empty."));
    }

    @Test
    public void createSuperHeroEmptyDescriptionIntegrationTest() throws Exception {
        SuperHeroDto superHeroDto = SuperHeroDto.builder()
                .id(6L)
                .name("Superman")
                .superPowers(Arrays.asList("Flight", "Super strength"))
                .vulnerabilities(List.of("Kryptonite"))
                .build();

        mockMvc.perform(post("/superheroes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(superHeroDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("The superhero description cannot be null or empty."));
    }

    @Test
    public void createSuperHeroEmptySuperPowersIntegrationTest() throws Exception {
        SuperHeroDto superHeroDto = SuperHeroDto.builder()
                .id(6L)
                .name("Superman")
                .description("The Man of Steel")
                .superPowers(new ArrayList<>())
                .vulnerabilities(List.of("Kryptonite"))
                .build();

        mockMvc.perform(post("/superheroes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(superHeroDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("The list of superhero superpowers cannot be null or empty."));
    }

    @Test
    public void createSuperHeroEmptyVulnerabilitiesIntegrationTest() throws Exception {
        SuperHeroDto superHeroDto = SuperHeroDto.builder()
                .id(6L)
                .name("Superman")
                .description("The Man of Steel")
                .superPowers(Arrays.asList("Flight", "Super strength"))
                .vulnerabilities(new ArrayList<>())
                .build();

        mockMvc.perform(post("/superheroes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(superHeroDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("The list of superhero vulnerabilities cannot be null or empty."));
    }
}
