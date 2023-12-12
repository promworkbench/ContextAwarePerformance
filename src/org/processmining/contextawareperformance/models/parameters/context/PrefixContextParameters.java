package org.processmining.contextawareperformance.models.parameters.context;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import org.deckfour.xes.model.XLog;
import org.processmining.contextawareperformance.models.functions.context.Context;
import org.processmining.contextawareperformance.view.dialogs.ContextProMTitledScrollContainerChild;
import org.processmining.contextawareperformance.view.utils.swing.SpringUtilities;
import org.processmining.framework.util.ui.widgets.ProMComboBox;
import org.processmining.framework.util.ui.widgets.ProMScrollContainer;
import org.processmining.framework.util.ui.widgets.ProMTextField;

import com.fluxicon.slickerbox.factory.SlickerFactory;

public class PrefixContextParameters extends ContextParameters {

	private static final long serialVersionUID = 7284886707818693247L;

	public static final String DESCRIPTION;
	public static final int DEFAULT_HORIZON;
	public static final PrefixAbstractionType DEFAULT_PREFIXABSTRACTIONTYPE;

	static {
		DESCRIPTION = "Prefix context parameters";
		DEFAULT_HORIZON = -1;
		DEFAULT_PREFIXABSTRACTIONTYPE = PrefixAbstractionType.SEQUENCE;
	}

	// If horizon is -1 then the whole prefix is taken.
	// Otherwise 'horizon' number of previous events are taken into account.
	private int horizon;
	// Prefixes can be abstracted to e.g. sets and multisets.
	private PrefixAbstractionType prefixAbstractionType;

	public PrefixContextParameters() {
		super(DESCRIPTION);
		setHorizon(DEFAULT_HORIZON);
		setPrefixAbstractionType(DEFAULT_PREFIXABSTRACTIONTYPE);
	}

	public int getHorizon() {
		return horizon;
	}

	public void setHorizon(int horizon) {
		this.horizon = horizon;
	}

	public PrefixAbstractionType getPrefixAbstractionType() {
		return prefixAbstractionType;
	}

	public void setPrefixAbstractionType(PrefixAbstractionType prefixAbstractionType) {
		this.prefixAbstractionType = prefixAbstractionType;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof PrefixContextParameters))
			return false;

		PrefixContextParameters parameters = (PrefixContextParameters) obj;

		if (parameters.getHorizon() != getHorizon())
			return false;

		if (!parameters.getPrefixAbstractionType().equals(getPrefixAbstractionType()))
			return false;

		return super.equals(parameters);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append("abstraction: " + getPrefixAbstractionType().toString());
		builder.append(", horizon: " + (getHorizon() == -1 ? "none" : getHorizon() + " events"));

		return builder.toString();
	}

	@Override
	public ContextProMTitledScrollContainerChild getGUIPanel(ProMScrollContainer parent, Context<?> context,
			XLog eventlog) {
		ContextProMTitledScrollContainerChild panel = new ContextProMTitledScrollContainerChild(parent, context);
		// Add the field for selecting the settings

		JPanel content = panel.getContentPanel();
		content.setLayout(new SpringLayout());

		final ProMTextField txtHorizon = new ProMTextField("-1");
		txtHorizon.addPropertyChangeListener(new PropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent evt) {
				horizon = Integer.parseInt(txtHorizon.getText());
			}

		});
		JLabel lblHorizon = SlickerFactory.instance().createLabel("Horizon (-1 for unlimited):");
		lblHorizon.setLabelFor(txtHorizon);

		final ProMComboBox<PrefixAbstractionType> cmbAbstractionType = new ProMComboBox<PrefixAbstractionType>(
				PrefixAbstractionType.values());
		txtHorizon.addPropertyChangeListener(new PropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent evt) {
				prefixAbstractionType = (PrefixAbstractionType) cmbAbstractionType.getSelectedItem();
			}

		});
		JLabel lblAbstractionType = SlickerFactory.instance().createLabel("Abstraction type:");
		lblHorizon.setLabelFor(cmbAbstractionType);

		content.add(lblHorizon);
		content.add(txtHorizon);
		content.add(lblAbstractionType);
		content.add(cmbAbstractionType);

		SpringUtilities.makeCompactGrid(content, 2, 2, //rows, cols
				6, 6, //initX, initY
				6, 6); //xPad, yPad

		return panel;
	}

	//@formatter:off
	public enum PrefixAbstractionType {
		SEQUENCE("Sequence", "Does not abstract the prefix, i.e. the prefix is a sequence of event classes."), 
		MULTISET("Multiset", "Abstracts the prefix to a multiset of event classes."), 
		SET("Set", "Abstracts the prefix to a set of event classes.");

		private String shortDescription;

		private String description;

		PrefixAbstractionType(String shortDescription, String description) {
			setShortDescription(shortDescription);
			setDescription(description);
		}
		
		public String getShortDescription() {
			return shortDescription;
		}

		public void setShortDescription(String shortDescription) {
			this.shortDescription = shortDescription;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		@Override
		public String toString() {
			return shortDescription;
		}
	}
	//@formatter:on
}
