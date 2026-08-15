package com.predictiveshard.simulation;

import java.time.Instant;
import java.util.*;

/** Correlated synthetic telemetry generator; never uses wall-clock time in its logical sequence. */
public final class DeterministicSimulator {
  public enum Scenario { NORMAL_TRAFFIC, TRAFFIC_SPIKE, CACHE_DEGRADATION, DATABASE_LATENCY, RETRY_STORM, HOT_SHARD, DEPENDENCY_FAILURE, CASCADING_FAILURE, RANDOM_MIXED_LOAD }
  public record Config(Scenario scenario,int durationSeconds,double trafficMultiplier,long randomSeed,double noiseLevel) { public Config {if(durationSeconds<1||trafficMultiplier<=0||noiseLevel<0||noiseLevel>1)throw new IllegalArgumentException("Invalid simulation configuration");} }
  public record Event(String eventId,Instant timestamp,String shardId,double requestRate,double cpuUsage,double queueLength,double p95Latency,double cacheMissRate,double dbLatency,double retryRate,double errorRate) {}
  public List<Event> generate(Config config,List<String> shardIds){var random=new Random(config.randomSeed());var out=new ArrayList<Event>();var start=Instant.ofEpochSecond(1_700_000_000L);for(int tick=0;tick<config.durationSeconds();tick++){double phase=tick/(double)Math.max(1,config.durationSeconds()-1);for(int index=0;index<shardIds.size();index++){var shard=shardIds.get(index);double pressure=pressure(config.scenario(),phase,index);double noise=(random.nextDouble()-.5)*config.noiseLevel();double effective=Math.max(0,pressure+noise)*config.trafficMultiplier();double request=1800*(1+effective*3);double queue=Math.max(0,80+effective*1650);double cpu=Math.min(100,22+effective*72);double cache=Math.min(100,7+effective*38);double db=35+effective*160;double latency=55+effective*405;double retry=Math.max(0,effective*9);double error=Math.max(0,(effective-.68)*12);out.add(new Event(config.scenario()+"-"+config.randomSeed()+"-"+tick+"-"+shard,start.plusSeconds(tick),shard,request,cpu,queue,latency,cache,db,retry,error));}}return out;}
  private double pressure(Scenario scenario,double p,int shardIndex){return switch(scenario){case NORMAL_TRAFFIC->.13;case HOT_SHARD->shardIndex==2?.22+.75*p:.14;case TRAFFIC_SPIKE->.15+.7*p;case CACHE_DEGRADATION->.15+.55*p;case DATABASE_LATENCY->.18+.58*p;case RETRY_STORM->.16+.82*p*p;case DEPENDENCY_FAILURE->shardIndex>1?.2+.62*p:.18;case CASCADING_FAILURE->.15+.75*p*(shardIndex%3+1)/3;case RANDOM_MIXED_LOAD->.15+.6*p;};}
}
