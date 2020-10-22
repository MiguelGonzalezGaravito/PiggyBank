
package com.PiggyBank.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.PiggyBank.Model.Role;
import com.PiggyBank.Model.User;
import com.PiggyBank.Repository.RoleRepository;
import com.PiggyBank.Service.UserService;






@Controller
public class UserController {


	@Autowired
	UserService userService;
	
	
	@Autowired
	RoleRepository roleRepository;
	

	
	@GetMapping({"/","/login"})
	public String login() {
		return "login";
	}
	
	@GetMapping({"/listUser"})
	public String listUser(Model model) {
		Iterable<User>  users = userService.getAllUsers();
		model.addAttribute("users", users);
		return "user-list";
	}
	
	
	@GetMapping({"/addUser"})
	public String addUser(Model model) {
		List<Role> roles = (List<Role>) roleRepository.findAll();
		model.addAttribute("userForm", new User());
		model.addAttribute("roles",roles);
		return "user-add";
	}
	
	
	@PostMapping("/addUser")
	public String addUserAction(@ModelAttribute("userForm")User user, BindingResult result, ModelMap model, Model model2) {
		List<Role> roles = (List<Role>) roleRepository.findAll();
		model.addAttribute("roles",roles);
		try {
			userService.createUser(user);
		}catch (Exception e) {
			model.addAttribute("formErrorMessage",e.getMessage());
			return "user-add";	
		}
		return listUser(model2);
	}

}


