package com.corey.javaretake.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


@Entity
@Table(name="ratings")
public class Rating {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
	@Max(5)
	@Min(1)
	private Double value;
	private Long userid;
	@Column(updatable=false)
    private Date createdAt;
    private Date updatedAt;
    
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="show_id")
    private Show rated_show;
	
	public Rating() {
		
	}

	public Rating(Long id, @Max(5) @Min(1) Double value, Long userid, Date createdAt, Date updatedAt,
			Show rated_show) {
		super();
		this.id = id;
		this.value = value;
		this.userid = userid;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.rated_show = rated_show;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
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

	public Show getRated_show() {
		return rated_show;
	}

	public void setRated_show(Show rated_show) {
		this.rated_show = rated_show;
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
