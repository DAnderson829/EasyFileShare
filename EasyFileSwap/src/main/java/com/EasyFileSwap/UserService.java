package com.EasyFileSwap;

public class UserService {
	
	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public String register(String email, String username, String password) {
		if(userRepository.findEmail(email).isPresent()) {
			return "Email is in use.";
		}
		if(userRepository.findUsername(username).isPresent()) {
			return "Username is in use.";
		}
		
		User user = new User(email, username, password);
		userRepository.save(user);
		return "Registration successful.";
		
	}
	
	//user can enter username or email to login
	public String login(String username, String password) {
		
	}
}
