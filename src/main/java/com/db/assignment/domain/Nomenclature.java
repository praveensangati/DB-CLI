package com.db.assignment.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "nomenclature")
public class Nomenclature {

    @Id
    private long orderId;
    private int level;
    private String code;
    private String parent;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(columnDefinition = "TEXT")
    private String inclusion;
    @Column(columnDefinition = "TEXT")
    private String secondaryInclusion;
    @Column(columnDefinition = "TEXT")
    private String rulings;
    @Column(columnDefinition = "TEXT")
    private String exclusion;
    private String reference;
}
