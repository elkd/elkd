package org.elkd.core.log

import org.elkd.core.consensus.messages.Entry
import org.elkd.core.log.ds.Log

class LogFacade (log: Log<Entry>) {
  val log
    get() = invoker

  val commandExecutor by lazy { LogCommandExecutor(invoker) }

  val changeRegistry by lazy { LogChangeRegistry(invoker) }

  fun registerListener(listener: LogChangeListener<Entry>) = log.registerListener(listener)
  fun deregisterListener(listener: LogChangeListener<Entry>) = log.deregisterListener(listener)

  private val invoker by lazy { LogInvoker(log) }

  override fun toString() = "Log(id=${log.id}, index=${log.lastIndex}, commit=${log.commitIndex})"
}
