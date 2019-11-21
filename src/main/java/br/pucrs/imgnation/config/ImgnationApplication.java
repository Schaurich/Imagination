package br.pucrs.imgnation.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages ={"br.pucrs.imgnation"})
public class ImgnationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImgnationApplication.class, args);
    }

}
