package com.appstore.contactboot.config;

//import org.apache.coyote.http11.AbstractHttp11JsseProtocol;
//import org.apache.tomcat.util.net.Nio2Channel;
//import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
//import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
//import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ConfigurationProperties ("spring.datasource")
@SuppressWarnings("unused")
public class DBConfig {
    private String driverClassName;
    private String url;
    private String username;
    private String password;

    @Profile("main")
    @Bean
    public String inMemDbConnection() {
        System.out.println(url);
        System.out.println(driverClassName);
        return "DB connection for 'main-profile'";
    }

    @Profile("embedded")
    @Bean
    public String fileDbConnection() {
        System.out.println(url);
        System.out.println(driverClassName);
        return "DB connection for 'embedded-profile'";
    }

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> containerCustomizer(){
        return new EmbeddedTomcatCustomizer();
    }

    private static class EmbeddedTomcatCustomizer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

        @Override
        public void customize(TomcatServletWebServerFactory factory) {
            factory.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> {
                connector.setAttribute("relaxedPathChars", "<>[\\]^`{|}");
                connector.setAttribute("relaxedQueryChars", "<>[\\]^`{|}");
            });
        }
    }
}
