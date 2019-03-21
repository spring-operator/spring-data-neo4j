/*
 * Copyright (c) 2018 "Neo4j, Inc." / "Pivotal Software, Inc."
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
package org.springframework.data.neo4j.domain.sample;

import java.util.UUID;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import org.neo4j.ogm.id.UuidStrategy;
import org.neo4j.ogm.typeconversion.UuidStringConverter;

/**
 * @author Michael J. Simons
 */
public class NodeWithUUIDAsId {

	private Long id;

	@Id @GeneratedValue(strategy = UuidStrategy.class) @Convert(UuidStringConverter.class) private UUID myNiceId;

	private String someProperty;

	public NodeWithUUIDAsId(String someProperty) {
		this.someProperty = someProperty;
	}

	public Long getId() {
		return id;
	}

	public UUID getMyNiceId() {
		return myNiceId;
	}

	public String getSomeProperty() {
		return someProperty;
	}
}
