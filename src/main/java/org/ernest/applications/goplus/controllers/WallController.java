package org.ernest.applications.goplus.controllers;


import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class WallController {

	private Facebook facebook;
	private ConnectionRepository connectionRepository;

	public WallController(Facebook facebook, ConnectionRepository connectionRepository) {
		this.facebook = facebook;
		this.connectionRepository = connectionRepository;
	}

	@RequestMapping({"/"})
	public String getHomeView(Model model) {
		return "home";
	}

	@RequestMapping({"/wall"})
	public String getWallView(Model model) {
		model.addAttribute("validated", connectionRepository.findPrimaryConnection(Facebook.class) != null);

		if (connectionRepository.findPrimaryConnection(Facebook.class) != null) {
			model.addAttribute("username", facebook.userOperations().getUserProfile().getFirstName());
		}
		return "wall";
	}

	@RequestMapping({"/profile"})
	public String getProfileView(Model model) {
		return "profile";
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
	public String logout(Model model) {
		connectionRepository.removeConnections("facebook");
		return "redirect:/";
	}
}