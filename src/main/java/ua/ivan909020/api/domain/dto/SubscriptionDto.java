package ua.ivan909020.api.domain.dto;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import ua.ivan909020.api.validators.DateTime;

public class SubscriptionDto {

	private Integer id;

	@JsonProperty("user_id")
	@NotNull(message = "User id field is required")
	private Integer userId;

	@JsonProperty("plan_id")
	@NotNull(message = "Plan id field is required")
	private Integer planId;

	@JsonProperty("start_time")
	@NotNull(message = "Start time field is required")
	@DateTime(message = "Start time field is not correct")
	private String startTime;

	@JsonProperty("expiration_time")
	@NotNull(message = "Expiration time field is required")
	@DateTime(message = "Expiration time field is not correct")
	private String expirationTime;

	public SubscriptionDto() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(String expirationTime) {
		this.expirationTime = expirationTime;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, userId, planId, startTime, expirationTime);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		SubscriptionDto other = (SubscriptionDto) obj;
		return Objects.equals(id, other.id) && Objects.equals(userId, other.userId)
				&& Objects.equals(planId, other.planId) && Objects.equals(startTime, other.startTime)
				&& Objects.equals(expirationTime, other.expirationTime);
	}

	@Override
	public String toString() {
		return "Subscription[id=" + id + ", userId=" + userId + ", planId=" + planId + ", startTime=" + startTime
				+ ", expirationTime=" + expirationTime + "]";
	}

}
