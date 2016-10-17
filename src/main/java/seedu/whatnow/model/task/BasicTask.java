package seedu.whatnow.model.task;

import java.util.Objects;

import seedu.whatnow.commons.util.CollectionUtil;

public abstract class BasicTask implements ReadOnlyTask{
	private Name name;

	public BasicTask(Name name) {
		assert !CollectionUtil.isAnyNull(name);
		this.name = name;
	}
	/**
	 * Copy constructor.
	 */
	public BasicTask(ReadOnlyTask source) {
		this(source.getName());
	}
	@Override
	public Name getName() {
		return name;
	}
	public void setName(Name name) {
		this.name = name;
	}
	@Override
	public boolean equals(Object other) {
		return other == this // short circuit if same object
				|| (other instanceof ReadOnlyTask // instanceof handles nulls
						&& this.isSameStateAs((ReadOnlyTask) other));
	}

	@Override
	public int hashCode() {
		// use this method for custom fields hashing instead of implementing your own
		return Objects.hash(name);
	}

	@Override
	public String toString() {
		return getAsText();
	}
}
