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

import java.util.Iterator;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.parser.AbstractQueryCreator;
import org.springframework.data.repository.query.parser.Part;
import org.springframework.data.repository.query.parser.PartTree;
import org.springframework.lang.Nullable;

/**
 * An {@link AbstractQueryCreator} that builds a query template based on filters. The intermediate object is a filter
 * definition whose state is modified during query creation.
 *
 * @author Luanne Misquitta
 * @author Michael J. Simons
 */
class TemplatedQueryCreator extends AbstractQueryCreator<TemplatedQuery, FilterBuildersDefinition> {

	private final Class<?> entityType;

	public TemplatedQueryCreator(PartTree tree, Class<?> entityType) {
		super(tree);

		this.entityType = entityType;
	}

	@Override
	protected FilterBuildersDefinition create(Part part, Iterator<Object> iterator) {
		return FilterBuildersDefinition.forType(entityType) //
				.startWith(part);
	}

	@Override
	protected FilterBuildersDefinition and(Part part, FilterBuildersDefinition base, Iterator<Object> iterator) {
		return base.and(part);
	}

	@Override
	protected FilterBuildersDefinition or(FilterBuildersDefinition base, FilterBuildersDefinition criteria) {
		return base.or(criteria.getBasePart());
	}

	@Override
	protected TemplatedQuery complete(@Nullable FilterBuildersDefinition filterDefinition, Sort sort) {

		return Optional.ofNullable(filterDefinition)
				.map(FilterBuildersDefinition::buildTemplatedQuery)
				.orElseGet(TemplatedQuery::unfiltered);
	}
}
