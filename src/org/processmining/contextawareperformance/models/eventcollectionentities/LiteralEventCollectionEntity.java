package org.processmining.contextawareperformance.models.eventcollectionentities;

import java.util.Objects;

import org.processmining.contextawareperformance.models.eventcollectionviews.EventCollectionViewType;

public class LiteralEventCollectionEntity extends EventCollectionEntity {

	private static final long serialVersionUID = 4353552505625181228L;

	private String literal;

	public LiteralEventCollectionEntity(String literal, EventCollectionViewType type) {
		super(type);
		setLiteral(literal);
	}

	public String getLiteral() {
		return literal;
	}

	public void setLiteral(String literal) {
		this.literal = literal;
	}

	@Override
	public String toString() {
		return literal;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof LiteralEventCollectionEntity))
			return false;

		LiteralEventCollectionEntity lit = (LiteralEventCollectionEntity) obj;

		if (!lit.getLiteral().equals(getLiteral()))
			return false;

		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), literal);
	}

}
