package ser516.project3.client.view;

import java.util.Observable;
import java.util.Observer;

import ser516.project3.client.controller.ClientController;
import ser516.project3.client.controller.FaceController;
import ser516.project3.interfaces.ControllerInterface;
import ser516.project3.model.FaceExpressionsObservable;
import ser516.project3.utilities.ControllerFactory;

/**
 * 
 * On receiving new data in ExpressionsDataObservable object update function of
 * this class can be used to update corresponding UI elements
 * 
 * @author Manish Tandon
 *
 */
public class FaceViewObserver implements Observer {
	/**
	 * Overridden method that updates the face elements.
	 */
	@Override
	public void update(Observable dataObject, Object observerObj) {
		FaceExpressionsObservable faceExpressionObject=(FaceExpressionsObservable) dataObject;

		FaceController faceController = ControllerFactory.getInstance().getFaceController();
		faceController.updateFaceElements(faceExpressionObject.getMessageBean());
	}

}