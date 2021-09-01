package RestTemplate.controler;

import RestTemplate.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.stream.Collectors;

@Component
public class RESTControler {

    private HttpHeaders headers;
    private RestTemplate restTemplate;
    private final String URL = "http://91.241.64.178:7081/api/users";
    private String cookie;

    @Autowired
    public RESTControler(HttpHeaders headers, RestTemplate restTemplate) {
        this.headers = headers;
        this.restTemplate = restTemplate;

    }
    @PostConstruct //Spring вызывает методы, аннотированные @PostConstruct, только один раз, сразу после инициализации
    public void init(){
        this.headers.set("Cookie",String.join(";",restTemplate.headForHeaders(URL).get("Set-Cookie"))); //получаем из Cookie id session\
    }



    //1. Получение всех пользователей - …/api/users ( GET )
    public String getProductList() {
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> result = restTemplate.exchange(URL, HttpMethod.GET, entity, String.class);
//        cookie = result.getHeaders().get("Set-Cookie").stream().collect(Collectors.joining(";")); // Получаем id session
        return result.getBody();
    }

    //2. Добавление пользователя - …/api/users ( POST )
    public String postProduct() {
//        headers.set("Cookie", cookie);
        HttpEntity<User> entity = new HttpEntity<>(newUser(), headers);
        ResponseEntity<String> result = restTemplate.exchange(URL, HttpMethod.POST, entity, String.class);
        System.out.println("Новый куки ??" + result.getHeaders().get("Set-Cookie"));
        return result.getBody();
    }

    //3. Изменение пользователя - …/api/users ( PUT )
    public String putUser() {
//        headers.set("Cookie", cookie);
        HttpEntity<User> entity = new HttpEntity<>(updateUser(), headers);
        ResponseEntity<String> result = restTemplate.exchange(URL, HttpMethod.PUT, entity, String.class);
        return result.getBody();
    }

    //4. Удаление пользователя -… / api / users / { id } ( УДАЛИТЬ )
    public String deleteUser() {
//        headers.set("Cookie", cookie);
        HttpEntity<User> entity = new HttpEntity<>(headers);
        ResponseEntity<String> result = restTemplate.exchange(URL + "/" + updateUser().getId(), HttpMethod.DELETE, entity, String.class);
        return result.getBody();
    }

    private User newUser() {
        User user = new User();
        user.setId(3L);
        user.setAge((byte) 20);
        user.setLastName("Brown");
        user.setName("James");
        return user;
    }

    private User updateUser() {
        User updaneUser = newUser();
        updaneUser.setLastName("Shelby");
        updaneUser.setName("Thomas");
        return updaneUser;
    }

}
