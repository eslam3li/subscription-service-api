package ua.ivan909020.api.domain.dao;

import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
	@SequenceGenerator(name = "users_seq", sequenceName = "users_id_seq", allocationSize = 1)
	private Integer id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private Integer balance;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Subscription> subscriptions;

	public User() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	public List<Subscription> getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(List<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
	}

	@Override
	public int hashCode() {
		return Objects.hash(balance, id, name, subscriptions);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		User other = (User) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(balance, other.balance) && Objects.equals(subscriptions, other.subscriptions);
	}

	@Override
	public String toString() {
		return "User[id=" + id + ", name=" + name + ", balance=" + balance + "]";
	}

}
