package org.springframework.data.neo4j.repository.support;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.neo4j.ogm.session.Session;
import org.reactivestreams.Publisher;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.util.Assert;

/**
 * Alpha version of SDN reactive repository base implementation.
 *
 * Use at your own risk. Current implementation is unoptimized and not really reactive.
 * It just simulates a reactive behavior.
 *
 * @author Nicolas Mervaillie
 * @since 5.0
 */
public class SimpleReactiveNeo4jRepository<T, ID extends Serializable> implements ReactiveNeo4jRepository<T, ID> {

	private final Class<T> clazz;
	private final Session session;

	public SimpleReactiveNeo4jRepository(Class<T> domainClass, Session session) {
		Assert.notNull(domainClass, "Domain class must not be null!");
		Assert.notNull(session, "Session must not be null!");

		this.clazz = domainClass;
		this.session = session;
	}

	@Override
	public <S extends T> Mono<S> save(S entity) {

		Assert.notNull(entity, "The parameter entity must not be null");

		session.save(entity);
		return Mono.just(entity);
	}

	@Override
	public <S extends T> Mono<S> save(S entity, int depth) {

		Assert.notNull(entity, "The parameter entity must not be null");

		session.save(entity, depth);
		return Mono.just(entity);
	}

	@Override
	public <S extends T> Flux<S> saveAll(Iterable<S> entities) {

		Assert.notNull(entities, "The parameter entities must not be null");

		entities.forEach(session::save);
		return Flux.fromIterable(entities);
	}

	@Override
	public <S extends T> Flux<S> saveAll(Publisher<S> entityStream) {

		Assert.notNull(entityStream, "The parameter publisher must not be null");

		return Flux.from(entityStream)
				.flatMap(this::save);
	}

	@Override
	public Mono<T> findById(ID id) {

		Assert.notNull(id, "The parameter id must not be null");

		return Mono.just(session.load(clazz, id));
	}

	@Override
	public Mono<T> findById(Publisher<ID> id) {

		Assert.notNull(id, "The parameter id must not be null");

		return Mono.from(id)
				.flatMap(this::findById);
	}

	@Override
	public Mono<Boolean> existsById(ID id) {

		Assert.notNull(id, "The parameter id must not be null");

		return Mono.just(session.load(clazz, id) != null);
	}

	@Override
	public Mono<Boolean> existsById(Publisher<ID> id) {

		Assert.notNull(id, "The parameter id must not be null");

		return Mono.from(id)
				.flatMap(this::existsById);
	}

	@Override
	public Flux<T> findAll() {

		return Flux.fromIterable(session.loadAll(clazz));
	}

	@Override
	public Flux<T> findAllById(Iterable<ID> ids) {

		Assert.notNull(ids, "The parameter ids must not be null");

		List<ID> idList = StreamSupport.stream(ids.spliterator(), false)
				.collect(Collectors.toList());
		return Flux.fromIterable(session.loadAll(clazz, idList));
	}

	@Override
	public Flux<T> findAllById(Publisher<ID> ids) {

		Assert.notNull(ids, "The parameter ids must not be null");

		return Flux.from(ids).buffer().flatMap(this::findAllById);
	}

	@Override
	public Mono<Long> count() {

		return Mono.just(session.countEntitiesOfType(clazz));
	}

	@Override
	public Mono<Void> deleteById(ID id) {

		Assert.notNull(id, "The parameter id must not be null");

		T entity = session.load(clazz, id);
		return delete(entity);
	}

	@Override
	public Mono<Void> deleteById(Publisher<ID> id) {

		Assert.notNull(id, "The parameter id must not be null");

		return Flux.from(id).flatMap(this::deleteById).then();
	}

	@Override
	public Mono<Void> delete(T entity) {

		Assert.notNull(entity, "The parameter entity must not be null");

		session.delete(entity);
		return Mono.empty();
	}

	@Override
	public Mono<Void> deleteAll(Iterable<? extends T> entities) {

		Assert.notNull(entities, "The parameter entities must not be null");

		return Flux.fromIterable(entities).flatMap(this::delete).then();
	}

	@Override
	public Mono<Void> deleteAll(Publisher<? extends T> entities) {

		Assert.notNull(entities, "The parameter entities must not be null");

		return Flux.from(entities).flatMap(this::delete).then();
	}

	@Override
	public Mono<Void> deleteAll() {

		session.deleteAll(clazz);
		return Mono.empty();
	}

}
