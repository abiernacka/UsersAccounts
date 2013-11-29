package pl.edu.agh.useraccounts.service;

import javax.jws.WebService;

/**
 * Created with IntelliJ IDEA.
 * User: Khajiit
 * Date: 29.11.13
 * Time: 10:57
 * To change this template use File | Settings | File Templates.
 */

@WebService(endpointInterface = "pl.edu.agh.useraccounts.service.HelloWorldService")
public class HelloWorldServiceBean implements HelloWorldService {

    @Override
    public String sayHello() {
        return "Hello!";
    }

    @Override
    public String sayHello(String name) {
        return "Hello, " + name + "!";
    }
}
