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

package org.springframework.data.neo4j.repository.support;

import org.neo4j.ogm.session.Session;
import org.springframework.data.repository.core.RepositoryMetadata;

/**
 * Factory to create {@link org.springframework.data.neo4j.repository.ReactiveNeo4jRepository} instances.
 *
 * @author Nicolas Mervaillie
 * @since 5.0
 */
public class ReactiveNeo4jRepositoryFactory extends Neo4jRepositoryFactory {

	/**
	 * Creates a new {@link ReactiveNeo4jRepositoryFactory} with the given {@link Session}.
	 *
	 * @param session must not be {@literal null}.
	 */
	public ReactiveNeo4jRepositoryFactory(Session session) {

		super(session);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.repository.core.support.RepositoryFactorySupport#getRepositoryBaseClass(org.springframework.data.repository.core.RepositoryMetadata)
	 */
	@Override
	protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
		return SimpleReactiveNeo4jRepository.class;
	}

}
