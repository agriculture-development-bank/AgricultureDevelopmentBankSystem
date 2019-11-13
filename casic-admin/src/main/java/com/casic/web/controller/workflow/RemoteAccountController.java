package com.casic.web.controller.workflow;

import com.casic.common.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class RemoteAccountController {
	@RequestMapping(value="/rest/account")
    public String getAccount() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id","admin");
		jsonObject.put("firstName","Test");
		jsonObject.put("lastName","Administrator");
		jsonObject.put("email","admin@flowable.org");
		jsonObject.put("fullName","Test Administrator");
		jsonObject.put("groups","[]");
		jsonObject.put("privileges","[\"access-idm\",\"access-task\",\"access-modeler\",\"access-admin\"]");
		return jsonObject.toString();
	}

	@RequestMapping(value="/test")
	public String test() {
		return "test1";
	}

}
