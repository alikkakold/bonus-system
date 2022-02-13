package com.testproblem.bonussystem.models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Card {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Integer balance; // баланс бонусной карты

	private Integer number; // номер карты

	private Boolean isActive; // активна/заблокирована

	@ManyToOne
	@JoinColumn(name="client_id")
	private Client client;

	@OneToMany(mappedBy = "card")
	private Set<BalanceHistory> balanceRecords; // история начислений бонусов

	public Card() {
	}

	public Card(Integer balance, Integer number, Client client) {
		this.balance = balance;
		this.number = number;
		this.isActive = true;
		this.client = client;
	}

	public Long getId() {
		return id;
	}

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean is_active) {
		this.isActive = is_active;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Client getClient() {
		return client;
	}

	public Set<BalanceHistory> getBalanceRecords() {
		return balanceRecords;
	}

	public void setBalanceRecords(Set<BalanceHistory> balanceRecords) {
		this.balanceRecords = balanceRecords;
	}
}
