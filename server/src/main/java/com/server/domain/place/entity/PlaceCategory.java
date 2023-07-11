package com.server.domain.place.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.server.domain.category.entity.Category;

@Entity
public class PlaceCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long placeCategoryId;

    @ManyToOne
    @JoinColumn(name = "placeId")
    private Place place;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;
}
