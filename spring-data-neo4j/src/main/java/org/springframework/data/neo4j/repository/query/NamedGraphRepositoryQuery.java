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
package org.springframework.data.neo4j.repository.query;

import org.neo4j.ogm.metadata.MetaData;
import org.neo4j.ogm.session.Session;
import org.springframework.data.repository.query.QueryMethodEvaluationContextProvider;

/**
 * Specialisation of {@link GraphRepositoryQuery} that creates queries from named queries defined in
 * {@code META-INFO/neo4j-named-queries.properties}.
 *
 * @author Gerrit Meier
 * @author Michael J. Simons
 */
public class NamedGraphRepositoryQuery extends GraphRepositoryQuery {

	private final String cypherQuery;

	NamedGraphRepositoryQuery(GraphQueryMethod graphQueryMethod, MetaData metaData, Session session, String cypherQuery,
			QueryMethodEvaluationContextProvider evaluationContextProvider) {

		super(graphQueryMethod, metaData, session, evaluationContextProvider);
		this.cypherQuery = cypherQuery;
	}

	@Override
	protected Query getQuery(Object[] parameters) {
		return new Query(cypherQuery, resolveParams(queryMethod.getParameters(), parameters));
	}

}
