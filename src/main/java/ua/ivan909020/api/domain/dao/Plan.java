package ua.ivan909020.api.domain.dao;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ua.ivan909020.api.domain.DurationUnit;

@Entity
@Table(name = "plans")
public class Plan {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plans_seq")
	@SequenceGenerator(name = "plans_seq", sequenceName = "plans_id_seq", allocationSize = 1)
	private Integer id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private Float price;

	@Enumerated(EnumType.STRING)
	@Column(name = "duration_unit", nullable = false)
	private DurationUnit durationUnit;

	@Column(name = "duration_count", nullable = false)
	private Integer durationCount;

	public Plan() {
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

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public DurationUnit getDurationUnit() {
		return durationUnit;
	}

	public void setDurationUnit(DurationUnit durationUnit) {
		this.durationUnit = durationUnit;
	}

	public Integer getDurationCount() {
		return durationCount;
	}

	public void setDurationCount(Integer durationCount) {
		this.durationCount = durationCount;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, price, durationUnit, durationCount);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Plan other = (Plan) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name) && Objects.equals(price, other.price)
				&& durationUnit == other.durationUnit && Objects.equals(durationCount, other.durationCount);
	}

	@Override
	public String toString() {
		return "Plan[id=" + id + ", name=" + name + ", price=" + price + ", durationUnit=" + durationUnit
				+ ", durationCount=" + durationCount + "]";
	}

}
