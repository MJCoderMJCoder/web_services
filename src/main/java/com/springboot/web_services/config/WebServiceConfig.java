package com.springboot.web_services.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

/**
 * It’s important to notice that you need to specify bean names for MessageDispatcherServlet and DefaultWsdl11Definition.
 * Bean names determine the URL under which web config and the generated WSDL file is available.
 * In this case, the WSDL will be available under http://<host>:<port>/ws/countries.wsdl.
 */
@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {
    /**
     * 服务器启动时调用顺序：第一
     * Spring WS uses a different servlet type for handling SOAP messages: MessageDispatcherServlet.
     * It is important to inject and set ApplicationContext to MessageDispatcherServlet.
     * Without that, Spring WS will not detect Spring beans automatically.
     * <p>
     * By naming this bean messageDispatcherServlet, it does not replace Spring Boot’s default DispatcherServlet bean.\
     * <p>
     * This configuration also uses the WSDL location servlet transformation servlet.setTransformWsdlLocations(true).
     * If you visit http://localhost:8080/ws/countries.wsdl, the soap:address will have the proper address.
     * If you instead visit the WSDL from the public facing IP address assigned to your machine, you will see that address instead.
     *
     * @param applicationContext
     * @return
     */
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        System.out.println("WebServiceConfig-messageDispatcherServlet");
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/ws/*");
    }

    /**
     * 服务器启动时调用顺序：第五
     * DefaultMethodEndpointAdapter configures annotation driven Spring WS programming model.
     * This makes it possible to use the various annotations like @Endpoint mentioned earlier.
     * <p>
     * DefaultWsdl11Definition exposes a standard WSDL 1.1 using XsdSchema
     *
     * @param countriesSchema
     * @return
     */
    @Bean(name = "countries")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema countriesSchema) {
        System.out.println("WebServiceConfig-defaultWsdl11Definition");
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("CountriesPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://spring.io/guides/gs-producing-web-service");
        wsdl11Definition.setSchema(countriesSchema);
        return wsdl11Definition;
    }

    /**
     * 服务器启动时调用顺序：第四
     *
     * @return
     */
    @Bean
    public XsdSchema countriesSchema() {
        System.out.println("WebServiceConfig-countriesSchema");
        return new SimpleXsdSchema(new ClassPathResource("countries.xsd"));
    }
}
