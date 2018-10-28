package org.elkd.core.cluster;

import java.util.Set;

public interface ClusterConfig {
  int clusterSize();

  void addNode(Node uri);

  void removeNode(Node uri);

  Set<Node> getNodes();
}
