package io.github.cepr0.demo.dto;

import io.github.cepr0.demo.model.User;
import lombok.Value;

import java.util.List;

@Value
public class Response {
	private Integer postId;
	private String title;
	private User user;
	private List<LightComment> comments;
}
