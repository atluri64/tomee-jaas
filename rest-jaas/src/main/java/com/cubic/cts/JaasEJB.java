/***********************************************************************************
* Copyright (c) 2016  CUBIC Transportation Systems. All rights reserved.
* CUBIC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
***********************************************************************************/
package com.cubic.cts;

import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.naming.InitialContext;

/**
 * @since Jan 20, 2016
 * @author Srinivas Atluri.
 */
@Stateless
public class JaasEJB {
	
	public String getUser() {
		try {
			SessionContext ctx = (SessionContext) new InitialContext().lookup("java:comp/EJBContext");
			return ctx.getCallerPrincipal().getName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}

