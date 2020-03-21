package ua.ivan909020.api.domain.dto;

import java.util.Objects;

public class ErrorResponseDto {

	private final int status;
	private final String error;
	private final String message;

	public ErrorResponseDto(int status, String error, String message) {
		this.status = status;
		this.error = error;
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public String getError() {
		return error;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		ErrorResponseDto other = (ErrorResponseDto) obj;
		return status == other.status && Objects.equals(error, other.error) && Objects.equals(message, other.message);
	}

	@Override
	public int hashCode() {
		return Objects.hash(status, error, message);
	}

	@Override
	public String toString() {
		return "ErrorResponseDto[status=" + status + ", error=" + error + ", message=" + message + "]";
	}

}
