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
package org.springframework.data.neo4j.mapping;

import org.neo4j.ogm.metadata.MetaData;

/**
 * The only meta-data provider currently is a Neo4j-OGM {@link org.neo4j.ogm.session.SessionFactory} by proxy. The
 * meta-data is needed during execution of graph-repository-queries to lookup mappings etc.
 *
 * @author Michael J. Simons
 * @since 5.1.2
 */
@FunctionalInterface
public interface MetaDataProvider {
	MetaData getMetaData();
}
