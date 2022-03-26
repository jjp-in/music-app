package com.user.Controller;

import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.user.Exception.UserExistsException;
import com.user.Exception.UserNotFoundException;
import com.user.Model.User;
import com.user.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class UserController {
	@Autowired
	UserService userService;

	ResponseEntity<?> response;
	String jwtToken;

	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	@PostMapping("/registerUser")
	public ResponseEntity<?> addUser(@RequestBody User user) {
		try {
			userService.registerUser(user);

			response = new ResponseEntity<String>("User Profile Created", HttpStatus.CREATED);

		} catch (UserExistsException e) {
			response = new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		}

		return response;
	}

	@PostMapping("/userLogin")
	public ResponseEntity<?> userLogin(@RequestBody User user, HttpSession session) {
		User userLogin;
		try {
			userLogin = userService.userLogin(user.getUserId(), user.getUserPassword());
			if (userLogin != null) {
				session.setAttribute("userId", user.getUserId());
				jwtToken = generateToken(user.getUserId());
				return new ResponseEntity<String>("User Logged in : " + user.getUserId() + " token :" + jwtToken,
						HttpStatus.OK);

			} else {
				return new ResponseEntity<String>("User Login Failed", HttpStatus.UNAUTHORIZED);
			}
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}

	}

	@GetMapping("/userLogout")
	public ResponseEntity<?> logout(HttpSession session) {
		if ((session != null) && (session.getAttribute("userId") != null)) {
			session.invalidate();
			response = new ResponseEntity<String>("Logged Out Successfully", HttpStatus.OK);
		} else {
			response = new ResponseEntity<String>("Failure", HttpStatus.BAD_REQUEST);
		}
		return response;
	}

	@PutMapping("/user/{id}")
	public ResponseEntity<?> updateUserPassword(@RequestBody User user, @PathVariable("id") String id) {

		User userUpdated = userService.updateUser(id, user);
		if (userUpdated != null) {
			response = new ResponseEntity<User>(userUpdated, HttpStatus.OK);

		} else {
			response = new ResponseEntity<String>("User Not Found", HttpStatus.NOT_FOUND);
		}
		return response;

	}

	private String generateToken(String userId) {
		String token = Jwts.builder().setSubject(userId).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 100000000))
				.signWith(SignatureAlgorithm.HS256, "secretKey").compact();

		System.out.println("generated Token " + token);

		return token;

	}

//	@GetMapping("/userList")
//	public ResponseEntity<?> getAllUsers() {
//		List<User> userList = userService.getAllUsers();
//
//		if (userList != null) {
//			response = new ResponseEntity<List<User>>(userList, HttpStatus.OK);
//
//		} else {
//			response = new ResponseEntity<String>("Failure", HttpStatus.BAD_REQUEST);
//
//		}
//		return response;
//
//	}

}
