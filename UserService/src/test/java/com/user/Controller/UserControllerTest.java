package com.user.Controller;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Optional;
import org.springframework.http.MediaType;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.user.Exception.UserExistsException;
import com.user.Model.User;
import com.user.service.UserService;
import static org.mockito.Mockito.any;
@RunWith(SpringRunner.class)
@WebMvcTest
class UserControllerTest {
	@Autowired
    private MockMvc mockMvc;
    @MockBean
    private User user;
    @MockBean
    UserService userService;
    @InjectMocks
    UserController userController;
    @BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        user = new User();
        user.setUserId("jude");
        user.setUserPassword("jude07");
	}
	@AfterEach
	void tearDown() throws Exception {
	}
	@Test
	void testRegister() throws Exception {
		//fail("Not yet implemented");
		when(userService.registerUser(user)).thenReturn(user);
        mockMvc.perform(post("/registerUser")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
                .andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
	}
	
	@Test
    public void registerUserFailure() throws Exception {
        when(userService.registerUser(any())).thenThrow(UserExistsException.class);
        mockMvc.perform(post("/registerUser")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
                .andExpect(status().isConflict()).andDo(MockMvcResultHandlers.print());
    }
	public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	@Test
    public void givenValidPostRequestShouldAuthenticateTheUser() throws Exception {
		User login= new User("jude","jude07");
        ObjectWriter ow= new ObjectMapper().writer();
        String loginjsonString = ow.writeValueAsString(login);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginjsonString))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
	//@Test
	void testLogin() {
		fail("Not yet implemented");
	}
	//@Test
	void testLogout() {
		fail("Not yet implemented");
	}
}