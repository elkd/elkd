package org.elkd.core.consensus

import io.grpc.stub.StreamObserver
import org.apache.log4j.Logger
import org.elkd.core.consensus.messages.AppendEntriesRequest
import org.elkd.core.consensus.messages.AppendEntriesResponse
import org.elkd.core.consensus.messages.RequestVoteRequest
import org.elkd.core.consensus.messages.RequestVoteResponse
import org.elkd.core.server.cluster.ClusterMessenger

class RaftLeaderDelegate(private val raft: Raft) : RaftState {

  private var mLeaderContext: LeaderContext? = null

  override fun on() {
    mLeaderContext = LeaderContext(raft.clusterSet.nodes, raft.log.lastIndex)
    LOG.info("leader ready")
    LOG.info(mLeaderContext?.toString())
  }

  override fun off() {
    LOG.info("leader offline")
  }

  override fun delegateAppendEntries(appendEntriesRequest: AppendEntriesRequest,
                                     responseObserver: StreamObserver<AppendEntriesResponse>) {
    responseObserver.onCompleted()
  }

  override fun delegateRequestVote(requestVoteRequest: RequestVoteRequest,
                                   responseObserver: StreamObserver<RequestVoteResponse>) {
    /* If term > currentTerm, Raft will always transition to Follower state. messages received
       here will only be term <= currentTerm so we can defer all logic to the raft delegate.
     */
    responseObserver.onNext(RequestVoteResponse.builder(raft.raftContext.currentTerm, false).build())
    responseObserver.onCompleted()
  }

  companion object {
    private val LOG = Logger.getLogger(RaftLeaderDelegate::class.java.name)
  }
}
