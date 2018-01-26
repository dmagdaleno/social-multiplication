package microservices.book.multiplication.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.domain.User;
import microservices.book.multiplication.service.MultiplicationService;

@RunWith(SpringRunner.class)
@WebMvcTest(MultiplicationResultAttemptController.class)
public class MultiplicationResultAttemptControllerTest {
	
	@MockBean
	private MultiplicationService multiplicationService;
	
	@Autowired
	private MockMvc mvc;
	
	private JacksonTester<MultiplicationResultAttempt> jsonResult;
	private JacksonTester<MultiplicationResultAttempt> jsonResponse;
	private JacksonTester<List<MultiplicationResultAttempt>> jsonResponseTop5;
	
	@Before
	public void setup() {
		JacksonTester.initFields(this, new ObjectMapper());
	}
	
	@Test
	public void postResultReturnCorrect() throws Exception {
		genericParameterizedTest(true);
	}
	
	@Test
	public void postResultReturnNotCorrect() throws Exception {
		genericParameterizedTest(false);
	}

	private void genericParameterizedTest(final boolean correct) throws Exception {
		// given
		User user = new User("john");
		Multiplication multiplication = new Multiplication(50, 70);
		MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3500, false);
		MultiplicationResultAttempt returnedAttempt = new MultiplicationResultAttempt(user, multiplication, 3500, correct);
		given(multiplicationService.checkAttempt(any(MultiplicationResultAttempt.class))).willReturn(returnedAttempt);
		
		// when
		MockHttpServletResponse response = mvc.perform(post("/results").contentType(MediaType.APPLICATION_JSON)
				.content(jsonResult.write(attempt).getJson())).andReturn().getResponse();
		
		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonResponse.write(returnedAttempt).getJson());
	}
	
	@Test
	public void getStatsTest() throws Exception {
		// given
		User user = new User("john");
		Multiplication multiplication = new Multiplication(50, 70);
		MultiplicationResultAttempt atpt1 = new MultiplicationResultAttempt(user, multiplication, 3001, false);
		MultiplicationResultAttempt atpt2 = new MultiplicationResultAttempt(user, multiplication, 3010, false);
		MultiplicationResultAttempt atpt3 = new MultiplicationResultAttempt(user, multiplication, 3000, false);
		MultiplicationResultAttempt atpt4 = new MultiplicationResultAttempt(user, multiplication, 2999, false);
		MultiplicationResultAttempt atpt5 = new MultiplicationResultAttempt(user, multiplication, 2000, false);
		List<MultiplicationResultAttempt> latestAttempts = Lists.newArrayList(atpt1, atpt2, atpt3, atpt4, atpt5);
		given(multiplicationService.getStatsForUser(any(String.class))).willReturn(latestAttempts);
		
		// when
		MockHttpServletResponse response = mvc.perform(get("/results/john")
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
		
		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonResponseTop5.write(latestAttempts).getJson());
	}
}