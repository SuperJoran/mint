package be.superjoran.mint.services;

import be.superjoran.mint.domain.Person;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class AssignCategoriesServiceImpl implements AssignCategoriesService {

    private final JobLauncher jobLauncher;
    private final Job assignCategoriesJob;

    public AssignCategoriesServiceImpl(JobLauncher jobLauncher, Job assignCategoriesJob) {
        this.jobLauncher = jobLauncher;
        this.assignCategoriesJob = assignCategoriesJob;
    }

    @Override
    public void assignCategoriesForPerson(Person person) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        JobParameters parameters = new JobParametersBuilder()
                .addString("date", DateTimeFormatter.ofPattern("yyyy-MM-dd-hh-mm-ss").format(LocalDateTime.now()))
                .addString("personUuid", person.getUuid())
                .toJobParameters();

        this.jobLauncher.run(this.assignCategoriesJob, parameters);
    }
}
