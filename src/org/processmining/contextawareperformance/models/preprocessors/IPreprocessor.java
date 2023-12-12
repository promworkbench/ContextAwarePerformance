package org.processmining.contextawareperformance.models.preprocessors;

public interface IPreprocessor<I, O> {

	O preprocess(I input);

}
