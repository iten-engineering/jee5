package ch.itenengineering.messaging.ejb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/testQueue"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
public class BrokerBean implements MessageListener {

	/**
	 * receive messages from agencyQueue
	 */
	public void onMessage(Message message) {
		try {

			// recieve message form queue
			TextMessage tm = (TextMessage) message;
			String agency = tm.getStringProperty("Agency");
			String news = tm.getText();

			System.out.println("received message form " + agency + ": " + news);

			// publish to topic
			// TODO

		} catch (JMSException ex) {
			ex.printStackTrace();
		}
	}

} // end of class
