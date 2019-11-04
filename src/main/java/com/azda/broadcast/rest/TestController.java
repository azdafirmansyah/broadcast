package com.azda.broadcast.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/api/v1")
@RestController
public class TestController {

    private static final Logger logger = LogManager.getLogger(TestController.class);

    @RequestMapping(method = RequestMethod.GET, path = "/abc")
    public String abc(@RequestParam(value = "keyword",defaultValue="") String param,
                                         HttpServletRequest request){

        String remoteAddress = request.getRemoteAddr();

        logger.info("Incoming Request from : "+remoteAddress);

        String result = "";
        try{
            result = "112113";
        }catch (Exception e){
            logger.error("Error " + e);
        }

        logger.info("Outgoing Response  to : "+remoteAddress+ " with value : " + result);
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/def")
    public String def(@RequestParam(value = "keyword",defaultValue="") String param,
                                         HttpServletRequest request){

        String remoteAddress = request.getRemoteAddr();

        System.out.println("Incoming Request  from : "+remoteAddress);

        String result = "";
        try{
            result = "423423";
        }catch (Exception e){
            System.out.println("Error " + e);
        }

        System.out.println("Outgoing Response to : "+remoteAddress+ " with value : " + result);
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/hello")
    public String firstPage() {
        logger.info("Hello First Page ");
        return "Hello World";
    }
}
