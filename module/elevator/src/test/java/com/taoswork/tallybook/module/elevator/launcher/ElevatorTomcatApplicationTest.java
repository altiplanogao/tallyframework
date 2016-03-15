package com.taoswork.tallybook.module.elevator.launcher;

import com.taoswork.tallybook.module.elevator.def.ElevatorModule;
import com.taoswork.tallybook.module.IModule;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by Gao Yuan on 2016/3/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration({ElevatorTomcatTestApplication.class})
@WebIntegrationTest("server.port:" + ElevatorTomcatTestApplication.PORT)
@DirtiesContext
public class ElevatorTomcatApplicationTest {
    @Value("${local.server.port}")
    private int port;

    @Resource(name = ElevatorTomcatTestApplication.MODULE_RPC_IN_HTTPINVOKER)
    private IModule moduleInHttp;

    @Resource(name = ElevatorTomcatTestApplication.MODULE_RPC_IN_RMI)
    private IModule moduleInRmi;

    @Test
    public void testHome() throws Exception {
        ResponseEntity<String> entity = new TestRestTemplate()
                .getForEntity("http://localhost:" + this.port, String.class);
        Assert.assertEquals(entity.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(entity.getBody(), "Hello Elevator Module");

        {
            String fullNameFromProxy = moduleInHttp.getFullName();
            Assert.assertEquals(ElevatorModule.FULLNAME, fullNameFromProxy);
        }
        {
            String fullNameFromProxy = moduleInRmi.getFullName();
            Assert.assertEquals(ElevatorModule.FULLNAME, fullNameFromProxy);
        }
    }

}
