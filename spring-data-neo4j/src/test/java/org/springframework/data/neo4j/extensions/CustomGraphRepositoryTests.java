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
package org.springframework.data.neo4j.extensions;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.ogm.session.SessionFactory;
import org.neo4j.ogm.testutil.MultiDriverTestClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author: Vince Bickers
 */
@ContextConfiguration(classes = { CustomGraphRepositoryTests.CustomPersistenceContext.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class CustomGraphRepositoryTests extends MultiDriverTestClass {

	@Autowired private UserRepository repository;

	/**
	 * asserts that the correct proxied object is created by Spring and that we can integrate with it.
	 */
	@Test
	public void shouldExposeCommonMethodOnExtendedRepository() {
		assertTrue(repository.sharedCustomMethod());
	}

	@Configuration
	@EnableNeo4jRepositories(repositoryBaseClass = CustomGraphRepositoryImpl.class)
	@EnableTransactionManagement
	static class CustomPersistenceContext {

		@Bean
		public PlatformTransactionManager transactionManager() {
			return new Neo4jTransactionManager(sessionFactory());
		}

		@Bean
		public SessionFactory sessionFactory() {
			return new SessionFactory(getBaseConfiguration().build(), "org.springframework.data.neo4j.extensions.domain");
		}
	}
}
