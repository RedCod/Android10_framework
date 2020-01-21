package com.test;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;


@Path("/person")
public class CtoFService {
	
	//http://localhost:8080/REST1/person
	@GET
	@Produces("application/xml")
	public String convertCtoF() {
 
		Double fahrenheit;
		Double celsius = 36.8;
		fahrenheit = ((celsius * 9) / 5) + 32;
 
		String result = "@Produces(\"application/xml\") Output: \n\nC to F Converter Output: \n\n" + fahrenheit;
		return "<ctofservice>" + "<celsius>" + celsius + "</celsius>" + "<ctofoutput>" + result + "</ctofoutput>" + "</ctofservice>";
	}
 
	//http://localhost:8080/REST1/person/44
	@Path("{c}")
	@GET
	@Produces("application/xml")
	public String convertCtoFfromInput(@PathParam("c") Double c) {
		Double fahrenheit;
		Double celsius = c;
		fahrenheit = c;//((celsius * 9) / 5) + 32;
		String result = "@Produces(\"application/xml\") Output: \n\nC to F Converter Output: \n\n" + fahrenheit;
		return "<ctofservice>" + "<celsius>" + celsius + "</celsius>" + "<ctofoutput>" + result + "</ctofoutput>" + "</ctofservice>";
	}

	//http://localhost:8080/REST1/person/get	
	@GET
	@Path("/get")
	@Produces("application/json")
	public Product getProduct() {
		Product product = new Product();
		product.setName("kerim Fırat");
		product.setAge(37);
		product.genelSelect2();
		return product;
	}
	
	
	//http://localhost:8080/REST1/person/get/kerim/89/engineer
	@GET
	@Path("/get/{name}/{age}/{career}")
	@Produces("application/json")
	public Response getProduct(@PathParam("name") String name,
			                  @PathParam("age") int age,
			                  @PathParam("career") String career) {
		Product product = new Product();
		product.setName(name);
		product.setAge(age);
		product.setCareer(career);
		//return product;
		return Response.status(200).entity(product).build();
	}
	
	//http://localhost:8080/REST1/person/get/kerim
    @GET
    @Path("/get/{param}")
    @Produces("application/json")
    public Response getMsg(@PathParam("param") String msg) {  
        String output = "Jersey say : " + msg;  
        return Response.status(200).entity(output).build();  
    }
    
    
    //http://localhost:8080/REST1/person/txt
    @GET  
    @Path("/txt")  
    @Produces("text/plain")  
    public Response getFile() {
    	/**
    	 * Dosya indirmek için. Genellikle Web_Page taraflı için geliştirilen bir fonks.
    	 */
    	String FILE_PATH = "/home/raptiye/Masaüstü/link.txt";
        File file = new File(FILE_PATH);   
        ResponseBuilder responseBuilder = Response.ok((Object) file);  
        responseBuilder.header("Content-Disposition","attachment; filename=\"javatpoint_file.txt\"");  
        return responseBuilder.build();  
    } 
    
    //************************** 
    @POST
	@Path("/post")
	//@Consumes("application/json")
    @Produces("application/json")
	public Response createProductInJSON(String product) {
		String result = "Product created : " + decodeValue(product);
		return Response.status(201).entity(result).build();
	}
    

    
    
    public static String decodeValue(String value) {
        try {
            return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }
	
}