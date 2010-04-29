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
import isbndb.RequestType;
import isbndb.net.HttpRequest;
import isbndb.net.HttpRequestFactory;
import isbndb.net.HttpResponse;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.*;


public class RestRequestorTest {
    private HttpRequestFactory httpRequestFactory;
    private RestRequestor requestor;
    private HttpRequest httpRequest;
    private HttpResponse httpResponse;

    @Test
    public void unsuccessfulRequest() {
        when(httpResponse.isSuccessful()).thenReturn(false);
        when(httpResponse.getCode()).thenReturn(10);
        when(httpResponse.getContentAsString()).thenReturn("content");
        when(httpRequest.getUrl()).thenReturn("url");
        try {
            requestor.request(new Request("access-key", RequestType.ISBN, "data"));
            fail();
        } catch (IsbnDbException err) {
            assertEquals("A problem contacting ISBN DB\n\nRequest:\nurl\n\nResponse:\nCode: 10\nContent:\ncontent\n", err.getMessage());
        }
    }

    @Test
    public void successfulRequest() {
        when(httpResponse.isSuccessful()).thenReturn(true);
        when(httpResponse.getContentAsString()).thenReturn("content");

        String response = requestor.request(new Request("access-key", RequestType.ISBN, "data"));

        assertNotNull(response);
        assertEquals("content", response);

        verify(httpRequest).addParameter("access_key", "access-key");
        verify(httpRequest).addParameter("index1", "isbn");
        verify(httpRequest).addParameter("value1", "data");
        verify(httpRequest).addParameter("results", "authors");
    }

    @Before
    public void setUp() throws Exception {
        httpRequestFactory = mock(HttpRequestFactory.class);
        httpRequest = mock(HttpRequest.class);
        httpResponse = mock(HttpResponse.class);

        requestor = new RestRequestor();
        requestor.setHttpRequestFactory(httpRequestFactory);

        when(httpRequestFactory.build("http://isbndb.com/api/books.xml")).thenReturn(httpRequest);
        when(httpRequest.doGet(false)).thenReturn(httpResponse);
    }
}
