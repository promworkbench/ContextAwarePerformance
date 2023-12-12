package org.processmining.contextawareperformance.plugins.out;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.commons.lang3.SerializationUtils;
import org.processmining.contextawareperformance.models.ContextAwareProcessPerformanceAnalysisOutput;
import org.processmining.contexts.uitopia.annotations.UIExportPlugin;
import org.processmining.framework.plugin.PluginContext;
import org.processmining.framework.plugin.annotations.Plugin;
import org.processmining.framework.plugin.annotations.PluginVariant;

@Plugin(
		name = "Context-Aware Process Performance Analysis Output",
		returnLabels = {},
		returnTypes = {},
		parameterLabels = { "CAPO", "File" },
		userAccessible = true)
@UIExportPlugin(
		description = "CAPO files",
		extension = "capo")
public class ContextAwareProcessPerformanceAnalysisOutputExportPlugin {

	@PluginVariant(
			variantLabel = "Export, default byte array serialization",
			requiredParameterLabels = { 0, 1 })
	public void export(PluginContext context, ContextAwareProcessPerformanceAnalysisOutput output, File file)
			throws FileNotFoundException {

		FileOutputStream stream = new FileOutputStream(file);
		SerializationUtils.serialize(output, stream);

	}
}
