package io.github.cepr0.demo.dto;

import io.github.cepr0.demo.model.Comment;
import lombok.Value;

@Value
public class LightComment {
	private String email;
	private String body;

	public LightComment(Comment comment) {
		this.email = comment.getEmail();
		this.body = comment.getBody();
	}
}
