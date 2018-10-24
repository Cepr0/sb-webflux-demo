package io.github.cepr0.demo;

import io.github.cepr0.demo.dto.LightComment;
import io.github.cepr0.demo.dto.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("/api")
public class ApiController {

	private final ApiService api;

	public ApiController(ApiService api) {
		this.api = api;
	}

	@GetMapping
	public Flux<Response> getData() {
		return api.fetchPosts()
				.parallel(4)
				.runOn(Schedulers.elastic())
				.flatMap(post -> api.fetchComments(post.getId())
						.map(LightComment::new)
						.collectList()
						.zipWith(api.fetchUser(post.getUserId()), (c, u) -> new Response(post.getId(), post.getTitle(), u, c)))
				.sequential();
	}
}
