package com.springboot.web_services.point;

import com.springboot.web_services.dao.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import xmlschema_jaxb.GetCountryRequest;
import xmlschema_jaxb.GetCountryResponse;

/**
 * @Endpoint registers the class with Spring WS as a potential candidate for processing incoming SOAP messages.
 */
@Endpoint
public class CountryEndpoint {
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    private CountryRepository countryRepository;

    /**
     * 服务器启动时调用顺序：第三
     *
     * @param countryRepository
     */
    @Autowired
    public CountryEndpoint(CountryRepository countryRepository) {
        System.out.println("CountryEndpoint-CountryEndpoint");
        this.countryRepository = countryRepository;
    }

    /**
     * 客户端请求时调用顺序：第一
     *
     * @param request
     * @return
     */
    //    @PayloadRoot is then used by Spring WS to pick the handler method based on the message's namespace and localPart.
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
    //    The @ResponsePayload annotation makes Spring WS map the returned value to the response payload.
    @ResponsePayload
    //    @RequestPayload indicates that the incoming message will be mapped to the method's request parameter.
    public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request) {
        System.out.println("CountryEndpoint-getCountry");
        GetCountryResponse response = new GetCountryResponse();
        response.setCountry(countryRepository.findCountry(request.getName()));
        return response;
    }
}
