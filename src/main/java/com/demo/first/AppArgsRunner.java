package com.demo.first;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

//Application runner is also an interface which is used when you want to run
//custom logic on application startup but here we get past application argument object
//using which we can retrieve those options which were passed before running application
@Component
public class AppArgsRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Options: "+ args.getOptionNames());
    }
}
