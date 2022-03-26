package com.track;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.track.filter.TrackFilter;

@SpringBootApplication
public class TrackServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrackServiceApplication.class, args);
	}
	
	@Bean
	public FilterRegistrationBean RecommendationFilter() {
		FilterRegistrationBean filter = new FilterRegistrationBean();
		filter.setFilter(new TrackFilter());
		filter.addUrlPatterns("/tracks/*");
		return filter;
	}

}
