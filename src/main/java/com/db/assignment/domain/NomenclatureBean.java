package com.db.assignment.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class NomenclatureBean {

    private long orderId;
    private int level;
    private String code;
    private String parent;
    private String description;
    private String inclusion;
    private String secondaryInclusion;
    private String rulings;
    private String exclusion;
    private String reference;
}
