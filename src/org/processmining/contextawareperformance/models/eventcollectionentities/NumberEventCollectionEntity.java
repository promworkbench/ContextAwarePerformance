package org.processmining.contextawareperformance.models.eventcollectionentities;

import java.util.Objects;

import org.processmining.contextawareperformance.models.eventcollectionviews.EventCollectionViewType;

public class NumberEventCollectionEntity extends EventCollectionEntity {

	private static final long serialVersionUID = 8836920926568401916L;

	private Number number;

	public NumberEventCollectionEntity(int number, EventCollectionViewType type) {
		super(type);
		setNumber(number);
	}

	public Number getNumber() {
		return number;
	}

	public void setNumber(Number number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return number + "";
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof NumberEventCollectionEntity))
			return false;

		NumberEventCollectionEntity number = (NumberEventCollectionEntity) obj;

		if (!number.getNumber().equals(getNumber()))
			return false;

		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), number);
	}
}
