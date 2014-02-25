/*   1:    */ package sh.agent;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.Observable;
/*   7:    */ import java.util.Stack;
/*   8:    */ import org.newdawn.slick.geom.Vector2f;
/*   9:    */ import sh.agent.aliens.BlipModel;
/*  10:    */ import sh.agent.weapons.Weapon;
/*  11:    */ import sh.multiplayer.AgentMovement;
/*  12:    */ import sh.multiplayer.AgentPlacement;
/*  13:    */ import sh.world.shworld.SpaceHulkWorldModel;
/*  14:    */ 
/*  15:    */ public class AgentModel
/*  16:    */   extends Observable
/*  17:    */ {
/*  18:    */   protected int UUID;
/*  19:    */   protected int owner;
/*  20: 24 */   protected boolean moving = false;
/*  21:    */   protected boolean animating;
/*  22:    */   protected String name;
/*  23:    */   protected boolean selected;
/*  24:    */   
/*  25:    */   public boolean isMoving()
/*  26:    */   {
/*  27: 28 */     return this.moving;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void setMoving(boolean moving)
/*  31:    */   {
/*  32: 33 */     this.moving = moving;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public boolean isAnimating()
/*  36:    */   {
/*  37: 39 */     return this.animating;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void setAnimating(boolean animating)
/*  41:    */   {
/*  42: 44 */     this.animating = animating;
/*  43:    */   }
/*  44:    */   
/*  45: 52 */   protected Vector2f position = new Vector2f(0.0F, 0.0F);
/*  46: 55 */   protected Vector2f xForce = new Vector2f(1.0F, 0.0F);
/*  47: 58 */   protected Vector2f yForce = new Vector2f(1.0F, 0.0F);
/*  48:    */   private float weaponX;
/*  49:    */   private float weaponY;
/*  50:    */   protected float angle;
/*  51:    */   public SpaceHulkWorldModel worldModel;
/*  52:    */   protected String faction;
/*  53:    */   protected int player;
/*  54:    */   protected int marineType;
/*  55:    */   protected int team;
/*  56:    */   protected int Ap;
/*  57:    */   protected int colour;
/*  58:    */   protected int maxAP;
/*  59:    */   protected boolean rotatable;
/*  60:    */   
/*  61:    */   public float getWeaponX()
/*  62:    */   {
/*  63: 63 */     return this.weaponX;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setWeaponX(float weaponX)
/*  67:    */   {
/*  68: 68 */     this.weaponX = weaponX;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public float getWeaponY()
/*  72:    */   {
/*  73: 73 */     return this.weaponY;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setWeaponY(float weaponY)
/*  77:    */   {
/*  78: 78 */     this.weaponY = weaponY;
/*  79:    */   }
/*  80:    */   
/*  81: 91 */   protected float angleDelta = 0.0F;
/*  82:    */   protected float attackRange;
/*  83: 93 */   protected HashMap<Integer, Integer> attacks = new HashMap();
/*  84: 94 */   private ArrayList<Weapon> weapons = new ArrayList();
/*  85: 95 */   private int selectedWeapon = 0;
/*  86:    */   private String floatingText;
/*  87:    */   
/*  88:    */   public float getAngle()
/*  89:    */   {
/*  90: 99 */     return (float)this.xForce.getTheta();
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String getFloatingText()
/*  94:    */   {
/*  95:104 */     return this.floatingText;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setFloatingText(String floatingText) {}
/*  99:    */   
/* 100:    */   public int getAttacks(int uuid)
/* 101:    */   {
/* 102:113 */     if (this.attacks.containsKey(Integer.valueOf(uuid))) {
/* 103:114 */       return ((Integer)this.attacks.get(Integer.valueOf(uuid))).intValue();
/* 104:    */     }
/* 105:116 */     return 0;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public int getColour()
/* 109:    */   {
/* 110:120 */     return this.colour;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setColour(int colour)
/* 114:    */   {
/* 115:125 */     this.colour = colour;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setAttacks(int uuid, int number)
/* 119:    */   {
/* 120:128 */     this.attacks.put(Integer.valueOf(uuid), Integer.valueOf(number));
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void clearAttacks()
/* 124:    */   {
/* 125:132 */     this.attacks = new HashMap();
/* 126:    */   }
/* 127:    */   
/* 128:135 */   protected Stack<AgentMovement> movementStack = new Stack();
/* 129:    */   
/* 130:    */   public AgentModel(int UUID, String name, float x, float y, float angle, SpaceHulkWorldModel worldModel, String faction, int ap, boolean rotatable, float attackRange, int player, int team, int marineType, int colour)
/* 131:    */   {
/* 132:140 */     this.UUID = UUID;
/* 133:141 */     this.name = name;
/* 134:142 */     this.angle = angle;
/* 135:143 */     this.position.set(x, y);
/* 136:144 */     this.worldModel = worldModel;
/* 137:145 */     this.faction = faction;
/* 138:146 */     this.Ap = ap;
/* 139:147 */     this.maxAP = ap;
/* 140:148 */     this.rotatable = rotatable;
/* 141:149 */     this.attackRange = attackRange;
/* 142:150 */     this.player = player;
/* 143:151 */     this.team = team;
/* 144:152 */     this.marineType = marineType;
/* 145:153 */     this.colour = colour;
/* 146:154 */     this.xForce.setTheta(angle);
/* 147:    */   }
/* 148:    */   
/* 149:    */   public int getPlayer()
/* 150:    */   {
/* 151:158 */     return this.player;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setPlayer(int player)
/* 155:    */   {
/* 156:162 */     this.player = player;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public int getTeam()
/* 160:    */   {
/* 161:166 */     return this.team;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void setTeam(int team)
/* 165:    */   {
/* 166:170 */     this.team = team;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public boolean isRotatable()
/* 170:    */   {
/* 171:174 */     return this.rotatable;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setRotatable(boolean rotatable)
/* 175:    */   {
/* 176:178 */     this.rotatable = rotatable;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public int getUUID()
/* 180:    */   {
/* 181:182 */     return this.UUID;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public int getAp()
/* 185:    */   {
/* 186:186 */     return this.Ap;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void withdrawAp(int min)
/* 190:    */   {
/* 191:190 */     this.Ap -= min;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void setAp(int ap)
/* 195:    */   {
/* 196:194 */     this.Ap = ap;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public int getMaxAP()
/* 200:    */   {
/* 201:198 */     return this.maxAP;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void setMaxAP(int maxAP)
/* 205:    */   {
/* 206:202 */     this.maxAP = maxAP;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public String getName()
/* 210:    */   {
/* 211:206 */     return this.name;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public Vector2f getPosition()
/* 215:    */   {
/* 216:210 */     return this.position;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public Vector2f getXForce()
/* 220:    */   {
/* 221:214 */     return this.xForce;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public Vector2f getYForce()
/* 225:    */   {
/* 226:218 */     return this.yForce;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public boolean isSelected()
/* 230:    */   {
/* 231:222 */     return this.selected;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public float getX()
/* 235:    */   {
/* 236:226 */     return this.position.x;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public float getY()
/* 240:    */   {
/* 241:230 */     return this.position.y;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public String getFaction()
/* 245:    */   {
/* 246:234 */     return this.faction;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public void setPosition(Vector2f newPosition)
/* 250:    */   {
/* 251:238 */     this.position.set(newPosition);
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void setAngle(float angle)
/* 255:    */   {
/* 256:242 */     this.angle = angle;
/* 257:243 */     this.xForce.setTheta(angle);
/* 258:    */   }
/* 259:    */   
/* 260:    */   public int alterAngle(float angle)
/* 261:    */   {
/* 262:247 */     this.angleDelta += 90.0F;
/* 263:248 */     this.angle = ((this.angle + angle) % 360.0F);
/* 264:249 */     if (this.angle < 0.0F) {
/* 265:252 */       this.angle = (360.0F + this.angle);
/* 266:    */     }
/* 267:255 */     this.xForce.setTheta(this.angle);
/* 268:    */     
/* 269:257 */     return 1;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public float getRealAngle()
/* 273:    */   {
/* 274:261 */     return this.angle;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public void think() {}
/* 278:    */   
/* 279:    */   public void act() {}
/* 280:    */   
/* 281:    */   public void setSelected(boolean selected)
/* 282:    */   {
/* 283:273 */     this.selected = selected;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public float transformX(float x)
/* 287:    */   {
/* 288:277 */     return x * this.worldModel.getTileWidth();
/* 289:    */   }
/* 290:    */   
/* 291:    */   public float transformY(float y)
/* 292:    */   {
/* 293:281 */     return y * this.worldModel.getTileHeight();
/* 294:    */   }
/* 295:    */   
/* 296:    */   public float deTransformX(float x)
/* 297:    */   {
/* 298:285 */     return x / this.worldModel.getTileWidth();
/* 299:    */   }
/* 300:    */   
/* 301:    */   public float deTransformY(float y)
/* 302:    */   {
/* 303:289 */     return y / this.worldModel.getTileHeight();
/* 304:    */   }
/* 305:    */   
/* 306:    */   public Vector2f localizePosition(Vector2f location)
/* 307:    */   {
/* 308:293 */     return location;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public int tileReachable(float tx, float ty)
/* 312:    */   {
/* 313:297 */     return 0;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public AgentPlacement getPlacement()
/* 317:    */   {
/* 318:302 */     AgentPlacement agentPlacement = new AgentPlacement();
/* 319:303 */     agentPlacement.x = (this.position.x - 0.5F);
/* 320:304 */     agentPlacement.y = (this.position.y - 0.5F);
/* 321:305 */     agentPlacement.angle = this.angle;
/* 322:306 */     agentPlacement.marineType = 0;
/* 323:307 */     agentPlacement.UUID = this.UUID;
/* 324:308 */     agentPlacement.faction = this.faction;
/* 325:309 */     agentPlacement.playerId = this.player;
/* 326:310 */     agentPlacement.team = this.team;
/* 327:311 */     agentPlacement.marineType = this.marineType;
/* 328:312 */     agentPlacement.colour = this.colour;
/* 329:313 */     if ((this instanceof BlipModel)) {
/* 330:314 */       agentPlacement.blip = true;
/* 331:    */     } else {
/* 332:316 */       agentPlacement.blip = false;
/* 333:    */     }
/* 334:318 */     return agentPlacement;
/* 335:    */   }
/* 336:    */   
/* 337:    */   public AgentMovement getMovement(int cost)
/* 338:    */   {
/* 339:322 */     AgentMovement agentPlacement = new AgentMovement();
/* 340:323 */     agentPlacement.x = this.position.x;
/* 341:324 */     agentPlacement.y = this.position.y;
/* 342:325 */     agentPlacement.angle = this.angle;
/* 343:326 */     agentPlacement.UUID = this.UUID;
/* 344:327 */     agentPlacement.faction = this.faction;
/* 345:328 */     agentPlacement.cost = cost;
/* 346:329 */     agentPlacement.weapon = this.selectedWeapon;
/* 347:330 */     this.movementStack.add(agentPlacement);
/* 348:331 */     return agentPlacement;
/* 349:    */   }
/* 350:    */   
/* 351:    */   public void clearMovementStack()
/* 352:    */   {
/* 353:335 */     this.movementStack.clear();
/* 354:    */   }
/* 355:    */   
/* 356:    */   public int rotatingCost(float turned)
/* 357:    */   {
/* 358:340 */     return 0;
/* 359:    */   }
/* 360:    */   
/* 361:    */   public boolean fov(float x, float y)
/* 362:    */   {
/* 363:345 */     return true;
/* 364:    */   }
/* 365:    */   
/* 366:    */   public AgentMovement undo()
/* 367:    */   {
/* 368:    */     try
/* 369:    */     {
/* 370:350 */       this.movementStack.pop();
/* 371:351 */       AgentMovement move = (AgentMovement)this.movementStack.peek();
/* 372:352 */       setAngle(move.angle);
/* 373:353 */       setPosition(new Vector2f(move.x, move.y));
/* 374:354 */       int cost = move.cost;
/* 375:355 */       if (this.Ap + cost > this.maxAP)
/* 376:    */       {
/* 377:356 */         int newCost = this.Ap + cost - this.maxAP;
/* 378:357 */         this.Ap = this.maxAP;
/* 379:358 */         move.cost = newCost;
/* 380:    */       }
/* 381:    */       else
/* 382:    */       {
/* 383:360 */         this.Ap += cost;
/* 384:361 */         move.cost = 0;
/* 385:    */       }
/* 386:364 */       return move;
/* 387:    */     }
/* 388:    */     catch (Exception e) {}
/* 389:367 */     return null;
/* 390:    */   }
/* 391:    */   
/* 392:    */   public float getAttackRange()
/* 393:    */   {
/* 394:373 */     return this.attackRange;
/* 395:    */   }
/* 396:    */   
/* 397:    */   public void AddWeapon(Weapon weapon)
/* 398:    */   {
/* 399:378 */     this.weapons.add(weapon);
/* 400:    */   }
/* 401:    */   
/* 402:    */   public Weapon getDefaultWeapon()
/* 403:    */   {
/* 404:382 */     return (Weapon)this.weapons.get(0);
/* 405:    */   }
/* 406:    */   
/* 407:    */   public ArrayList<Weapon> getWeapons()
/* 408:    */   {
/* 409:385 */     return this.weapons;
/* 410:    */   }
/* 411:    */   
/* 412:    */   public Weapon getCurrentWeapon()
/* 413:    */   {
/* 414:390 */     System.out.println("Current weapon is " + this.selectedWeapon);
/* 415:391 */     if ((this.weapons.size() > 0) && (this.weapons.size() > this.selectedWeapon)) {
/* 416:393 */       return (Weapon)this.weapons.get(this.selectedWeapon);
/* 417:    */     }
/* 418:395 */     return null;
/* 419:    */   }
/* 420:    */   
/* 421:    */   public void setCurrentWeapon(int weapon)
/* 422:    */   {
/* 423:401 */     this.selectedWeapon = weapon;
/* 424:    */   }
/* 425:    */   
/* 426:    */   public Weapon getWeapon(int name)
/* 427:    */   {
/* 428:406 */     return (Weapon)this.weapons.get(name);
/* 429:    */   }
/* 430:    */   
/* 431:    */   public Weapon getWeapon(String name)
/* 432:    */   {
/* 433:410 */     for (int i = 0; i < this.weapons.size(); i++) {
/* 434:412 */       if (name.equalsIgnoreCase(((Weapon)this.weapons.get(i)).getName())) {
/* 435:414 */         return (Weapon)this.weapons.get(i);
/* 436:    */       }
/* 437:    */     }
/* 438:417 */     return getDefaultWeapon();
/* 439:    */   }
/* 440:    */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.agent.AgentModel
 * JD-Core Version:    0.7.0.1
 */