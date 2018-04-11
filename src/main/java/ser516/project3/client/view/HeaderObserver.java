package ser516.project3.client.view;

import ser516.project3.client.controller.ClientController;
import ser516.project3.client.controller.HeaderController;
import ser516.project3.model.HeaderObservable;
import ser516.project3.utilities.ControllerFactory;

import java.util.Observable;
import java.util.Observer;

/**
 * The HeaderOvserver class implements the Observer interface to update the
 * header time stamp value.
 * 
 * @author vsriva12
 *
 */
public class HeaderObserver implements Observer {

	public void update(Observable observable, Object observerObj) {
		HeaderObservable headerObservable = (HeaderObservable) observable;
		HeaderController headerController = ControllerFactory.getInstance().getHeaderController();
		double currentTimeStampFromServer = headerObservable.getHeaderTimeStamp();
		if (currentTimeStampFromServer == 0) {
			headerController.setHeaderTimeStamp((int)currentTimeStampFromServer);
		} else {
			headerController.setHeaderTimeStamp((int)currentTimeStampFromServer - headerObservable.getInterval());
		}
	}
}