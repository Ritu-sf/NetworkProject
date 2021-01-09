package com.ajira.network.controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ajira.network.entity.CreateConnectionRequest;
import com.ajira.network.service.NetworkService;

@RestController
public class AjiraController {

	JSONParser parser = new JSONParser();  
	@Autowired
	private NetworkService networkService;

	@GetMapping("/devices")
	public String fetchDeviceList() {
		return networkService.listDevices();
	}  

	@PostMapping("/createDevices")
	public JSONObject createDeviceList(@RequestBody JSONObject deviceDetail){
		String msg = networkService.addDevice(deviceDetail.get("name").toString(), deviceDetail.get("type").toString());
		JSONObject obj = new JSONObject();
		obj.put("msg", msg);
		return obj;
	}

	@PostMapping("/modify")
	public JSONObject modifyStrength(@RequestBody JSONObject modifyStrength) {
		String msg = networkService.modifyStrength(modifyStrength.get("deviceName").toString(),Integer.parseInt(modifyStrength.get("strength").toString()), false);
		JSONObject obj = new JSONObject();
		obj.put("msg", msg);
		return obj;
		}

	@PostMapping("/createConnections")
	public JSONObject createConnections(@RequestBody CreateConnectionRequest createConnection) {
		String msg =  networkService.connectDevice(createConnection.getSource(), createConnection.getTargets());
		JSONObject obj = new JSONObject();
		obj.put("msg", msg);
		return obj;
				}

	@PostMapping("/routeInfo")
	public JSONObject fetchrouteInfo(@RequestBody JSONObject routePath) {
		String msg =  networkService.routeInfo(routePath.get("source").toString(), routePath.get("target").toString());
		JSONObject obj = new JSONObject();
		obj.put("msg", msg);
		return obj;
				}
	
}
