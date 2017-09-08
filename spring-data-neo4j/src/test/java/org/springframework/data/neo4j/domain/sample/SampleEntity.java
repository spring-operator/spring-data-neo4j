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
package org.springframework.data.neo4j.domain.sample;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.util.Assert;

/**
 * @author Mark Angrish
 * @author Mark Paluch
 */
@NodeEntity
public class SampleEntity {

	@GraphId
	protected Long id;
	private String first;
	private String second;

	protected SampleEntity() {

	}

	public SampleEntity(String first, String second) {
		Assert.notNull(first, "First must not be null!");
		Assert.notNull(second, "Second mot be null!");
		this.first = first;
		this.second = second;
	}

	public Long getId() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		SampleEntity that = (SampleEntity) o;

		if (!id.equals(that.id)) return false;
		if (first != null ? !first.equals(that.first) : that.first != null) return false;
		return second != null ? second.equals(that.second) : that.second == null;
	}

	@Override
	public int hashCode() {
		int result = id.hashCode();
		result = 31 * result + (first != null ? first.hashCode() : 0);
		result = 31 * result + (second != null ? second.hashCode() : 0);
		return result;
	}
}
