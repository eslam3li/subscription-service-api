package ua.ivan909020.api.domain.dao;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "subscriptions")
public class Subscription {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subscriptions_seq")
	@SequenceGenerator(name = "subscriptions_seq", sequenceName = "subscriptions_id_seq", allocationSize = 1)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "plan_id", nullable = false)
	private Plan plan;

	@Column(name = "start_time", nullable = false)
	private LocalDateTime startTime;

	@Column(name = "expiration_time", nullable = false)
	private LocalDateTime expirationTime;

	public Subscription() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(LocalDateTime expirationTime) {
		this.expirationTime = expirationTime;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, user, plan, startTime, expirationTime);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Subscription other = (Subscription) obj;
		return Objects.equals(id, other.id) && Objects.equals(user, other.user) && Objects.equals(plan, other.plan)
				&& Objects.equals(startTime, other.startTime) && Objects.equals(expirationTime, other.expirationTime);
	}

	@Override
	public String toString() {
		return "Subscription[id=" + id + ", user=" + user + ", plan=" + plan + ", startTime=" + startTime
				+ ", expirationTime=" + expirationTime + "]";
	}

}
