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

package isbndb;

import isbndb.rest.RestRequestor;

public class BookLookup {
    private Requestor requestor = new RestRequestor();
    private ResponseParser responseParser = new XmlResponseParser();
    private String accessKey;

    public Book byIsbn(String isbnNumber) {
        String response = requestor.request(new Request(accessKey, RequestType.ISBN, isbnNumber));
        return responseParser.parse(response);
    }

    public void setRequestor(Requestor requestor) {
        this.requestor = requestor;
    }

    public void setResponseParser(ResponseParser responseParser) {
        this.responseParser = responseParser;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }
}
