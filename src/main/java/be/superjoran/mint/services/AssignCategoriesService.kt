package be.superjoran.mint.services

import be.superjoran.mint.domain.Person
import org.springframework.batch.core.JobParametersInvalidException
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException
import org.springframework.batch.core.repository.JobRestartException

interface AssignCategoriesService {

    @Throws(JobParametersInvalidException::class, JobExecutionAlreadyRunningException::class, JobRestartException::class, JobInstanceAlreadyCompleteException::class)
    fun assignCategoriesForPerson(person: Person)
}
