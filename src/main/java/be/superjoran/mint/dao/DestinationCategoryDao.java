package be.superjoran.mint.dao;

import be.superjoran.mint.domain.searchresults.DestinationCategory;

import java.util.List;

public interface DestinationCategoryDao {
    List<DestinationCategory> findDestinationCategories();

}
