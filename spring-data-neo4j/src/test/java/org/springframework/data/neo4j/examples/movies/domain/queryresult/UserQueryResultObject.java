/*
 * Copyright 2011-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.neo4j.examples.movies.domain.queryresult;

import org.springframework.data.neo4j.annotation.QueryResult;
import org.springframework.data.neo4j.examples.movies.domain.User;
import org.springframework.data.neo4j.examples.movies.repo.UserRepository;

/**
 * Example interface annotated with {@link QueryResult} to test mapping onto proxied objects, where only getter methods
 * are needed to define the mapped result columns.
 *
 * @see UserRepository
 */
@QueryResult
public class UserQueryResultObject {

	private String name;
	private int ageOfUser;
	private User user;

	public String getName() {
		return name;
	}

	public int getAgeOfUser() {
		return ageOfUser;
	}

	public User getUser() {
		return user;
	}

}
