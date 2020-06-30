package com.mycommunity.common.serializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@Component
public class ErrorSerializer extends JsonSerializer<Errors>{
	
	 @Override public void serialize(Errors errors, JsonGenerator jgen,
	 SerializerProvider serializers) throws IOException { 
	 /*
		 jgen.writeStartArray();
	 * List<FieldError> fieldList = new ArrayList<FieldError>(); List<ObjectError>
	 * globalList = new ArrayList<ObjectError>(); fieldList =
	 * errors.getFieldErrors();
	 * 
	 * for(FieldError e : fieldList) { try { jgen.writeStartObject();
	 * jgen.writeStringField("field", e.getField());
	 * jgen.writeStringField("objectName", e.getObjectName());
	 * jgen.writeStringField("code", e.getCode());
	 * jgen.writeStringField("defaultMessage", e.getDefaultMessage()); Object
	 * rejectedValue = e.getRejectedValue(); if(Objects.nonNull(rejectedValue)) {
	 * jgen.writeStringField("rejectedValue", rejectedValue.toString()); }
	 * jgen.writeEndObject(); }catch (Exception error) { error.printStackTrace(); }
	 * };
	 * 
	 * globalList = errors.getGlobalErrors();
	 * 
	 * for(ObjectError e : globalList) {
	 * 
	 * try { jgen.writeStartObject();
	 * 
	 * jgen.writeStringField("objectName", e.getObjectName());
	 * jgen.writeStringField("code", e.getCode());
	 * jgen.writeStringField("defaultMessage", e.getDefaultMessage());
	 * 
	 * jgen.writeEndObject(); } catch (IOException e1) { e1.printStackTrace(); } };
	 * 
	 * jgen.writeEndArray();
	 * 
	 */
	  }
	 
	 
}
