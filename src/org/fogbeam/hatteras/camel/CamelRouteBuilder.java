package org.fogbeam.hatteras.camel;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class CamelRouteBuilder extends RouteBuilder 
{
	private Processor 					downloadLogger;
	
	
	public void configure() throws Exception 
	{			
		from( "jms:queue:foobar" )
		.process(downloadLogger)
		.to( "jms:queue:foobar2" ); 
	}	

	public void setDownloadLogger( Processor downloadLogger )
	{
		this.downloadLogger = downloadLogger;
	}
}
