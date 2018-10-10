package com.corey.javaretake.services;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.corey.javaretake.models.Rating;
import com.corey.javaretake.models.Show;
import com.corey.javaretake.models.User;
import com.corey.javaretake.repositories.RatingRepository;
import com.corey.javaretake.repositories.ShowRepository;
import com.corey.javaretake.repositories.UserRepository;

@Service
@Transactional
public class HomeService {
	@Autowired
	private final ShowRepository showRepo;
	private final UserRepository userRepo;
	private final RatingRepository ratingRepo;
	
	public HomeService(ShowRepository showRepo, UserRepository userRepo, RatingRepository ratingRepo) {
		this.showRepo = showRepo;
		this.userRepo = userRepo;
		this.ratingRepo = ratingRepo;
	}

	// register user and hash their password
    public User registerUser(User user) {
        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashed);
        return userRepo.save(user);
    }
    
    // find user by email
    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }
    
    // find user by id
    public User findUserById(Long id) {
    	Optional<User> u = userRepo.findById(id);
    	
    	if(u.isPresent()) {
            return u.get();
    	} else {
    	    return null;
    	}
    }
    
    // authenticate user
    public boolean authenticateUser(String email, String password) {
        // first find the user by email
        User user = userRepo.findByEmail(email);
        // if we can't find it by email, return false
        if(user == null) {
            return false;
        } else {
            // if the passwords match, return true, else, return false
            if(BCrypt.checkpw(password, user.getPassword())) {
                return true;
            } else {
                return false;
            }
        }
    }
    
    public boolean authenticateExistingUser(String email) {
    	User user = userRepo.findByEmail(email);
    	if(user == null) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public boolean authenticateExistingRating(Show show, Long uId) {
    	List<User> rated_users = show.getRated_Users();
    	for (int i = 0; i < rated_users.size(); i++) {
    		if (rated_users.get(i).getId().equals(uId)) {
    			return false;
    		}
    	}
    	return true;
    }
    
    public void addUserToShow(Show show, User user) {
    	List<User> users = show.getRated_Users();
    	users.add(user);
    	show.setRated_Users(users);
    	showRepo.save(show);
    }
    
    public boolean authenticateExistingTitle(String title) {
    	Show show = showRepo.findByTitle(title);
    	if (show == null) {
    		return true;
    	}else {
    		return false;
    	}
    	
    }
    
    public List<User> allUsers(){
    	return userRepo.findAll();
    }
    
    public List<Show> allShows(){
    	return showRepo.findAll();
    }
    
    public Show createShow(Show s) {
    	return showRepo.save(s);
    }
    
    public Show updateShow(Long sId, String title, String network) {
    	Optional<Show> optionalShow = showRepo.findById(sId);
    	if(optionalShow.isPresent()) {
    		Show s = optionalShow.get();
    		s.setNetwork(network);
    		s.setTitle(title);
    		return showRepo.save(s);
    	}else {
    		return null;
    	}
    }
    
    public Rating updateRating(Long sId, Long uId, Double value) {
    	Optional<Show> show = showRepo.findById(sId);
    	Rating rating = ratingRepo.findByUserid(uId);
    	rating.setValue(value);
    	if(show.isPresent()) {
    		Show s = show.get();
    		addShowToRating(rating, s);
    	}else {
    		return null;
    	}
    	return ratingRepo.save(rating);
    }
    
    public void deleteShow(Long sId) {
    	showRepo.deleteById(sId);
    }
    
    public Page<Show> highToLow(){
//    	List<Task> tasks = taskRepo.findAll();
//    	tasks.sort(DESC);
    	PageRequest pageRequest = new PageRequest(0, 999, Sort.Direction.DESC, "aveRating");
    	Page<Show> shows = showRepo.findAll(pageRequest);
    	return shows;
    }
    
    public Page<Show> lowToHigh(){
    	PageRequest pageRequest = new PageRequest(0, 999, Sort.Direction.ASC, "aveRating");
    	Page<Show> shows = showRepo.findAll(pageRequest);
    	return shows;
    }
    
    public List<User> findAllRated_Users(Long sId) {
        Optional<Show> s = showRepo.findById(sId);
        if(s.isPresent()) {
        	Show show = s.get();
        	return show.getRated_Users();
        } else {
        	return null;
        }
    }
    
    public Show findShowById(Long sId) {
    	Optional<Show> s = showRepo.findById(sId);
    	if(s.isPresent()) {
        	Show show = s.get();
        	return show;
        } else {
        	return null;
        }
    }
    
    public Rating createRating(Rating r) {
    	return ratingRepo.save(r);
    }
    
    
    public void addUsersToShow(User user, Show show) {
    	show.getRated_Users().add(user);
    	List<User> users = show.getRated_Users();
    	show.setRated_Users(users);
    	showRepo.save(show);
    }
    
    public void addShowToRating(Rating rating, Show rated_show) {
    	rating.setRated_show(rated_show);
    	ratingRepo.save(rating);
    }
}