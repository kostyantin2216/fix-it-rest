/**
 * 
 */
package com.fixit.rest.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fixit.core.dao.sql.RestClientDao;
import com.fixit.core.data.sql.RestClient;

/**
 * @author 		Kostyantin
 * @createdAt 	2016/12/25 20:06:14 GMT+2
 */
@Component
public class RestClientAuthorizationFilter implements Filter {
	
	private final static String AUTHORIZATION_HEADER = "X-Authorization";

	@Autowired
	private RestClientDao mDao;
	
	@Override
	public void init(FilterConfig config) throws ServletException {	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpReq = (HttpServletRequest) request;
		HttpServletResponse httpResp = (HttpServletResponse) response;
		
		String userAgent = httpReq.getHeader(HttpHeaders.USER_AGENT);
		String key = httpReq.getHeader(AUTHORIZATION_HEADER);
		
		if(key != null) {
			RestClient restClient = mDao.findById(key);
			
			if(restClient != null) {
				String permittedUserAgent = restClient.getUserAgent();
				if(permittedUserAgent.equals("*") || userAgent.contains(permittedUserAgent)) {
					chain.doFilter(request, response);
				} else {
					httpResp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unknown client agent.");
				}
			} else {
				httpResp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unregistered client.");
			}
		} else {
			httpResp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing API Key");
		}
	}

	@Override
	public void destroy() {	}

}
