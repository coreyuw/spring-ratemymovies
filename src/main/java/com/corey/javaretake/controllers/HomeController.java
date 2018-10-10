package com.corey.javaretake.controllers;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.corey.javaretake.models.User;
import com.corey.javaretake.models.Rating;
import com.corey.javaretake.models.Show;
import com.corey.javaretake.services.HomeService;
import com.corey.javaretake.validators.UserValidator;

@Controller
public class HomeController {
	private final HomeService homeService;
	private final UserValidator userValidator;

	public HomeController(HomeService homeService, UserValidator userValidator) {
        this.homeService = homeService;
        this.userValidator = userValidator;
    }
	
	@RequestMapping("")
    public String index(@ModelAttribute("user") User user) {
    	return "index.jsp";
    }
	
	@RequestMapping("ninjagold")
	public String ninjagold(HttpSession session) {
		session.setAttribute("gold", 0);
		session.setAttribute("log", "Welcome to Ninja Gold! Click any button above to play!");
		return "ninjagold.jsp";
	}
	
	@RequestMapping("process_money_farm")
	public String farm(HttpSession session) {
		int gold = (Integer) session.getAttribute("gold");
		int randomNum = ThreadLocalRandom.current().nextInt(10, 21);
		session.setAttribute("gold", gold + randomNum);
		ZonedDateTime now = ZonedDateTime.now( ZoneOffset.UTC );
		session.setAttribute("log", "You entered the farm and earned " + randomNum +" gold. Time: " + now + " .");
		return "ninjagold.jsp";
	}
	
	@RequestMapping("process_money_cave")
	public String cave(HttpSession session) {
		int gold = (Integer) session.getAttribute("gold");
		int randomNum = ThreadLocalRandom.current().nextInt(5, 11);
		session.setAttribute("gold", gold+randomNum);
		ZonedDateTime now = ZonedDateTime.now( ZoneOffset.UTC );
		session.setAttribute("log", "You entered the cave and earned " + randomNum +" gold. Time: " + now + " .");
		return "ninjagold.jsp";
	}
	
	@RequestMapping("process_money_house")
	public String house(HttpSession session) {
		int gold = (Integer) session.getAttribute("gold");
		int randomNum = ThreadLocalRandom.current().nextInt(2, 6);
		session.setAttribute("gold", gold+randomNum);
		ZonedDateTime now = ZonedDateTime.now( ZoneOffset.UTC );
		session.setAttribute("log", "You entered the house and earned " + randomNum +" gold. Time: " + now + " .");
		return "ninjagold.jsp";
	}
	
	@RequestMapping("process_money_casino")
	public String casino(HttpSession session) {
		int gold = (Integer) session.getAttribute("gold");
		int change = ThreadLocalRandom.current().nextInt(0, 51);
		int decide = ThreadLocalRandom.current().nextInt(0, 2);
		ZonedDateTime now = ZonedDateTime.now( ZoneOffset.UTC );
		if (decide == 0) {
			gold += change;
			session.setAttribute("log", "You entered the house and earned " + change +" gold. Time: " + now + " .");
		}else {
			gold -= change;
			session.setAttribute("log", "You entered the house and lost " + change +" gold. Time: " + now + " .");
		}
		session.setAttribute("gold", gold);
		return "ninjagold.jsp";
	}
	
	@RequestMapping("resetgold")
	public String resetgoldt(HttpSession session) {
		session.setAttribute("gold", 0);
		session.setAttribute("log", "Ninja Gold has been resetted! Click any button above to play!");
		return "ninjagold.jsp";
	}
	
	
	@RequestMapping(value="/registration", method=RequestMethod.POST)
    public String registration(@RequestParam("email") String email, Model model, @Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session) {
    	userValidator.validate(user, result);
    	if(result.hasErrors()) {
    		return "index.jsp";
    	}
    	boolean isAuthenticated = homeService.authenticateExistingUser(email);
    	if(isAuthenticated) {
    		User u = homeService.registerUser(user);
    		session.setAttribute("userId", u.getId());
    		return "redirect:/home";
    	}else {
    		model.addAttribute("registerError", "Email already exists!");
    		return "index.jsp";
    	}
    }
    
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String loginUser(@RequestParam("loginEmail") String email, @RequestParam("password") String password,@ModelAttribute("user") User user, Model model, HttpSession session) {
        // if the user is authenticated, save their user id in session
        // else, add error messages and return the login page
    	if (email.equals("")) {
    		model.addAttribute("loginError", "Login email cannot be empty!");
    		return "index.jsp";
    	}
    	boolean isAuthenticated = homeService.authenticateUser(email, password);
    	if(isAuthenticated) {
    		User u = homeService.findByEmail(email);
    		session.setAttribute("userId", u.getId());
    		return "redirect:/home";
    	}else {
    		model.addAttribute("loginError", "Invalid Credentials. Please try again.");
    		return "index.jsp";
    	}
    	
    }
    
    @RequestMapping("/home")
    public String home(HttpSession session, Model model) {
    	Page<Show> shows = homeService.highToLow();
        // get user from session, save them in the model and return the home page
    	Long userId = (Long) session.getAttribute("userId");
    	User u = homeService.findUserById(userId);
//    	List<Show> shows = homeService.allShows();
    	model.addAttribute("user", u);
    	model.addAttribute("shows", shows);
    	return "home.jsp";
    }
    
    @RequestMapping("/shows/new")
    public String newShow(@ModelAttribute("show") Show show, HttpSession session, Model model) {
    	Long userId = (Long) session.getAttribute("userId");
    	User u = homeService.findUserById(userId);
    	model.addAttribute("creator", u);
    	return "newshow.jsp";
    }
    
    @RequestMapping("/shows/create")
    public String createShow(HttpServletRequest request,@Valid @ModelAttribute("show") Show show, BindingResult result, HttpSession session, Model model) {
    	if (show.getTitle().equals("")) {
        	model.addAttribute("titleError", "Title cannot be empty!");
    		return "newshow.jsp";
    	}else if (show.getNetwork().equals("")) {
    		model.addAttribute("networkError", "Network cannot be empty!");
    		return "newshow.jsp";
    	}else {
    		boolean titleNotExists = homeService.authenticateExistingTitle(show.getTitle());
    		if (titleNotExists) {
    			homeService.createShow(show);
    			return "redirect:/home";
    		}else {
    			model.addAttribute("error", "Title must be unique.");
    			return "newshow.jsp";
    		}
    	}
    }
    
    @RequestMapping("/shows/{sId}")
    public String displayShow(@PathVariable("sId") Long sId, Model model, HttpSession session, @ModelAttribute("rating") Rating rating) {
    	Long userId = (Long) session.getAttribute("userId");
    	User u = homeService.findUserById(userId);
    	model.addAttribute("currentUser", u);
    	Show show = homeService.findShowById(sId);
    	List<Rating> ratings = show.getRatings();
    	List<User> users = new ArrayList<User>();
    	for (int i = 0; i < ratings.size(); i++) {
    		users.add(homeService.findUserById(ratings.get(i).getUserid()));
    	}
    	model.addAttribute("users", users);
    	model.addAttribute("ratings", ratings);
    	model.addAttribute("show", show);
    	return "displayshow.jsp";
    }
    
    @RequestMapping("/shows/{sId}/edit")
    public String editShow(@PathVariable("sId") Long sId, Model model) {
    	Show s = homeService.findShowById(sId);
    	model.addAttribute("show", s);
    	return "editshow.jsp";
    }
    
    @RequestMapping(value="/shows/{sId}/updateEdit", method=RequestMethod.PUT)
    public String updateEdit(@PathVariable("sId") Long sId, Model model, @Valid @ModelAttribute("show") Show show, BindingResult result) {
    	if (show.getTitle().equals("")) {
    		Show s = homeService.findShowById(sId);
        	model.addAttribute("show", s);
        	model.addAttribute("titleError", "Title cannot be empty!");
    		return "editshow.jsp";
    	}else if (show.getNetwork().equals("")) {
    		Show s = homeService.findShowById(sId);
        	model.addAttribute("show", s);
    		model.addAttribute("networkError", "Network cannot be empty!");
    		return "editshow.jsp";
    	}else {
    		boolean titleNotExists = homeService.authenticateExistingTitle(show.getTitle());
    		if (titleNotExists || show.getTitle().equals(homeService.findShowById(sId).getTitle())) {
    			model.addAttribute("show", show);
    			homeService.updateShow(sId, show.getTitle(), show.getNetwork());
    			return "redirect:/home";
    		}else {
    			model.addAttribute("error", "Title must be unique.");
    			Show s = homeService.findShowById(sId);
    	    	model.addAttribute("show", s);
    			return "editshow.jsp";
    		}
    	}
//    	taskValidator.validate(task, result);
//    	if (result.hasErrors()) {
//    		Show s = homeService.findShowById(sId);
//        	model.addAttribute("show", s);
//			return "editshow.jsp";
//		}else {
//			homeService.updateShow(sId, show.getTitle(), show.getNetwork());
//			return "redirect:/home";
//		}
    }
    
    @RequestMapping("/shows/{sId}/delete")
    public String delete(@PathVariable("sId") Long sId) {
    	homeService.deleteShow(sId);
    	return "redirect:/home";
    }
    
    @RequestMapping("/shows/{sId}/rate")
    public String rate(@PathVariable("sId") Long sId, Model model, HttpSession session, @Valid @ModelAttribute("rating") Rating rating, BindingResult result) {
//    	Long userId = (Long) session.getAttribute("userId");
//    	User u = homeService.findUserById(userId);
    	if (rating.getValue() == null) {
    		model.addAttribute("error", "Rating cannot be empty!");
    		Long userId = (Long) session.getAttribute("userId");
        	User u = homeService.findUserById(userId);
        	model.addAttribute("currentUser", u);
        	Show show = homeService.findShowById(sId);
        	List<Rating> ratings = show.getRatings();
        	List<User> users = new ArrayList<User>();
        	for (int i = 0; i < ratings.size(); i++) {
        		users.add(homeService.findUserById(ratings.get(i).getUserid()));
        	}
        	model.addAttribute("users", users);
        	model.addAttribute("ratings", ratings);
        	model.addAttribute("show", show);
    		return "displayshow.jsp";
    	}else {
    		if (rating.getValue() < 1.0 || rating.getValue() > 5.0) {
//    		model.addAttribute("error", "Rating should be within 1~5.");
    		Long userId = (Long) session.getAttribute("userId");
        	User u = homeService.findUserById(userId);
        	model.addAttribute("currentUser", u);
        	Show show = homeService.findShowById(sId);
        	List<Rating> ratings = show.getRatings();
        	List<User> users = new ArrayList<User>();
        	for (int i = 0; i < ratings.size(); i++) {
        		users.add(homeService.findUserById(ratings.get(i).getUserid()));
        	}
        	model.addAttribute("users", users);
        	model.addAttribute("ratings", ratings);
        	model.addAttribute("show", show);
    		return "displayshow.jsp";
    	}else {
    		boolean RatingNotExists = homeService.authenticateExistingRating(homeService.findShowById(sId), (Long) session.getAttribute("userId"));
    		if(RatingNotExists) {
    			Show show = homeService.findShowById(sId);
    			homeService.createRating(rating);
    			homeService.addShowToRating(rating, show);
    			List<Rating> ratings = show.getRatings();
    			Double total = 0.0;
    			for(int i = 0; i < ratings.size(); i ++) {
    				total += ratings.get(i).getValue();
    			}
    			show.setAveRating(total / (ratings.size() * 1.0));
    			homeService.createShow(show);
    			homeService.addUserToShow(show, homeService.findUserById((Long) session.getAttribute("userId")));
    			return "redirect:/shows/{sId}";
    		}else {
    			System.out.println("LAJdslkajdlkajdlkajdklajdlka");
    			Show show = homeService.findShowById(sId);
    			System.out.println("input id is: " + rating.getId() );
    			homeService.updateRating(sId, (Long) session.getAttribute("userId"), rating.getValue());
    			System.out.println("LAJdslkajdlkajdlkajdklajLAJdslkajdlkajdlkajdklajdlkaLAJdslkajdlkajdlkajdklajdlkaLAJdslkajdlkajdlkajdklajdlkadlka");
    			List<Rating> ratings = show.getRatings();
    			Double total = 0.0;
    			for(int i = 0; i < ratings.size(); i ++) {
    				total += ratings.get(i).getValue();
    			}
    			show.setAveRating(total / (ratings.size() * 1.0));
    			homeService.createShow(show);
    			homeService.addUserToShow(show, homeService.findUserById((Long) session.getAttribute("userId")));
    			return "redirect:/shows/{sId}";
    		}
    		
    	}
    }
    	
    	
    }
    
    @RequestMapping("/lowToHigh")
    public String lowToHigh(Model model, HttpSession session) {
    	Page<Show> shows = homeService.lowToHigh();
    	Long userId = (Long) session.getAttribute("userId");
    	User u = homeService.findUserById(userId);
    	model.addAttribute("user", u);
//    	int totalPages = tasks.getTotalPages();
//    	model.addAttribute("totalPages", totalPages);
    	model.addAttribute("shows", shows);
    	return "home.jsp";
    }
    
    @RequestMapping("/highToLow")
    public String highToLow(Model model, HttpSession session) {
    	Page<Show> shows = homeService.highToLow();
    	Long userId = (Long) session.getAttribute("userId");
    	User u = homeService.findUserById(userId);
    	model.addAttribute("user", u);
//    	int totalPages = tasks.getTotalPages();
//    	model.addAttribute("totalPages", totalPages);
    	model.addAttribute("shows", shows);
    	return "home.jsp";
    }
    
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        // invalidate session
        // redirect to login page
    	session.invalidate();
    	return "redirect:/";
    }
}
