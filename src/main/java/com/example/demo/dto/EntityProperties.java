package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EntityProperties {

	private String type;
	private String name;
	private String path;
	private Integer size;
	private String errorMsg;

	public EntityProperties(String type, String name, String path, Integer size) {
		super();
		this.type = type;
		this.name = name;
		this.path = path;
		this.size = size;
	}

	
	public EntityProperties(String errorMsg) {
		this.errorMsg=errorMsg;
	}


	public String getErrorMsg() {
		return errorMsg;
	}


	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "EntityProperties [type=" + type + ", name=" + name + ", path=" + path + ", size=" + size + ", errorMsg="
				+ errorMsg + "]";
	}
}
