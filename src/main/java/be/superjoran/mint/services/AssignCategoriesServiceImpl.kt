package be.superjoran.mint.services

import be.superjoran.mint.domain.Person
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.JobParametersInvalidException
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException
import org.springframework.batch.core.repository.JobRestartException
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class AssignCategoriesServiceImpl(private val jobLauncher: JobLauncher, private val assignCategoriesJob: Job) : AssignCategoriesService {

    @Throws(JobParametersInvalidException::class, JobExecutionAlreadyRunningException::class, JobRestartException::class, JobInstanceAlreadyCompleteException::class)
    override fun assignCategoriesForPerson(person: Person) {
        val parameters = JobParametersBuilder()
                .addString("date", DateTimeFormatter.ofPattern("yyyy-MM-dd-hh-mm-ss").format(LocalDateTime.now()))
                .addString("personUuid", person.uuid)
                .toJobParameters()

        this.jobLauncher.run(this.assignCategoriesJob, parameters)
    }
}
