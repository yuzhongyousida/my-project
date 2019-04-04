package com.myself.util.common;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContextEvent;

/**
 * 不必专门为注入WebApplicationContext写一个servlet，继承ContextLoaderListener即可
 */
public class SuperContextLoaderListener extends ContextLoaderListener {

	/**
	 * @see javax.servlet.ServletContextListener#contextInitialized(ServletContextEvent)
	 * @param event
	 * ServletContextEvent
	 */
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);

		WebApplicationContext context = (WebApplicationContext) event
				.getServletContext()
				.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

		SpringBeanProxy.setApplicationContext(context);
	}
}
