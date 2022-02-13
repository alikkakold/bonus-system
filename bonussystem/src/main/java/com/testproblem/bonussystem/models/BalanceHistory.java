package com.testproblem.bonussystem.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
public class BalanceHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Integer value; // сколько баллов начислено

	@Column(nullable = false, updatable = false)
	@CreationTimestamp
	private Date created_at; // когда было начислены баллы

	@ManyToOne
	@JoinColumn(name="card_id")
	private Card card;

	public BalanceHistory() {
	}

	public BalanceHistory(Integer value, Date created_at, Card card) {
		this.value = value;
		this.created_at = created_at;
		this.card = card;
	}

	public Long getId() {
		return id;
	}

	public Card getCard() {
		return card;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
}
