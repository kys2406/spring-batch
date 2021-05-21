package me.kys2406.helloworld.configuration

import org.slf4j.LoggerFactory
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.annotation.AfterJob
import org.springframework.batch.core.annotation.BeforeJob

class JobLoggerListener {
    private val log = LoggerFactory.getLogger(this.javaClass.simpleName)

    companion object {
        val START_MESSAGE = "%s is beginning execution"
        val END_MESSAGE = "%s has completed with the status %s"
    }

    @BeforeJob
    fun beforeJob(jobExecution: JobExecution) {
        log.info(String.format(START_MESSAGE, jobExecution.jobInstance.jobName))
    }

    @AfterJob
    fun afterJob(jobExecution: JobExecution) {
        log.info(String.format(END_MESSAGE, jobExecution.jobInstance.jobName, jobExecution.status))
    }
}