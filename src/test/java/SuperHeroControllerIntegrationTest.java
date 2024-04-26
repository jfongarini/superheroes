import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindata.superheroes.Application;
import com.mindata.superheroes.dto.SuperHeroRequestDto;
import com.mindata.superheroes.model.SuperHero;
import com.mindata.superheroes.repository.SuperHeroRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
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
                .superPowers(Arrays.asList("Combat Skills","Intelligence"))
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
                .andExpect(jsonPath("$[0].superPowers[0]").value("Combat Skills"))
                .andExpect(jsonPath("$[0].superPowers[1]").value("Intelligence"))
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
                .andExpect(jsonPath("$[0].superPowers[0]").value("Combat Skills"))
                .andExpect(jsonPath("$[0].superPowers[1]").value("Intelligence"))
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
                .andExpect(jsonPath("$.superPowers[0]").value("Combat Skills"))
                .andExpect(jsonPath("$.superPowers[1]").value("Intelligence"))
                .andExpect(jsonPath("$.vulnerabilities[0]").value("No superpowers"));
    }

    @Test
    public void createSuperHeroIntegrationTest() throws Exception {
        SuperHeroRequestDto superman = SuperHeroRequestDto.builder()
                .name("Wonder Woman")
                .description("Amazon princess and warrior")
                .superPowers(Arrays.asList("Super strength", "Flight", "Lasso of Truth"))
                .vulnerabilities(List.of("Vulnerability to piercing weapons"))
                .build();

        mockMvc.perform(post("/superheroes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(superman)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Wonder Woman"))
                .andExpect(jsonPath("$.description").value("Amazon princess and warrior"))
                .andExpect(jsonPath("$.superPowers[0]").value("Super strength"))
                .andExpect(jsonPath("$.superPowers[1]").value("Flight"))
                .andExpect(jsonPath("$.superPowers[2]").value("Lasso of Truth"))
                .andExpect(jsonPath("$.vulnerabilities[0]").value("Vulnerability to piercing weapons"));
    }

    @Test
    public void updateSuperHeroIntegrationTest() throws Exception {
        SuperHeroRequestDto batman = SuperHeroRequestDto.builder()
                .name("Spiderman")
                .description("Friendly neighborhood spider")
                .superPowers(Arrays.asList("Spider sense", "Super strength"))
                .vulnerabilities(List.of("Guilty over uncle Ben's death","Limited web fluid"))
                .build();

        mockMvc.perform(put("/superheroes/3")
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
                .andExpect(status().isOk())
                .andExpect(content().string("Superhero successfully deleted."));
    }

    @Test
    public void getByIdSuperHeroNotFoundIntegrationTest() throws Exception {
        mockMvc.perform(get("/superheroes/{id}", 100L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("SuperHero not found"));
    }

    @Test
    public void createSuperHeroRepeatedNameIntegrationTest() throws Exception {
        SuperHeroRequestDto superHeroDto = SuperHeroRequestDto.builder()
                .name("Superman")
                .description("The Man of Steel")
                .superPowers(Arrays.asList("Flight", "Super strength"))
                .vulnerabilities(List.of("Kryptonite"))
                .build();

        mockMvc.perform(post("/superheroes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(superHeroDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Superhero with the same name already exists."));
    }

    @Test
    public void createSuperHeroEmptyNameIntegrationTest() throws Exception {
        SuperHeroRequestDto superHeroDto = SuperHeroRequestDto.builder()
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
        SuperHeroRequestDto superHeroDto = SuperHeroRequestDto.builder()
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
        SuperHeroRequestDto superHeroDto = SuperHeroRequestDto.builder()
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
        SuperHeroRequestDto superHeroDto = SuperHeroRequestDto.builder()
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

    @Test
    public void createSuperHeroRepeatSuperPowersIntegrationTest() throws Exception {
        SuperHeroRequestDto superHeroDto = SuperHeroRequestDto.builder()
                .name("Superman")
                .description("The Man of Steel")
                .superPowers(Arrays.asList("Flight", "Flight"))
                .vulnerabilities(List.of("Kryptonite"))
                .build();

        mockMvc.perform(post("/superheroes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(superHeroDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("The list of superhero superpowers cannot contain duplicate elements."));
    }

    @Test
    public void createSuperHeroRepeatVulnerabilitiesIntegrationTest() throws Exception {
        SuperHeroRequestDto superHeroDto = SuperHeroRequestDto.builder()
                .name("Superman")
                .description("The Man of Steel")
                .superPowers(Arrays.asList("Flight"))
                .vulnerabilities(List.of("Kryptonite","Kryptonite"))
                .build();

        mockMvc.perform(post("/superheroes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(superHeroDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("The list of superhero vulnerabilities cannot contain duplicate elements."));
    }
}
