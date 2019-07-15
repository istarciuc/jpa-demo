package ro.esolutions.internship.jpaDemo.jpademo.repository.local;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import ro.esolutions.internship.jpaDemo.jpademo.repository.ConfigProfileInterface;


@Profile("local")
@Component
public class ConfigProfile implements ConfigProfileInterface {

    @Value("${config.value}")
    private String configValue;

    public void getConfig() {
        System.out.println(this.configValue);
    }
}
