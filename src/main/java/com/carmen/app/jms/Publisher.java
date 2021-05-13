package com.carmen.app.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.annotation.Resource;

import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.carmen.app.utils.Logged;

@Singleton
@Logged
public class Publisher {

	
	private static Logger LOG = LoggerFactory.getLogger(Publisher.class);
	
	@Resource(mappedName = "jms/cars")
	private Queue queue;
	
	@Resource(mappedName = "jms/connectionFactory")
	private ConnectionFactory cf;
	
	
	public void send (String id) {
		try {
			Connection connection = cf.createConnection();
			
			
			
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer mp = session.createProducer(queue);
			mp.send(session.createTextMessage(id));
		
			
		LOG.info("Publishing... " +id);
		
			
		}catch(JMSException e) {
			e.getStackTrace();
		}
	}
	
	
}
