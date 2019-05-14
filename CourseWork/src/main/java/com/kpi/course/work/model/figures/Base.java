package com.kpi.course.work.model.figures;

import org.apache.commons.lang3.builder.*;

public class Base implements Comparable<Base> {

    private int width;
    private int length;

    public Base() {
    }

    public Base(int width, int length) {
        this.width = width;
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getSquare() {
        return width * length;
    }

    @Override
    public int compareTo(Base o) {
        return new CompareToBuilder()
                .append(this.getSquare(), o.getSquare())
                .build();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("width", width)
                .append("length", length)
                .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Base rhs = (Base) obj;
        return new EqualsBuilder()
                .append(this.width, rhs.width)
                .append(this.length, rhs.length)
                .isEquals() || new EqualsBuilder()
                .append(this.width, rhs.length)
                .append(this.length, rhs.width)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(width)
                .append(length)
                .toHashCode();
    }
}
