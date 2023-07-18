package com.flatdevs.virtualassistant.user.entity;


import com.flatdevs.virtualassistant.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "profile_images")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ProfileImageEntity extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "image", nullable = false, length = 100000)
    private byte[] image;
}
