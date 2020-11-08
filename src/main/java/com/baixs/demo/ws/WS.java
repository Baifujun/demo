package com.baixs.demo.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface WS {
    @WebMethod
    String sayHi(String text);
}
