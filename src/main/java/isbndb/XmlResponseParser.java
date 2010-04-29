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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;


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
        book.setIsbn(attribute(node, "isbn"));
        book.setIsbn13(attribute(node, "isbn13"));
        book.setTitle(child(node, "Title"));
        for (String authorName : authors(node)) {
            book.addAuthor(authorName);   
        }
        return book;
    }

    private List<String> authors(Node node) {
        NodeList children = node.getChildNodes();
        Node authorsNode = null;
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if ("Authors".equals(child.getNodeName())) {
                authorsNode = child;
                break;
            }
        }

        List<String> authors = new ArrayList<String>();
        if (authorsNode != null) {
            NodeList authorChildren = authorsNode.getChildNodes();
            for (int i = 0; i < authorChildren.getLength(); i++) {
                Node child = authorChildren.item(i);
                if ("Person".equals(child.getNodeName())) {
                    authors.add(child.getFirstChild().getNodeValue());
                }
            }
        }
        return authors;
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
