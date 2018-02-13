package be.superjoran.mint.services;

import be.superjoran.mint.domain.searchresults.DestinationCategory;

import java.util.List;

public interface DestinationCategoryService {
    List<DestinationCategory> findDestinationCategories();

}
