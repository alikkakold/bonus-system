package com.testproblem.bonussystem.models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Client {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id; // идентификатор клиента

	private String name; // имя владельца

	private Boolean isActive; // активна/заблокирована

	@OneToMany(mappedBy = "client")
	private Set<Card> cards; // бонусные карты клиента

	public Client() {
	}

	public Client(String name) {
		this.name = name;
		this.isActive = true;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean is_active) {
		this.isActive = is_active;
	}
}
