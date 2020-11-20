package com.apirest.mvc.model;

import com.apirest.mvc.enums.UserType;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;

import org.springframework.data.jpa.domain.AbstractAuditable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class User extends AbstractAuditable<User, Long> {

	@Column(nullable = false)
	private String fullname;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private UserType userType;

	@PrePersist
	void prePersist() {
		if (Objects.isNull(userType)) {
			userType = UserType.DEFAULT;
		}
	}

}