package com.apirest.mvc;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@SpringBootApplication
public class MvcRestfullApplication {

	public static void main(String[] args) {
		SpringApplication.run(MvcRestfullApplication.class, args);
	}

	@Bean
	public LocaleResolver localeResolver() {
		Locale.setDefault(new Locale("PT", "br"));
		var slr = new SessionLocaleResolver();
		slr.setDefaultLocale(Locale.getDefault());
		return slr;
	}

}