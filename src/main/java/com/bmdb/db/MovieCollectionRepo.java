package com.bmdb.db;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bmdb.business.MovieCollection;

public interface MovieCollectionRepo extends JpaRepository<MovieCollection, Integer> {

}
