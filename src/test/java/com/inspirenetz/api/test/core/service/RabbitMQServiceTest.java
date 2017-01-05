package com.inspirenetz.api.test.core.service;

import com.google.common.collect.Sets;
import com.inspirenetz.api.core.domain.Brand;
import com.inspirenetz.api.core.repository.BrandRepository;
import com.inspirenetz.api.core.service.BrandService;
import com.inspirenetz.api.rest.exception.InspireNetzException;
import com.inspirenetz.api.test.config.*;
import com.inspirenetz.api.test.core.fixture.BrandFixture;
import com.inspirenetz.api.test.util.ControllerTestUtils;
import com.inspirenetz.api.util.GeneralUtils;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by sandheepgr on 30/4/14.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes  = {ApplicationTestConfig.class,ServiceTestConfig.class, PersistenceTestConfig.class,SecurityTestConfig.class, RabbitMQTestConfig.class})
public class RabbitMQServiceTest {


    private static Logger log = LoggerFactory.getLogger(RabbitMQServiceTest.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Environment environment;

    @Autowired
    private AmqpAdmin rabbitAdmin;

    @Test
    public void testReadSalesFastData() {

        // Get the fastdata queue name
        String fastdataName = environment.getProperty("rmq.fastdataqueuename");
        // Declare the data
        HashMap<String,Object> hashMap = new HashMap<>(0);

        // get the queue name from the property
        rabbitTemplate.convertAndSend(fastdataName,hashMap);

        // Log the information
        log.info("Data added to fastdata queue");

    }


    @Test
    public void purgeFastdataQueue() {

        // Get the fastdata queue name
        String fastdataName = environment.getProperty("rmq.fastdataqueuename");

        // Purge the queue
        rabbitAdmin.purgeQueue(fastdataName,true);

    }

}
