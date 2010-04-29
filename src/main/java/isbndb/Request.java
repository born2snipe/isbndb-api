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


public class Request {
    public final String accessKey;
    public final RequestType type;
    public final String data;

    public Request(String accessKey, RequestType type, String data) {
        this.accessKey = accessKey;
        this.type = type;
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Request request = (Request) o;

        if (accessKey != null ? !accessKey.equals(request.accessKey) : request.accessKey != null) return false;
        if (data != null ? !data.equals(request.data) : request.data != null) return false;
        if (type != request.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = accessKey != null ? accessKey.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }
}
