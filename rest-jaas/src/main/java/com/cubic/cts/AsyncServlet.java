/***********************************************************************************
* Copyright (c) 2016  CUBIC Transportation Systems. All rights reserved.
* CUBIC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
***********************************************************************************/
package com.cubic.cts;

import java.io.IOException;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @since Jan 20, 2016
 * @author Srinivas Atluri.
 */
@WebServlet(asyncSupported=true, value="/async", name="async-servlet" )
public class AsyncServlet extends HttpServlet {
	
	@EJB
	private JaasEJB jaasEjb;
	
	@Resource(name="exec/sample")
	ManagedExecutorService execService;
	
	protected void service(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
		
		//WORKING: This piece of code is working fine.
		/*req.login("tomee", "tomee");
		resp.getWriter().println(jaasEjb.getUser());*/
		
		
		final AsyncContext asyncCtx = req.startAsync();
		asyncCtx.setTimeout(0);
		
		execService.execute(new Runnable() {
			
			@Override
			public void run() {
				try {
					
					req.login("tomee", "tomee");
					//NOT WORKING: It is always returning "guest"
					resp.getWriter().println(jaasEjb.getUser());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					asyncCtx.complete();
				}
				
			}
		});
		
	}

}

