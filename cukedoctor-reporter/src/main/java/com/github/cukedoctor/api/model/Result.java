package com.github.cukedoctor.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {

	private Status status;
	private String error_message;
	private Long duration;

	public Result() {

	}

	public Result(Status status) {
		this.status = status;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Long getDuration() {
		return duration == null ? 0L : duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public String getErrorMessage() {
		return error_message;
	}
}
