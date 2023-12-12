package org.processmining.contextawareperformance.models.eventcollectionentities;

import java.util.Objects;

import org.deckfour.xes.id.XID;
import org.processmining.contextawareperformance.models.eventcollectionviews.EventCollectionViewType;

public class IdentityEventCollectionEntity extends EventCollectionEntity {

	private static final long serialVersionUID = -512299329411352037L;

	private XID identity;

	public IdentityEventCollectionEntity(XID identity, EventCollectionViewType type) {
		super(type);
		setIdentity(identity);
	}

	public XID getIdentity() {
		return identity;
	}

	public void setIdentity(XID identity) {
		this.identity = identity;
	}

	@Override
	public String toString() {
		return identity.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof IdentityEventCollectionEntity))
			return false;

		IdentityEventCollectionEntity id = (IdentityEventCollectionEntity) obj;

		if (!id.getIdentity().equals(getIdentity()))
			return false;

		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), identity);
	}

}
