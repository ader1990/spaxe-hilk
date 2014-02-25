/*   1:    */ package sh.world.shworld;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Map;
/*   6:    */ import org.newdawn.slick.SlickException;
/*   7:    */ import org.newdawn.slick.tiled.TiledMap;
/*   8:    */ import sh.Selector.Target;
/*   9:    */ import sh.SpaceHulkGameContainer;
/*  10:    */ import sh.agent.AgentModel;
/*  11:    */ import sh.agent.aliens.AlienModel;
/*  12:    */ import sh.agent.aliens.BlipModel;
/*  13:    */ import sh.agent.door.DoorModel;
/*  14:    */ import sh.agent.marines.MarineModel;
/*  15:    */ import sh.agent.movement.PathAgentModel;
/*  16:    */ import sh.gameobject.startposition.StartPositionModel;
/*  17:    */ import sh.world.TileBasedWorldModel;
/*  18:    */ 
/*  19:    */ public class SpaceHulkWorldModel
/*  20:    */   extends TileBasedWorldModel
/*  21:    */ {
/*  22:    */   private int[][] highlight;
/*  23:    */   private boolean[][] walkable;
/*  24:    */   private boolean[][] blocked;
/*  25:    */   private boolean[][] finish;
/*  26:    */   private boolean[][] door;
/*  27:    */   private boolean[][] grass;
/*  28:    */   private boolean[][] rock;
/*  29:    */   private boolean[][] fire;
/*  30:    */   private boolean[][] sand;
/*  31:    */   private boolean[][] reach;
/*  32:    */   private SpaceHulkGameContainer container;
/*  33:    */   private int CR;
/*  34:    */   private int reachablePlaces;
/*  35:    */   private int burnPlaces;
/*  36:    */   private boolean mission;
/*  37:    */   
/*  38:    */   public int getCR()
/*  39:    */   {
/*  40: 39 */     return this.CR;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void setCR(int cR)
/*  44:    */   {
/*  45: 44 */     this.CR = cR;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public SpaceHulkWorldModel(TiledMap map, int players)
/*  49:    */   {
/*  50: 49 */     super(map);
/*  51: 50 */     this.mission = false;
/*  52: 51 */     this.reachablePlaces = 0;
/*  53: 52 */     this.highlight = new int[getWidthInTiles()][getHeightInTiles()];
/*  54: 53 */     this.walkable = new boolean[getWidthInTiles()][getHeightInTiles()];
/*  55: 54 */     this.blocked = new boolean[getWidthInTiles()][getHeightInTiles()];
/*  56: 55 */     this.finish = new boolean[getWidthInTiles()][getHeightInTiles()];
/*  57: 56 */     this.door = new boolean[getWidthInTiles()][getHeightInTiles()];
/*  58: 57 */     this.rock = new boolean[getWidthInTiles()][getHeightInTiles()];
/*  59: 58 */     this.fire = new boolean[getWidthInTiles()][getHeightInTiles()];
/*  60: 59 */     this.grass = new boolean[getWidthInTiles()][getHeightInTiles()];
/*  61: 60 */     this.sand = new boolean[getWidthInTiles()][getHeightInTiles()];
/*  62:    */     
/*  63: 62 */     this.reach = new boolean[getWidthInTiles()][getHeightInTiles()];
/*  64: 64 */     for (int x = 0; x < getWidthInTiles(); x++) {
/*  65: 66 */       for (int y = 0; y < getHeightInTiles(); y++) {
/*  66: 69 */         this.walkable[x][y] = checkTileProperty(x, y, "walkable", "true");
/*  67:    */       }
/*  68:    */     }
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setObjectives(TiledMap map)
/*  72:    */   {
/*  73: 80 */     this.mission = true;
/*  74: 81 */     for (int x = 0; x < getWidthInTiles(); x++) {
/*  75: 83 */       for (int y = 0; y < getHeightInTiles(); y++)
/*  76:    */       {
/*  77: 85 */         this.reach[x][y] = checkTileProperty(x, y, "reach", "true");
/*  78: 86 */         if (this.reach[x][y] != 0) {
/*  79: 88 */           this.reachablePlaces += 1;
/*  80:    */         }
/*  81: 90 */         this.fire[x][y] = checkTileProperty(x, y, "fire", "true");
/*  82: 91 */         if (this.fire[x][y] != 0) {
/*  83: 93 */           this.burnPlaces += 1;
/*  84:    */         }
/*  85:    */       }
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   public ArrayList<MarineModel> getOverWatchList(int team)
/*  90:    */   {
/*  91:100 */     ArrayList<MarineModel> list = new ArrayList(
/*  92:101 */       getAgentModels(MarineModel.class));
/*  93:102 */     ArrayList<MarineModel> returnlist = new ArrayList();
/*  94:103 */     for (MarineModel model : list) {
/*  95:105 */       if ((model.isOverwatch()) && (model.getTeam() != team)) {
/*  96:107 */         returnlist.add(model);
/*  97:    */       }
/*  98:    */     }
/*  99:111 */     return returnlist;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void update(int delta)
/* 103:    */     throws SlickException
/* 104:    */   {
/* 105:117 */     this.highlight = new int[getWidthInTiles()][getHeightInTiles()];
/* 106:    */     
/* 107:119 */     super.update(delta);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public boolean tileInBounds(float xTile, float yTile)
/* 111:    */   {
/* 112:124 */     return (xTile >= 0.0F) && (xTile < getWidthInTiles()) && (yTile >= 0.0F) && (
/* 113:125 */       yTile < getHeightInTiles());
/* 114:    */   }
/* 115:    */   
/* 116:    */   public boolean tileBlocked(float xTile, float yTile)
/* 117:    */   {
/* 118:131 */     return (!tileInBounds(xTile, yTile)) || (this.blocked[((int)xTile)][((int)yTile)] != 0);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public boolean tileReachObjectives(float xTile, float yTile)
/* 122:    */   {
/* 123:136 */     return (!tileInBounds(xTile, yTile)) || 
/* 124:137 */       (this.reach[((int)xTile)][((int)yTile)] != 0);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public boolean tileFireObjectives(float xTile, float yTile)
/* 128:    */   {
/* 129:142 */     return (!tileInBounds(xTile, yTile)) || 
/* 130:143 */       (this.fire[((int)xTile)][((int)yTile)] != 0);
/* 131:    */   }
/* 132:    */   
/* 133:    */   public boolean checkReached(float xTile, float yTile)
/* 134:    */   {
/* 135:149 */     if ((this.mission) && (tileReachObjectives(xTile, yTile)))
/* 136:    */     {
/* 137:152 */       this.reach[((int)xTile)][((int)yTile)] = 0;
/* 138:153 */       this.reachablePlaces -= 1;
/* 139:154 */       if (this.reachablePlaces == 0) {
/* 140:156 */         return true;
/* 141:    */       }
/* 142:    */     }
/* 143:160 */     return false;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public boolean checkFire(float xTile, float yTile)
/* 147:    */   {
/* 148:167 */     if ((this.mission) && (tileFireObjectives(xTile, yTile)))
/* 149:    */     {
/* 150:169 */       this.fire[((int)xTile)][((int)yTile)] = 1;
/* 151:170 */       this.burnPlaces -= 1;
/* 152:171 */       if (this.burnPlaces == 0) {
/* 153:173 */         return true;
/* 154:    */       }
/* 155:    */     }
/* 156:177 */     return false;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public boolean tileWalkable(float xTile, float yTile)
/* 160:    */   {
/* 161:183 */     return (!tileInBounds(xTile, yTile)) || 
/* 162:184 */       (this.walkable[((int)xTile)][((int)yTile)] != 0);
/* 163:    */   }
/* 164:    */   
/* 165:    */   public boolean tileFinish(float xTile, float yTile)
/* 166:    */   {
/* 167:189 */     return (tileInBounds(xTile, yTile)) && (this.finish[((int)xTile)][((int)yTile)] != 0);
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setFire(float xTile, float yTile)
/* 171:    */   {
/* 172:194 */     if (tileWalkable(xTile, yTile)) {
/* 173:196 */       this.fire[((int)xTile)][((int)yTile)] = 1;
/* 174:    */     }
/* 175:    */   }
/* 176:    */   
/* 177:    */   public boolean tileAlienPlacable(float xTile, float yTile, BlipModel model)
/* 178:    */   {
/* 179:202 */     if (tileWalkable(xTile, yTile))
/* 180:    */     {
/* 181:204 */       float blipx = model.getX() - 0.5F;
/* 182:205 */       float blipy = model.getY() - 0.5F;
/* 183:206 */       System.out.println("Blip: " + blipx + " " + blipy + " tile " + 
/* 184:207 */         xTile + " " + yTile);
/* 185:208 */       if ((xTile >= blipx - 1.0F) && (xTile <= blipx + 1.0F) && (yTile >= blipy - 1.0F) && 
/* 186:209 */         (yTile <= blipy + 1.0F))
/* 187:    */       {
/* 188:211 */         System.out.println("ALien placable");
/* 189:212 */         return true;
/* 190:    */       }
/* 191:    */     }
/* 192:215 */     return false;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public boolean tilePlayerPlacable(float xTile, float yTile, int player)
/* 196:    */   {
/* 197:220 */     return (tileInBounds(xTile, yTile)) && 
/* 198:221 */       (getStartPositionOnTile(player, xTile, yTile));
/* 199:    */   }
/* 200:    */   
/* 201:    */   public boolean tileFence(float xTile, float yTile)
/* 202:    */   {
/* 203:226 */     return (tileInBounds(xTile, yTile)) && (this.door[((int)xTile)][((int)yTile)] != 0);
/* 204:    */   }
/* 205:    */   
/* 206:    */   public boolean tileRock(float xTile, float yTile)
/* 207:    */   {
/* 208:231 */     return (tileInBounds(xTile, yTile)) && (this.rock[((int)xTile)][((int)yTile)] != 0);
/* 209:    */   }
/* 210:    */   
/* 211:    */   public boolean tileGrass(float xTile, float yTile)
/* 212:    */   {
/* 213:236 */     return (tileInBounds(xTile, yTile)) && (this.grass[((int)xTile)][((int)yTile)] != 0);
/* 214:    */   }
/* 215:    */   
/* 216:    */   public boolean tileSand(float xTile, float yTile)
/* 217:    */   {
/* 218:241 */     return (tileInBounds(xTile, yTile)) && (this.sand[((int)xTile)][((int)yTile)] != 0);
/* 219:    */   }
/* 220:    */   
/* 221:    */   public boolean tileHighlighted(float xTile, float yTile)
/* 222:    */   {
/* 223:246 */     return (tileInBounds(xTile, yTile)) && 
/* 224:247 */       (this.highlight[((int)xTile)][((int)yTile)] != 0);
/* 225:    */   }
/* 226:    */   
/* 227:    */   public int tileHighlight(float xTile, float yTile)
/* 228:    */   {
/* 229:252 */     if (tileInBounds(xTile, yTile)) {
/* 230:254 */       return this.highlight[((int)xTile)][((int)yTile)];
/* 231:    */     }
/* 232:257 */     return 0;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void highlightTile(float xTile, float yTile, int color)
/* 236:    */   {
/* 237:262 */     if (tileInBounds(xTile, yTile)) {
/* 238:264 */       this.highlight[((int)xTile)][((int)yTile)] = color;
/* 239:    */     }
/* 240:    */   }
/* 241:    */   
/* 242:    */   public boolean tileReachable(float tx, float ty, AgentModel selectedAgent)
/* 243:    */   {
/* 244:271 */     float x = selectedAgent.getX() - 0.5F;
/* 245:272 */     float y = selectedAgent.getY() - 0.5F;
/* 246:274 */     switch ((int)selectedAgent.getAngle())
/* 247:    */     {
/* 248:    */     case 0: 
/* 249:277 */       if ((y - 1.0F == ty) && (tx >= x - 1.0F) && (tx <= x + 1.0F)) {
/* 250:279 */         return true;
/* 251:    */       }
/* 252:    */       break;
/* 253:    */     case 90: 
/* 254:283 */       if ((x + 1.0F == tx) && (ty >= y - 1.0F) && (ty <= y + 1.0F)) {
/* 255:285 */         return true;
/* 256:    */       }
/* 257:    */       break;
/* 258:    */     case 180: 
/* 259:289 */       if ((y + 1.0F == ty) && (tx >= x - 1.0F) && (tx <= x + 1.0F)) {
/* 260:291 */         return true;
/* 261:    */       }
/* 262:    */       break;
/* 263:    */     case 270: 
/* 264:295 */       if ((x - 1.0F == tx) && (ty >= y - 1.0F) && (ty <= y + 1.0F)) {
/* 265:298 */         return true;
/* 266:    */       }
/* 267:    */       break;
/* 268:    */     }
/* 269:303 */     return false;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void GiveBackAp(String faction)
/* 273:    */   {
/* 274:308 */     for (AgentModel model : this.agentModels.values()) {
/* 275:310 */       if (model.getFaction().equals(faction)) {
/* 276:312 */         model.setAp(model.getMaxAP());
/* 277:    */       }
/* 278:    */     }
/* 279:    */   }
/* 280:    */   
/* 281:    */   public void endTurn(int player)
/* 282:    */   {
/* 283:320 */     for (AgentModel model : this.agentModels.values()) {
/* 284:322 */       if (model.getPlayer() == player)
/* 285:    */       {
/* 286:324 */         model.clearMovementStack();
/* 287:325 */         model.setAp(0);
/* 288:    */       }
/* 289:    */     }
/* 290:    */   }
/* 291:    */   
/* 292:    */   public boolean lineOfSight(PathAgentModel viewer, PathAgentModel target)
/* 293:    */   {
/* 294:    */     float ty;
/* 295:    */     float tx;
/* 296:    */     float ty;
/* 297:336 */     if (target.hasTarget())
/* 298:    */     {
/* 299:338 */       float tx = target.getTargetX();
/* 300:339 */       ty = target.getTargetY();
/* 301:    */     }
/* 302:    */     else
/* 303:    */     {
/* 304:343 */       tx = target.getX();
/* 305:344 */       ty = target.getY();
/* 306:    */     }
/* 307:348 */     if (viewer.fov(tx, ty)) {
/* 308:350 */       if (line(viewer.getX(), viewer.getY(), tx, ty)) {
/* 309:352 */         return true;
/* 310:    */       }
/* 311:    */     }
/* 312:355 */     return false;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public boolean lineOfSight(AgentModel viewer, float x, float y)
/* 316:    */   {
/* 317:361 */     if ((viewer.fov(x, y)) && (line(viewer.getX(), viewer.getY(), x, y))) {
/* 318:363 */       return true;
/* 319:    */     }
/* 320:366 */     return false;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public ArrayList<BlipModel> lineOfSightMarineBlips(MarineModel marine)
/* 324:    */   {
/* 325:373 */     ArrayList<BlipModel> models = new ArrayList();
/* 326:374 */     for (AgentModel model : this.agentModels.values()) {
/* 327:376 */       if ((model instanceof BlipModel))
/* 328:    */       {
/* 329:378 */         System.out.println("blip");
/* 330:379 */         if (marine.fov(model.getX(), model.getY())) {
/* 331:381 */           if (line(marine.getX(), marine.getY(), model.getX(), model.getY())) {
/* 332:383 */             models.add((BlipModel)model);
/* 333:    */           }
/* 334:    */         }
/* 335:    */       }
/* 336:    */     }
/* 337:388 */     return models;
/* 338:    */   }
/* 339:    */   
/* 340:    */   public boolean lineOfSightEnemies(float x, float y, AgentModel agent)
/* 341:    */   {
/* 342:394 */     for (AgentModel model : this.agentModels.values()) {
/* 343:396 */       if ((((model instanceof MarineModel)) || ((model instanceof AlienModel))) && 
/* 344:397 */         (agent.getTeam() != model.getTeam())) {
/* 345:399 */         if ((model.fov(x, y)) && 
/* 346:400 */           (line(model.getX() - 0.5F, model.getY() - 0.5F, x, y))) {
/* 347:402 */           return true;
/* 348:    */         }
/* 349:    */       }
/* 350:    */     }
/* 351:407 */     return false;
/* 352:    */   }
/* 353:    */   
/* 354:    */   public boolean line(float x0, float y0, float x1, float y1)
/* 355:    */   {
/* 356:412 */     float dx = Math.abs(x1 - x0);
/* 357:413 */     float dy = Math.abs(y1 - y0);
/* 358:    */     int sx;
/* 359:    */     int sx;
/* 360:416 */     if (x0 < x1) {
/* 361:418 */       sx = 1;
/* 362:    */     } else {
/* 363:421 */       sx = -1;
/* 364:    */     }
/* 365:    */     int sy;
/* 366:    */     int sy;
/* 367:423 */     if (y0 < y1) {
/* 368:425 */       sy = 1;
/* 369:    */     } else {
/* 370:429 */       sy = -1;
/* 371:    */     }
/* 372:431 */     float err = dx - dy;
/* 373:432 */     boolean line = true;
/* 374:433 */     while (line)
/* 375:    */     {
/* 376:435 */       float e2 = 2.0F * err;
/* 377:437 */       if (e2 > -dy)
/* 378:    */       {
/* 379:439 */         err -= dy;
/* 380:440 */         x0 += sx;
/* 381:    */       }
/* 382:442 */       if (e2 < dx)
/* 383:    */       {
/* 384:444 */         err += dx;
/* 385:445 */         y0 += sy;
/* 386:    */       }
/* 387:448 */       line = tileWalkable(x0, y0);
/* 388:451 */       if ((x0 == x1) && (y0 == y1)) {
/* 389:    */         break;
/* 390:    */       }
/* 391:457 */       if (getAgentOnTile(x0, y0) != null) {
/* 392:459 */         line = false;
/* 393:    */       }
/* 394:    */     }
/* 395:465 */     return line;
/* 396:    */   }
/* 397:    */   
/* 398:    */   public boolean hasStartPositions(int player)
/* 399:    */   {
/* 400:470 */     ArrayList<StartPositionModel> models = getStartPositions(player);
/* 401:472 */     for (StartPositionModel model : models) {
/* 402:474 */       if ((model.isOpen()) && (getAgentOnTile(model.getX(), model.getY()) == null)) {
/* 403:476 */         return true;
/* 404:    */       }
/* 405:    */     }
/* 406:479 */     return false;
/* 407:    */   }
/* 408:    */   
/* 409:    */   public boolean standsOnOpenStartPosition(float x, float y)
/* 410:    */   {
/* 411:485 */     ArrayList<StartPositionModel> models = getOpenAlienStartPositions();
/* 412:487 */     for (StartPositionModel model : models) {
/* 413:489 */       if ((model.getX() == x) && (model.getY() == y)) {
/* 414:491 */         return true;
/* 415:    */       }
/* 416:    */     }
/* 417:494 */     return false;
/* 418:    */   }
/* 419:    */   
/* 420:    */   public StartPositionModel getOpenStartPosition(float x, float y)
/* 421:    */   {
/* 422:499 */     ArrayList<StartPositionModel> models = getOpenAlienStartPositions();
/* 423:501 */     for (StartPositionModel model : models) {
/* 424:503 */       if ((model.getX() == x) && (model.getY() == y))
/* 425:    */       {
/* 426:505 */         model.setOpen(false);
/* 427:506 */         return model;
/* 428:    */       }
/* 429:    */     }
/* 430:509 */     return null;
/* 431:    */   }
/* 432:    */   
/* 433:    */   public ArrayList<Target> getAlienPlaces(BlipModel model)
/* 434:    */   {
/* 435:515 */     int angle = (int)model.getAngle();
/* 436:516 */     float x = model.getX() - 0.5F;
/* 437:517 */     float y = model.getY() - 0.5F;
/* 438:    */     
/* 439:519 */     ArrayList<Target> targets = new ArrayList();
/* 440:521 */     if (checkAlienPlacementTile(x, y)) {
/* 441:523 */       targets.add(new Target(x, y));
/* 442:    */     }
/* 443:525 */     if (checkAlienPlacementTile(x, y + 1.0F)) {
/* 444:527 */       targets.add(new Target(x, y + 1.0F));
/* 445:    */     }
/* 446:529 */     if (checkAlienPlacementTile(x, y - 1.0F)) {
/* 447:531 */       targets.add(new Target(x, y - 1.0F));
/* 448:    */     }
/* 449:533 */     if (checkAlienPlacementTile(x + 1.0F, y)) {
/* 450:535 */       targets.add(new Target(x + 1.0F, y));
/* 451:    */     }
/* 452:537 */     if (checkAlienPlacementTile(x - 1.0F, y)) {
/* 453:539 */       targets.add(new Target(x - 1.0F, y));
/* 454:    */     }
/* 455:541 */     if (checkAlienPlacementTile(x + 1.0F, y + 1.0F)) {
/* 456:543 */       targets.add(new Target(x + 1.0F, y + 1.0F));
/* 457:    */     }
/* 458:545 */     if (checkAlienPlacementTile(x - 1.0F, y + 1.0F)) {
/* 459:547 */       targets.add(new Target(x - 1.0F, y + 1.0F));
/* 460:    */     }
/* 461:549 */     if (checkAlienPlacementTile(x + 1.0F, y - 1.0F)) {
/* 462:551 */       targets.add(new Target(x + 1.0F, y - 1.0F));
/* 463:    */     }
/* 464:553 */     if (checkAlienPlacementTile(x - 1.0F, y - 1.0F)) {
/* 465:555 */       targets.add(new Target(x - 1.0F, y - 1.0F));
/* 466:    */     }
/* 467:558 */     return targets;
/* 468:    */   }
/* 469:    */   
/* 470:    */   public boolean checkAlienPlacementTile(float x, float y)
/* 471:    */   {
/* 472:563 */     if (!tileWalkable(x, y)) {
/* 473:565 */       return false;
/* 474:    */     }
/* 475:568 */     AgentModel a = getAgentOnTile(x, y);
/* 476:569 */     if ((a != null) || (((a instanceof DoorModel)) && (!((DoorModel)a).isOpen()))) {
/* 477:571 */       return false;
/* 478:    */     }
/* 479:574 */     return true;
/* 480:    */   }
/* 481:    */   
/* 482:    */   public boolean checkOpen(float x, float y)
/* 483:    */   {
/* 484:580 */     if (!tileWalkable(x, y)) {
/* 485:582 */       return false;
/* 486:    */     }
/* 487:585 */     AgentModel a = getAgentOnTile(x, y);
/* 488:586 */     if ((a != null) || (((a instanceof DoorModel)) && (!((DoorModel)a).isOpen()))) {
/* 489:588 */       return false;
/* 490:    */     }
/* 491:591 */     return true;
/* 492:    */   }
/* 493:    */   
/* 494:    */   public void setFire(AgentModel model, int area)
/* 495:    */   {
/* 496:599 */     float x = model.getX();
/* 497:600 */     float y = model.getY();
/* 498:601 */     float startX = x - (int)Math.floor(area / 2);
/* 499:602 */     float startY = y - (int)Math.floor(area / 2);
/* 500:604 */     for (int px = (int)startX; px < startX + area; px++) {
/* 501:606 */       for (int py = (int)startY; py < startY + area; py++)
/* 502:    */       {
/* 503:608 */         System.out.println("Fire to " + px + ":" + py);
/* 504:609 */         setFire(px, py);
/* 505:    */       }
/* 506:    */     }
/* 507:    */   }
/* 508:    */   
/* 509:    */   public boolean tileOnFire(float xTile, float yTile)
/* 510:    */   {
/* 511:616 */     return (tileInBounds(xTile, yTile)) && (this.fire[((int)xTile)][((int)yTile)] != 0);
/* 512:    */   }
/* 513:    */   
/* 514:    */   public float[] GetStartPosition(int playerId)
/* 515:    */   {
/* 516:621 */     for (int x = 0; x < getWidthInTiles(); x++) {
/* 517:623 */       for (int y = 0; y < getHeightInTiles(); y++) {
/* 518:625 */         if (tilePlayerPlacable(x, y, playerId)) {
/* 519:627 */           return new float[] { x, y };
/* 520:    */         }
/* 521:    */       }
/* 522:    */     }
/* 523:631 */     return new float[] { 0.0F, 0.0F };
/* 524:    */   }
/* 525:    */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.world.shworld.SpaceHulkWorldModel
 * JD-Core Version:    0.7.0.1
 */