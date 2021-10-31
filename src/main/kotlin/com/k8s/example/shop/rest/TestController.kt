package com.k8s.example.shop.rest

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.availability.AvailabilityChangeEvent
import org.springframework.boot.availability.LivenessState
import org.springframework.boot.availability.ReadinessState
import org.springframework.context.ApplicationEventPublisher
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.management.ManagementFactory
import java.util.*
import javax.annotation.PostConstruct


@RestController
class TestController(private val eventPublisher: ApplicationEventPublisher) {
    var logger: Logger = LoggerFactory.getLogger(TestController::class.java)

    @GetMapping("/messages")
    fun getMessage(): String {
        return "Hello";
    }

    @PostMapping("/notPrepared/{version}")
    fun youAreNotPrepared(@PathVariable version:String): String {
        if (Objects.deepEquals(version,"broken")){
        AvailabilityChangeEvent.publish(this.eventPublisher, IllegalArgumentException("no no"), LivenessState.BROKEN)}

        when (version) {
            "broken" -> AvailabilityChangeEvent.publish(this.eventPublisher, IllegalArgumentException("no no"), LivenessState.BROKEN)
            "refusing" -> AvailabilityChangeEvent.publish(this.eventPublisher, IllegalArgumentException("no no"), ReadinessState.REFUSING_TRAFFIC)
            else -> {}
        }
        return "done";
    }

    @PostConstruct
    fun params(){
        val runtimeMxBean = ManagementFactory.getRuntimeMXBean()
        val arguments = runtimeMxBean.inputArguments
        arguments.forEach { logger.warn(it) }
    }
}