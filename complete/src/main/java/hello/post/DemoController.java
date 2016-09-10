package hello.post;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MyWorld on 2016/9/7.
 */
@RestController
public class DemoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "{userId}/tags/hobby", method = RequestMethod.POST)
    public String postValue(HttpServletRequest request, @Valid UserInfo userInfo, BindingResult bindingResult, @PathVariable("userId") String userId) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            return allErrors.toString();
        }
        String content = String.format("%s , code:%s , ids:%s , userId:%s ",
                RequestMethod.PUT, request.getHeader("code"), userInfo.getIds(), userId);
        LOGGER.info(content);
        return content;
    }

    @RequestMapping(value = "{userId}/tags/hobby", method = RequestMethod.PUT)
    public String putValue(HttpServletRequest request, @Valid UserInfo userInfo, BindingResult bindingResult, @PathVariable("userId") String userId) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            return allErrors.toString();
        }
        String content = String.format("%s , code:%s , ids:%s , userId:%s ",
                RequestMethod.PUT, request.getHeader("code"), userInfo.getIds(), userId);
        LOGGER.info(content);
        return content;
    }


    @RequestMapping(value = "/invoke/{userId}/post", method = RequestMethod.GET)
    public String restClient(HttpServletRequest request, @RequestParam String ids, @PathVariable("userId") String userId) {
        String headerKey = "code";
        HttpHeaders headers = new HttpHeaders();
        headers.add(headerKey, request.getHeader(headerKey));
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("ids", ids);
        HttpEntity httpEntity = new HttpEntity(body, headers);

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("userId", userId);

        return restTemplate.postForObject("http://localhost:8080/{userId}/tags/hobby", httpEntity, String.class, uriVariables);
    }

    @RequestMapping(value = "/invoke/{userId}/put", method = RequestMethod.GET)
    public String restClientPut(HttpServletRequest request, String ids, @PathVariable("userId") String userId) {
        String headerKey = "code";
        HttpHeaders headers = new HttpHeaders();
        headers.add(headerKey, request.getHeader(headerKey));
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("ids", ids);
        HttpEntity httpEntity = new HttpEntity(body, headers);

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("userId", userId);

        ResponseEntity<String> exchange = restTemplate.exchange("http://localhost:8080/{userId}/tags/hobby", HttpMethod.PUT, httpEntity, String.class, uriVariables);
        return exchange.getBody();
    }

}
