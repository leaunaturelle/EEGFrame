/**
 * 
 */
package features.output;

import gui.SelectedSignal;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author lsuc
 *
 */
public class ExtractMixedFeaturesController extends ExtractFeaturesController {
	
	private ExtractUnivariateFeaturesController extractUnivariateFeaturesController;
	private ExtractMultivariateFeaturesController extractMultivariateFeaturesController;
	/* (non-Javadoc)
	 * @see features.output.ExtractFeaturesController#extractFeatures()
	 */
	public ExtractMixedFeaturesController(ExtractUnivariateFeaturesController extractUnivariateFeaturesController, ExtractMultivariateFeaturesController extractMultivariateFeaturesController){
		this.setExtractUnivariateFeaturesController(extractUnivariateFeaturesController);
		this.setExtractMultivariateFeaturesController(extractMultivariateFeaturesController);
		featuresType = MIXED_FEATURES;
	}
	@Override
	public void extractFeatures() {
		extractUnivariateFeaturesController.extractFeatures();
		selectedFeatures = extractUnivariateFeaturesController.getSelectedFeatures();
		extractMultivariateFeaturesController.extractFeatures();

		UnivariateFeatures uniFeatures = (UnivariateFeatures) selectedFeatures.get(0);
		MultivariateFeatures multiFeatures = (MultivariateFeatures) extractMultivariateFeaturesController.getSelectedFeatures().get(0);
		
		int length1 = uniFeatures.getExtractedFeatures().length;
		int length2 = multiFeatures.getExtractedFeatures().length;
		
		for(int j = 0; j < selectedFeatures.size(); j++){
			uniFeatures = (UnivariateFeatures) selectedFeatures.get(j);
			multiFeatures = (MultivariateFeatures) extractMultivariateFeaturesController.getSelectedFeatures().get(j);
			
			ArrayList<String> optionsToPrintNoParams = uniFeatures.getOptionsToPrintNoParams();
			optionsToPrintNoParams.addAll(multiFeatures.getOptionsToPrintNoParams());		
			ArrayList<SelectedSignal[]> signals = uniFeatures.getSignals();
			signals.addAll(multiFeatures.getSignals());
			selectedFeatures.get(j).setOptionsToPrint(optionsToPrintNoParams);
			selectedFeatures.get(j).setSignals(signals);
			
			HashMap<String, String>[] extractedFeatures = (HashMap<String, String>[]) new HashMap[length1+length2];
//			System.out.println("velicina u mixed extractanih je " + extractedFeatures.length);
			for(int i = 0; i < length1; i++){
				extractedFeatures[i] = uniFeatures.getExtractedFeatures()[i];
			}
			for(int i = 0; i < length2; i++){
				extractedFeatures[i+length1] = multiFeatures.getExtractedFeatures()[i];
			}
//			System.out.println("interval je "+ j + "a mutual dim "+ multiFeatures.getExtractedFeatures()[i].get(MultivariateFeatures.MUTUAL_DIM));
			selectedFeatures.get(j).setExtractedFeatures(extractedFeatures);
		}
		
	}
	
	public ExtractUnivariateFeaturesController getExtractUnivariateFeaturesController() {
		return extractUnivariateFeaturesController;
	}
	public void setExtractUnivariateFeaturesController(
			ExtractUnivariateFeaturesController extractUnivariateFeaturesController) {
		this.extractUnivariateFeaturesController = extractUnivariateFeaturesController;
	}
	public ExtractMultivariateFeaturesController getExtractMultivariateFeaturesController() {
		return extractMultivariateFeaturesController;
	}
	public void setExtractMultivariateFeaturesController(
			ExtractMultivariateFeaturesController extractMultivariateFeaturesController) {
		this.extractMultivariateFeaturesController = extractMultivariateFeaturesController;
	}
	/* (non-Javadoc)
	 * @see features.output.ExtractFeaturesController#createNewExtractFeaturesController()
	 */
	@Override
	public void createNewExtractFeaturesController() {
		ExtractUnivariateFeaturesController univariate = new ExtractUnivariateFeaturesController();
		ExtractMultivariateFeaturesController multivariate = new ExtractMultivariateFeaturesController();
		univariate.setExtractMixedFeaturesController(this);
//		multivariate.setEx
		this.setExtractUnivariateFeaturesController(univariate);
		this.setExtractMultivariateFeaturesController(multivariate);
	}
}
