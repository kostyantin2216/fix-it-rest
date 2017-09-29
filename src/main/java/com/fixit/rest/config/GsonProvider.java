package com.fixit.rest.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fixit.core.config.GsonManager;
import com.fixit.core.dao.mongo.OrderDataDao;
import com.fixit.core.dao.mongo.TradesmanDao;
import com.fixit.core.data.mongo.OrderData;
import com.fixit.core.data.mongo.Tradesman;
import com.fixit.rest.deserialization.FieldExclusionStrategyManager;
import com.google.gson.Gson;

@Provider
@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GsonProvider<T> implements MessageBodyReader<T>, MessageBodyWriter<T> {

    @Context
    private UriInfo ui;
    
    @Autowired 
    private GsonManager gsonManager;
    
    private Gson gson;	
    
    @PostConstruct
    public void init() {
    	gson = gsonManager.getRestResourceGsonBuilder().addSerializationExclusionStrategy(
    				new FieldExclusionStrategyManager.Builder()
    					.add(Tradesman.class, 
    							TradesmanDao.PROP_PASSWORD, 
    							TradesmanDao.PROP_SUBSCRIPTION_EXPIRY_TIME,
    							TradesmanDao.PROP_LEAD_ID,
    							TradesmanDao.PROP_WORKING_AREAS)
    					.add(OrderData.class, 
    							OrderDataDao.PROP_USER_ID)
    					.build()
    			).create();
    }

    @Override
    public boolean isReadable(Class<?> type, Type genericType,
                              Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    @Override
    public T readFrom(Class<T> type, Type genericType, Annotation[] annotations,
                      MediaType mediaType, MultivaluedMap<String, String> httpHeaders,
                      InputStream entityStream) throws IOException, WebApplicationException {
        InputStreamReader reader = new InputStreamReader(entityStream, "UTF-8");
        try {
            return gson.fromJson(reader, genericType);
        } finally {
            reader.close();
        }
    }

    @Override
    public boolean isWriteable(Class<?> type, Type genericType,
                               Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    @Override
    public long getSize(T t, Class<?> type, Type genericType,
                        Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(T t, Class<?> type, Type genericType, Annotation[] annotations,
                        MediaType mediaType, MultivaluedMap<String, Object> httpHeaders,
                        OutputStream entityStream) throws IOException, WebApplicationException {
        PrintWriter printWriter = new PrintWriter(entityStream);
        try {
            String json = gson.toJson(t);
            printWriter.write(json);
            printWriter.flush();
        } finally {
            printWriter.close();
        }
    }
}
