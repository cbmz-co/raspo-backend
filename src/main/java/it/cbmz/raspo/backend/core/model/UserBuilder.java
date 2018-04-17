package it.cbmz.raspo.backend.core.model;

import org.bson.types.ObjectId;

import java.util.function.Consumer;

public class UserBuilder {
	public String objectId;
	public String username;
	public String email;

	public UserBuilder with(Consumer<UserBuilder> builderFunction) {
		builderFunction.accept(this);
		return this;
	}

	public User create() {
		return new User(
			objectId,
			username,
			email
		);
	}
}
