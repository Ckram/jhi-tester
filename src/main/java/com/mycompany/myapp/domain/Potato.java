package com.mycompany.myapp.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Potato.
 */
@Entity
@Table(name = "potato")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Potato implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "shape")
    private String shape;

    @Column(name = "size")
    private String size;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShape() {
        return shape;
    }

    public Potato shape(String shape) {
        this.shape = shape;
        return this;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getSize() {
        return size;
    }

    public Potato size(String size) {
        this.size = size;
        return this;
    }

    public void setSize(String size) {
        this.size = size;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Potato)) {
            return false;
        }
        return id != null && id.equals(((Potato) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Potato{" +
            "id=" + getId() +
            ", shape='" + getShape() + "'" +
            ", size='" + getSize() + "'" +
            "}";
    }
}
