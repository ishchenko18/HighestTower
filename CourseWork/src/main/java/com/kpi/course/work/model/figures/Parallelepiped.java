package com.kpi.course.work.model.figures;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Parallelepiped {

    private int height;
    private int number;
    private String name;
    private Base base;

    public Parallelepiped() {
    }

    public Parallelepiped(int height, int number, String name, Base base) {
        this.height = height;
        this.number = number;
        this.name = name;
        this.base = base;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }

    public boolean isBaseCompatible(Base base) {
        return (this.base.getLength() >= base.getLength() && this.base.getWidth() >= base.getWidth())
                || (this.base.getWidth() >= base.getLength() && this.base.getLength() >= base.getWidth());
    }

    public boolean isSameParallelepiped(Parallelepiped p) {
        return this.name.equalsIgnoreCase(p.getName());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("height", height)
                .append("number", number)
                .append("name", name)
                .append("base", base)
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
        Parallelepiped rhs = (Parallelepiped) obj;
        return new EqualsBuilder()
                .append(this.height, rhs.height)
                .append(this.number, rhs.number)
                .append(this.name, rhs.name)
                .append(this.base, rhs.base)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(height)
                .append(number)
                .append(name)
                .append(base)
                .toHashCode();
    }
}
