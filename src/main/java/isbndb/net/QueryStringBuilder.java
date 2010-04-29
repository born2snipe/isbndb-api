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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;


public class QueryStringBuilder {
    private StringBuilder builder = new StringBuilder();
    private boolean firstParameter = true;

    public QueryStringBuilder parameter(String key, String value) {
        if (firstParameter) {
            builder.append("?");
            firstParameter = false;
        } else {
            builder.append("&");
        }
        builder.append(key).append("=").append(encode(value));
        return this;
    }

    public QueryStringBuilder parameter(String key, int value) {
        parameter(key, String.valueOf(value));
        return this;
    }

    public String toString() {
        return builder.toString();
    }

    private String encode(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public void parameters(Map<String, String> params) {
        for (Map.Entry<String, String> entry : params.entrySet()) {
            parameter(entry.getKey(), entry.getValue());
        }
    }

}
