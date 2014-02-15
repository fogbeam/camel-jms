package org.fogbeam.hatteras.camel;

import org.springframework.jms.core.MessageCreator;

public interface SubMessageCreator extends MessageCreator
{
	public void setData( final String data );
}