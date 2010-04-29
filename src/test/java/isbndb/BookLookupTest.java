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

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class BookLookupTest {
    private BookLookup lookup;
    private Requestor requestor;
    private ResponseParser responseParser;

    @Test
    public void byIsbn() {
        Book expectedBook = new Book();

        when(requestor.request(new Request("access-key", RequestType.ISBN, "isbn-number"))).thenReturn("response");
        when(responseParser.parse("response")).thenReturn(expectedBook);

        Book book = lookup.byIsbn("isbn-number");

        assertNotNull(book);
        assertSame(expectedBook, book);
    }

    @Before
    public void setUp() throws Exception {
        requestor = mock(Requestor.class);
        responseParser = mock(ResponseParser.class);

        lookup = new BookLookup();
        lookup.setRequestor(requestor);
        lookup.setResponseParser(responseParser);
        lookup.setAccessKey("access-key");
    }
}
