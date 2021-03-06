package org.kerala.ctl.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.parameters.arguments.argument
import org.kerala.core.server.client.KeralaArgPair
import org.kerala.ctl.Context
import org.kerala.ctl.asChannel
import org.kerala.ctl.leader
import org.kerala.ctl.sendCommand
import org.kerala.shared.client.ClientACK

class CreateTopicCommand : CliktCommand(name = "create-topic") {
  private val namespace by argument("namespace", help = "namespace of the new topic")
  private val ctx by requireObject<Context>()

  override fun run() {
    try {
      echo("creating topic -> $namespace")
      val response = sendCommand(ctx.cluster!!.leader()!!.asChannel(), "create-topic", listOf(
          KeralaArgPair.newBuilder()
              .setParam("namespace")
              .setArg(namespace)
              .build())
      )
      when (response.responseCode) {
        ClientACK.Codes.OK.id -> echo(response.response)
        ClientACK.Codes.ERROR.id -> throw Exception(response.response)
      }
    } catch (e: Exception) {
      echo(e.message, err = true)
    }
  }
}
