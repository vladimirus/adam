package com.adam.model;

import static com.google.common.base.MoreObjects.toStringHelper;
import static com.google.common.base.Objects.equal;

import com.google.common.base.Objects;
import org.springframework.data.annotation.Id;

/**
 * POJO for a friend.
 */
public class Friend {
    private final String name;
    private final int noOfMutualFrieds;
    private final String addLink;
    @Id
    private String id;

    public Friend(String name, int noOfMutualFrieds, String addLink) {
        this.name = name;
        this.noOfMutualFrieds = noOfMutualFrieds;
        this.addLink = addLink;
    }

    public String getName() {
        return name;
    }

    public int getNoOfMutualFrieds() {
        return noOfMutualFrieds;
    }

    public String getAddLink() {
        return addLink;
    }

    @Override
    public boolean equals(Object o) {
        final Friend other = (Friend) o;
        return equal(this.noOfMutualFrieds, other.noOfMutualFrieds)
                && equal(this.addLink, other.addLink)
                && equal(this.name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(noOfMutualFrieds, addLink);
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("name", name)
                .add("noOfMutualFrieds", noOfMutualFrieds)
                .add("addLink", addLink)
                .toString();
    }
}
