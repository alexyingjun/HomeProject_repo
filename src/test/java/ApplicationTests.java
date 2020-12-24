import com.iocoder.demo01.Application;
import com.iocoder.demo01.springdemo.properties.BlogProperties;
import com.iocoder.demo01.springdemo.properties.HomeProperties;
import com.iocoder.demo01.springdemo.properties.UserProperties;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringJUnit4ClassRunner.class)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ApplicationTests {
    @Autowired
    private BlogProperties blogProperties;

    @Autowired
    private HomeProperties homeProperties;

    @Autowired
    private UserProperties userProperties;

    @Test
    public void getHello() {
        Assert.assertEquals(blogProperties.getName(), "devName");
        Assert.assertEquals(blogProperties.getTitle(), "Spring Boot Tutorial");
        Assert.assertEquals(blogProperties.getDesc(), "devName is writing <<Spring Boot Tutorial>>");
    }

    @Test
    public void testProperties(){
        System.out.println(homeProperties);
        System.out.println(userProperties);
    }
}