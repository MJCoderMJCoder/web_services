package com.springboot.web_services.dao;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import sun.rmi.runtime.Log;
import xmlschema_jaxb.Country;
import xmlschema_jaxb.Currency;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class CountryRepository {
    private static final Map<String, Country> countries = new HashMap<String, Country>();

    /**
     * 服务器启动时调用顺序：第二
     */
    @PostConstruct
    public void initData() {
        System.out.println("CountryRepository-initData");
        Country spain = new Country();
        spain.setName("Spain");
        spain.setCapital("Madrid");
        spain.setCurrency(Currency.EUR);
        spain.setPopulation(46704314);

        countries.put(spain.getName(), spain);

        Country poland = new Country();
        poland.setName("Poland");
        poland.setCapital("Warsaw");
        poland.setCurrency(Currency.PLN);
        poland.setPopulation(38186860);

        countries.put(poland.getName(), poland);

        Country uk = new Country();
        uk.setName("United Kingdom");
        uk.setCapital("London");
        uk.setCurrency(Currency.GBP);
        uk.setPopulation(63705000);

        countries.put(uk.getName(), uk);
    }

    /**
     * 客户端请求时调用顺序：第二
     *
     * @param name
     * @return
     */
    public Country findCountry(String name) {
        System.out.println("CountryRepository-findCountry");
        Assert.notNull(name, "The country's name must not be null");
        return countries.get(name);
    }
}
