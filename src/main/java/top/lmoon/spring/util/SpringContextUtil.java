/**
 * 
 */
package top.lmoon.spring.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author LMoon
 * @date 2017年8月4日
 * 
 */
public class SpringContextUtil implements ApplicationContextAware{
	
	private static ApplicationContext applicationContext;

	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		this.applicationContext = applicationContext;
	}
	
	public static ApplicationContext getApplicationContext() {  
        return applicationContext;  
    }  
	
	public static Object getBean(String name) throws BeansException {  
        return applicationContext.getBean(name);  
    }  

}
