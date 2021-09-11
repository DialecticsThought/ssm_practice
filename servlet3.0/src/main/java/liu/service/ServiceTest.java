package liu.service;

import org.springframework.stereotype.Service;

@Service
public class ServiceTest {

    public String sayHello(String name){
        return "Hello"+name;
    }
}
