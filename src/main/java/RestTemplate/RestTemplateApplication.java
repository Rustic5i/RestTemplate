package RestTemplate;

import RestTemplate.controler.RESTControler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import java.util.Arrays;
@SpringBootApplication
public class RestTemplateApplication {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigReactiveWebApplicationContext(RestTemplateApplication.class);
        RESTControler restControler = context.getBean(RESTControler.class);
        System.out.println("1. Список всех юзеров "+restControler.getProductList());
        System.out.println("2. Добавление пользователя "+restControler.postProduct());
        System.out.println("1. Список всех юзеров "+restControler.getProductList());
        System.out.println("3. Изменение пользователя "+restControler.putUser());
        System.out.println("4. Удаление пользователя "+restControler.deleteUser());
        System.out.println("1. Список всех юзеров "+restControler.getProductList());


    }
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() { // Это нужно для того что заработали HTTP методы DELETE UPDATE и тд
        return new HiddenHttpMethodFilter();
    }

    @Bean
    public HttpHeaders headers(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

}
