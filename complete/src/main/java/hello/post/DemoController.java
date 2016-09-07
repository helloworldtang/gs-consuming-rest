package hello.post;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by MyWorld on 2016/9/7.
 */
@RestController
public class DemoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/tags/hobby", method = RequestMethod.POST)
    public String putValue(HttpServletRequest request, @Valid UserId userId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            return allErrors.toString();
        }
        String content = request.getHeader("code") + ";" + userId.getIds();
        LOGGER.info(content);
        return content;
    }


    @RequestMapping(value = "/invoke", method = RequestMethod.GET)
    public String restClient(HttpServletRequest request, String ids) {

        HttpHeaders headers = new HttpHeaders();
        String headerKey = "code";
        headers.add(headerKey, request.getHeader(headerKey));

        HttpEntity httpEntity = new HttpEntity(headers);
        return restTemplate.postForObject("http://localhost:8080/tags/hobby?ids=" + ids, httpEntity, String.class);
    }

}
