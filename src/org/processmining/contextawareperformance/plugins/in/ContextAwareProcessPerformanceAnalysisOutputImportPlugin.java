package org.processmining.contextawareperformance.plugins.in;

import java.io.InputStream;

import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.lang3.SerializationUtils;
import org.processmining.contextawareperformance.models.ContextAwareProcessPerformanceAnalysisOutput;
import org.processmining.contexts.uitopia.annotations.UIImportPlugin;
import org.processmining.framework.abstractplugins.AbstractImportPlugin;
import org.processmining.framework.plugin.PluginContext;
import org.processmining.framework.plugin.annotations.Plugin;

@Plugin(
		name = "Context-Aware Process Performance Analysis Output",
		parameterLabels = { "Filename" },
		returnLabels = { "CAPO" },
		returnTypes = { ContextAwareProcessPerformanceAnalysisOutput.class })
@UIImportPlugin(
		description = "CAPO files",
		extensions = { "capo" })
public class ContextAwareProcessPerformanceAnalysisOutputImportPlugin extends AbstractImportPlugin {

	protected FileFilter getFileFilter() {
		return new FileNameExtensionFilter("CAPO files", "capo");
	}

	protected Object importFromStream(PluginContext context, InputStream input, String filename, long fileSizeInBytes) {

		ContextAwareProcessPerformanceAnalysisOutput capo = SerializationUtils.deserialize(input);
		return capo;

	}

}
