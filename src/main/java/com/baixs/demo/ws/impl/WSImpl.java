package com.baixs.demo.ws.impl;

import com.baixs.demo.ws.WS;

import javax.jws.WebService;

@WebService(endpointInterface = "com.baixs.demo.ws.WS")
public class WSImpl implements WS {
    @Override
    public String sayHi(String text) {
        return text;
    }
}
