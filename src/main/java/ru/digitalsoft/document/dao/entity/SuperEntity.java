package ru.digitalsoft.document.dao.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@MappedSuperclass
public class SuperEntity {

    private long id;
    private Timestamp createTime;
    private Timestamp updateTime;
    private boolean actual;

    @PrePersist
    public void prePersist () {
        Timestamp nowTimestamp = Timestamp.from(Instant.now());

        createTime = nowTimestamp;
        updateTime = nowTimestamp;

        actual = true;
    }

    @PreUpdate
    public void preUpdateBase() {
        updateTime = Timestamp.from(Instant.now());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "create_time", nullable = false, updatable = false)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "update_time", nullable = true)
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "actual", nullable = false)
    public boolean isActual() {
        return actual;
    }

    public void setActual(boolean actual) {
        this.actual = actual;
    }

}
