package me.kys2406.helloworld.job

import me.kys2406.helloworld.configuration.DailyJobTimeStamper
import me.kys2406.helloworld.configuration.JobLoggerListener
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.job.CompositeJobParametersValidator
import org.springframework.batch.core.listener.JobListenerFactoryBean
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnProperty(name = ["job.name"], havingValue = HelloWorld.JOB_NAME)
class HelloWorld(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
    private val validator: CompositeJobParametersValidator,
) {
    //    private val log = KotlinLogging.logger {}
    private val log = LoggerFactory.getLogger(this.javaClass.simpleName)

    companion object {
        internal const val JOB_NAME = "job"
        private const val STEP1 = "step1"
    }

    @Bean
    fun jon(): Job {
        return this.jobBuilderFactory
            .get(JOB_NAME)
            .start(step())
            .validator(validator)
            .incrementer(DailyJobTimeStamper())
//            .listener(JobLoggerListener())
            .listener(JobListenerFactoryBean.getListener(JobLoggerListener()))
            .build()
    }

    @Bean
    fun step(): Step {
        return stepBuilderFactory.get(STEP1)
            .tasklet(helloWorldTasklet("")).build()
    }

    private fun helloWorldTasklet(@Value("#{jobParameters['name']}") name: String): Tasklet {
        return Tasklet { contribution, chunkContext ->
            log.info("Hello World!! $name")
            log.info(contribution.toString())
            log.info(chunkContext.toString())
            RepeatStatus.FINISHED
        }
    }
}