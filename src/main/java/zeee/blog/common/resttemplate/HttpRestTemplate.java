package zeee.blog.common.resttemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.Callable;

/**
 * @author wz
 * @date 2022/12/13
 */
@Service
public class HttpRestTemplate {

    private static Logger log = LoggerFactory.getLogger(HttpRestTemplate.class);

    @Resource
    private RestTemplate restTemplate;

    public <T> ResponseEntity<T> exchange(
            String url, HttpMethod method, HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType) throws RestClientException {
        return restTemplate.exchange(url, method, requestEntity, responseType);
    }

    public <T> ResponseEntity<T> exchange(
            String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> responseType) throws RestClientException {
        return restTemplate.exchange(url, method, requestEntity, responseType);
    }

    // get方法
    public <T> ResponseEntity<T> getForEntity(String url, Class<T> t) {
        return this.exchange(url, HttpMethod.GET, null, t);
    }

    public <T> ResponseEntity<T> getForEntity(String url, ParameterizedTypeReference<T> responseTye) {
        return this.exchange(url, HttpMethod.GET, null, responseTye);
    }

    // post方法
    public <T> ResponseEntity<T> postForEntity(String url, Object o, Class<T> t) {
        HttpEntity<Object> entity = new HttpEntity<>(o);
        return this.exchange(url, HttpMethod.POST, entity, t);
    }

    public <T> ResponseEntity<T> postForEntity(String url, HttpEntity<T> entity, Class<T> t) {
        return this.exchange(url, HttpMethod.POST, entity, t);
    }

    public <T> ResponseEntity<T> postForEntity(String url, Object o, ParameterizedTypeReference<T> responseType) {
        HttpEntity<Object> entity = new HttpEntity<>(o);
        return this.exchange(url, HttpMethod.POST, entity, responseType);
    }

    // put方法
    public <T> ResponseEntity<T> putForEntity(String url, Object o, Class<T> t) {
        HttpEntity<Object> entity = new HttpEntity<>(o);
        return this.exchange(url, HttpMethod.PUT, entity, t);
    }

    // delete方法
    public <T> ResponseEntity<T> delete(String url, Object o) {
        HttpEntity<Object> entity = new HttpEntity<>(o);
        return this.exchange(url, HttpMethod.DELETE, entity, (Class<T>) null);
    }

    public <T> ResponseEntity<T> runWithSome(Callable<T> c) throws Throwable {
        // a demo, like runWithAuth
        int retryTime = 1;
        do {
            ResponseEntity resp = null;
            try {
                resp = (ResponseEntity) c.call();
            } catch (HttpClientErrorException e) {
                if (Objects.equals(e.getStatusCode(), HttpStatus.UNAUTHORIZED)) {
                    // do something, like refresh token
                    continue;
                } else {
                    return resp;
                }
            }
            if (resp != null && Objects.equals(resp.getStatusCode(), HttpStatus.UNAUTHORIZED)) {
                // do something, like refresh token
            } else {
                return resp;
            }
        } while (retryTime-- > 0);
        return null;
    }



//    private HttpEntity<?> wrapHeaders(HttpEntity<?> requestEntity) {
//        if (Objects.nonNull(requestEntity)) {
//            requestEntity = new HttpEntity<>(new HttpHeaders());
//        }
//        requestEntity =
//    }
}
