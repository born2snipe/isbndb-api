/**
 * Copyright to the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package isbndb.net;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class QueryStringBuilderTest {
    private QueryStringBuilder builder;

    @Before
    public void setUp() throws Exception {
        builder = new QueryStringBuilder();
    }

    @Test
    public void test_multipleParameters_asMap() {
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("key1", "value1");
        params.put("key2", "value2");

        builder.parameters(params);

        assertEquals("?key1=value1&key2=value2", builder.toString());
    }

    @Test
    public void test_multipleParameters() {
        builder.parameter("key1", "value1").parameter("key2", "value2");

        assertEquals("?key1=value1&key2=value2", builder.toString());
    }

    @Test
    public void test_noParameters() {
        assertEquals("", builder.toString());
    }

    @Test
    public void test_intParameter() {
        builder.parameter("key", 11);

        assertEquals("?key=11", builder.toString());
    }

    @Test
    public void test_stringParameter() {
        builder.parameter("key", "value");

        assertEquals("?key=value", builder.toString());
    }

    @Test
    public void test_encodeEachParameter() {
        builder.parameter("key", "long value");

        assertEquals("?key=long+value", builder.toString());
    }
}
