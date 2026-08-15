package com.predictiveshard.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public final class ConsistentHashRing {
  private final int virtualNodes; private final NavigableMap<Long,String> ring=new TreeMap<>();
  public ConsistentHashRing(int virtualNodes){if(virtualNodes<1)throw new IllegalArgumentException("virtualNodes must be positive");this.virtualNodes=virtualNodes;}
  public void addShard(String shard){for(int i=0;i<virtualNodes;i++)ring.put(hash(shard+"#"+i),shard);}
  public void removeShard(String shard){ring.entrySet().removeIf(e->e.getValue().equals(shard));}
  public String assign(String key){if(ring.isEmpty())throw new IllegalStateException("No shards available");var e=ring.ceilingEntry(hash(key));return(e==null?ring.firstEntry():e).getValue();}
  public double migrationFraction(Collection<String> keys, ConsistentHashRing prior){if(keys.isEmpty())return 0;return keys.stream().filter(k->!assign(k).equals(prior.assign(k))).count()/(double)keys.size();}
  public Set<String> shards(){return Set.copyOf(ring.values());}
  private static long hash(String value){try{byte[] d=MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8));long result=0;for(int i=0;i<8;i++)result=(result<<8)|(d[i]&0xffL);return result&Long.MAX_VALUE;}catch(NoSuchAlgorithmException e){throw new IllegalStateException(e);}}
}
