package com.corey.javaretake.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;


@Entity
@Table(name="shows")
public class Show {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	private String creator;
	@Size(min=1, message="Title cannot be empty!")
	private String title;
	@Size(min=1, message="Network cannot be empty!")
	private String network;
	private Double aveRating;
	@Column(updatable=false)
    private Date createdAt;
    private Date updatedAt;
    
    @OneToMany(mappedBy="rated_show", fetch = FetchType.LAZY)
    private List<Rating> ratings;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "shows_users", 
        joinColumns = @JoinColumn(name = "show_id"), 
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> rated_users;
	
	public Show() {
		
	}

	public Show(Long id, String creator, @Size(min = 1, message = "Title cannot be empty!") String title,
			@Size(min = 1, message = "Network cannot be empty!") String network, Double aveRating,
			Date createdAt, Date updatedAt, List<User> rated_users, List<Rating> ratings) {
		super();
		this.id = id;
		this.creator = creator;
		this.title = title;
		this.network = network;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.rated_users = rated_users;
		this.aveRating = aveRating;
		this.ratings = ratings;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}	

	public Double getAveRating() {
		return aveRating;
	}

	public void setAveRating(Double aveRating) {
		this.aveRating = aveRating;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<User> getRated_Users() {
		return rated_users;
	}

	public void setRated_Users(List<User> rated_users) {
		this.rated_users = rated_users;
	}
	
	
	
	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}

	@PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }
	
	
}
