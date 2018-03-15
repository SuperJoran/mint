package be.superjoran.mint.services

import be.superjoran.mint.domain.Person
import org.springframework.stereotype.Service

@Service
class AssignCategoriesServiceImpl(private val destinationCategoryService: DestinationCategoryService) : AssignCategoriesService {

    override fun assignCategoriesForPerson(person: Person) {
        this.destinationCategoryService.assignCategoriesAutomatically(person)
    }
}
