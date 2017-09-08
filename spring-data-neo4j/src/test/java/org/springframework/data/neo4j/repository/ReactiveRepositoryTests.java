/*
 * Copyright (c)  [2011-2017] "Pivotal Software, Inc." / "Neo Technology" / "Graph Aware Ltd."
 *
 * This product is licensed to you under the Apache License, Version 2.0 (the "License").
 * You may not use this product except in compliance with the License.
 *
 * This product may include a number of subcomponents with
 * separate copyright notices and license terms. Your use of the source
 * code for these subcomponents is subject to the terms and
 * conditions of the subcomponent's license, as noted in the LICENSE file.
 *
 */

package org.springframework.data.neo4j.repository;

import static org.assertj.core.api.Assertions.*;
import static org.neo4j.ogm.testutil.MultiDriverTestClass.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.domain.sample.SampleEntity;
import org.springframework.data.neo4j.repository.config.EnableReactiveNeo4jRepositories;
import org.springframework.data.neo4j.repository.support.ReactiveNeo4jRepositoryFactory;
import org.springframework.data.neo4j.repository.support.TransactionalRepositoryTests;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Nicolas Mervaillie
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ReactiveRepositoryTests.Config.class)
@Transactional
public class ReactiveRepositoryTests {

	@Autowired Session session;

	private ReactiveNeo4jRepository<SampleEntity, Long> repository;
	private SampleEntity sampleEntity;

	interface ReactiveSampleEntityRepository extends ReactiveNeo4jRepository<SampleEntity, Long> {

	}

	@Before
	public void setUp() {

		repository = new ReactiveNeo4jRepositoryFactory(session).getRepository(ReactiveSampleEntityRepository.class);

		session.deleteAll(SampleEntity.class);
		sampleEntity = new SampleEntity("foo", "bar");
		session.save(sampleEntity);
	}

	@Test
	public void testExistsById() throws Exception {

		StepVerifier.create(repository.existsById(sampleEntity.getId()))
				.expectNext(true)
				.verifyComplete();
	}

	@Test
	public void testExistsByIdPublisher() throws Exception {

		StepVerifier.create(repository.existsById(Mono.just(sampleEntity.getId())))
				.expectNext(true)
				.verifyComplete();
	}

	@Test
	public void testCount() throws Exception {

		StepVerifier.create(repository.count())
				.expectNext(1L)
				.verifyComplete();
	}

	@Test
	public void testDeleteById() throws Exception {

		StepVerifier.create(repository.deleteById(sampleEntity.getId()))
				.verifyComplete();

		assertThat(session.countEntitiesOfType(SampleEntity.class)).isEqualTo(0);
	}

	@Test
	public void testDeleteByPublisher() throws Exception {

		StepVerifier.create(repository.deleteById(Flux.just(sampleEntity.getId())))
				.verifyComplete();

		assertThat(session.countEntitiesOfType(SampleEntity.class)).isEqualTo(0);
	}

	@Test
	public void testFindAll() throws Exception {

		StepVerifier.create(repository.findAll())
				.expectNext(sampleEntity)
				.verifyComplete();
	}

	@Test
	public void testFindAllByIdIterable() throws Exception {

		StepVerifier.create(repository.findAllById(Collections.singleton(sampleEntity.getId())))
				.expectNext(sampleEntity)
				.verifyComplete();
	}

	@Test
	public void testFindAllByIdPublisher() throws Exception {

		StepVerifier.create(repository.findAllById(Flux.just(sampleEntity.getId())))
				.expectNext(sampleEntity)
				.verifyComplete();
	}

	@Test
	public void testFindById() throws Exception {

		StepVerifier.create(repository.findById(sampleEntity.getId()))
				.expectNext(sampleEntity)
				.verifyComplete();
	}

	@Test
	public void testFindByIdPublisher() throws Exception {

		StepVerifier.create(repository.findById(Flux.just(sampleEntity.getId())))
				.expectNext(sampleEntity)
				.verifyComplete();
	}

	@Test
	public void testSave() throws Exception {

		SampleEntity newEntity = new SampleEntity("baz", "qux");

		StepVerifier.create(repository.save(newEntity))
				.expectNext(newEntity)
				.verifyComplete();

		assertThat(session.countEntitiesOfType(SampleEntity.class)).isEqualTo(2);
	}

	@Test
	public void testSaveWithDepth() throws Exception {
		// TODO
	}

	@Test
	public void testSaveAll() throws Exception {
		SampleEntity e1 = new SampleEntity("baz", "qux");
		SampleEntity e2 = new SampleEntity("qsd", "wxc");

		StepVerifier.create(repository.saveAll(Arrays.asList(e1, e2)))
				.expectNextCount(2)
				.verifyComplete();

		assertThat(session.countEntitiesOfType(SampleEntity.class)).isEqualTo(3);
	}

	@Test
	public void testSaveAllPublisher() throws Exception {
		SampleEntity e1 = new SampleEntity("baz", "qux");
		SampleEntity e2 = new SampleEntity("qsd", "wxc");

		StepVerifier.create(repository.saveAll(Flux.just(e1, e2)))
				.expectNextCount(2)
				.verifyComplete();

		assertThat(session.countEntitiesOfType(SampleEntity.class)).isEqualTo(3);
	}

	@Test
	public void testDelete() throws Exception {

		StepVerifier.create(repository.delete(sampleEntity))
				.verifyComplete();

		assertThat(session.countEntitiesOfType(SampleEntity.class)).isEqualTo(0);
	}

	@Test
	public void testDeleteAllIterable() throws Exception {

		StepVerifier.create(repository.deleteAll(Collections.singleton(sampleEntity)))
				.verifyComplete();

		assertThat(session.countEntitiesOfType(SampleEntity.class)).isEqualTo(0);
	}

	@Test
	public void testDeleteAllPublisher() throws Exception {

		StepVerifier.create(repository.deleteAll(Flux.just(sampleEntity)))
				.verifyComplete();

		assertThat(session.countEntitiesOfType(SampleEntity.class)).isEqualTo(0);
	}

	@Test
	public void testDeleteAll() throws Exception {

		StepVerifier.create(repository.deleteAll())
				.verifyComplete();

		assertThat(session.countEntitiesOfType(SampleEntity.class)).isEqualTo(0);
	}

	@Configuration
	@EnableReactiveNeo4jRepositories(basePackageClasses = ReactiveSampleEntityRepository.class)
	@EnableTransactionManagement
	public static class Config {

		@Bean
		public TransactionalRepositoryTests.DelegatingTransactionManager transactionManager() throws Exception {
			return new TransactionalRepositoryTests.DelegatingTransactionManager(new Neo4jTransactionManager(sessionFactory()));
		}

		@Bean
		public SessionFactory sessionFactory() {
			return new SessionFactory(getBaseConfiguration().build(),"org.springframework.data.neo4j.domain.sample");
		}
	}
}
