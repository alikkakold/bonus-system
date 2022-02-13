package com.testproblem.bonussystem.responses;

public class BasicResponse {

	private Integer code;

	private String content;

	public BasicResponse(Integer code, String content) {
		this.code = code;
		this.content = content;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
