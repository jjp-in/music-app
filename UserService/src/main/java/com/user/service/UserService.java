package com.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.user.Exception.UserExistsException;
import com.user.Exception.UserNotFoundException;
import com.user.Model.User;
import com.user.Repository.UserRepository;

@Service
public class UserService {

	UserRepository userRepository;

//	BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;

	}

	// need to check user already exists -user already exists exception
	public User registerUser(User user) throws UserExistsException {

		/*
		 * final String encodedPassword =
		 * passwordEncoder.encode(user.getUserPassword()); String encryptPassword =
		 * BCrypt.hashpw(user.getUserPassword(), BCrypt.gensalt());
		 * System.out.println("hashed pw is " + encryptPassword);
		 * user.setUserPassword(encryptPassword);
		 */
//		if (userRepository.existsById(user.getUserId())) {
//			throw new UserExistsException("User Already Exists Exception");
//		} else {
		
		User newUser = null;
		Optional<User> optUser = userRepository.findById(user.getUserId());
		if (optUser.isPresent()) {
			throw new UserExistsException("User Already Exists Exception");
		} else {
			encryptPassword(user);

			 newUser = userRepository.save(user);
			if (newUser == null) {
				throw new UserExistsException("User Already Exists Exception");

			}

		}

		return newUser;
	}

	// need to implement user not found if invalid credentials ? in controller :
	// service
	public User userLogin(String userId, String password) throws UserNotFoundException {
		User fetchUser = null;
		Optional<User> userExists = userRepository.findById(userId);
		if (userExists.isPresent()) {
			fetchUser = userExists.get();

			boolean checkPassword = BCrypt.checkpw(password, fetchUser.getUserPassword());
			System.out.println("is password same" + checkPassword);
			if (!checkPassword) { // if false return exception
				throw new UserNotFoundException("User Not Found ! Check Your Credentials");
			}
		}
		return fetchUser;
	}

	public User updateUser(String userId, User user) {
		User fetchUser = null;
		Optional<User> userExists = userRepository.findById(userId);
		if (userExists.isPresent()) {
			fetchUser = userExists.get();

			System.out.println("usertoUpdate" + fetchUser);
			if (fetchUser != null) {

				/*
				 * String encryptPassword = BCrypt.hashpw(user.getUserPassword(),
				 * BCrypt.gensalt()); user.setUserPassword(encryptPassword);
				 * System.out.println("hashed pw is " + encryptPassword);
				 */

				encryptPassword(user);
				userRepository.save(user);
				System.out.println("userUpdated" + user);

				return user;
			}

		}
		return fetchUser;
	}

	public String encryptPassword(User user) {
		String encryptPassword = BCrypt.hashpw(user.getUserPassword(), BCrypt.gensalt());
		user.setUserPassword(encryptPassword);
		System.out.println("hashed pw is " + encryptPassword);
		return encryptPassword;
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

//	public boolean findUserById(User user) {
//	Optional<User> userOptional = userRepository.findById(user.getUserId());
//	if (userOptional.isPresent()) {
//		return true;
//
//	}
//	return false;
//
//}

//	public List<User> getAllUsers() {
//		return userRepository.findAll();
//
//	}

}
