package ua.ivan909020.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;
import ua.ivan909020.api.annotations.ValueOfEnum;
import ua.ivan909020.api.domain.DurationUnit;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class PlanDto {

	private Integer id;

	@NotBlank(message = "Name field is required")
	@Length(max = 255, message = "Name field too long (more than 255 characters)")
	private String name;

	@NotNull(message = "Price field is required")
	private Float price;

	@JsonProperty("duration_unit")
	@NotNull(message = "Duration unit field is required")
	@ValueOfEnum(enumClass = DurationUnit.class, message = "Duration unit field is not correct")
	private String durationUnit;

	@JsonProperty("duration_count")
	@NotNull(message = "Duration count field is required")
	private Integer durationCount;

	public PlanDto() {
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

	public String getDurationUnit() {
		return durationUnit;
	}

	public void setDurationUnit(String durationUnit) {
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
		PlanDto other = (PlanDto) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name) && Objects.equals(price, other.price)
				&& Objects.equals(durationUnit, other.durationUnit)
				&& Objects.equals(durationCount, other.durationCount);
	}

	@Override
	public String toString() {
		return "PlanDto[id=" + id + ", name=" + name + ", price=" + price + ", durationUnit=" + durationUnit
				+ ", durationCount=" + durationCount + "]";
	}

}
