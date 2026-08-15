package com.predictiveshard;

import com.predictiveshard.graph.GraphEngine;
import com.predictiveshard.optimization.TrafficOptimizer;
import com.predictiveshard.simulation.DeterministicSimulator;
import com.predictiveshard.util.ConsistentHashRing;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class CoreAlgorithmsTest {
  @Test void traversesAndDetectsCycles(){var g=new GraphEngine(List.of(new GraphEngine.Edge("A","B",1,1,1),new GraphEngine.Edge("B","C",1,1,1)));assertEquals(List.of("A","B","C"),g.bfs("A"));assertFalse(g.hasCycle());assertEquals(3,g.cascade("A",.9,.1).affectedNodes().size());}
  @Test void detectsCycle(){var g=new GraphEngine(List.of(new GraphEngine.Edge("A","B",1,1,1),new GraphEngine.Edge("B","A",1,1,1)));assertTrue(g.hasCycle());}
  @Test void hashRingPreservesMostAssignments(){var prior=new ConsistentHashRing(64);prior.addShard("a");prior.addShard("b");var next=new ConsistentHashRing(64);next.addShard("a");next.addShard("b");next.addShard("c");var keys=java.util.stream.IntStream.range(0,1000).mapToObj(i->"key-"+i).toList();assertTrue(next.migrationFraction(keys,prior)<.6);}
  @Test void optimizerRespectsCapacityAndConservation(){var o=new TrafficOptimizer();var r=o.optimize("hot",List.of(new TrafficOptimizer.Shard("hot",45,.95,.92,300,50),new TrafficOptimizer.Shard("a",25,.35,.12,80,40),new TrafficOptimizer.Shard("b",30,.4,.18,90,42)),.5,25);assertTrue(r.feasible());assertEquals(100,r.allocations().stream().mapToDouble(TrafficOptimizer.Allocation::recommendedTrafficPercent).sum(),.001);assertTrue(r.estimatedRiskReduction()>0);}
  @Test void simulationIsReproducible(){var s=new DeterministicSimulator();var c=new DeterministicSimulator.Config(DeterministicSimulator.Scenario.HOT_SHARD,10,1,12345,.05);assertEquals(s.generate(c,List.of("a","b","c")),s.generate(c,List.of("a","b","c")));}
}
