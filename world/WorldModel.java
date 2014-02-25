/*   1:    */ package sh.world;
/*   2:    */ 
/*   3:    */ import java.awt.geom.Arc2D;
/*   4:    */ import java.awt.geom.Arc2D.Float;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Collection;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Observable;
/*  10:    */ import java.util.concurrent.ConcurrentSkipListMap;
/*  11:    */ import org.newdawn.slick.SlickException;
/*  12:    */ import sh.agent.AgentModel;
/*  13:    */ import sh.agent.door.DoorModel;
/*  14:    */ import sh.agent.fire.FireModel;
/*  15:    */ import sh.agent.weapons.projectiles.BulletModel;
/*  16:    */ import sh.gameobject.GameModel;
/*  17:    */ 
/*  18:    */ public class WorldModel
/*  19:    */   extends Observable
/*  20:    */ {
/*  21: 21 */   protected Map<Integer, AgentModel> agentModels = new ConcurrentSkipListMap();
/*  22: 22 */   protected Map<Integer, GameModel> gameModels = new ConcurrentSkipListMap();
/*  23: 23 */   protected Map<Integer, BulletModel> bulletModels = new ConcurrentSkipListMap();
/*  24:    */   
/*  25:    */   public void update(int delta)
/*  26:    */     throws SlickException
/*  27:    */   {
/*  28: 26 */     for (AgentModel agent : this.bulletModels.values()) {
/*  29: 27 */       agent.think();
/*  30:    */     }
/*  31: 30 */     for (AgentModel agent : this.bulletModels.values()) {
/*  32: 31 */       agent.act();
/*  33:    */     }
/*  34: 34 */     for (AgentModel agent : this.agentModels.values()) {
/*  35: 35 */       agent.think();
/*  36:    */     }
/*  37: 38 */     for (AgentModel agent : this.agentModels.values()) {
/*  38: 39 */       agent.act();
/*  39:    */     }
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void addGameModel(GameModel agentModel)
/*  43:    */   {
/*  44: 48 */     this.gameModels.put(Integer.valueOf(agentModel.getUUID()), agentModel);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void addAgentModel(AgentModel agentModel)
/*  48:    */   {
/*  49: 52 */     this.agentModels.put(Integer.valueOf(agentModel.getUUID()), agentModel);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void addBulletModel(BulletModel agentModel)
/*  53:    */   {
/*  54: 56 */     this.bulletModels.put(Integer.valueOf(agentModel.getUUID()), agentModel);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void removeAgentModel(int UUID)
/*  58:    */   {
/*  59: 61 */     this.agentModels.remove(Integer.valueOf(UUID));
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void removeGameModel(int UUID)
/*  63:    */   {
/*  64: 65 */     this.gameModels.remove(Integer.valueOf(UUID));
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void removeBulletModel(int UUID)
/*  68:    */   {
/*  69: 69 */     this.bulletModels.remove(Integer.valueOf(UUID));
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void clearAgentModels()
/*  73:    */   {
/*  74: 73 */     this.agentModels.clear();
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void clearGameModels()
/*  78:    */   {
/*  79: 77 */     this.gameModels.clear();
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void clearBulletModels()
/*  83:    */   {
/*  84: 81 */     this.bulletModels.clear();
/*  85:    */   }
/*  86:    */   
/*  87:    */   public Collection<AgentModel> getAgentModels()
/*  88:    */   {
/*  89: 85 */     return this.agentModels.values();
/*  90:    */   }
/*  91:    */   
/*  92:    */   public Collection<GameModel> getGameModels()
/*  93:    */   {
/*  94: 89 */     return this.gameModels.values();
/*  95:    */   }
/*  96:    */   
/*  97:    */   public Collection<BulletModel> getBulletModels()
/*  98:    */   {
/*  99: 93 */     return this.bulletModels.values();
/* 100:    */   }
/* 101:    */   
/* 102:    */   public <T extends AgentModel> Collection<T> getAgentModels(Class<T> cls)
/* 103:    */   {
/* 104: 98 */     List<T> agents = new ArrayList();
/* 105:100 */     for (AgentModel agent : this.agentModels.values()) {
/* 106:101 */       if (cls.isAssignableFrom(agent.getClass())) {
/* 107:102 */         agents.add(agent);
/* 108:    */       }
/* 109:    */     }
/* 110:106 */     return agents;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public <T extends GameModel> Collection<T> getGameModels(Class<T> cls)
/* 114:    */   {
/* 115:112 */     List<T> agents = new ArrayList();
/* 116:114 */     for (GameModel agent : this.gameModels.values()) {
/* 117:115 */       if (cls.isAssignableFrom(agent.getClass())) {
/* 118:116 */         agents.add(agent);
/* 119:    */       }
/* 120:    */     }
/* 121:120 */     return agents;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public AgentModel getAgentModel(int UUID)
/* 125:    */   {
/* 126:124 */     return (AgentModel)this.agentModels.get(Integer.valueOf(UUID));
/* 127:    */   }
/* 128:    */   
/* 129:    */   public GameModel getGameModel(int UUID)
/* 130:    */   {
/* 131:128 */     return (GameModel)this.gameModels.get(Integer.valueOf(UUID));
/* 132:    */   }
/* 133:    */   
/* 134:    */   public int agentCount()
/* 135:    */   {
/* 136:132 */     return this.agentModels.size();
/* 137:    */   }
/* 138:    */   
/* 139:    */   public int gameCount()
/* 140:    */   {
/* 141:136 */     return this.gameModels.size();
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void destroy()
/* 145:    */   {
/* 146:139 */     clearAgentModels();
/* 147:140 */     clearGameModels();
/* 148:141 */     clearBulletModels();
/* 149:    */   }
/* 150:    */   
/* 151:    */   public List<? extends AgentModel> getAgentsInRange(AgentModel a, float range, float angle)
/* 152:    */   {
/* 153:145 */     return getAgentsInArcRange(a, this.agentModels.values(), range, angle);
/* 154:    */   }
/* 155:    */   
/* 156:    */   public List<? extends GameModel> getObjectsInRange(GameModel a, float range, float angle)
/* 157:    */   {
/* 158:148 */     return getObjectsInArcRange(a, this.gameModels.values(), range, angle);
/* 159:    */   }
/* 160:    */   
/* 161:    */   public List<? extends AgentModel> getAgentsInRange(AgentModel a, float range)
/* 162:    */   {
/* 163:152 */     return getAgentsInEuclideanRange(a, this.agentModels.values(), range);
/* 164:    */   }
/* 165:    */   
/* 166:    */   public List<? extends GameModel> getObjectsInRange(GameModel a, float range)
/* 167:    */   {
/* 168:156 */     return getObjectsInEuclideanRange(a, this.gameModels.values(), range);
/* 169:    */   }
/* 170:    */   
/* 171:    */   public List<? extends AgentModel> getAttackableAgentsInRange(AgentModel a, float range)
/* 172:    */   {
/* 173:160 */     return getAttackableAgentsInEuclideanRange(a, this.agentModels.values(), range);
/* 174:    */   }
/* 175:    */   
/* 176:    */   public List<? extends AgentModel> getAttackableAgentsInRange(float x, float y, float range)
/* 177:    */   {
/* 178:164 */     return getAttackableAgentsInEuclideanRange(x, y, this.agentModels.values(), range);
/* 179:    */   }
/* 180:    */   
/* 181:    */   private List<? extends AgentModel> getAgentsInArcRange(AgentModel a, Collection<? extends AgentModel> agents, float range, float angle)
/* 182:    */   {
/* 183:186 */     List<AgentModel> agentsInArcRange = new ArrayList();
/* 184:    */     
/* 185:    */ 
/* 186:189 */     List<? extends AgentModel> agentsInEuclideanRange = getAgentsInEuclideanRange(a, agents, range);
/* 187:191 */     for (AgentModel b : agentsInEuclideanRange)
/* 188:    */     {
/* 189:192 */       float angFrom = (720.0F - (a.getAngle() + angle)) % 360.0F;
/* 190:    */       
/* 191:194 */       Arc2D arc = new Arc2D.Float(a.getX() - range, a.getY() - range, range * 2.0F, range * 2.0F, angFrom, angle * 2.0F, 2);
/* 192:195 */       if (arc.contains(b.getX(), b.getY())) {
/* 193:196 */         agentsInArcRange.add(b);
/* 194:    */       }
/* 195:    */     }
/* 196:200 */     return agentsInArcRange;
/* 197:    */   }
/* 198:    */   
/* 199:    */   private List<? extends GameModel> getObjectsInArcRange(GameModel a, Collection<? extends GameModel> agents, float range, float angle)
/* 200:    */   {
/* 201:206 */     List<GameModel> agentsInArcRange = new ArrayList();
/* 202:    */     
/* 203:    */ 
/* 204:209 */     List<? extends GameModel> agentsInEuclideanRange = getObjectsInEuclideanRange(a, agents, range);
/* 205:211 */     for (GameModel b : agentsInEuclideanRange)
/* 206:    */     {
/* 207:212 */       float angFrom = (720.0F - (a.getAngle() + angle)) % 360.0F;
/* 208:    */       
/* 209:214 */       Arc2D arc = new Arc2D.Float(a.getX() - range, a.getY() - range, range * 2.0F, range * 2.0F, angFrom, angle * 2.0F, 2);
/* 210:215 */       if (arc.contains(b.getX(), b.getY())) {
/* 211:216 */         agentsInArcRange.add(b);
/* 212:    */       }
/* 213:    */     }
/* 214:220 */     return agentsInArcRange;
/* 215:    */   }
/* 216:    */   
/* 217:    */   private List<? extends AgentModel> getAgentsInEuclideanRange(AgentModel a, Collection<? extends AgentModel> agents, float range)
/* 218:    */   {
/* 219:237 */     List<AgentModel> agentsInEuclideanRange = new ArrayList();
/* 220:    */     
/* 221:    */ 
/* 222:240 */     List<? extends AgentModel> agentsInRange = getAgentsBoundingInRange(a, agents, range);
/* 223:242 */     for (AgentModel b : agentsInRange) {
/* 224:243 */       if (getEuclideanDistance(a, b) < range) {
/* 225:244 */         agentsInEuclideanRange.add(b);
/* 226:    */       }
/* 227:    */     }
/* 228:248 */     return agentsInEuclideanRange;
/* 229:    */   }
/* 230:    */   
/* 231:    */   private List<? extends GameModel> getObjectsInEuclideanRange(GameModel a, Collection<? extends GameModel> agents, float range)
/* 232:    */   {
/* 233:252 */     List<GameModel> agentsInEuclideanRange = new ArrayList();
/* 234:    */     
/* 235:    */ 
/* 236:255 */     List<? extends GameModel> agentsInRange = getObjectsBoundingInRange(a, agents, range);
/* 237:257 */     for (GameModel b : agentsInRange) {
/* 238:258 */       if (getEuclideanDistance(a, b) < range) {
/* 239:259 */         agentsInEuclideanRange.add(b);
/* 240:    */       }
/* 241:    */     }
/* 242:263 */     return agentsInEuclideanRange;
/* 243:    */   }
/* 244:    */   
/* 245:    */   private List<? extends AgentModel> getAttackableAgentsInEuclideanRange(float x, float y, Collection<? extends AgentModel> agents, float range)
/* 246:    */   {
/* 247:267 */     List<AgentModel> agentsInEuclideanRange = new ArrayList();
/* 248:    */     
/* 249:    */ 
/* 250:270 */     List<? extends AgentModel> agentsInRange = getAgentsBoundingInRange(x, y, agents, range);
/* 251:273 */     for (AgentModel b : agentsInRange) {
/* 252:274 */       if ((getEuclideanDistance(x, y, b.getX(), b.getY()) < range) && 
/* 253:275 */         (!(b instanceof DoorModel)) && (!(b instanceof FireModel))) {
/* 254:281 */         agentsInEuclideanRange.add(b);
/* 255:    */       }
/* 256:    */     }
/* 257:286 */     return agentsInEuclideanRange;
/* 258:    */   }
/* 259:    */   
/* 260:    */   private List<? extends AgentModel> getAttackableAgentsInEuclideanRange(AgentModel a, Collection<? extends AgentModel> agents, float range)
/* 261:    */   {
/* 262:291 */     List<AgentModel> agentsInEuclideanRange = new ArrayList();
/* 263:    */     
/* 264:    */ 
/* 265:294 */     List<? extends AgentModel> agentsInRange = getAgentsBoundingInRange(a, agents, range);
/* 266:296 */     for (AgentModel b : agentsInRange) {
/* 267:297 */       if ((getEuclideanDistance(a, b) < range) && 
/* 268:298 */         (!(b instanceof DoorModel)) && (!(b instanceof FireModel))) {
/* 269:304 */         agentsInEuclideanRange.add(b);
/* 270:    */       }
/* 271:    */     }
/* 272:309 */     return agentsInEuclideanRange;
/* 273:    */   }
/* 274:    */   
/* 275:    */   private List<? extends AgentModel> getAgentsBoundingInRange(AgentModel a, Collection<? extends AgentModel> agents, float range)
/* 276:    */   {
/* 277:330 */     List<AgentModel> agentsInRange = new ArrayList();
/* 278:332 */     for (AgentModel b : agents) {
/* 279:333 */       if ((a != b) && (b.getX() < a.getX() + range) && (b.getX() > a.getX() - range) && (b.getY() < a.getY() + range) && (b.getY() > a.getY() - range)) {
/* 280:334 */         agentsInRange.add(b);
/* 281:    */       }
/* 282:    */     }
/* 283:338 */     return agentsInRange;
/* 284:    */   }
/* 285:    */   
/* 286:    */   private List<? extends GameModel> getObjectsBoundingInRange(GameModel a, Collection<? extends GameModel> agents, float range)
/* 287:    */   {
/* 288:342 */     List<GameModel> agentsInRange = new ArrayList();
/* 289:344 */     for (GameModel b : agents) {
/* 290:345 */       if ((a != b) && (b.getX() < a.getX() + range) && (b.getX() > a.getX() - range) && (b.getY() < a.getY() + range) && (b.getY() > a.getY() - range)) {
/* 291:346 */         agentsInRange.add(b);
/* 292:    */       }
/* 293:    */     }
/* 294:350 */     return agentsInRange;
/* 295:    */   }
/* 296:    */   
/* 297:    */   private List<? extends AgentModel> getAgentsBoundingInRange(float x, float y, Collection<? extends AgentModel> agents, float range)
/* 298:    */   {
/* 299:354 */     List<AgentModel> agentsInRange = new ArrayList();
/* 300:356 */     for (AgentModel b : agents) {
/* 301:357 */       if ((b.getX() < x + range) && (b.getX() > x - range) && (b.getY() < y + range) && (b.getY() > y - range)) {
/* 302:358 */         agentsInRange.add(b);
/* 303:    */       }
/* 304:    */     }
/* 305:362 */     return agentsInRange;
/* 306:    */   }
/* 307:    */   
/* 308:    */   private List<? extends GameModel> getObjectsBoundingInRange(float x, float y, Collection<? extends GameModel> agents, float range)
/* 309:    */   {
/* 310:367 */     List<GameModel> agentsInRange = new ArrayList();
/* 311:369 */     for (GameModel b : agents) {
/* 312:370 */       if ((b.getX() < x + range) && (b.getX() > x - range) && (b.getY() < y + range) && (b.getY() > y - range)) {
/* 313:371 */         agentsInRange.add(b);
/* 314:    */       }
/* 315:    */     }
/* 316:375 */     return agentsInRange;
/* 317:    */   }
/* 318:    */   
/* 319:    */   public static float getEuclideanDistance(AgentModel a, AgentModel b)
/* 320:    */   {
/* 321:389 */     return getEuclideanDistance(a.getX(), a.getY(), b.getX(), b.getY());
/* 322:    */   }
/* 323:    */   
/* 324:    */   public static float getEuclideanDistance(GameModel a, GameModel b)
/* 325:    */   {
/* 326:392 */     return getEuclideanDistance(a.getX(), a.getY(), b.getX(), b.getY());
/* 327:    */   }
/* 328:    */   
/* 329:    */   public static float getEuclideanDistance(float x1, float y1, float x2, float y2)
/* 330:    */   {
/* 331:410 */     float dx = x1 - x2;
/* 332:411 */     float dy = y1 - y2;
/* 333:    */     
/* 334:413 */     return (float)Math.sqrt(dx * dx + dy * dy);
/* 335:    */   }
/* 336:    */   
/* 337:    */   public static double getAgentRelativePosition(AgentModel a, AgentModel b)
/* 338:    */   {
/* 339:427 */     return getRelativePosition(a.getX(), a.getY(), b.getX(), b.getY());
/* 340:    */   }
/* 341:    */   
/* 342:    */   public static double getRelativePosition(float x1, float y1, float x2, float y2)
/* 343:    */   {
/* 344:446 */     float x = x2 - x1;
/* 345:447 */     float y = y1 - y2;
/* 346:    */     
/* 347:    */ 
/* 348:450 */     double t = Math.atan2(x, y);
/* 349:    */     
/* 350:    */ 
/* 351:453 */     t = t < 0.0D ? t + 6.283185307179586D : t;
/* 352:    */     
/* 353:    */ 
/* 354:456 */     return Math.toDegrees(t);
/* 355:    */   }
/* 356:    */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.world.WorldModel
 * JD-Core Version:    0.7.0.1
 */