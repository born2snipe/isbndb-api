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

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;


public class XmlResponseParser implements ResponseParser {
    public Book parse(String response) {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(response.getBytes()));
            return parseBook(document.getDocumentElement());
        } catch (Exception e) {
            throw new IsbnDbException("A problem occured while parsing response\n" + response, e);
        }
    }

    private Book parseBook(Element document) {
        Book book = new Book();
        NodeList nodeList = document.getElementsByTagName("BookData");
        Node node = nodeList.item(0);
        NamedNodeMap attributes = node.getAttributes();
        book.setIsbn(attribute(node, "isbn"));
        book.setIsbn13(attribute(node, "isbn13"));
        book.setTitle(child(node, "Title"));
        return book;
    }

    private String attribute(Node node, String name) {
        return node.getAttributes().getNamedItem(name).getTextContent();
    }

    private String child(Node node, String name) {
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (name.equals(child.getNodeName())) {
                return child.getFirstChild().getNodeValue();
            }
        }
        return "";
    }
}
