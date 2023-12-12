package org.processmining.contextawareperformance.models.eventcollectionentities;

import java.io.Serializable;
import java.util.Objects;

import org.processmining.contextawareperformance.models.eventcollectionviews.EventCollectionViewType;

public abstract class EventCollectionEntity implements Serializable {

	private static final long serialVersionUID = -417505009884795185L;

	private EventCollectionViewType type;

	public EventCollectionEntity(EventCollectionViewType type) {
		setType(type);
	}

	public EventCollectionViewType getType() {
		return type;
	}

	public void setType(EventCollectionViewType type) {
		this.type = type;
	}

	@Override
	public abstract String toString();

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof EventCollectionEntity))
			return false;

		EventCollectionEntity entity = (EventCollectionEntity) obj;

		if (type != entity.type)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(type);
	}

}
