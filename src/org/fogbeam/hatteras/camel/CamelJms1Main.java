package org.fogbeam.hatteras.camel;

import org.apache.camel.CamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CamelJms1Main
{
	public static void main( String[] args ) throws Exception
	{
		
		ApplicationContext appContext = new ClassPathXmlApplicationContext( "applicationContext.xml" );
		
		CamelContext context = appContext.getBean( "camelContext", CamelContext.class );
		
		
		Thread.sleep( 30000 );
		
		// context.stop();
		
		System.out.println( "done" );
	}
}
