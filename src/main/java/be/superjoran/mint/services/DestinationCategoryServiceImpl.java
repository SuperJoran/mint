package be.superjoran.mint.services;

import be.superjoran.mint.dao.DestinationCategoryDao;
import be.superjoran.mint.domain.searchresults.DestinationCategory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DestinationCategoryServiceImpl implements DestinationCategoryService{
    private final DestinationCategoryDao destinationCategoryDao;

    public DestinationCategoryServiceImpl(DestinationCategoryDao destinationCategoryDao) {
        this.destinationCategoryDao = destinationCategoryDao;
    }

    @Override
    public List<DestinationCategory> findDestinationCategories() {
        return this.destinationCategoryDao.findDestinationCategories();
    }
}
