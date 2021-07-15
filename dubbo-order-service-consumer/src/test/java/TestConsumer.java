import com.bwing.service.OrderService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class TestConsumer {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("consumer.xml");
        applicationContext.start();
        OrderService orderService = applicationContext.getBean(OrderService.class);
        orderService.initOrder(1);
        System.out.println("______");
        System.in.read();
    }
}
