package com.jay.liqui;

import com.jay.liqui.model.Role;
import com.jay.liqui.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import com.jay.liqui.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Component
public class UsrCmdRunner implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(UsrCmdRunner.class);


    @Autowired
    private UserService userService;


    @Override
    public void run(String... arg0) throws Exception {
        log.info("Started running");

        System.out.println("Started from Runner 01");

        Sort sort;
        Pageable pageable = PageRequest.of(0, 10);

        //Page<Aradhak> page = aradhakRepo.findAll(pageable);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            User usr01 = new User();
            //usr01.setId(999);
            usr01.setName("jayendra");
            usr01.setUsername("jayendra");
            usr01.setEnabled(true);
            usr01.setRole(Role.ADMIN);
            usr01.setPassword(passwordEncoder.encode("titan123"));
            //  String password = "titan123";
            usr01.setEmail("sjayendra@gmail.com");
            userService.saveUser(usr01);



    }
}

