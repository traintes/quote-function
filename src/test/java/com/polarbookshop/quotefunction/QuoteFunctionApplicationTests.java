package com.polarbookshop.quotefunction;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.polarbookshop.quotefunction.domain.Genre;
import com.polarbookshop.quotefunction.domain.Quote;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class QuoteFunctionApplicationTests {
	@Autowired
	WebTestClient webTestClient;
	
	@Test
	void whenAllQuotesThenReturn() {
		this.webTestClient
			.get()
			.uri("/")
			.exchange()
			.expectStatus().is2xxSuccessful()
			.expectBodyList(Quote.class);
	}
	
	@Test
	void whenRandomQuoteThenReturn() {
		this.webTestClient
			.get()
			.uri("/randomQuote")
			.exchange()
			.expectStatus().is2xxSuccessful()
			.expectBody(Quote.class);
	}
	
	@Test
	void whenGenreQuoteThenReturn() {
		this.webTestClient
			.post()
			.uri("/genreQuote")
			.bodyValue("FANTASY")
			.exchange()
			.expectStatus().is2xxSuccessful()
			.expectBody(Quote.class)
			.value(quote -> assertThat(quote.genre()).isEqualTo(Genre.FANTASY));
	}
	
	@Test
	void whenGenreQuoteLogQuoteThenReturn() {
		this.webTestClient
			.post()
			.uri("/genreQuote,logQuote")
			.contentType(MediaType.APPLICATION_JSON)
			.bodyValue("FANTASY")
			.exchange()
			.expectStatus().is2xxSuccessful();
	}
}
