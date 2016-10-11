package org.ernest.applications.goplus.controllers;


import org.ernest.applications.goplus.services.UserService;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Controller
public class WallController {

	private Facebook facebook;
	private ConnectionRepository connectionRepository;
	private UserService userService;

	public WallController(Facebook facebook, ConnectionRepository connectionRepository, UserService userService) {
		this.facebook = facebook;
		this.connectionRepository = connectionRepository;
		this.userService = userService;
	}

	@RequestMapping({"/"})
	public String getHomeView(Model model) {
		return "home";
	}

	@RequestMapping({"/wall"})
	public String getWallView(Model model, HttpServletRequest req) {
		model.addAttribute("validated", connectionRepository.findPrimaryConnection(Facebook.class) != null);

		if (connectionRepository.findPrimaryConnection(Facebook.class) != null) {
			User user = facebook.userOperations().getUserProfile();

			StringBuilder username = new StringBuilder("");
			username.append(user.getFirstName() == null ? "" : user.getFirstName() + " ");
			username.append(user.getMiddleName() == null ? "" : user.getMiddleName() + " ");
			username.append(user.getLastName() == null ? "" : user.getLastName());

			req.getSession().setAttribute("u", userService.introduceUser(user.getId(), username.toString()));
			model.addAttribute("username", username);
		}
		return "wall";
	}

	@RequestMapping({"/profile"})
	public String getProfileView(Model model) {

		return "profile";
	}

	@RequestMapping({"/myprofile"})
	public String getMyProfileView(Model model, HttpServletRequest req) {
		if(req.getSession().getAttribute("u") == null) return "wall";
		model.addAttribute("myprofileinfo", userService.getMyProfileInformation((Long)req.getSession().getAttribute("u")));
		return "myprofile";
	}

	@RequestMapping({"/map"})
	public String getMapView(Model model) {
		return "map";
	}

	@RequestMapping({"/contact"})
	public String getContactView(Model model) {
		return "contact";
	}

	@RequestMapping({"/blog"})
	public String getBlogView(Model model) {
		return "blog";
	}

	@RequestMapping({"/post"})
	public String getPostView(Model model) {
		return "post";
	}

	@RequestMapping({"/messages"})
	public String getMessagesView(Model model) {
		return "messages";
	}

	@RequestMapping("/logout")
	public String logout(Model model, HttpServletRequest req) {
		connectionRepository.removeConnections("facebook");
		req.getSession().setAttribute("u", null);
		return "redirect:/";
	}
}