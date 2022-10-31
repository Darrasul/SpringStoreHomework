package com.buzas.springstorehomework.web.endpoints;

import com.buzas.springstorehomework.entities.products.GetAllProductRequest;
import com.buzas.springstorehomework.entities.products.GetAllProductResponse;
import com.buzas.springstorehomework.web.services.WebService;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
@RequiredArgsConstructor
public class ProductsEndpoint {
    private static final String NAMESPACE_URI = "http://www.buzas.com/springstorehomework/entities/products";
    private final WebService webService;
/*
    Address:
     http://localhost/SpringStore/ws
    Headers:
     Content-Type: text/xml
    Body:
     <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope" xmlns:f="http://www.buzas.com/springstorehomework/entities/products">
     <soapenv:Header/>
     <soapenv:Body>
         <f:getAllProductRequest/>
     </soapenv:Body>
     <soapenv:Envelope>
*/

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductRequest")
    @ResponsePayload
    public GetAllProductResponse getAllProductRequest(@RequestPayload GetAllProductRequest request) {
        GetAllProductResponse response = new GetAllProductResponse();
        webService.findAll().forEach(response.getProducts()::add);
        return response;
    }
}
