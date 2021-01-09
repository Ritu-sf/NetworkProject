package com.ajira.network;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import com.ajira.network.controller.AjiraController;
import com.ajira.network.service.NetworkService;


@RunWith(SpringRunner.class)
@WebMvcTest(value = AjiraController.class)
public class AjiraControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private NetworkService networkService;

	@Test
	public void createDeviceList() throws Exception {
		
	      JSONObject testjson = new JSONObject();
	      
	      testjson.put("name", "R1");
	      testjson.put("type", "REPEATER");
	      
	      JSONObject expected = new JSONObject();
	      expected.put("msg", "Successfully added R1");
	     
	      Mockito.when(
				networkService.addDevice(Mockito.anyString(), Mockito.anyString()))
	      .thenReturn("Successfully added R1");

	      RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/createDevices")
				.accept(MediaType.APPLICATION_JSON)
				.content(testjson.toJSONString())
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		 String content = result.getResponse().getContentAsString();
		// System.out.println(content);
		 assertEquals(content, expected.toJSONString());
		 assertEquals(200, response.getStatus());

		

	}
	
	@Test
	public void fetchrouteInfo() throws Exception {
		
	      JSONObject testjson = new JSONObject();
	      
	      testjson.put("source", "C1");
	      testjson.put("target", "C4");
	      
	      JSONObject expected = new JSONObject();
	      expected.put("msg", "C1 -> C2 -> C3 -> R1 -> C4");
	     
	      Mockito.when(
				networkService.routeInfo(Mockito.anyString(), Mockito.anyString()))
	      .thenReturn("C1 -> C2 -> C3 -> R1 -> C4");

	      RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/routeInfo")
				.accept(MediaType.APPLICATION_JSON)
				.content(testjson.toJSONString())
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		 String content = result.getResponse().getContentAsString();
		 //System.out.println(content);
		 assertEquals(content, expected.toJSONString());
		 assertEquals(200, response.getStatus());

		

	}
	
	

	@Test
	public void createConnections() throws Exception {
		
	      JSONObject testjson = new JSONObject();
	      List<String> targets = new ArrayList<>();
	      targets.add("C2");
	      targets.add("C3");
	      testjson.put("source", "C1");
	      testjson.put("targets", targets);
	      
	      JSONObject expected = new JSONObject();
	      expected.put("msg", "Successfully connected");
	      
	      Mockito.when(
	    		  networkService.connectDevice(
							Mockito.anyString(),Mockito.anyList())).thenReturn("Successfully connected");

	      RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/createConnections")
				.accept(MediaType.APPLICATION_JSON)
				.content(testjson.toJSONString())
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		 String content = result.getResponse().getContentAsString();
		// System.out.println(content);
		 assertEquals(content, expected.toJSONString());
		 assertEquals(200, response.getStatus());
	}
	
	
	@Test
	public void devices() throws Exception {

		 Mockito.when(
				 networkService.listDevices(
							)).thenReturn("\n{\n\t'type': COMPUTER,\n\tname: C1\n}");

			RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
					"/devices").accept(
					MediaType.APPLICATION_JSON);

			MvcResult result = mockMvc.perform(requestBuilder).andReturn();

			//System.out.println(result.getResponse().getContentAsString());
			String expected = "{type:COMPUTER,name:C1}";

			JSONAssert.assertEquals(expected, result.getResponse()
					.getContentAsString(), false);
		

	}
	

	@Test
	public void modify() throws Exception {
		
	      JSONObject testjson = new JSONObject();
	      
	      testjson.put("deviceName", "R1");
	      testjson.put("strength", 2);
	      
	      JSONObject expected = new JSONObject();
	      expected.put("msg", "Successfully defined strength");

	      Mockito.when(
				networkService.modifyStrength(Mockito.anyString(),Mockito.anyInt(),Mockito.anyBoolean()))
	      .thenReturn("Successfully defined strength");

	      RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/modify")
				.accept(MediaType.APPLICATION_JSON)
				.content(testjson.toJSONString())
				.characterEncoding("utf-8")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		 String content = result.getResponse().getContentAsString();
		// System.out.println(content);
		 assertEquals(content, expected.toJSONString());
		 assertEquals(200, response.getStatus());
	}


}
