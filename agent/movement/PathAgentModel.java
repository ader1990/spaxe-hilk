/*   1:    */ package sh.agent.movement;
/*   2:    */ 
/*   3:    */ import java.awt.Rectangle;
/*   4:    */ import java.awt.geom.Line2D.Float;
/*   5:    */ import java.io.PrintStream;
/*   6:    */ import org.newdawn.slick.geom.Vector2f;
/*   7:    */ import sh.pathfinding.AStarPathfinder;
/*   8:    */ import sh.pathfinding.AStarPathfinder.Path;
/*   9:    */ import sh.world.shworld.SpaceHulkWorldModel;
/*  10:    */ 
/*  11:    */ public class PathAgentModel
/*  12:    */   extends MovingAgentModel
/*  13:    */ {
/*  14: 19 */   protected float predict = 2.0F;
/*  15: 21 */   protected float pathDistance = 0.3F;
/*  16: 23 */   protected float slowDistance = 1.5F;
/*  17: 25 */   protected float arrivalDistance = 0.05F;
/*  18: 28 */   protected AStarPathfinder aStarPathfinder = null;
/*  19: 31 */   protected Vector2f future = new Vector2f();
/*  20: 34 */   protected Vector2f pathTarget = new Vector2f();
/*  21: 37 */   protected Vector2f target = null;
/*  22:    */   protected AStarPathfinder.Path path;
/*  23:    */   
/*  24:    */   public PathAgentModel(int UUID, String name, float x, float y, float angle, SpaceHulkWorldModel worldModel, String faction, int ap, boolean rotatable, float attackRange, int player, int team, int marineType, int colour)
/*  25:    */   {
/*  26: 45 */     super(UUID, name, x, y, angle, worldModel, faction, ap, rotatable, attackRange, player, team, marineType, colour);
/*  27:    */     
/*  28: 47 */     this.aStarPathfinder = new AStarPathfinder(worldModel);
/*  29:    */     
/*  30: 49 */     this.mass = 0.1F;
/*  31: 50 */     this.maxSpeed = 0.02F;
/*  32: 51 */     this.maxForce = 0.005F;
/*  33: 52 */     this.damping = 0.4F;
/*  34: 53 */     this.breaking = 0.9F;
/*  35:    */     
/*  36: 55 */     this.agentRadius = 0.2F;
/*  37: 56 */     this.obstacleRadius = 0.1F;
/*  38:    */     
/*  39: 58 */     this.future.set(x, y);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void think()
/*  43:    */   {
/*  44: 64 */     this.future.set(this.velocity);
/*  45: 65 */     this.future.scale(this.predict / (this.maxSpeed * this.adrenaline));
/*  46: 66 */     this.future.add(this.position);
/*  47:    */     
/*  48: 68 */     Vector2f steering = new Vector2f(0.0F, 0.0F);
/*  49:    */     
/*  50:    */ 
/*  51: 71 */     Vector2f targetSteer = steerTowardsTarget();
/*  52:    */     
/*  53:    */ 
/*  54: 74 */     Vector2f avoidSteer = steerToAvoidObstacles(this.predict);
/*  55: 75 */     avoidSteer.normalise();
/*  56:    */     
/*  57:    */ 
/*  58:    */ 
/*  59: 79 */     steering.add(targetSteer);
/*  60:    */     
/*  61:    */ 
/*  62: 82 */     applyGlobalForce(steering);
/*  63:    */   }
/*  64:    */   
/*  65:    */   protected Vector2f steerToAvoidObstacles(float predict)
/*  66:    */   {
/*  67: 86 */     Vector2f steering = new Vector2f();
/*  68:    */     
/*  69: 88 */     float minDistance = (1.0F / 1.0F);
/*  70:    */     
/*  71:    */ 
/*  72: 91 */     Vector2f future = new Vector2f(this.velocity);
/*  73: 92 */     future.scale(predict / (this.maxSpeed * this.adrenaline));
/*  74:    */     
/*  75:    */ 
/*  76: 95 */     float lead = future.length();
/*  77:    */     
/*  78:    */ 
/*  79: 98 */     future.add(this.position);
/*  80:    */     
/*  81:    */ 
/*  82:101 */     Vector2f v0 = new Vector2f(this.yForce.x, this.yForce.y).scale(this.agentRadius);
/*  83:102 */     Vector2f v3 = new Vector2f(this.yForce.x, this.yForce.y).scale(-this.agentRadius);
/*  84:    */     
/*  85:104 */     Vector2f v1 = new Vector2f(this.xForce.x, this.xForce.y).scale(lead);
/*  86:    */     
/*  87:106 */     Vector2f v2 = new Vector2f(v3.x, v3.y).add(v1);
/*  88:107 */     v1.add(v0);
/*  89:    */     
/*  90:109 */     v0.add(this.position);
/*  91:110 */     v1.add(this.position);
/*  92:111 */     v2.add(this.position);
/*  93:112 */     v3.add(this.position);
/*  94:    */     
/*  95:114 */     Rectangle b1 = new Line2D.Float(v0.x, v0.y, v2.x, v2.y).getBounds();
/*  96:115 */     Rectangle b2 = new Line2D.Float(v1.x, v1.y, v3.x, v3.y).getBounds();
/*  97:    */     
/*  98:117 */     Rectangle bounds = b1.union(b2);
/*  99:119 */     for (int x = bounds.x; x < bounds.x + bounds.width; x++) {
/* 100:120 */       for (int y = bounds.y; y < bounds.y + bounds.height; y++)
/* 101:    */       {
/* 102:122 */         this.worldModel.highlightTile(x, y, 1862336256);
/* 103:125 */         if (!this.worldModel.tileWalkable(x, y))
/* 104:    */         {
/* 105:128 */           float distance = this.agentRadius + this.obstacleRadius;
/* 106:    */           
/* 107:    */ 
/* 108:131 */           Vector2f obstacleCenter = new Vector2f(x + 0.5F, y + 0.5F);
/* 109:    */           
/* 110:    */ 
/* 111:134 */           obstacleCenter.sub(this.position);
/* 112:    */           
/* 113:    */ 
/* 114:137 */           Vector2f localObstacle = new Vector2f(obstacleCenter.dot(this.xForce), obstacleCenter.dot(this.yForce));
/* 115:140 */           if ((localObstacle.x > 0.0F) && (localObstacle.x - this.obstacleRadius < lead))
/* 116:    */           {
/* 117:141 */             Vector2f local2dCenter = new Vector2f(localObstacle);
/* 118:142 */             local2dCenter.x = 0.0F;
/* 119:    */             
/* 120:    */ 
/* 121:145 */             float futureDistance = local2dCenter.length();
/* 122:    */             
/* 123:    */ 
/* 124:148 */             float safetyMargin = this.agentRadius * this.margin;
/* 125:151 */             if (futureDistance < distance + safetyMargin)
/* 126:    */             {
/* 127:152 */               this.worldModel.highlightTile(x, y, 1878982656);
/* 128:    */               
/* 129:154 */               float clearPath = localObstacle.x - this.obstacleRadius;
/* 130:157 */               if (minDistance > clearPath)
/* 131:    */               {
/* 132:158 */                 minDistance = clearPath;
/* 133:    */                 
/* 134:160 */                 obstacleCenter.set(this.yForce.x, this.yForce.y).scale(local2dCenter.y);
/* 135:161 */                 steering.add(obstacleCenter);
/* 136:    */                 
/* 137:163 */                 obstacleCenter.set(this.xForce.x, this.xForce.y).scale(local2dCenter.x);
/* 138:164 */                 steering.add(obstacleCenter);
/* 139:    */                 
/* 140:    */ 
/* 141:167 */                 float factor = Math.max(Math.min((lead - clearPath) / lead, 1.0F), 0.0F);
/* 142:    */                 
/* 143:169 */                 steering.normalise();
/* 144:    */                 
/* 145:    */ 
/* 146:172 */                 steering.scale(-(factor * this.maxForce));
/* 147:    */                 
/* 148:    */ 
/* 149:175 */                 Vector2f braking = new Vector2f(this.xForce.x, this.xForce.y).scale(-this.breaking * factor * factor * this.maxForce);
/* 150:    */                 
/* 151:    */ 
/* 152:178 */                 float length = braking.length();
/* 153:179 */                 float threshold = 0.5F * this.velocity.length();
/* 154:180 */                 if (length > threshold) {
/* 155:181 */                   braking.scale(threshold / length);
/* 156:    */                 }
/* 157:185 */                 steering.add(braking);
/* 158:    */               }
/* 159:    */             }
/* 160:    */           }
/* 161:    */         }
/* 162:    */       }
/* 163:    */     }
/* 164:193 */     return steering;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void moveTo(float x, float y)
/* 168:    */   {
/* 169:201 */     System.out.println("move to " + x + " " + y);
/* 170:    */     
/* 171:203 */     this.moving = true;
/* 172:204 */     if (((int)x == (int)this.position.x) && ((int)y == (int)this.position.y))
/* 173:    */     {
/* 174:205 */       this.target = new Vector2f(x, y);
/* 175:206 */       this.moving = false;
/* 176:207 */       return;
/* 177:    */     }
/* 178:209 */     System.out.println("Moving forward!");
/* 179:    */     
/* 180:    */ 
/* 181:212 */     AStarPathfinder.Path newPath = this.aStarPathfinder.findPath(getX(), getY(), x, y);
/* 182:215 */     if (newPath != null)
/* 183:    */     {
/* 184:216 */       this.path = newPath;
/* 185:    */       
/* 186:    */ 
/* 187:219 */       this.target = new Vector2f(x, y);
/* 188:    */     }
/* 189:    */   }
/* 190:    */   
/* 191:    */   public boolean hasTarget()
/* 192:    */   {
/* 193:224 */     if (this.target != null) {
/* 194:226 */       return true;
/* 195:    */     }
/* 196:228 */     return false;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public float getTargetX()
/* 200:    */   {
/* 201:233 */     return this.target.x;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public float getTargetY()
/* 205:    */   {
/* 206:238 */     return this.target.y;
/* 207:    */   }
/* 208:    */   
/* 209:    */   protected Vector2f steerTowardsTarget()
/* 210:    */   {
/* 211:242 */     Vector2f steering = new Vector2f(0.0F, 0.0F);
/* 212:245 */     if (this.target != null)
/* 213:    */     {
/* 214:248 */       float distance = this.position.distance(this.target);
/* 215:250 */       if (this.path != null) {
/* 216:252 */         distance += this.path.distanceOnPath(this.target) - this.path.distanceOnPath(this.position);
/* 217:    */       }
/* 218:255 */       if (distance < this.arrivalDistance)
/* 219:    */       {
/* 220:257 */         this.target = null;
/* 221:258 */         this.path = null;
/* 222:    */         
/* 223:260 */         this.acceleration.set(0.0F, 0.0F);
/* 224:261 */         this.velocity.set(0.0F, 0.0F);
/* 225:    */         
/* 226:    */ 
/* 227:264 */         float px = (float)Math.floor(this.position.x) + 0.5F;
/* 228:265 */         float py = (float)Math.floor(this.position.y) + 0.5F;
/* 229:266 */         System.out.println("Stopping");
/* 230:267 */         this.moving = false;
/* 231:268 */         this.position.x = px;
/* 232:269 */         this.position.y = py;
/* 233:270 */         this.xForce.setTheta(getRealAngle());
/* 234:    */         
/* 235:    */ 
/* 236:    */ 
/* 237:274 */         this.future.set(this.position);
/* 238:    */       }
/* 239:275 */       else if (distance < this.slowDistance)
/* 240:    */       {
/* 241:277 */         Vector2f arrivalSteer = steerToArrive(this.target);
/* 242:    */         
/* 243:    */ 
/* 244:280 */         steering.add(arrivalSteer);
/* 245:    */       }
/* 246:281 */       else if (this.path != null)
/* 247:    */       {
/* 248:283 */         Vector2f pathSteer = steerToFollowPath();
/* 249:284 */         pathSteer.normalise();
/* 250:287 */         if ((pathSteer.x == 0.0F) && (pathSteer.y == 0.0F))
/* 251:    */         {
/* 252:288 */           Vector2f speedup = new Vector2f(this.xForce);
/* 253:289 */           pathSteer.set(speedup.scale(0.75F * this.maxForce));
/* 254:    */         }
/* 255:293 */         steering.add(pathSteer);
/* 256:    */       }
/* 257:    */     }
/* 258:297 */     return steering;
/* 259:    */   }
/* 260:    */   
/* 261:    */   protected Vector2f steerToFollowPath()
/* 262:    */   {
/* 263:301 */     Vector2f steering = new Vector2f(0.0F, 0.0F);
/* 264:303 */     if (this.path != null)
/* 265:    */     {
/* 266:305 */       float lead = this.predict * this.velocity.length() / (this.maxSpeed * this.adrenaline);
/* 267:    */       
/* 268:    */ 
/* 269:308 */       float distance = this.path.distanceFromPath(this.future);
/* 270:    */       
/* 271:    */ 
/* 272:311 */       float totalDistance = this.path.distanceOnPath(this.position);
/* 273:    */       
/* 274:    */ 
/* 275:314 */       this.pathTarget = this.path.pointOnPath(totalDistance + lead);
/* 276:    */       
/* 277:    */ 
/* 278:317 */       Vector2f tangent = this.path.getTangent(this.future);
/* 279:320 */       if ((distance >= this.pathDistance) || (this.xForce.dot(tangent) <= 0.0F)) {
/* 280:321 */         return steerTowardsPath(this.pathTarget);
/* 281:    */       }
/* 282:    */     }
/* 283:325 */     return steering;
/* 284:    */   }
/* 285:    */   
/* 286:    */   protected Vector2f steerTowardsPath(Vector2f target)
/* 287:    */   {
/* 288:329 */     Vector2f steering = new Vector2f(target).sub(this.position);
/* 289:    */     
/* 290:    */ 
/* 291:    */ 
/* 292:    */ 
/* 293:    */ 
/* 294:    */ 
/* 295:336 */     steering.add(this.velocity);
/* 296:    */     
/* 297:338 */     return steering;
/* 298:    */   }
/* 299:    */   
/* 300:    */   protected Vector2f steerToArrive(Vector2f target)
/* 301:    */   {
/* 302:342 */     Vector2f steering = new Vector2f(0.0F, 0.0F);
/* 303:    */     
/* 304:344 */     steering.set(target.x, target.y).sub(this.position);
/* 305:    */     
/* 306:    */ 
/* 307:    */ 
/* 308:    */ 
/* 309:    */ 
/* 310:    */ 
/* 311:    */ 
/* 312:    */ 
/* 313:353 */     steering.sub(this.velocity);
/* 314:    */     
/* 315:355 */     return steering;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public AStarPathfinder.Path getPath()
/* 319:    */   {
/* 320:359 */     return this.path;
/* 321:    */   }
/* 322:    */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.agent.movement.PathAgentModel
 * JD-Core Version:    0.7.0.1
 */