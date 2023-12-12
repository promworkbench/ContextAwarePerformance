package org.processmining.contextawareperformance.models.nlg;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.processmining.contextawareperformance.models.ContextResult;
import org.processmining.contextawareperformance.models.PerformanceMeasurement;
import org.processmining.contextawareperformance.models.SignificanceResult;
import org.processmining.contextawareperformance.models.eventcollectionentities.EventCollectionEntity;
import org.processmining.contextawareperformance.models.eventcollectionviews.EventCollectionViewType;
import org.processmining.contextawareperformance.models.functions.context.Context;
import org.processmining.contextawareperformance.models.functions.performance.Performance;

import simplenlg.features.Feature;
import simplenlg.features.NumberAgreement;
import simplenlg.features.Tense;
import simplenlg.framework.CoordinatedPhraseElement;
import simplenlg.framework.NLGElement;
import simplenlg.framework.NLGFactory;
import simplenlg.lexicon.Lexicon;
import simplenlg.phrasespec.SPhraseSpec;
import simplenlg.realiser.english.Realiser;

public class NaturalLanguageGenerator {

	private static Lexicon lexicon = Lexicon.getDefaultLexicon();
	private static NLGFactory nlgFactory = new NLGFactory(lexicon);
	private static Realiser realiser = new Realiser(lexicon);

	public static String generateStatement(EventCollectionViewType viewType, EventCollectionEntity entity,
			Performance<?> performance, Context<?> context,
			Map<ContextResult<?>, List<PerformanceMeasurement<?>>> results, SignificanceResult significanceResult) {

		// We create a phase that is structured as cause-effect / when-then
		// (e.g. "when (cue phrase) activity A (subject) is executed by (verb) Bob, Mary, or John (object(s)),
		// (then) it (pronoun for activity A) takes longer (verb)")

		// First the cause part (context)
		SPhraseSpec causePhrase = nlgFactory.createClause();

		// Build cause phrase
		// The entity is the subject
		causePhrase.setSubject("\"" + entity.toString() + "\"");
		// The verb is specified by the context function
		causePhrase.setVerb(context.VERB);
		// The objects
		CoordinatedPhraseElement object = nlgFactory.createCoordinatedPhrase();
		// Because we use OR as the conjunction,  make the verb be conjugated in singular form
		object.setFeature(Feature.NUMBER, NumberAgreement.SINGULAR);

		// See whether the context's performance is above or below the overall mean performance.
		long total = 0l;
		for (ContextResult<?> contextResult : results.keySet()) {
			long contextTotal = 0l;
			for (PerformanceMeasurement<?> measurement : results.get(contextResult)) {
				Long result = (Long) measurement.getResult();
				contextTotal += result;
			}
			long contextMean = contextTotal / results.get(contextResult).size();
			total += contextMean;
		}
		long mean = total / results.keySet().size();

		HashSet<ContextResult<?>> aboveMeanExcl = new HashSet<ContextResult<?>>();
		for (ContextResult<?> contextResult : results.keySet()) {
			long contextTotal = 0l;
			for (PerformanceMeasurement<?> measurement : results.get(contextResult)) {
				Long result = (Long) measurement.getResult();
				contextTotal += result;
			}
			long contextMean = contextTotal / results.get(contextResult).size();
			if (contextMean > mean)
				aboveMeanExcl.add(contextResult);
		}

		// Show up to three context results, if there's more, add 'etc.'
		int i = 0;
		for (ContextResult<?> result : aboveMeanExcl) {
			if (i < 3) {
				i++;
				object.addCoordinate("\"" + result.getResult().toString() + "\"");
			} else {
				object.addCoordinate("etc.");
				break;
			}
		}
		if (aboveMeanExcl.size() <= 3) {
			// Use the OR conjunction (separate context values)
			object.setConjunction("or");
		} else {
			object.setConjunction(" ");
		}

		causePhrase.setObject(object);
		// We have a when-then structure (CUE phrase)
		//		causePhrase.setFeature(Feature.CUE_PHRASE, "when");
		causePhrase.setFeature(Feature.COMPLEMENTISER, "when");
		// Use present tense
		causePhrase.setFeature(Feature.TENSE, Tense.PRESENT);

		// Add an indirect object that explains the parameters for the context.
		if (context.getParameters() != null)
			causePhrase.setIndirectObject(context.getParameters().toString());

		// Then the effect part (performance)

		//		NLGElement itWord = nlgFactory.createWord("it", LexicalCategory.PRONOUN);
		//		//		x.setFeature(LexicalFeature.REFLEXIVE, true);
		//		NPPhraseSpec itNoun = nlgFactory.createNounPhrase(itWord);

		SPhraseSpec effectPhrase = nlgFactory.createClause();
		NLGElement subject = nlgFactory.createNounPhrase("the", performance.toString().toLowerCase().trim());
		effectPhrase.setSubject(subject);
		//		effectPhrase.setObject("object");
		effectPhrase.setVerb(performance.VERB);
		effectPhrase.setIndirectObject(performance.ADJECTIVE_GREATER_THAN);
		effectPhrase.setComplement(causePhrase);

		// Use HTML output
		//		realiser.setFormatter(new HTMLFormatter());
		// Output debug information
		realiser.setDebugMode(false);
		// Create string
		return realiser.realiseSentence(effectPhrase);
	}

}
