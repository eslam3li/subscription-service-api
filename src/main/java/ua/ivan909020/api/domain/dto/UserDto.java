package ua.ivan909020.api.domain.dto;

import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class UserDto {

	private Integer id;

	@NotBlank(message = "Name field is required")
	@Length(max = 255, message = "Name field too long (more than 255 characters)")
	private String name;

	@NotNull(message = "Balance field is required")
	private Integer balance;

	public UserDto() {
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

	@Override
	public int hashCode() {
		return Objects.hash(balance, id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		UserDto other = (UserDto) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(balance, other.balance);
	}

	@Override
	public String toString() {
		return "UserDto[id=" + id + ", name=" + name + ", balance=" + balance + "]";
	}

}
