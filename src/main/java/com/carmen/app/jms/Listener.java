package com.carmen.app.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.carmen.app.control.CarService;
import com.carmen.app.entities.Car;
import com.carmen.app.exceptions.EntityNotFoundException;

import javax.ejb.MessageDriven;
import javax.inject.Inject;

import java.util.logging.Level;

import javax.ejb.ActivationConfigProperty;

/**
 * 
 * Defines the JMS interface used to create, update and remove cars
 * asynchronously
 * 
 * 
 * @author Carmen Piñera Moreno
 *
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/cars"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "CARS"),
		@ActivationConfigProperty(propertyName = "resourceAdapter", propertyValue = "activemq-rar-5.16.2") })

public class Listener implements MessageListener {

	private static Logger LOG = LogManager.getLogger(Publisher.class);

	@Inject
	CarService carService;

	public Listener() {

	}

	@Override
	public void onMessage(Message message) {

		TextMessage msg = (TextMessage) message;
		try {

			String id = msg.getText();

			try {
				Car car = this.carService.getCar(id);

				LOG.info("Received: " + id);
				LOG.info("Car updated: " + car.toString());

			} catch (EntityNotFoundException ex) {
				LOG.error(ex.getMessage());
			}

		} catch (JMSException e) {
			e.getStackTrace();
		}
	}

}