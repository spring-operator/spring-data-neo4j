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
package org.springframework.data.neo4j.repository.query.filter;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import org.neo4j.ogm.cypher.BooleanOperator;
import org.neo4j.ogm.cypher.ComparisonOperator;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.cypher.function.PropertyComparison;
import org.springframework.data.repository.query.parser.Part;

/**
 * @author Jasper Blues
 * @author Nicolas Mervaillie
 * @author Gerrit Meier
 * @author Michael J. Simons
 */
class BetweenComparisonBuilder extends FilterBuilder {

	BetweenComparisonBuilder(Part part, BooleanOperator booleanOperator, Class<?> entityType) {
		super(part, booleanOperator, entityType);
	}

	@Override
	public List<Filter> build(Stack<Object> params) {
		final Object value1 = params.pop();
		Filter gt = new Filter(propertyName(), ComparisonOperator.GREATER_THAN_EQUAL, value1);
		gt.setOwnerEntityType(entityType);
		gt.setBooleanOperator(booleanOperator);
		gt.setNegated(isNegated());
		gt.setFunction(new PropertyComparison(value1));
		setNestedAttributes(part, gt);

		final Object value2 = params.pop();
		Filter lt = new Filter(propertyName(), ComparisonOperator.LESS_THAN_EQUAL, value2);
		lt.setOwnerEntityType(entityType);
		lt.setBooleanOperator(BooleanOperator.AND);
		lt.setNegated(isNegated());
		lt.setFunction(new PropertyComparison(value2));
		setNestedAttributes(part, lt);

		return Arrays.asList(gt, lt);
	}
}
