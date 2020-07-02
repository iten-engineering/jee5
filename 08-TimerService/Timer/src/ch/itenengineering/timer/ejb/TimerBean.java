package ch.itenengineering.timer.ejb;

import javax.ejb.Stateless;

import org.jboss.ejb3.annotation.RemoteBinding;

@Stateless
@RemoteBinding(jndiBinding = "ejb/Timer")
public class TimerBean implements TimerRemote {

	public void start(String name, long interval) {
	}

	public void stop(String name) {
	}

} // end of class
