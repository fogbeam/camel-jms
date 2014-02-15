package org.fogbeam.hatteras.camel;

import java.io.File;
import java.io.StringWriter;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.w3c.dom.Document;

public class CamelJmsXmlSender
{
	public static void main( String[] args ) throws Exception
	{
		if( args.length < 1 )
		{
			System.err.println( "Please specify a test file to message!" );
			System.exit( -1 );
		}
		
		ApplicationContext appContext = new ClassPathXmlApplicationContext( "senderContext.xml" );
		// ApplicationContext appContext = new FileSystemXmlApplicationContext( "conf/senderContext.xml" );
	
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
		
		String fileName = args[0];
		File fXmlFile = new File(fileName);
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();
		
		String msg = getStringFromDocument( doc );
		
		
		msgCreator.setData( msg );
		jmsTemplate.send( "foobar", msgCreator );
		
		
		System.out.println( "done" );
	}
	
	//method to convert Document to String
	public static String getStringFromDocument(Document doc)
	{
	    try
	    {
	       DOMSource domSource = new DOMSource(doc);
	       StringWriter writer = new StringWriter();
	       StreamResult result = new StreamResult(writer);
	       TransformerFactory tf = TransformerFactory.newInstance();
	       Transformer transformer = tf.newTransformer();
	       transformer.transform(domSource, result);
	       return writer.toString();
	    }
	    catch(TransformerException ex)
	    {
	       ex.printStackTrace();
	       return null;
	    }
	}
	
}