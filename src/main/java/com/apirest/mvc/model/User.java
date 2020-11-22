package com.apirest.mvc.model;

import com.apirest.mvc.enums.UserType;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.PrePersist;

import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
@EntityListeners(AuditingEntityListener.class)
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