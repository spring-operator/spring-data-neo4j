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

import reactor.core.publisher.Mono;

import java.io.Serializable;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * Neo4j OGM specific extension of {@link org.springframework.data.repository.Repository}.
 * This is an alpha version, use at your own risk. Current implementation is un-optimized and not
 * production ready.
 *
 * @author Nicolas Mervaillie
 * @since 5.0
 */
@NoRepositoryBean
public interface ReactiveNeo4jRepository<T, ID extends Serializable> extends ReactiveCrudRepository<T, ID> {

	<S extends T> Mono<S> save(S s, int depth);

//  TODO : add graph specific variants (depth, filters, ...)
//	<S extends T> Iterable<S> save(Flux<S> entities, int depth);
//
//	Mono<T> findById(ID id, int depth);
//
//	Flux<T> findAll(int depth);
//
//	Flux<T> findAllById(Publisher<ID> ids, int depth);
}
