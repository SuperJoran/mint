package be.superjoran.mint.services

import be.superjoran.mint.domain.Person
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class AssignCategoriesServiceImpl(private val mintJobLauncher: JobLauncher, private val assignCategoriesJob: Job) : AssignCategoriesService {

    override fun assignCategoriesForPerson(person: Person) {
        val parameters = JobParametersBuilder()
                .addString("date", DateTimeFormatter.ofPattern("yyyy-MM-dd-hh-mm-ss").format(LocalDateTime.now()))
                .addString("personUuid", person.uuid)
                .toJobParameters()

        this.mintJobLauncher.run(this.assignCategoriesJob, parameters)
    }
}
