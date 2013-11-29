package pl.edu.agh.useraccounts.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Created with IntelliJ IDEA.
 * User: Khajiit
 * Date: 29.11.13
 * Time: 10:57
 * To change this template use File | Settings | File Templates.
 */
@WebService
public interface HelloWorldService {
    @WebMethod(operationName = "sayHello")
    public String sayHello();

    @WebMethod(operationName = "sayHelloTo")
    public String sayHello(String name);
}
