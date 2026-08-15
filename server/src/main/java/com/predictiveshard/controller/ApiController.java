package com.predictiveshard.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ApiController {
  private final AtomicLong ids = new AtomicLong(100);
  private final Map<String, Map<String,Object>> users = new ConcurrentHashMap<>();
  private final Map<Long, Map<String,Object>> clusters = new ConcurrentHashMap<>();
  private final Map<Long, Map<String,Object>> services = new ConcurrentHashMap<>();
  private final Map<Long, Map<String,Object>> shards = new ConcurrentHashMap<>();
  private final Map<Long, Map<String,Object>> predictions = new ConcurrentHashMap<>();
  private final Map<Long, Map<String,Object>> incidents = new ConcurrentHashMap<>();
  private final List<Map<String,Object>> telemetry = Collections.synchronizedList(new ArrayList<>());
  private final List<Map<String,Object>> edges = Collections.synchronizedList(new ArrayList<>());
  private final List<Map<String,Object>> audit = Collections.synchronizedList(new ArrayList<>());

  public ApiController() { seed(); }
  private Map<String,Object> obj(Object... v) { var m = new LinkedHashMap<String,Object>(); for(int i=0;i<v.length;i+=2)m.put((String)v[i],v[i+1]); return m; }
  private long id() { return ids.incrementAndGet(); }
  private String now() { return Instant.now().toString(); }
  private void record(String action, String resource, Object resourceId) { audit.add(0,obj("id",id(),"action",action,"resourceType",resource,"resourceId",resourceId,"correlationId",UUID.randomUUID().toString(),"timestamp",now())); }
  private List<Map<String,Object>> all(Map<Long,Map<String,Object>> store) { return new ArrayList<>(store.values()); }
  private Map<String,Object> require(Map<Long,Map<String,Object>> store, long id) { var r=store.get(id); if(r==null) throw new NoSuchElementException("Resource " + id + " was not found"); return r; }

  private void seed() {
    users.put("admin@predictiveshard.ai", obj("id",1,"name","Demo Admin","email","admin@predictiveshard.ai","role","ADMIN"));
    users.put("operator@predictiveshard.ai", obj("id",2,"name","Demo Operator","email","operator@predictiveshard.ai","role","OPERATOR"));
    long cluster=1; clusters.put(cluster,obj("id",cluster,"name","Demo Commerce Cluster","environment","simulation","region","local","status","HEALTHY","createdAt",now(),"updatedAt",now()));
    String[] names={"api-gateway","product-service","inventory-service","search-service","cart-service","checkout-service","database-service"}; Map<String,Long> svc=new HashMap<>();
    for(String name:names){ long x=id(); svc.put(name,x); services.put(x,obj("id",x,"clusterId",cluster,"name",name,"version","1.0.0","replicas",2,"cpuCapacity",100,"memoryCapacity",100,"requestCapacity",10000,"currentStatus","HEALTHY")); }
    for(String name:new String[]{"product-shard-01","product-shard-02","product-shard-03","product-shard-04"}) addShard(svc.get("product-service"),name);
    for(String name:new String[]{"inventory-shard-01","inventory-shard-02"}) addShard(svc.get("inventory-service"),name);
    for(String name:new String[]{"database-shard-01","database-shard-02"}) addShard(svc.get("database-service"),name);
    addEdge(cluster,svc,"api-gateway","product-service"); addEdge(cluster,svc,"api-gateway","search-service"); addEdge(cluster,svc,"api-gateway","cart-service"); addEdge(cluster,svc,"cart-service","inventory-service"); addEdge(cluster,svc,"checkout-service","inventory-service"); addEdge(cluster,svc,"inventory-service","database-service");
  }
  private void addShard(long serviceId,String name){ long x=id(); shards.put(x,obj("id",x,"serviceId",serviceId,"name",name,"nodeId","node-"+x,"capacity",100,"currentLoad",35,"cpuUsage",32,"memoryUsage",38,"networkUsage",22,"queueLength",90,"requestRate",1800,"p95Latency",74,"cacheMissRate",7,"dbConnectionUsage",29,"retryRate",.4,"status","HEALTHY","trafficPercent",25)); }
  private void addEdge(long cluster,Map<String,Long> s,String a,String b){ edges.add(obj("id",id(),"clusterId",cluster,"sourceServiceId",s.get(a),"targetServiceId",s.get(b),"dependencyType","HTTP","weight",.8,"averageLatency",45,"timeoutMs",500,"failurePropagationFactor",.7)); }

  @GetMapping("/health") public Map<String,Object> health(){return obj("status","ok","service","PredictiveShard AI API","mlRuntime","fallback","kafka","configured","redis","optional","database","in-memory-demo","autoRemediationEnabled",false);}
  @PostMapping("/auth/register") public ResponseEntity<?> register(@RequestBody @Valid Credentials input){ if(users.containsKey(input.email())) return ResponseEntity.status(409).body(obj("message","Email already registered")); var u=obj("id",id(),"name",input.name(),"email",input.email(),"role","VIEWER");users.put(input.email(),u);record("USER_REGISTERED","USER",u.get("id"));return ResponseEntity.status(201).body(obj("user",u,"token","demo."+Base64.getEncoder().encodeToString(input.email().getBytes())+".token"));}
  @PostMapping("/auth/login") public ResponseEntity<?> login(@RequestBody Credentials input){var u=users.get(input.email());if(u==null||!"Password@123".equals(input.password()))return ResponseEntity.status(401).body(obj("message","Invalid credentials"));return ResponseEntity.ok(obj("user",u,"token","demo."+Base64.getEncoder().encodeToString(input.email().getBytes())+".token"));}
  @GetMapping("/auth/me") public Map<String,Object> me(){return users.get("operator@predictiveshard.ai");}

  @GetMapping("/clusters") public List<Map<String,Object>> clusters(){return all(clusters);}
  @PostMapping("/clusters") public ResponseEntity<?> createCluster(@RequestBody Map<String,Object> body){long x=id();var v=obj("id",x,"name",body.getOrDefault("name","New Cluster"),"environment",body.getOrDefault("environment","simulation"),"region",body.getOrDefault("region","local"),"status","UNKNOWN","createdAt",now(),"updatedAt",now());clusters.put(x,v);record("CLUSTER_CREATED","CLUSTER",x);return ResponseEntity.status(201).body(v);}
  @GetMapping("/clusters/{id}") public Map<String,Object> cluster(@PathVariable long id){var c=new LinkedHashMap<>(require(clusters,id)); c.put("totalServices",services.values().stream().filter(s->Objects.equals(s.get("clusterId"),id)).count());c.put("totalShards",shards.size());return c;}
  @PatchMapping("/clusters/{id}") public Map<String,Object> updateCluster(@PathVariable long id,@RequestBody Map<String,Object> body){var c=require(clusters,id);c.putAll(body);c.put("updatedAt",now());record("CLUSTER_UPDATED","CLUSTER",id);return c;}
  @DeleteMapping("/clusters/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void deleteCluster(@PathVariable long id){clusters.remove(id);record("CLUSTER_DELETED","CLUSTER",id);}

  @GetMapping("/clusters/{clusterId}/services") public List<Map<String,Object>> listServices(@PathVariable long clusterId){return services.values().stream().filter(s->Objects.equals(s.get("clusterId"),clusterId)).toList();}
  @GetMapping("/services/{id}") public Map<String,Object> service(@PathVariable long id){return require(services,id);}
  @PostMapping("/clusters/{clusterId}/services") public ResponseEntity<?> createService(@PathVariable long clusterId,@RequestBody Map<String,Object> body){long x=id();var s=obj("id",x,"clusterId",clusterId,"name",body.get("name"),"version",body.getOrDefault("version","1.0.0"),"replicas",body.getOrDefault("replicas",1),"cpuCapacity",100,"memoryCapacity",100,"requestCapacity",10000,"currentStatus","HEALTHY");services.put(x,s);record("SERVICE_CREATED","SERVICE",x);return ResponseEntity.status(201).body(s);}
  @PatchMapping("/services/{id}") public Map<String,Object> updateService(@PathVariable long id,@RequestBody Map<String,Object> b){var s=require(services,id);s.putAll(b);record("SERVICE_UPDATED","SERVICE",id);return s;}
  @DeleteMapping("/services/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void deleteService(@PathVariable long id){services.remove(id);record("SERVICE_DELETED","SERVICE",id);}
  @GetMapping("/services/{serviceId}/shards") public List<Map<String,Object>> listShards(@PathVariable long serviceId){return shards.values().stream().filter(s->Objects.equals(s.get("serviceId"),serviceId)).toList();}
  @GetMapping("/shards/{id}") public Map<String,Object> shard(@PathVariable long id){return require(shards,id);}
  @PostMapping("/services/{serviceId}/shards") public ResponseEntity<?> createShard(@PathVariable long serviceId,@RequestBody Map<String,Object> b){long x=id();var s=obj("id",x,"serviceId",serviceId,"name",b.getOrDefault("name","shard-"+x),"capacity",100,"currentLoad",0,"status","HEALTHY");shards.put(x,s);record("SHARD_CREATED","SHARD",x);return ResponseEntity.status(201).body(s);}
  @PatchMapping("/shards/{id}") public Map<String,Object> updateShard(@PathVariable long id,@RequestBody Map<String,Object> b){var s=require(shards,id);s.putAll(b);record("SHARD_UPDATED","SHARD",id);return s;}
  @DeleteMapping("/shards/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void deleteShard(@PathVariable long id){shards.remove(id);record("SHARD_DELETED","SHARD",id);}
  @GetMapping("/clusters/{id}/graph") public Map<String,Object> graph(@PathVariable long id){return obj("nodes",listServices(id),"edges",edges.stream().filter(e->Objects.equals(e.get("clusterId"),id)).toList(),"hasCycle",false);}
  @PostMapping("/clusters/{id}/graph/edges") public ResponseEntity<?> createEdge(@PathVariable long id,@RequestBody Map<String,Object> b){var e=new LinkedHashMap<>(b);e.put("id",id());e.put("clusterId",id);e.putIfAbsent("weight",.8);e.putIfAbsent("failurePropagationFactor",.7);edges.add(e);record("GRAPH_EDGE_CREATED","DEPENDENCY_EDGE",e.get("id"));return ResponseEntity.status(201).body(e);}
  @DeleteMapping("/graph/edges/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void deleteEdge(@PathVariable long id){edges.removeIf(e->Objects.equals(e.get("id"),id));record("GRAPH_EDGE_DELETED","DEPENDENCY_EDGE",id);}

  @GetMapping("/telemetry") public List<Map<String,Object>> telemetry(@RequestParam(required=false) Long shardId){return telemetry.stream().filter(t->shardId==null||Objects.equals(t.get("shardId"),shardId)).toList();}
  @GetMapping("/telemetry/{shardId}/latest") public Map<String,Object> latest(@PathVariable long shardId){return telemetry.stream().filter(t->Objects.equals(t.get("shardId"),shardId)).reduce((a,b)->b).orElseGet(()->new LinkedHashMap<>(require(shards,shardId)));}
  @GetMapping("/telemetry/{shardId}/aggregate") public Map<String,Object> aggregate(@PathVariable long shardId){var t=latest(shardId);return obj("shardId",shardId,"window","5m","count",telemetry.size(),"cpuUsage",t.getOrDefault("cpuUsage",0),"p95Latency",t.getOrDefault("p95Latency",0),"queueLength",t.getOrDefault("queueLength",0));}
  @PostMapping("/telemetry/ingest") public ResponseEntity<?> ingest(@RequestBody Map<String,Object> body){if(telemetry.stream().anyMatch(t->Objects.equals(t.get("eventId"),body.get("eventId"))))return ResponseEntity.ok(obj("duplicate",true));var t=new LinkedHashMap<>(body);t.putIfAbsent("id",id());t.putIfAbsent("eventId",UUID.randomUUID().toString());t.putIfAbsent("timestamp",now());telemetry.add(t);return ResponseEntity.status(201).body(t);}

  @GetMapping("/predictions") public List<Map<String,Object>> predictions(){return all(predictions);}
  @GetMapping("/predictions/{shardId}/latest") public Map<String,Object> latestPrediction(@PathVariable long shardId){return predictions.values().stream().filter(p->Objects.equals(p.get("shardId"),shardId)).reduce((a,b)->b).orElseThrow(()->new NoSuchElementException("No prediction for shard"));}
  @GetMapping("/predictions/{shardId}/history") public List<Map<String,Object>> predictionHistory(@PathVariable long shardId){return predictions.values().stream().filter(p->Objects.equals(p.get("shardId"),shardId)).toList();}
  @PostMapping("/predictions/run") public Map<String,Object> runPrediction(@RequestBody Map<String,Object> b){long shardId=((Number)b.get("shardId")).longValue();return createPrediction(shardId);}
  private Map<String,Object> createPrediction(long shardId){var s=require(shards,shardId);double cpu=((Number)s.getOrDefault("cpuUsage",0)).doubleValue(), q=((Number)s.getOrDefault("queueLength",0)).doubleValue(),lat=((Number)s.getOrDefault("p95Latency",0)).doubleValue(),retry=((Number)s.getOrDefault("retryRate",0)).doubleValue();double risk=Math.min(1,.18*cpu/100+.15*q/2000+.15*lat/500+.10*retry/20);long x=id();var p=obj("id",x,"clusterId",1,"serviceId",s.get("serviceId"),"shardId",shardId,"timestamp",now(),"overloadProbability",risk,"failureProbability",risk*.78,"estimatedTimeToFailureSeconds",Math.max(15,Math.round(180*(1-risk))),"confidence",.82,"riskLevel",risk>=.9?"CRITICAL":risk>=.7?"HIGH":risk>=.4?"MEDIUM":"LOW","contributingFactors",List.of("CPU utilization rising","Queue length accelerating","p95 latency elevated"),"modelVersion","fallback-1.0.0","fallbackUsed",true,"generatedAt",now());predictions.put(x,p);record("PREDICTION_GENERATED","PREDICTION",x);if(risk>=.7)createIncident(p);return p;}
  private void createIncident(Map<String,Object> p){if(incidents.values().stream().anyMatch(i->Objects.equals(i.get("rootShardId"),p.get("shardId"))&&!Objects.equals(i.get("status"),"RESOLVED")))return;long x=id();var i=obj("id",x,"clusterId",1,"rootServiceId",p.get("serviceId"),"rootShardId",p.get("shardId"),"severity",p.get("riskLevel"),"status","DETECTED","predictionId",p.get("id"),"blastRadius",28.57,"failurePaths",List.of("product-service → inventory-service → database-service"),"recommendedAction","REDISTRIBUTE_TRAFFIC","startedAt",now(),"createdAt",now());incidents.put(x,i);record("INCIDENT_CREATED","INCIDENT",x);}

  @GetMapping("/incidents") public List<Map<String,Object>> incidentList(){return all(incidents);}
  @GetMapping("/incidents/{id}") public Map<String,Object> incident(@PathVariable long id){return require(incidents,id);}
  @PatchMapping("/incidents/{id}") public Map<String,Object> updateIncident(@PathVariable long id,@RequestBody Map<String,Object> b){var i=require(incidents,id);i.putAll(b);record("INCIDENT_UPDATED","INCIDENT",id);return i;}
  @GetMapping("/remediation") public List<Map<String,Object>> remediation(){return incidents.values().stream().filter(i->i.containsKey("remediation")).map(i->(Map<String,Object>)i.get("remediation")).toList();}
  @PostMapping("/incidents/{id}/recommend") public Map<String,Object> recommend(@PathVariable long id){var i=require(incidents,id);long source=((Number)i.get("rootShardId")).longValue();var targets=shards.values().stream().filter(s->!Objects.equals(s.get("id"),source)&&Objects.equals(s.get("serviceId"),i.get("rootServiceId"))).toList();var r=obj("id",this.id(),"incidentId",id,"actionType","REDISTRIBUTE_TRAFFIC","sourceShardId",source,"targetShardId",targets.isEmpty()?null:targets.getFirst().get("id"),"percentage",17,"status","RECOMMENDED","expectedRiskReduction",.55,"actualRiskReduction",null,"migrationCost",17,"allocations",targets.stream().map(t->obj("shardId",t.get("id"),"currentTrafficPercent",t.get("trafficPercent"),"recommendedTrafficPercent",((Number)t.get("trafficPercent")).intValue()+6,"deltaPercent",6)).toList());i.put("remediation",r);record("REMEDIATION_RECOMMENDED","REMEDIATION",r.get("id"));return r;}
  @PostMapping("/incidents/{id}/approve") public Map<String,Object> approve(@PathVariable long id){var r=(Map<String,Object>)require(incidents,id).get("remediation");if(r==null)throw new IllegalStateException("Recommendation required before approval");r.put("status","APPROVED");record("REMEDIATION_APPROVED","REMEDIATION",r.get("id"));return r;}
  @PostMapping("/incidents/{id}/execute") public Map<String,Object> execute(@PathVariable long id){var i=require(incidents,id);var r=(Map<String,Object>)i.get("remediation");if(r==null||!"APPROVED".equals(r.get("status")))throw new IllegalStateException("Approved recommendation required");var source=require(shards,((Number)r.get("sourceShardId")).longValue());source.put("trafficPercent",Math.max(0,((Number)source.get("trafficPercent")).intValue()-17));source.put("cpuUsage",42);source.put("queueLength",300);source.put("status","HEALTHY");r.put("status","EXECUTED");r.put("actualRiskReduction",.52);r.put("executedAt",now());i.put("status","RESOLVED");i.put("resolvedAt",now());record("REMEDIATION_EXECUTED","REMEDIATION",r.get("id"));return r;}
  @PostMapping("/incidents/{id}/rollback") public Map<String,Object> rollback(@PathVariable long id){var r=(Map<String,Object>)require(incidents,id).get("remediation");if(r==null)throw new IllegalStateException("No remediation");r.put("status","ROLLED_BACK");record("REMEDIATION_ROLLED_BACK","REMEDIATION",r.get("id"));return r;}
  @PostMapping("/clusters/{id}/simulate") public Map<String,Object> clusterSimulate(@PathVariable long id,@RequestBody Map<String,Object> b){return simulate(id,b);}
  @PostMapping("/simulations") public Map<String,Object> simulation(@RequestBody Map<String,Object> b){return simulate(((Number)b.getOrDefault("clusterId",1)).longValue(),b);}
  @GetMapping("/simulations") public List<Map<String,Object>> simulations(){return List.of();}
  @GetMapping("/simulations/{id}") public Map<String,Object> simulationById(@PathVariable long id){return obj("id",id,"status","COMPLETED");}
  @PostMapping("/simulations/{id}/{action:start|pause|resume|stop|reset}") public Map<String,Object> simulationAction(@PathVariable long id,@PathVariable String action){return obj("id",id,"status",action.equals("stop")?"STOPPED":action.equals("reset")?"CREATED":"RUNNING","action",action.toUpperCase());}
  private Map<String,Object> simulate(long clusterId,Map<String,Object> b){String scenario=String.valueOf(b.getOrDefault("scenario","HOT_SHARD"));long seed=((Number)b.getOrDefault("randomSeed",12345)).longValue();var random=new Random(seed);var target=shards.values().stream().filter(s->"product-shard-03".equals(s.get("name"))).findFirst().orElseThrow();for(int n=0;n<20;n++){for(var s:shards.values()){boolean hot=scenario.equals("HOT_SHARD")&&Objects.equals(s.get("id"),target.get("id"));double factor=hot?.65+.015*n:.18;double cpu=Math.min(99,20+factor*100+random.nextDouble()*3), queue=Math.round(factor*1900+random.nextDouble()*30),lat=Math.round(55+factor*430);s.put("cpuUsage",cpu);s.put("queueLength",queue);s.put("p95Latency",lat);s.put("retryRate",Math.round(factor*10*10)/10.0);s.put("status",cpu>85?"OVERLOADED":cpu>65?"WARNING":"HEALTHY");telemetry.add(obj("id",id(),"eventId",scenario+"-"+seed+"-"+n+"-"+s.get("id"),"timestamp",Instant.now().plusSeconds(n).toString(),"clusterId",clusterId,"serviceId",s.get("serviceId"),"shardId",s.get("id"),"requestRate",Math.round(1600+factor*7000),"cpuUsage",cpu,"memoryUsage",45+factor*30,"networkUsage",35+factor*25,"queueLength",queue,"p50Latency",lat*.55,"p95Latency",lat,"p99Latency",lat*1.3,"cacheHitRate",90-factor*35,"cacheMissRate",10+factor*35,"dbConnectionUsage",30+factor*55,"dbLatency",35+factor*150,"retryRate",s.get("retryRate"),"errorRate",factor*4,"activeConnections",200+factor*1000));}}
    var p=createPrediction(((Number)target.get("id")).longValue());record("SIMULATION_STARTED","SIMULATION",scenario);return obj("id",id(),"clusterId",clusterId,"scenario",scenario,"randomSeed",seed,"status","COMPLETED","eventsGenerated",telemetry.size(),"highRiskShard",target.get("name"),"prediction",p);}
  @GetMapping("/replays") public List<Map<String,Object>> replays(){return List.of(obj("id",1,"name","HOT_SHARD demo replay","scenario","HOT_SHARD","randomSeed",12345,"duration",60,"trafficMultiplier",1,"noiseLevel",.05));}
  @GetMapping("/replays/{id}") public Map<String,Object> replay(@PathVariable long id){return replays().getFirst();}
  @PostMapping("/replays/{id}/start") public Map<String,Object> replayStart(@PathVariable long id){return simulate(1,obj("scenario","HOT_SHARD","randomSeed",12345));}
  @GetMapping("/analytics/overview") public Map<String,Object> overview(){return obj("totalPredictions",predictions.size(),"highRiskPredictions",predictions.values().stream().filter(p->Set.of("HIGH","CRITICAL").contains(p.get("riskLevel"))).count(),"incidents",incidents.size(),"activeIncidents",incidents.values().stream().filter(i->!"RESOLVED".equals(i.get("status"))).count(),"averageRiskReduction",.52,"remediationSuccessRate",1.0,"fallbackPredictionCount",predictions.size(),"mlAvailability","fallback");}
  @GetMapping("/analytics/{section:predictions|incidents|remediation|model}") public Map<String,Object> analytics(@PathVariable String section){return obj("section",section,"data",overview());}
  @GetMapping("/audit-logs") public List<Map<String,Object>> audit(){return audit;}
  @ExceptionHandler({NoSuchElementException.class}) public ResponseEntity<?> missing(Exception e){return ResponseEntity.status(404).body(obj("timestamp",now(),"status",404,"error","Not Found","message",e.getMessage(),"correlationId",UUID.randomUUID().toString()));}
  @ExceptionHandler({IllegalStateException.class}) public ResponseEntity<?> invalid(Exception e){return ResponseEntity.badRequest().body(obj("timestamp",now(),"status",400,"error","Bad Request","message",e.getMessage(),"correlationId",UUID.randomUUID().toString()));}
  public record Credentials(@NotBlank String name, @NotBlank String email, @NotBlank String password) {}
}
