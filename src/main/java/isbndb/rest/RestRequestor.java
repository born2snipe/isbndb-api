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

package isbndb.rest;

import isbndb.IsbnDbException;
import isbndb.Request;
import isbndb.Requestor;
import isbndb.net.HttpRequest;
import isbndb.net.HttpRequestFactory;
import isbndb.net.HttpResponse;


public class RestRequestor implements Requestor {
    private static final String BASE_URL = "http://isbndb.com/api/books.xml";
    private HttpRequestFactory httpRequestFactory = new HttpRequestFactory();

    public String request(Request request) throws IsbnDbException {
        HttpRequest httpRequest = httpRequestFactory.build(BASE_URL);
        httpRequest.addParameter("access_key", request.accessKey);
        httpRequest.addParameter("index1", "isbn");
        httpRequest.addParameter("value1", request.data);
        httpRequest.addParameter("results", "authors");

        HttpResponse httpResponse = httpRequest.doGet(false);
        if (!httpResponse.isSuccessful()) {
            StringBuilder builder = new StringBuilder();
            builder.append("A problem contacting ISBN DB\n\n");
            builder.append("Request:\n").append(httpRequest.getUrl()).append("\n\n");
            builder.append("Response:\n").append("Code: ").append(httpResponse.getCode()).append("\n");
            builder.append("Content:\n").append(httpResponse.getContentAsString()).append("\n");
            throw new IsbnDbException(builder.toString());
        }
        return httpResponse.getContentAsString();
    }

    public void setHttpRequestFactory(HttpRequestFactory httpRequestFactory) {
        this.httpRequestFactory = httpRequestFactory;
    }
}
