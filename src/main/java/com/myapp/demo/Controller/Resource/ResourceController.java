package com.myapp.demo.Controller.Resource;

import org.springframework.web.bind.annotation.SessionAttributes;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;


@SessionAttributes("HP")
public abstract class ResourceController {
    public static String root = "https://fhir.monash.edu/hapi-fhir-jpaserver/fhir/";
    public static FhirContext ctx = FhirContext.forR4();
    public static IGenericClient client = ctx.newRestfulGenericClient(root);

    
    /** 
     * Render error page (usually invoked when there's error when accessing server)
     * @return String errorpage: the name of error page view (jsp)
     */
    public String errorPageGet() {
        return "ErrorPage";
    }

    

}