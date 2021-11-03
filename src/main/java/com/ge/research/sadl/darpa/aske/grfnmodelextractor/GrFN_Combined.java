package com.ge.research.sadl.darpa.aske.grfnmodelextractor;

/**
 * @author alfredo
 *
 */

public class GrFN_Combined {

	private GrFN_Graph grfn;
	private GrFN_ExpressionTree[] expTreeArray;

	public GrFN_Combined() {
	}

	public GrFN_Graph getGrfn() {
		return grfn;
	}

	public void setGrfn(GrFN_Graph grfn) {
		this.grfn = grfn;
	}

	public GrFN_ExpressionTree[] getExpTreeArray() {
		return expTreeArray;
	}

	public void setExpTreeArray(GrFN_ExpressionTree[] expTreeArray) {
		this.expTreeArray = expTreeArray;
	}
	
}
