package com.PiggyBank.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.PiggyBank.Exeption.IdNotFound;
import com.PiggyBank.Exeption.ValidationUser;
import com.PiggyBank.Model.User;
import com.PiggyBank.Repository.UserRepository;




@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository repository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public Iterable<User> getAllUsers() {
		return repository.findAll();
	}
	
	private boolean checkUsernameAvailable(User user) throws Exception {
		Optional<User> userFound = repository.findByUsername(user.getUsername());
		if (userFound.isPresent() && userFound.get().getId() != user.getId()) {
			throw new ValidationUser("Usuario no disponible","username");
		}
		return true;
	}

	private boolean checkPasswordValid(User user) throws Exception {
		if (user.getConfirmPassword() == null || user.getConfirmPassword().isEmpty()) {
			throw new ValidationUser("La contraseña es obligatoria","confirmPassword");
		}
		
		if ( !user.getPassword().equals(user.getConfirmPassword())) {
			throw new ValidationUser("Contraseña y confirmación de contraseña no son iguales","password");
		}
		return true;
	}


	@Override
	public User createUser(User user) throws Exception {
		if (checkUsernameAvailable(user) && checkPasswordValid(user)) {
			String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
			user.setPassword(encodedPassword);
			user = repository.save(user);
		}
		return user;
	}

	@Override
	public User getUserById(Long id) throws IdNotFound {
		return repository.findById(id).orElseThrow(() -> new IdNotFound("El Id del usuario no existe."));
	}

	@Override
	public User updateUser(User fromUser) throws Exception {
		
		boolean validatePassword = true;
		if(!fromUser.getPassword().isEmpty()) {
			validatePassword = checkPasswordValid(fromUser);
		}
		if (checkUsernameAvailable(fromUser) && validatePassword) {
			User toUser = getUserById(fromUser.getId());
			mapUser(fromUser, toUser);
			
			if(!fromUser.getPassword().isEmpty()) {
				String encodedPassword = bCryptPasswordEncoder.encode(fromUser.getPassword());
				toUser.setPassword(encodedPassword);
			}
			fromUser = repository.save(toUser);
		}
		return fromUser;
	}
	

	protected void mapUser(User from,User to) {
		to.setUsername(from.getUsername());
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setEmail(from.getEmail());
		to.setRoles(from.getRoles());
		to.setIdentity(from.getIdentity());
	}

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public void deleteUser(Long id) throws IdNotFound {
		User user = getUserById(id);
		repository.delete(user);
	}

	

	
	public boolean isLoggedUserADMIN() {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		UserDetails loggedUser = null;
		Object roles = null;

		if (principal instanceof UserDetails) {
			loggedUser = (UserDetails) principal;
	
			roles = loggedUser.getAuthorities().stream()
					.filter(x -> "Administrador".equals(x.getAuthority())).findFirst()
					.orElse(null); 
		}
		return roles != null ? true : false;
	}
	
	public boolean isLoggedUserUSUARIO() {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		UserDetails loggedUser = null;
		Object roles = null;

		if (principal instanceof UserDetails) {
			loggedUser = (UserDetails) principal;
	
			roles = loggedUser.getAuthorities().stream()
					.filter(x -> "Usuario".equals(x.getAuthority())).findFirst()
					.orElse(null); 
		}
		return roles != null ? true : false;
	}
	
	
	public User getLoggedUser() throws Exception {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		UserDetails loggedUser = null;

		if (principal instanceof UserDetails) {
			loggedUser = (UserDetails) principal;
		}
		
		User myUser = repository.findByUsername(loggedUser.getUsername()).orElseThrow(() -> new Exception("Error obteniendo el usuario logeado desde la sesion."));
		
		return myUser;
	}
}
