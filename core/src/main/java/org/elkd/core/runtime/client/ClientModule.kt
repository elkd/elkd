package org.elkd.core.runtime.client

import org.elkd.core.runtime.topic.TopicGateway
import org.elkd.core.runtime.topic.TopicRegistry

data class ClientModule(val topicRegistry: TopicRegistry,
                        val topicGateway: TopicGateway)
