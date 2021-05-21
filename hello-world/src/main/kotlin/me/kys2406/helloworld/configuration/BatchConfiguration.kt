package me.kys2406.helloworld.configuration

import org.springframework.batch.core.job.CompositeJobParametersValidator
import org.springframework.batch.core.job.DefaultJobParametersValidator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BatchConfiguration {
    //Parameter를 엄격하게 적용하고 싶을때...

    @Bean
    fun validator(): CompositeJobParametersValidator {
        val validator = CompositeJobParametersValidator()
        val defaultJobParametersValidator = DefaultJobParametersValidator(
            arrayOf("name"),
            arrayOf("currentDate", "aaa")
        )

        defaultJobParametersValidator.afterPropertiesSet()

        validator.setValidators(
            listOf(DefaultJobParametersValidator(),
                defaultJobParametersValidator)
        )

        return validator
    }
}