package cl.bohiggins.ms_academico.integration;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EstudianteIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void addEstudiante_ok() throws Exception {
		mockMvc.perform(post("/api/v1/addEstudiante")
				.contentType(APPLICATION_JSON)
				.content("{\"cursoId\":1,\"rut\":\"21827564-8\",\"nombres\":\"Cristobal\",\"apellidoPaterno\":\"Huinca\",\"apellidoMaterno\":\"Aravena\",\"email\":\"cristobal@colegio.cl\"}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.rut").value("21827564-8"));
	}

	@Test
	void estudianteById_noExiste() throws Exception {
		mockMvc.perform(get("/api/v1/estudianteByID/999"))
				.andExpect(status().isNotFound());
	}

	@Test
	void addEstudiante_luegoGet() throws Exception {
		String creado = mockMvc.perform(post("/api/v1/addEstudiante")
				.contentType(APPLICATION_JSON)
				.content("{\"cursoId\":1,\"rut\":\"19988776-5\",\"nombres\":\"Ana\",\"apellidoPaterno\":\"Lopez\",\"apellidoMaterno\":\"Diaz\",\"email\":\"ana@colegio.cl\"}"))
				.andExpect(status().isCreated())
				.andReturn()
				.getResponse()
				.getContentAsString();

		long id = new ObjectMapper().readTree(creado).get("id").asLong();

		mockMvc.perform(get("/api/v1/estudianteByID/" + id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.nombres").value("Ana"));
	}
}
