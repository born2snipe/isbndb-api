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

import org.junit.Test;

import java.util.Arrays;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;


public class XmlResponseParserTest {
    @Test
    public void test() {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "\n" +
                "<ISBNdb server_time=\"2010-04-29T01:46:57Z\">\n" +
                "<BookList total_results=\"1\" page_size=\"10\" page_number=\"1\" shown_results=\"1\">\n" +
                "<BookData book_id=\"programming_web_services_with_perl\" isbn=\"0596002068\" isbn13=\"9780596002060\">\n" +
                "<Title>Programming Web services with Perl</Title>\n" +
                "<TitleLong></TitleLong>\n" +
                "<AuthorsText>Randy J. Ray and Pavel Kulchenko</AuthorsText>\n" +
                "<PublisherText publisher_id=\"oreilly\">Farnham ; O'Reilly, 2002 printing, c2003.</PublisherText>\n" +
                "<Authors>\n" +
                "<Person person_id=\"ray_randy_j\">Ray, Randy J.</Person>\n" +
                "<Person person_id=\"kulchenko_pavel\">Kulchenko, Pavel</Person>\n" +
                "</Authors>\n" +
                "</BookData>\n" +
                "</BookList>\n" +
                "</ISBNdb>";

        XmlResponseParser parser = new XmlResponseParser();

        Book book = parser.parse(xml);

        assertNotNull(book);
        assertEquals("0596002068", book.getIsbn());
        assertEquals("9780596002060", book.getIsbn13());
        assertEquals("Programming Web services with Perl", book.getTitle());
        assertEquals(Arrays.asList("Ray, Randy J.", "Kulchenko, Pavel"), book.getAuthors());
    }
}
