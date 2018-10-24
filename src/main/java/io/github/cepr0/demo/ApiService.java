package io.github.cepr0.demo;

import io.github.cepr0.demo.model.Comment;
import io.github.cepr0.demo.model.Post;
import io.github.cepr0.demo.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@Service
public class ApiService {

	private final WebClient client;

	public ApiService() {
		client = WebClient
				.builder()
				.baseUrl(("http://jsonplaceholder.typicode.com/"))
				.build();
	}

	@Cacheable("comments")
	public Flux<Comment> fetchComments(Integer postId) {

		String commentUri = String.format("/posts/%s/comments", postId);
		log.debug("[d] Fetching comment {}", commentUri);

		return client
				.get()
				.uri(commentUri)
				.retrieve()
				.bodyToFlux(Comment.class)
				.cache(Duration.ofDays(1))
				;
	}

	public Flux<Post> fetchPosts() {
		return client
				.get()
				.uri("/posts")
				.retrieve()
				.bodyToFlux(Post.class);
	}

	@Cacheable("users")
	public Mono<User> fetchUser(Integer userId) {

		String userUri = "/users/" + userId;
		log.debug("[d] Fetching user {}", userUri);

		return client
				.get()
				.uri(userUri)
				.retrieve()
				.bodyToMono(User.class)
				.cache(Duration.ofDays(1))
				;
	}
}
