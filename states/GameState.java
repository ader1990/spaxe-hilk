/*    1:     */ package sh.states;
/*    2:     */ 
/*    3:     */ import java.io.PrintStream;
/*    4:     */ import java.util.ArrayList;
/*    5:     */ import java.util.HashMap;
/*    6:     */ import java.util.UUID;
/*    7:     */ import org.newdawn.slick.Animation;
/*    8:     */ import org.newdawn.slick.Color;
/*    9:     */ import org.newdawn.slick.GameContainer;
/*   10:     */ import org.newdawn.slick.Graphics;
/*   11:     */ import org.newdawn.slick.Image;
/*   12:     */ import org.newdawn.slick.SlickException;
/*   13:     */ import org.newdawn.slick.state.StateBasedGame;
/*   14:     */ import org.newdawn.slick.tiled.TiledMap;
/*   15:     */ import sh.SpaceHulkGame;
/*   16:     */ import sh.SpaceHulkGameContainer;
/*   17:     */ import sh.agent.AgentController;
/*   18:     */ import sh.agent.AgentModel;
/*   19:     */ import sh.agent.AgentView;
/*   20:     */ import sh.agent.aliens.AlienModel;
/*   21:     */ import sh.agent.aliens.AlienView;
/*   22:     */ import sh.agent.aliens.BlipModel;
/*   23:     */ import sh.agent.aliens.BlipView;
/*   24:     */ import sh.agent.door.DoorModel;
/*   25:     */ import sh.agent.door.DoorView;
/*   26:     */ import sh.agent.fire.FireModel;
/*   27:     */ import sh.agent.fire.FireView;
/*   28:     */ import sh.agent.marines.MarineModel;
/*   29:     */ import sh.agent.marines.MarineView;
/*   30:     */ import sh.agent.weapons.AlienMelee;
/*   31:     */ import sh.agent.weapons.Bolter;
/*   32:     */ import sh.agent.weapons.Flamer;
/*   33:     */ import sh.agent.weapons.PowerGlove;
/*   34:     */ import sh.agent.weapons.Weapon;
/*   35:     */ import sh.agent.weapons.projectiles.BulletModel;
/*   36:     */ import sh.agent.weapons.projectiles.BulletView;
/*   37:     */ import sh.gameobject.GameController;
/*   38:     */ import sh.gameobject.startposition.StartPositionModel;
/*   39:     */ import sh.gameobject.startposition.StartPositionView;
/*   40:     */ import sh.gui.TWL.BasicTWLGameState;
/*   41:     */ import sh.multiplayer.Finished;
/*   42:     */ import sh.multiplayer.SyncLijst;
/*   43:     */ import sh.multiplayer.player.Player;
/*   44:     */ import sh.utils.Diceroller;
/*   45:     */ import sh.world.shworld.SpaceHulkWorldController;
/*   46:     */ import sh.world.shworld.SpaceHulkWorldModel;
/*   47:     */ import sh.world.shworld.SpaceHulkWorldView;
/*   48:     */ 
/*   49:     */ public class GameState
/*   50:     */   extends BasicTWLGameState
/*   51:     */ {
/*   52:     */   protected static enum State
/*   53:     */   {
/*   54:  65 */     INIT,  WAITING,  PLACEMENT,  PLAY,  BLIPCONVERT,  OTHERTURN;
/*   55:     */   }
/*   56:     */   
/*   57:  69 */   protected State state = State.INIT;
/*   58:  70 */   protected State previousState = State.INIT;
/*   59:     */   protected SpaceHulkGameContainer shgameContainer;
/*   60:     */   protected SpaceHulkGame shgame;
/*   61:     */   protected SpaceHulkWorldModel worldModel;
/*   62:  75 */   protected int GID = 0;
/*   63:     */   protected SpaceHulkWorldView worldView;
/*   64:     */   protected SpaceHulkWorldController worldController;
/*   65:     */   protected boolean gameEnds;
/*   66:     */   protected boolean interruptionServerToggle;
/*   67:  82 */   int stateID = -1;
/*   68:     */   public TiledMap map;
/*   69:  84 */   public boolean isDefeated = false;
/*   70:     */   Animation selected;
/*   71:  87 */   float scaleX = 1.0F;
/*   72:  89 */   float scaleY = 1.0F;
/*   73:  91 */   int cameraPositionX = 0;
/*   74:  92 */   int cameraPositionY = 0;
/*   75:  93 */   int mapHeight = 0;
/*   76:  95 */   int mapWidth = 0;
/*   77:     */   ArrayList<Image> marinePlayer;
/*   78:     */   Image marineSelect;
/*   79:     */   Image overWatch;
/*   80:     */   ArrayList<Image> blipPlayer;
/*   81:     */   Image blipSelect;
/*   82:     */   ArrayList<Image> alienPlayer;
/*   83:     */   Image alienSelect;
/*   84:     */   Image alienPlacement;
/*   85:     */   Image doorImage;
/*   86:     */   ArrayList<Image> fireImage;
/*   87:     */   protected Image bullet;
/*   88: 112 */   boolean panning = false;
/*   89:     */   private ArrayList<Image> sergeantPlayer;
/*   90:     */   private ArrayList<Image> flamerPlayer;
/*   91:     */   protected ArrayList<Image> marineStartPositions;
/*   92:     */   protected ArrayList<Image> alienStartPositions;
/*   93: 118 */   protected HashMap<String, ArrayList<Image>> sergeantAnimations = new HashMap();
/*   94: 119 */   protected HashMap<String, ArrayList<Image>> flamerAnimations = new HashMap();
/*   95: 120 */   protected HashMap<String, ArrayList<Image>> marineAnimations = new HashMap();
/*   96: 121 */   protected HashMap<String, ArrayList<Image>> alienAnimations = new HashMap();
/*   97: 122 */   protected HashMap<String, ArrayList<Image>> blipAnimations = new HashMap();
/*   98:     */   
/*   99:     */   public GameState(int stateID)
/*  100:     */   {
/*  101: 125 */     this.stateID = stateID;
/*  102:     */   }
/*  103:     */   
/*  104:     */   public void addGID()
/*  105:     */   {
/*  106: 130 */     this.GID += 1;
/*  107:     */   }
/*  108:     */   
/*  109:     */   private void checkDefeated()
/*  110:     */   {
/*  111: 134 */     if (this.shgameContainer.getPlayer().checkDefeated(this.shgameContainer.getKills())) {
/*  112: 136 */       sendDefeat();
/*  113:     */     }
/*  114:     */   }
/*  115:     */   
/*  116:     */   public void enter(GameContainer container, StateBasedGame game)
/*  117:     */     throws SlickException
/*  118:     */   {
/*  119: 147 */     this.shgameContainer = ((SpaceHulkGameContainer)container);
/*  120: 148 */     this.shgame = ((SpaceHulkGame)game);
/*  121: 149 */     super.enter(container, game);
/*  122: 150 */     this.map = this.shgameContainer.getMap();
/*  123:     */     
/*  124:     */ 
/*  125:     */ 
/*  126: 154 */     loadWorld(this.map, this.shgameContainer.getPlayers());
/*  127: 155 */     loadDoors(this.map);
/*  128: 156 */     loadStartPositions(this.map, this.shgameContainer.getPlayers());
/*  129: 157 */     if (this.shgameContainer.getMission().equalsIgnoreCase("mission")) {
/*  130: 159 */       loadObjectives();
/*  131:     */     }
/*  132: 161 */     this.state = State.PLACEMENT;
/*  133:     */   }
/*  134:     */   
/*  135:     */   public void fireBullets(AgentModel attack, float tx, float ty, Weapon weapon)
/*  136:     */   {
/*  137: 167 */     final Weapon attackWeapon = weapon;
/*  138: 168 */     final AgentModel attacker = attack;
/*  139: 169 */     final float targetX = tx;
/*  140: 170 */     final float targetY = ty;
/*  141: 171 */     System.out.println("Bullets " + System.currentTimeMillis());
/*  142: 172 */     System.out.println(weapon.getBullet());
/*  143: 173 */     if (weapon.getBullet() != null)
/*  144:     */     {
/*  145: 175 */       System.out.println(weapon.getBullet());
/*  146: 176 */       new Thread()
/*  147:     */       {
/*  148:     */         public void run()
/*  149:     */         {
/*  150: 181 */           for (int i = 0; i < attackWeapon.getFireAmount(); i++)
/*  151:     */           {
/*  152: 183 */             float spreadX = 0.0F;
/*  153: 184 */             float spreadY = 0.0F;
/*  154: 185 */             if (attackWeapon.getSpread() > 0.1D)
/*  155:     */             {
/*  156: 187 */               System.out.println(attackWeapon.getSpread() + " " + (int)(attackWeapon.getSpread() * 100.0F));
/*  157: 188 */               int roll = Diceroller.roll((int)(attackWeapon.getSpread() * 100.0F));
/*  158: 189 */               spreadX = roll / 100.0F - attackWeapon.getSpread();
/*  159: 190 */               System.out.println("Bullet to x " + targetX + " " + spreadX + " " + roll + " " + attackWeapon.getSpread());
/*  160: 191 */               int roll2 = Diceroller.roll((int)(attackWeapon.getSpread() * 100.0F));
/*  161: 192 */               spreadY = roll2 / 100.0F - attackWeapon.getSpread();
/*  162: 193 */               System.out.println("Bullet to y " + targetY + " " + spreadY + " " + roll2 + " " + attackWeapon.getSpread());
/*  163:     */             }
/*  164: 196 */             BulletModel bul = new BulletModel(UUID.randomUUID().hashCode(), "bul", attacker.getX(), attacker.getY(), attacker.getAngle(), GameState.this.worldModel, "bullets", 1, true, -1.0F, -1, -1, -1, -1);
/*  165: 197 */             BulletView view = new BulletView(bul, attackWeapon.getBullet());
/*  166: 198 */             GameState.this.worldModel.addBulletModel(bul);
/*  167: 199 */             GameState.this.worldView.addBulletView(bul.getUUID(), view);
/*  168:     */             
/*  169: 201 */             bul.moveTo(targetX + spreadX, targetY + spreadY);
/*  170:     */             try
/*  171:     */             {
/*  172: 204 */               Thread.sleep(attackWeapon.getCooldown());
/*  173:     */             }
/*  174:     */             catch (InterruptedException e)
/*  175:     */             {
/*  176: 208 */               e.printStackTrace();
/*  177:     */             }
/*  178:     */           }
/*  179:     */         }
/*  180:     */       }.start();
/*  181:     */     }
/*  182:     */   }
/*  183:     */   
/*  184:     */   public int getGID()
/*  185:     */   {
/*  186: 220 */     return this.GID;
/*  187:     */   }
/*  188:     */   
/*  189:     */   public int getID()
/*  190:     */   {
/*  191: 226 */     return this.stateID;
/*  192:     */   }
/*  193:     */   
/*  194:     */   public void init(GameContainer container, StateBasedGame arg1)
/*  195:     */     throws SlickException
/*  196:     */   {
/*  197:     */     try
/*  198:     */     {
/*  199: 236 */       this.alienPlayer = new ArrayList();
/*  200: 237 */       this.marinePlayer = new ArrayList();
/*  201: 238 */       this.sergeantPlayer = new ArrayList();
/*  202: 239 */       this.flamerPlayer = new ArrayList();
/*  203: 240 */       this.fireImage = new ArrayList();
/*  204: 241 */       this.blipPlayer = new ArrayList();
/*  205: 242 */       this.marineStartPositions = new ArrayList();
/*  206: 243 */       this.alienStartPositions = new ArrayList();
/*  207: 244 */       this.marineSelect = new Image("data/images/select.png");
/*  208: 245 */       this.overWatch = new Image("data/images/SpaceHulk3E/overwatch.png");
/*  209: 246 */       this.bullet = new Image("data/images/decals/bullet.png");
/*  210:     */       
/*  211: 248 */       this.blipSelect = new Image("data/images/select.png");
/*  212: 249 */       this.alienPlacement = new Image("data/images/select.png");
/*  213:     */       
/*  214:     */ 
/*  215: 252 */       this.alienSelect = new Image("data/images/select.png");
/*  216:     */       
/*  217: 254 */       this.doorImage = new Image("data/images/tiles/door.png");
/*  218:     */       
/*  219: 256 */       this.marineStartPositions = new ArrayList();
/*  220: 259 */       for (int i = 1; i < 7; i++) {
/*  221: 261 */         this.fireImage.add(new Image("data/images/SpaceHulk3E/fl_" + i + ".png"));
/*  222:     */       }
/*  223: 265 */       this.marinePlayer.add(new Image("data/images/marines/marine.png"));
/*  224: 266 */       boolean images = true;
/*  225: 267 */       int count = 1;
/*  226: 268 */       while (images)
/*  227:     */       {
/*  228:     */         try
/*  229:     */         {
/*  230: 272 */           this.marinePlayer.add(new Image("data/images/marines/marine" + count + ".png"));
/*  231:     */         }
/*  232:     */         catch (Exception e)
/*  233:     */         {
/*  234: 276 */           images = false;
/*  235:     */         }
/*  236: 278 */         count++;
/*  237:     */       }
/*  238: 282 */       this.marineAnimations.put("default", this.marinePlayer);
/*  239: 283 */       count = 1;
/*  240: 284 */       images = true;
/*  241:     */       
/*  242: 286 */       this.sergeantPlayer.add(new Image("data/images/marines/sergeant.png"));
/*  243: 287 */       while (images)
/*  244:     */       {
/*  245:     */         try
/*  246:     */         {
/*  247: 292 */           this.sergeantPlayer.add(new Image("data/images/marines/sergeant" + count + ".png"));
/*  248:     */         }
/*  249:     */         catch (Exception e)
/*  250:     */         {
/*  251: 297 */           images = false;
/*  252:     */         }
/*  253: 299 */         count++;
/*  254:     */       }
/*  255: 303 */       this.sergeantAnimations.put("default", this.sergeantPlayer);
/*  256: 304 */       count = 1;
/*  257: 305 */       images = true;
/*  258:     */       
/*  259: 307 */       this.flamerPlayer.add(new Image("data/images/marines/flamer.png"));
/*  260: 308 */       while (images)
/*  261:     */       {
/*  262:     */         try
/*  263:     */         {
/*  264: 312 */           this.flamerPlayer.add(new Image("data/images/marines/flamer" + count + ".png"));
/*  265:     */         }
/*  266:     */         catch (Exception e)
/*  267:     */         {
/*  268: 316 */           images = false;
/*  269:     */         }
/*  270: 318 */         count++;
/*  271:     */       }
/*  272: 322 */       this.flamerAnimations.put("default", this.flamerPlayer);
/*  273:     */       
/*  274: 324 */       count = 1;
/*  275: 325 */       images = true;
/*  276:     */       
/*  277: 327 */       this.marineStartPositions.add(new Image("data/images/tiles/marinestart.png"));
/*  278: 328 */       while (images)
/*  279:     */       {
/*  280:     */         try
/*  281:     */         {
/*  282: 332 */           this.marineStartPositions.add(new Image("data/images/tiles/marinestart" + count + ".png"));
/*  283:     */         }
/*  284:     */         catch (Exception e)
/*  285:     */         {
/*  286: 336 */           images = false;
/*  287:     */         }
/*  288: 338 */         count++;
/*  289:     */       }
/*  290: 344 */       count = 1;
/*  291: 345 */       images = true;
/*  292:     */       
/*  293: 347 */       this.alienStartPositions.add(new Image("data/images/tiles/alienstart.png"));
/*  294: 348 */       while (images)
/*  295:     */       {
/*  296:     */         try
/*  297:     */         {
/*  298: 352 */           this.alienStartPositions.add(new Image("data/images/tiles/alienstart" + count + ".png"));
/*  299:     */         }
/*  300:     */         catch (Exception e)
/*  301:     */         {
/*  302: 356 */           images = false;
/*  303:     */         }
/*  304: 358 */         count++;
/*  305:     */       }
/*  306: 362 */       count = 1;
/*  307: 363 */       images = true;
/*  308: 364 */       this.blipPlayer.add(new Image("data/images/aliens/blip.png"));
/*  309: 365 */       while (images)
/*  310:     */       {
/*  311:     */         try
/*  312:     */         {
/*  313: 369 */           this.blipPlayer.add(new Image("data/images/aliens/blip" + count + ".png"));
/*  314:     */         }
/*  315:     */         catch (Exception e)
/*  316:     */         {
/*  317: 373 */           images = false;
/*  318:     */         }
/*  319: 375 */         count++;
/*  320:     */       }
/*  321: 379 */       this.blipAnimations.put("default", this.blipPlayer);
/*  322: 380 */       count = 1;
/*  323: 381 */       images = true;
/*  324:     */       
/*  325: 383 */       this.alienPlayer.add(new Image("data/images/aliens/alien.png"));
/*  326: 384 */       while (images)
/*  327:     */       {
/*  328:     */         try
/*  329:     */         {
/*  330: 388 */           this.alienPlayer.add(new Image("data/images/aliens/alien" + count + ".png"));
/*  331:     */         }
/*  332:     */         catch (Exception e)
/*  333:     */         {
/*  334: 392 */           images = false;
/*  335:     */         }
/*  336: 394 */         count++;
/*  337:     */       }
/*  338: 397 */       this.alienAnimations.put("default", this.alienPlayer);
/*  339:     */       
/*  340: 399 */       initAnimations();
/*  341:     */     }
/*  342:     */     catch (Exception e)
/*  343:     */     {
/*  344: 404 */       e.printStackTrace();
/*  345:     */     }
/*  346:     */   }
/*  347:     */   
/*  348:     */   private void initAnimations()
/*  349:     */   {
/*  350: 412 */     System.out.println("Init animations");
/*  351:     */     try
/*  352:     */     {
/*  353: 415 */       int count = 1;
/*  354: 416 */       boolean images = true;
/*  355:     */       
/*  356: 418 */       ArrayList<Image> serWalking = new ArrayList();
/*  357:     */       
/*  358: 420 */       serWalking.add(new Image("data/images/marines/sergeantwalking.png"));
/*  359: 421 */       System.out.println("walking");
/*  360: 422 */       while (images)
/*  361:     */       {
/*  362:     */         try
/*  363:     */         {
/*  364: 426 */           System.out.println("Walking " + count);
/*  365: 427 */           serWalking.add(new Image("data/images/marines/sergeantwalking" + count + ".png"));
/*  366:     */         }
/*  367:     */         catch (Exception e)
/*  368:     */         {
/*  369: 431 */           System.out.println("no more images");
/*  370: 432 */           images = false;
/*  371:     */         }
/*  372: 434 */         count++;
/*  373:     */       }
/*  374: 438 */       this.sergeantAnimations.put("walking", serWalking);
/*  375:     */     }
/*  376:     */     catch (Exception e)
/*  377:     */     {
/*  378: 442 */       e.printStackTrace();
/*  379:     */     }
/*  380:     */     try
/*  381:     */     {
/*  382: 447 */       int count = 1;
/*  383: 448 */       boolean images = true;
/*  384:     */       
/*  385: 450 */       ArrayList<Image> sergeantMelee = new ArrayList();
/*  386:     */       
/*  387: 452 */       sergeantMelee.add(new Image("data/images/marines/sergeantmelee.png"));
/*  388: 453 */       while (images)
/*  389:     */       {
/*  390:     */         try
/*  391:     */         {
/*  392: 457 */           sergeantMelee.add(new Image("data/images/marines/sergeantmelee" + count + ".png"));
/*  393:     */         }
/*  394:     */         catch (Exception e)
/*  395:     */         {
/*  396: 461 */           images = false;
/*  397:     */         }
/*  398: 463 */         count++;
/*  399:     */       }
/*  400: 467 */       this.sergeantAnimations.put("melee", sergeantMelee);
/*  401:     */     }
/*  402:     */     catch (Exception localException1) {}
/*  403:     */     try
/*  404:     */     {
/*  405: 476 */       int count = 1;
/*  406: 477 */       boolean images = true;
/*  407:     */       
/*  408: 479 */       ArrayList<Image> marWalking = new ArrayList();
/*  409:     */       
/*  410: 481 */       marWalking.add(new Image("data/images/marines/marinewalking.png"));
/*  411: 482 */       while (images)
/*  412:     */       {
/*  413:     */         try
/*  414:     */         {
/*  415: 486 */           marWalking.add(new Image("data/images/marines/marinewalking" + count + ".png"));
/*  416:     */         }
/*  417:     */         catch (Exception e)
/*  418:     */         {
/*  419: 490 */           images = false;
/*  420:     */         }
/*  421: 492 */         count++;
/*  422:     */       }
/*  423: 496 */       this.marineAnimations.put("walking", marWalking);
/*  424:     */     }
/*  425:     */     catch (Exception localException2) {}
/*  426:     */     try
/*  427:     */     {
/*  428: 505 */       int count = 1;
/*  429: 506 */       boolean images = true;
/*  430:     */       
/*  431: 508 */       ArrayList<Image> flaWalking = new ArrayList();
/*  432:     */       
/*  433: 510 */       flaWalking.add(new Image("data/images/marines/flamerwalking.png"));
/*  434: 511 */       while (images)
/*  435:     */       {
/*  436:     */         try
/*  437:     */         {
/*  438: 515 */           flaWalking.add(new Image("data/images/marines/flamerwalking" + count + ".png"));
/*  439:     */         }
/*  440:     */         catch (Exception e)
/*  441:     */         {
/*  442: 519 */           images = false;
/*  443:     */         }
/*  444: 521 */         count++;
/*  445:     */       }
/*  446: 525 */       this.flamerAnimations.put("walking", flaWalking);
/*  447:     */     }
/*  448:     */     catch (Exception localException3) {}
/*  449:     */     try
/*  450:     */     {
/*  451: 534 */       int count = 1;
/*  452: 535 */       boolean images = true;
/*  453:     */       
/*  454: 537 */       ArrayList<Image> alienWalking = new ArrayList();
/*  455:     */       
/*  456: 539 */       alienWalking.add(new Image("data/images/aliens/alienswalking.png"));
/*  457: 540 */       while (images)
/*  458:     */       {
/*  459:     */         try
/*  460:     */         {
/*  461: 544 */           alienWalking.add(new Image("data/images/aliens/alienswalking" + count + ".png"));
/*  462:     */         }
/*  463:     */         catch (Exception e)
/*  464:     */         {
/*  465: 548 */           images = false;
/*  466:     */         }
/*  467: 550 */         count++;
/*  468:     */       }
/*  469: 554 */       this.alienAnimations.put("walking", alienWalking);
/*  470:     */     }
/*  471:     */     catch (Exception localException4) {}
/*  472:     */     try
/*  473:     */     {
/*  474: 564 */       int count = 1;
/*  475: 565 */       boolean images = true;
/*  476:     */       
/*  477: 567 */       ArrayList<Image> alienMelee = new ArrayList();
/*  478:     */       
/*  479: 569 */       alienMelee.add(new Image("data/images/aliens/aliensattack.png"));
/*  480: 570 */       while (images)
/*  481:     */       {
/*  482:     */         try
/*  483:     */         {
/*  484: 574 */           alienMelee.add(new Image("data/images/aliens/aliensattack" + count + ".png"));
/*  485:     */         }
/*  486:     */         catch (Exception e)
/*  487:     */         {
/*  488: 578 */           images = false;
/*  489:     */         }
/*  490: 580 */         count++;
/*  491:     */       }
/*  492: 584 */       this.alienAnimations.put("melee", alienMelee);
/*  493:     */     }
/*  494:     */     catch (Exception localException5) {}
/*  495:     */   }
/*  496:     */   
/*  497:     */   protected void loadDoors(TiledMap map)
/*  498:     */   {
/*  499: 596 */     int index = map.getLayerIndex("Doors");
/*  500: 598 */     if (index < 0) {
/*  501: 601 */       return;
/*  502:     */     }
/*  503: 604 */     for (int x = 0; x < map.getWidth(); x++) {
/*  504: 606 */       for (int y = 0; y < map.getHeight(); y++)
/*  505:     */       {
/*  506: 609 */         int tileID = map.getTileId(x, y, index);
/*  507:     */         
/*  508: 611 */         String door = map.getTileProperty(tileID, "door", "false");
/*  509: 612 */         if (door.equalsIgnoreCase("true"))
/*  510:     */         {
/*  511: 614 */           String allignment = map.getTileProperty(tileID, 
/*  512: 615 */             "allignment", "horizontal");
/*  513: 616 */           float angle = 90.0F;
/*  514: 617 */           if (allignment.equalsIgnoreCase("horizontal")) {
/*  515: 619 */             angle = 0.0F;
/*  516:     */           }
/*  517: 621 */           int UUID = ("doors" + String.valueOf(getGID())).hashCode();
/*  518:     */           
/*  519: 623 */           DoorModel agentModel = new DoorModel(UUID, "door", 
/*  520: 624 */             x + 0.5F, y + 0.5F, angle, this.worldModel, "doors", 0, 
/*  521: 625 */             false, 0.0F, -1, -1, -1, 0);
/*  522: 626 */           DoorView agentView = new DoorView(agentModel, this.doorImage);
/*  523:     */           
/*  524: 628 */           AgentController agentController = new AgentController(
/*  525: 629 */             agentModel, agentView);
/*  526: 630 */           this.worldModel.addAgentModel(agentModel);
/*  527: 631 */           this.worldView.addAgentView(UUID, agentView);
/*  528: 632 */           this.worldController.addAgentController(UUID, agentController);
/*  529: 633 */           addGID();
/*  530:     */         }
/*  531:     */       }
/*  532:     */     }
/*  533:     */   }
/*  534:     */   
/*  535:     */   private void loadObjectives()
/*  536:     */   {
/*  537: 645 */     String objective = this.map.getMapProperty("objective_final", "none");
/*  538: 647 */     if (!objective.equalsIgnoreCase("none"))
/*  539:     */     {
/*  540: 649 */       int index = this.map.getLayerIndex("Objective");
/*  541: 651 */       if (index > 0) {
/*  542: 653 */         this.worldModel.setObjectives(this.map);
/*  543:     */       }
/*  544:     */     }
/*  545:     */   }
/*  546:     */   
/*  547:     */   private void loadStartPositions(TiledMap map2, int players)
/*  548:     */   {
/*  549: 663 */     int index = this.map.getLayerIndex("Players");
/*  550: 665 */     if (index < 0) {
/*  551: 668 */       return;
/*  552:     */     }
/*  553: 670 */     for (int x = 0; x < this.map.getWidth(); x++) {
/*  554: 672 */       for (int y = 0; y < this.map.getHeight(); y++)
/*  555:     */       {
/*  556: 675 */         int tileID = this.map.getTileId(x, y, index);
/*  557:     */         
/*  558: 677 */         int player = Integer.parseInt(this.map.getTileProperty(tileID, "player", "-1"));
/*  559: 678 */         String faction = this.map.getTileProperty(tileID, "faction", "");
/*  560: 679 */         if ((player >= 0) && (!faction.isEmpty()))
/*  561:     */         {
/*  562: 681 */           System.out.println("found");
/*  563: 682 */           String allignment = this.map.getTileProperty(tileID, 
/*  564: 683 */             "allignment", "horizontal");
/*  565: 684 */           float angle = 90.0F;
/*  566: 685 */           if (allignment.equalsIgnoreCase("horizontal")) {
/*  567: 687 */             angle = 0.0F;
/*  568:     */           }
/*  569: 689 */           int UUID = ("startPosition" + String.valueOf(getGID())).hashCode();
/*  570:     */           
/*  571: 691 */           StartPositionModel agentModel = new StartPositionModel(UUID, "Start Position", 
/*  572: 692 */             x + 0.5F, y + 0.5F, angle, this.worldModel, false, this.shgameContainer.getColour(player), faction, player);
/*  573: 693 */           StartPositionView agentView = null;
/*  574: 694 */           GameController agentController = null;
/*  575: 696 */           if ((faction.equalsIgnoreCase("marines")) || (faction.equalsIgnoreCase("marine"))) {
/*  576: 699 */             agentView = new StartPositionView(agentModel, this.marineStartPositions);
/*  577:     */           } else {
/*  578: 705 */             agentView = new StartPositionView(agentModel, this.alienStartPositions);
/*  579:     */           }
/*  580: 707 */           agentController = new GameController(agentModel, agentView);
/*  581: 708 */           this.worldModel.addGameModel(agentModel);
/*  582: 709 */           this.worldView.addGameView(UUID, agentView);
/*  583: 710 */           this.worldController.addGameController(UUID, agentController);
/*  584: 711 */           addGID();
/*  585:     */         }
/*  586:     */       }
/*  587:     */     }
/*  588:     */   }
/*  589:     */   
/*  590:     */   protected void loadWorld(TiledMap map, int players)
/*  591:     */   {
/*  592:     */     try
/*  593:     */     {
/*  594: 723 */       this.worldModel = new SpaceHulkWorldModel(map, players);
/*  595: 724 */       this.worldView = new SpaceHulkWorldView(this.worldModel, map);
/*  596: 725 */       this.worldController = new SpaceHulkWorldController(this.worldModel, this.worldView);
/*  597:     */     }
/*  598:     */     catch (SecurityException e)
/*  599:     */     {
/*  600: 729 */       e.printStackTrace();
/*  601:     */     }
/*  602:     */   }
/*  603:     */   
/*  604:     */   public void mouseDragged(int oldx, int oldy, int newx, int newy)
/*  605:     */   {
/*  606: 736 */     super.mouseDragged(oldx, oldy, newx, newy);
/*  607: 737 */     if (this.panning)
/*  608:     */     {
/*  609: 740 */       int deltax = oldx - newx;
/*  610: 741 */       int deltay = oldy - newy;
/*  611: 742 */       this.cameraPositionX -= (int)(deltax * (1.0F / this.scaleX));
/*  612: 743 */       this.cameraPositionY -= (int)(deltay * (1.0F / this.scaleY));
/*  613:     */     }
/*  614:     */   }
/*  615:     */   
/*  616:     */   public void mousePressed(int button, int x, int y)
/*  617:     */   {
/*  618: 750 */     super.mousePressed(button, x, y);
/*  619: 751 */     if (button == 2) {
/*  620: 753 */       this.panning = true;
/*  621:     */     }
/*  622:     */   }
/*  623:     */   
/*  624:     */   public void mouseReleased(int button, int x, int y)
/*  625:     */   {
/*  626: 761 */     super.mouseReleased(button, x, y);
/*  627: 762 */     if (button == 2) {
/*  628: 765 */       this.panning = false;
/*  629:     */     }
/*  630:     */   }
/*  631:     */   
/*  632:     */   public void mouseWheelMoved(int change)
/*  633:     */   {
/*  634: 772 */     float zoom = change / 2000.0F;
/*  635:     */     
/*  636: 774 */     this.scaleX += zoom;
/*  637: 775 */     this.scaleY += zoom;
/*  638:     */   }
/*  639:     */   
/*  640:     */   protected int PlaceAgent(float x, float y, float angle, String faction, boolean alien, int playerID, int team, int colour)
/*  641:     */   {
/*  642: 781 */     String name = "marine";
/*  643:     */     
/*  644:     */ 
/*  645: 784 */     int UUID = (faction + String.valueOf(getGID())).hashCode();
/*  646:     */     
/*  647:     */ 
/*  648: 787 */     float agentX = x + 0.5F;
/*  649: 788 */     float agentY = y + 0.5F;
/*  650:     */     AgentView agentView;
/*  651:     */     AgentModel agentModel;
/*  652:     */     AgentView agentView;
/*  653: 794 */     if (!alien)
/*  654:     */     {
/*  655: 796 */       this.shgameContainer.playSound("blip");
/*  656: 797 */       AgentModel agentModel = new BlipModel(UUID, name, agentX, agentY, angle, 
/*  657: 798 */         this.worldModel, faction, 6, false, Diceroller.roll(6), 0.0F, 
/*  658: 799 */         playerID, team, -1, colour);
/*  659: 800 */       agentView = new BlipView((BlipModel)agentModel, this.blipPlayer, this.blipSelect, this.alienPlacement);
/*  660:     */     }
/*  661:     */     else
/*  662:     */     {
/*  663: 803 */       this.shgameContainer.playSound("alien");
/*  664: 804 */       agentModel = new AlienModel(UUID, name, agentX, agentY, angle, 
/*  665: 805 */         this.worldModel, faction, 6, false, 1.0F, playerID, team, -1, colour);
/*  666: 806 */       agentModel.AddWeapon(new AlienMelee(2));
/*  667: 807 */       agentView = new AlienView((AlienModel)agentModel, this.alienAnimations, this.alienSelect);
/*  668:     */     }
/*  669: 810 */     AgentController agentController = new AgentController(agentModel, agentView);
/*  670:     */     
/*  671:     */ 
/*  672:     */ 
/*  673: 814 */     this.worldModel.addAgentModel(agentModel);
/*  674: 815 */     this.worldView.addAgentView(UUID, agentView);
/*  675: 816 */     this.worldController.addAgentController(UUID, agentController);
/*  676: 817 */     addGID();
/*  677: 818 */     return UUID;
/*  678:     */   }
/*  679:     */   
/*  680:     */   protected int PlaceAgent(float x, float y, float angle, String faction, int UUID, boolean blip, int playerID, int team, int marineType, int colour)
/*  681:     */   {
/*  682: 825 */     float agentX = x + 0.5F;
/*  683: 826 */     float agentY = y + 0.5F;
/*  684:     */     AgentModel agentModel;
/*  685:     */     AgentView agentView;
/*  686: 833 */     if (faction.equalsIgnoreCase("marines"))
/*  687:     */     {
/*  688:     */       AgentView agentView;
/*  689:     */       AgentView agentView;
/*  690: 836 */       if (marineType == 1)
/*  691:     */       {
/*  692: 838 */         AgentModel agentModel = new MarineModel(UUID, "Flamer", agentX, agentY, angle, 
/*  693: 839 */           this.worldModel, faction, 4, true, 6.0F, playerID, team, marineType, colour, 0);
/*  694: 840 */         agentModel.AddWeapon(new Flamer(2, this.bullet));
/*  695: 841 */         agentView = new MarineView(agentModel, this.flamerAnimations, this.marineSelect, this.overWatch);
/*  696:     */       }
/*  697:     */       else
/*  698:     */       {
/*  699:     */         AgentView agentView;
/*  700: 843 */         if (marineType == 2)
/*  701:     */         {
/*  702: 845 */           AgentModel agentModel = new MarineModel(UUID, "Sergeant", agentX, agentY, angle, 
/*  703: 846 */             this.worldModel, faction, 4, true, 6.0F, playerID, team, marineType, colour, 2);
/*  704: 847 */           agentModel.AddWeapon(new Bolter(2, this.bullet));
/*  705: 848 */           agentModel.AddWeapon(new PowerGlove(2));
/*  706: 849 */           agentView = new MarineView(agentModel, this.sergeantAnimations, this.marineSelect, this.overWatch);
/*  707:     */         }
/*  708:     */         else
/*  709:     */         {
/*  710: 853 */           AgentModel agentModel = new MarineModel(UUID, "Marine", agentX, agentY, angle, 
/*  711: 854 */             this.worldModel, faction, 4, true, 6.0F, playerID, team, marineType, colour, 0);
/*  712: 855 */           agentModel.AddWeapon(new Bolter(2, this.bullet));
/*  713: 856 */           agentView = new MarineView(agentModel, this.marineAnimations, this.marineSelect, this.overWatch);
/*  714:     */         }
/*  715:     */       }
/*  716: 858 */       this.shgameContainer.playSound("marine");
/*  717:     */     }
/*  718:     */     else
/*  719:     */     {
/*  720:     */       AgentController agentController;
/*  721: 860 */       if (faction.equalsIgnoreCase("fires"))
/*  722:     */       {
/*  723: 862 */         System.out.println("Make fire " + UUID);
/*  724: 863 */         AgentModel agentModel = new FireModel(UUID, "fire", 
/*  725: 864 */           agentX, agentY, 0.0F, this.worldModel, "fires", 0, false, 0.0F, -1, -1, -1, 0);
/*  726: 865 */         AgentView agentView = new FireView(agentModel, this.fireImage);
/*  727:     */         
/*  728: 867 */         agentController = new AgentController(
/*  729: 868 */           agentModel, agentView);
/*  730:     */       }
/*  731: 873 */       else if (blip)
/*  732:     */       {
/*  733: 876 */         AgentModel agentModel = new BlipModel(UUID, "Blip", agentX, agentY, angle, 
/*  734: 877 */           this.worldModel, faction, 6, false, Diceroller.roll(6), 0.0F, 
/*  735: 878 */           playerID, team, marineType, colour);
/*  736: 879 */         AgentView agentView = new BlipView((BlipModel)agentModel, this.blipPlayer, this.blipSelect, this.alienPlacement);
/*  737: 880 */         this.shgameContainer.playSound("blip");
/*  738:     */       }
/*  739:     */       else
/*  740:     */       {
/*  741: 884 */         agentModel = new AlienModel(UUID, "Alien", agentX, agentY, angle, 
/*  742: 885 */           this.worldModel, faction, 6, false, 1.0F, playerID, team, marineType, colour);
/*  743: 886 */         agentModel.AddWeapon(new AlienMelee(2));
/*  744: 887 */         agentView = new AlienView((AlienModel)agentModel, this.alienAnimations, this.alienSelect);
/*  745: 888 */         this.shgameContainer.playSound("alien");
/*  746:     */       }
/*  747:     */     }
/*  748: 892 */     AgentController agentController = new AgentController(agentModel, agentView);
/*  749:     */     
/*  750:     */ 
/*  751:     */ 
/*  752: 896 */     this.worldModel.addAgentModel(agentModel);
/*  753: 897 */     this.worldView.addAgentView(UUID, agentView);
/*  754: 898 */     this.worldController.addAgentController(UUID, agentController);
/*  755: 899 */     addGID();
/*  756: 900 */     return UUID;
/*  757:     */   }
/*  758:     */   
/*  759:     */   protected int PlaceAgent(float x, float y, float angle, String faction, int playerID, int team, int marineType, int colour)
/*  760:     */   {
/*  761: 908 */     int UUID = (faction + String.valueOf(getGID())).hashCode();
/*  762:     */     
/*  763:     */ 
/*  764: 911 */     float agentX = x + 0.5F;
/*  765: 912 */     float agentY = y + 0.5F;
/*  766:     */     AgentModel agentModel;
/*  767:     */     AgentView agentView;
/*  768: 918 */     if (faction.equalsIgnoreCase("marines"))
/*  769:     */     {
/*  770:     */       AgentView agentView;
/*  771:     */       AgentView agentView;
/*  772: 922 */       if (marineType == 1)
/*  773:     */       {
/*  774: 924 */         AgentModel agentModel = new MarineModel(UUID, "Flamer", agentX, agentY, angle, 
/*  775: 925 */           this.worldModel, faction, 4, true, 6.0F, playerID, team, marineType, colour, 0);
/*  776: 926 */         agentModel.AddWeapon(new Flamer(2, this.bullet));
/*  777: 927 */         agentView = new MarineView(agentModel, this.flamerAnimations, this.marineSelect, this.overWatch);
/*  778:     */       }
/*  779:     */       else
/*  780:     */       {
/*  781:     */         AgentView agentView;
/*  782: 929 */         if (marineType == 2)
/*  783:     */         {
/*  784: 931 */           AgentModel agentModel = new MarineModel(UUID, "Sergeant", agentX, agentY, angle, 
/*  785: 932 */             this.worldModel, faction, 4, true, 6.0F, playerID, team, marineType, colour, 2);
/*  786: 933 */           agentModel.AddWeapon(new Bolter(2, this.bullet));
/*  787: 934 */           agentModel.AddWeapon(new PowerGlove(2));
/*  788: 935 */           agentView = new MarineView(agentModel, this.sergeantAnimations, this.marineSelect, this.overWatch);
/*  789:     */         }
/*  790:     */         else
/*  791:     */         {
/*  792: 939 */           AgentModel agentModel = new MarineModel(UUID, "Marine", agentX, agentY, angle, 
/*  793: 940 */             this.worldModel, faction, 4, true, 6.0F, playerID, team, marineType, colour, 0);
/*  794: 941 */           agentModel.AddWeapon(new Bolter(2, this.bullet));
/*  795: 942 */           agentView = new MarineView(agentModel, this.marineAnimations, this.marineSelect, this.overWatch);
/*  796:     */         }
/*  797:     */       }
/*  798: 944 */       this.shgameContainer.playSound("marine");
/*  799:     */     }
/*  800:     */     else
/*  801:     */     {
/*  802: 947 */       this.shgameContainer.playSound("blip");
/*  803: 948 */       agentModel = new BlipModel(UUID, "Blip", agentX, agentY, angle, 
/*  804: 949 */         this.worldModel, faction, 6, false, Diceroller.roll(6), 0.0F, 
/*  805: 950 */         playerID, team, marineType, colour);
/*  806: 951 */       agentView = new BlipView((BlipModel)agentModel, this.blipPlayer, this.blipSelect, this.alienPlacement);
/*  807:     */     }
/*  808: 954 */     AgentController agentController = new AgentController(agentModel, agentView);
/*  809:     */     
/*  810:     */ 
/*  811:     */ 
/*  812: 958 */     this.worldModel.addAgentModel(agentModel);
/*  813: 959 */     this.worldView.addAgentView(UUID, agentView);
/*  814: 960 */     this.worldController.addAgentController(UUID, agentController);
/*  815: 961 */     addGID();
/*  816: 962 */     return UUID;
/*  817:     */   }
/*  818:     */   
/*  819:     */   protected int PlaceFire(float x, float y)
/*  820:     */   {
/*  821: 967 */     float agentX = x + 0.5F;
/*  822: 968 */     float agentY = y + 0.5F;
/*  823: 969 */     int UUID = ("Fire" + String.valueOf(getGID())).hashCode();
/*  824:     */     
/*  825:     */ 
/*  826: 972 */     FireModel agentModel = new FireModel(UUID, "fire", 
/*  827: 973 */       x + 0.5F, y + 0.5F, 0.0F, this.worldModel, "fires", 0, 
/*  828: 974 */       false, 0.0F, -1, -1, -1, 0);
/*  829: 975 */     FireView agentView = new FireView(agentModel, this.fireImage);
/*  830:     */     
/*  831: 977 */     AgentController agentController = new AgentController(
/*  832: 978 */       agentModel, agentView);
/*  833:     */     
/*  834: 980 */     this.worldModel.addAgentModel(agentModel);
/*  835: 981 */     this.worldView.addAgentView(UUID, agentView);
/*  836: 982 */     this.worldController.addAgentController(UUID, agentController);
/*  837: 983 */     addGID();
/*  838: 984 */     return UUID;
/*  839:     */   }
/*  840:     */   
/*  841:     */   public void removeAgent(int UUID, boolean killed)
/*  842:     */   {
/*  843: 989 */     System.out.println("Removing from state " + UUID);
/*  844: 990 */     AgentModel a = this.worldModel.getAgentModel(UUID);
/*  845: 991 */     if ((!a.getFaction().equalsIgnoreCase("fires")) && (a.getPlayer() == this.shgameContainer.getPlayerId()) && (killed))
/*  846:     */     {
/*  847: 993 */       int deaths = this.shgameContainer.getPlayer().getDeaths();
/*  848: 994 */       deaths++;
/*  849: 995 */       this.shgameContainer.getPlayer().setDeaths(deaths);
/*  850: 996 */       if (this.shgameContainer.getMission().equalsIgnoreCase("slaugther")) {
/*  851: 998 */         checkDefeated();
/*  852:     */       }
/*  853:     */     }
/*  854:1002 */     this.worldModel.removeAgentModel(UUID);
/*  855:1003 */     this.worldView.removeAgentView(UUID);
/*  856:1004 */     this.worldController.removeAgentController(UUID);
/*  857:     */   }
/*  858:     */   
/*  859:     */   public void render(GameContainer container, StateBasedGame arg1, Graphics g)
/*  860:     */     throws SlickException
/*  861:     */   {
/*  862:1011 */     g.scale(this.scaleX, this.scaleY);
/*  863:1012 */     for (int l = 0; l < this.map.getLayerCount(); l++) {
/*  864:1015 */       if (Boolean.parseBoolean(this.map.getLayerProperty(l, "visible", "false"))) {
/*  865:1017 */         this.map.render(this.cameraPositionX, this.cameraPositionY, 0, 0, 
/*  866:1018 */           this.map.getWidth(), this.map.getHeight(), l, false);
/*  867:     */       }
/*  868:     */     }
/*  869:1022 */     g.translate(this.cameraPositionX, this.cameraPositionY);
/*  870:1023 */     this.worldView.draw(g);
/*  871:     */     
/*  872:     */ 
/*  873:1026 */     Color color = new Color(Color.darkGray);
/*  874:1027 */     g.setColor(color);
/*  875:     */   }
/*  876:     */   
/*  877:     */   protected void sendDefeat()
/*  878:     */   {
/*  879:1033 */     Finished defeat = new Finished();
/*  880:1034 */     defeat.playerId = this.shgameContainer.getPlayerId();
/*  881:1035 */     defeat.defeat = true;
/*  882:1036 */     if (!this.shgameContainer.isServer())
/*  883:     */     {
/*  884:1038 */       this.shgameContainer.send(defeat);
/*  885:1039 */       System.out.println("Victory");
/*  886:1040 */       SyncLijst end = this.shgameContainer.getOverview();
/*  887:1041 */       end.endGame = true;
/*  888:1042 */       this.shgameContainer.send(end);
/*  889:1043 */       this.shgame.enterState(6);
/*  890:     */     }
/*  891:     */     else
/*  892:     */     {
/*  893:1048 */       int team = this.shgameContainer.setDefeat(this.shgameContainer.getPlayerId());
/*  894:1049 */       if (this.shgameContainer.teamDefeat(team))
/*  895:     */       {
/*  896:1051 */         SyncLijst end = this.shgameContainer.getOverview();
/*  897:1052 */         end.endGame = true;
/*  898:1053 */         this.shgameContainer.send(end);
/*  899:1054 */         this.shgame.enterState(6);
/*  900:     */       }
/*  901:     */     }
/*  902:     */   }
/*  903:     */   
/*  904:     */   protected void sendVictory()
/*  905:     */   {
/*  906:1061 */     Finished victory = new Finished();
/*  907:1062 */     victory.playerId = this.shgameContainer.getPlayerId();
/*  908:1063 */     victory.teamId = this.shgameContainer.getTeam();
/*  909:1064 */     victory.defeat = false;
/*  910:1065 */     if (!this.shgameContainer.isServer())
/*  911:     */     {
/*  912:1067 */       this.shgameContainer.send(victory);
/*  913:     */     }
/*  914:     */     else
/*  915:     */     {
/*  916:1072 */       int team = this.shgameContainer.setDefeat(this.shgameContainer.getPlayerId());
/*  917:1073 */       if (this.shgameContainer.teamDefeat(team))
/*  918:     */       {
/*  919:1075 */         SyncLijst end = this.shgameContainer.getOverview();
/*  920:1076 */         end.endGame = true;
/*  921:1077 */         this.shgameContainer.send(end);
/*  922:1078 */         this.shgame.enterState(6);
/*  923:     */       }
/*  924:     */     }
/*  925:     */   }
/*  926:     */   
/*  927:     */   public void setGID(int GID)
/*  928:     */   {
/*  929:1085 */     this.GID = GID;
/*  930:     */   }
/*  931:     */   
/*  932:     */   public void update(GameContainer container, StateBasedGame arg1, int arg2)
/*  933:     */     throws SlickException
/*  934:     */   {
/*  935:1092 */     this.worldModel.update(arg2);
/*  936:1094 */     for (BulletModel model : this.worldModel.getBulletModels()) {
/*  937:1096 */       if (model.isFinished())
/*  938:     */       {
/*  939:1098 */         System.out.println("Remove bullet");
/*  940:1099 */         this.worldView.removeBulletView(model.getUUID());
/*  941:1100 */         this.worldModel.removeBulletModel(model.getUUID());
/*  942:     */       }
/*  943:     */     }
/*  944:     */   }
/*  945:     */   
/*  946:     */   public void tryCloseStartPosition(float x, float y)
/*  947:     */   {
/*  948:1109 */     StartPositionModel start = this.worldModel.getStartPositionOnTile(x, y);
/*  949:1110 */     if ((start != null) && (start.getFaction().equals("aliens"))) {
/*  950:1112 */       start.setOpen(false);
/*  951:     */     }
/*  952:     */   }
/*  953:     */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.states.GameState
 * JD-Core Version:    0.7.0.1
 */