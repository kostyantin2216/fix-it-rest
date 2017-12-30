/**
 * 
 */
package com.fixit.rest.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fixit.core.dao.sql.RestClientDao;
import com.fixit.core.dao.sql.StoredPropertyDao;
import com.fixit.core.general.PropertyGroup;
import com.fixit.core.general.StoredProperties;
import com.fixit.core.logging.FILog;
import com.google.gson.reflect.TypeToken;

/**
 * @author 		Kostyantin
 * @createdAt 	2016/12/25 20:06:14 GMT+2
 */
@Component
public class CorsFilter implements Filter {
	
	private final PropertyGroup mProperties;

	@Autowired
	public CorsFilter(RestClientDao restClientDao, StoredPropertyDao storedPropertyDao) {
		mProperties = storedPropertyDao.getPropertyGroup(PropertyGroup.Group.rest);
	}
	
	@Override
	public void init(FilterConfig config) throws ServletException {	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpReq = (HttpServletRequest) request;
		HttpServletResponse httpResp = (HttpServletResponse) response;
		
		chain.doFilter(request, response);
		
		String path = httpReq.getPathInfo();
		List<String> corsPaths = mProperties.getJsonProperty(StoredProperties.REST_PUBLIC_PATHS, new TypeToken<ArrayList<String>>(){}.getType());
		
		if(corsPaths.contains(path)) {
			FILog.i("adding cors to path " + path);
			httpResp.addHeader("Access-Control-Allow-Origin", "*");
			httpResp.addHeader("Access-Control-Allow-Credentials", "true");
			httpResp.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
			httpResp.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
		}
	}

	@Override
	public void destroy() {	}

}
