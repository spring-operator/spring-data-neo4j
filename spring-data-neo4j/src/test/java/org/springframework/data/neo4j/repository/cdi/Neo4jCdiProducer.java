/*
 * Copyright 2016 the original author or authors.
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

package org.springframework.data.neo4j.repository.cdi;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.data.neo4j.examples.friends.domain.Person;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

/**
 * Simple component exposing a {@link org.neo4j.ogm.session.Session} as CDI bean.
 *
 * @author Mark Paluch
 * @see DATAGRAPH-879
 */
class Neo4jCdiProducer {

    @Produces
    @ApplicationScoped
    Session createSession() {

        return null;
    }

    @Produces
    @ApplicationScoped
    @PersonDB
    @OtherQualifier
    Session createQualifiedSession() {
        return createSession();
    }
}
