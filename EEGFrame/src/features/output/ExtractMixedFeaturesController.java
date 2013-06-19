/**
 * 
 */
package features.output;

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
	}
	@Override
	public void extractFeatures() {
		extractUnivariateFeaturesController.extractFeatures();

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

}
