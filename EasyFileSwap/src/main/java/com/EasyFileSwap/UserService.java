package com.EasyFileSwap;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserService {

	private UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public String register(String email, String username, String password) {
		if (userRepository.findEmail(email).isPresent()) {
			return "Email is in use.";
		}
		if (userRepository.findUsername(username).isPresent()) {
			return "Username is in use.";
		}

		User user = new User(email, username, password);
		userRepository.save(user);
		return "Registration successful.";

	}

	// user can enter username or email to login
	public String login(String usernameOrEmail, String password) {
		Optional<User> findUser = userRepository.findUsername(usernameOrEmail);

		if (!findUser.isPresent()) {
			findUser = userRepository.findEmail(usernameOrEmail);
		}

		if (findUser.isPresent()) {
			User user = findUser.get();
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			if (encoder.matches(password, user.getHashPassword())) {
				return "Login successful";
			} else {
				return "Invalid password";
			}
		}

		return "Username or email not found";

	}
}
