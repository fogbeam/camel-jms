package org.fogbeam.hatteras.camel;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class CamelJmsMsgSender
{
	public static void main( String[] args )
	{
		ApplicationContext appContext = new ClassPathXmlApplicationContext( "senderContext.xml" );
		
		JmsTemplate jmsTemplate = appContext.getBean( "jmsTemplate", JmsTemplate.class );
		SubMessageCreator msgCreator = new SubMessageCreator() {
			
			private String data;
			
			@Override
			public void setData( String data )
			{
				this.data = data;
			}
			
			public Message createMessage(Session session) throws JMSException 
			{
				return session.createTextMessage( data );
			}
		};
		
		for( int i = 0; i < 10; i++ )
		{
			msgCreator.setData( "Hello, Queue World: " + i );
			jmsTemplate.send( "foobar", msgCreator );
		
			try
			{
				Thread.sleep( 15000 );
			}
			catch( InterruptedException e )
			{}
		}
		
		System.out.println( "done" );
	}
}