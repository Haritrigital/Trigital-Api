package com.trigital.cm.api.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import scala.collection.immutable.Page;

import com.trigital.cm.domain.MACDetails;
import com.trigital.cm.exception.DataFormatException;
import com.trigital.cm.service.MACDetailsService;
import com.trigital.cm.service.PropertyManager;
import com.trigital.cm.service.TelnetCommandExecutor;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/*
 * Demonstrates how to set up RESTful API endpoints using Spring MVC
 */

@RestController
@RequestMapping(value = "/cablemodem")
@Api(value = "hotels", description = "Hotel API")
public class MACController extends AbstractRestHandler {
	
	private static final Logger log = LoggerFactory.getLogger(MACController.class);

    @Autowired
    private MACDetailsService macDetailsService;
    
    @Autowired
    private PropertyManager propertyManager;

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a hotel resource.", notes = "Returns the URL of the new resource in the Location header.")
    public
    @ResponseBody
    ResponseEntity processCMTSDetails(@RequestBody MACDetails macDetails,
                                 HttpServletRequest request, HttpServletResponse response) {
    	
    	System.out.println("Ip Address "+macDetails.getIpDetails().getIp_Address());
    	System.out.println("Mac Address "+macDetails.getMac_Address());
    	System.out.println("Command "+macDetails.getCommand());
    	System.out.println("Make "+macDetails.getIpDetails().getCmts_make());
    	return this.success(this.macDetailsService.getMACDetails(macDetails));
    }

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a list of cmtsips from properties files", notes = "This is a list of CMTSIP's. Enter list of CMTS IP in Properties.ini file with || as separator")
    public
    @ResponseBody
    ResponseEntity getCMTSIP(HttpServletRequest request, HttpServletResponse response) {
    	log.info("Loding CMTS IP's from Properties");
    	
        return this.success("hello world");
    }
    
    public ResponseEntity success(final Object obj) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        return new ResponseEntity(obj, responseHeaders, HttpStatus.OK);
    }


    
}
