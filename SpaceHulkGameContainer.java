/*   1:    */ package sh;
/*   2:    */ 
/*   3:    */ import com.esotericsoftware.kryo.Kryo;
/*   4:    */ import com.esotericsoftware.kryonet.Client;
/*   5:    */ import com.esotericsoftware.kryonet.Connection;
/*   6:    */ import com.esotericsoftware.kryonet.Listener;
/*   7:    */ import com.esotericsoftware.kryonet.Server;
/*   8:    */ import java.io.FileOutputStream;
/*   9:    */ import java.io.IOException;
/*  10:    */ import java.io.PrintStream;
/*  11:    */ import java.util.Properties;
/*  12:    */ import org.newdawn.slick.AppGameContainer;
/*  13:    */ import org.newdawn.slick.Game;
/*  14:    */ import org.newdawn.slick.SlickException;
/*  15:    */ import org.newdawn.slick.tiled.TiledMap;
/*  16:    */ import sh.multiplayer.AgentMovement;
/*  17:    */ import sh.multiplayer.AgentPlacement;
/*  18:    */ import sh.multiplayer.AgentRemove;
/*  19:    */ import sh.multiplayer.Attack;
/*  20:    */ import sh.multiplayer.BlipConvertInterupt;
/*  21:    */ import sh.multiplayer.ChatMessage;
/*  22:    */ import sh.multiplayer.EndInterrupt;
/*  23:    */ import sh.multiplayer.EndTurn;
/*  24:    */ import sh.multiplayer.Finished;
/*  25:    */ import sh.multiplayer.Interrupt;
/*  26:    */ import sh.multiplayer.InterruptRequest;
/*  27:    */ import sh.multiplayer.MapName;
/*  28:    */ import sh.multiplayer.Overwatch;
/*  29:    */ import sh.multiplayer.PlayerJoined;
/*  30:    */ import sh.multiplayer.ReadyMessage;
/*  31:    */ import sh.multiplayer.SyncLijst;
/*  32:    */ import sh.multiplayer.player.Player;
/*  33:    */ import sh.properties.GameProperties;
/*  34:    */ import sh.sound.SoundEngine;
/*  35:    */ 
/*  36:    */ public class SpaceHulkGameContainer
/*  37:    */   extends AppGameContainer
/*  38:    */ {
/*  39:    */   private String mission;
/*  40:    */   private int kills;
/*  41:    */   private Player player;
/*  42:    */   private SyncLijst overview;
/*  43:    */   private Server server;
/*  44:    */   private Client client;
/*  45:    */   private TiledMap map;
/*  46:    */   private GameProperties gproperties;
/*  47:    */   private int players;
/*  48:    */   private int connectionID;
/*  49:    */   private SoundEngine audio;
/*  50:    */   private Properties properties;
/*  51:    */   
/*  52:    */   public SpaceHulkGameContainer(Game game, GameProperties gproperties, Properties properties)
/*  53:    */     throws SlickException
/*  54:    */   {
/*  55: 78 */     super(game);
/*  56: 79 */     this.player = new Player();
/*  57: 80 */     this.gproperties = gproperties;
/*  58: 81 */     this.properties = properties;
/*  59: 82 */     System.out.println("Copyright (C) 2012 Michel Obbink");
/*  60: 83 */     System.out.println("This program comes with ABSOLUTELY NO WARRANTY");
/*  61: 84 */     System.out.println("This is free software, and you are welcome to redistribute it under certain conditions ");
/*  62: 85 */     System.out.println("which can be found in the file 'License.txt' that's distributed with Spaxe Hilk.");
/*  63:    */     
/*  64:    */ 
/*  65: 88 */     int screen_width = getScreenWidth();
/*  66: 89 */     int screen_height = getScreenHeight();
/*  67:    */     
/*  68: 91 */     super.setMusicVolume(gproperties.music / 100.0F);
/*  69: 92 */     super.setSoundVolume(gproperties.sound / 100.0F);
/*  70: 93 */     boolean playMusic = false;
/*  71: 94 */     if (gproperties.music > 0) {
/*  72: 96 */       playMusic = true;
/*  73:    */     }
/*  74: 98 */     this.audio = new SoundEngine(playMusic, gproperties.music, gproperties.sound);
/*  75:    */     
/*  76:100 */     this.audio.init();
/*  77:102 */     if ((gproperties.width > 0) && (gproperties.height > 0)) {
/*  78:104 */       setDisplayMode(gproperties.width, gproperties.height, gproperties.fullscreen);
/*  79:    */     } else {
/*  80:108 */       setDisplayMode(screen_width, screen_height, true);
/*  81:    */     }
/*  82:112 */     setShowFPS(false);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public int getScreenW()
/*  86:    */   {
/*  87:117 */     return this.gproperties.width;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setScreenW(int width)
/*  91:    */   {
/*  92:122 */     this.gproperties.width = width;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public int getScreenH()
/*  96:    */   {
/*  97:128 */     return this.gproperties.height;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setScreenH(int height)
/* 101:    */   {
/* 102:132 */     this.gproperties.height = height;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setSoundAudioVolume(float volume)
/* 106:    */   {
/* 107:136 */     this.gproperties.sound = ((int)volume * 100);
/* 108:    */     
/* 109:138 */     this.audio.setSoundVolume(volume);
/* 110:139 */     super.setSoundVolume(volume);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public float getSoundAudioVolume()
/* 114:    */   {
/* 115:147 */     return this.audio.getSoundVolume();
/* 116:    */   }
/* 117:    */   
/* 118:    */   public float getMusicAudioVolume()
/* 119:    */   {
/* 120:155 */     return this.audio.getMusicVolume();
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setMusicAudioVolume(float volume)
/* 124:    */   {
/* 125:162 */     this.gproperties.music = ((int)volume * 100);
/* 126:163 */     this.audio.setMusicVolume(volume);
/* 127:164 */     super.setMusicVolume(volume);
/* 128:    */   }
/* 129:    */   
/* 130:    */   public Player getPlayer()
/* 131:    */   {
/* 132:169 */     return this.player;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setPlayer(Player player)
/* 136:    */   {
/* 137:174 */     System.out.println("Set player");
/* 138:175 */     this.player = player;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void playSound(String sound)
/* 142:    */   {
/* 143:180 */     this.audio.playSound(sound);
/* 144:    */   }
/* 145:    */   
/* 146:    */   public String getMission()
/* 147:    */   {
/* 148:184 */     return this.mission;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setMission(String mission)
/* 152:    */   {
/* 153:189 */     this.mission = mission;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public int getKills()
/* 157:    */   {
/* 158:194 */     return this.kills;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setKills(int kills)
/* 162:    */   {
/* 163:199 */     this.kills = kills;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public Server getServer()
/* 167:    */   {
/* 168:204 */     return this.server;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setServer(Server server)
/* 172:    */   {
/* 173:209 */     this.server = server;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public Client getClient()
/* 177:    */   {
/* 178:214 */     return this.client;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setClient(Client client)
/* 182:    */   {
/* 183:219 */     this.client = client;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public String getAdres()
/* 187:    */   {
/* 188:224 */     return this.gproperties.adres;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setAdres(String adres)
/* 192:    */   {
/* 193:229 */     this.gproperties.adres = adres;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public String getName()
/* 197:    */   {
/* 198:235 */     return this.gproperties.name;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public int getColour()
/* 202:    */   {
/* 203:240 */     return this.player.getColour();
/* 204:    */   }
/* 205:    */   
/* 206:    */   public int getColour(int playerId)
/* 207:    */   {
/* 208:245 */     if (playerId < this.overview.colours.length) {
/* 209:247 */       return this.overview.colours[playerId];
/* 210:    */     }
/* 211:251 */     return 0;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void setColour(int c)
/* 215:    */   {
/* 216:258 */     this.player.setColour(c);
/* 217:    */   }
/* 218:    */   
/* 219:    */   public int getPlayerId()
/* 220:    */   {
/* 221:263 */     return this.player.getPlayerId();
/* 222:    */   }
/* 223:    */   
/* 224:    */   public void setPlayerId(int id)
/* 225:    */   {
/* 226:268 */     this.player.setPlayerId(id);
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void setName(String name)
/* 230:    */   {
/* 231:274 */     this.gproperties.name = name;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public int getPoints()
/* 235:    */   {
/* 236:279 */     return this.player.getPoints();
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void setPoints(int points)
/* 240:    */   {
/* 241:284 */     this.player.setPoints(points);
/* 242:    */   }
/* 243:    */   
/* 244:    */   public String getFaction()
/* 245:    */   {
/* 246:289 */     return this.player.getFaction();
/* 247:    */   }
/* 248:    */   
/* 249:    */   public void setFaction(String faction)
/* 250:    */   {
/* 251:294 */     this.player.setFaction(faction);
/* 252:    */   }
/* 253:    */   
/* 254:    */   public int getTeam()
/* 255:    */   {
/* 256:299 */     return this.player.getTeam();
/* 257:    */   }
/* 258:    */   
/* 259:    */   public void setTeam(int team)
/* 260:    */   {
/* 261:304 */     this.player.setTeam(team);
/* 262:    */   }
/* 263:    */   
/* 264:    */   public String getMap2()
/* 265:    */   {
/* 266:309 */     return this.gproperties.map;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public void setMap2(String map2)
/* 270:    */   {
/* 271:314 */     this.gproperties.map = map2;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public int getPlayers()
/* 275:    */   {
/* 276:319 */     return this.players;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void setServer(boolean isServer)
/* 280:    */   {
/* 281:324 */     this.gproperties.server = isServer;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public int getConnectionID()
/* 285:    */   {
/* 286:330 */     return this.connectionID;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public void setConnectionID(int connectionID)
/* 290:    */   {
/* 291:335 */     this.connectionID = connectionID;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public void setMap(TiledMap map)
/* 295:    */   {
/* 296:341 */     this.map = map;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public TiledMap getMap()
/* 300:    */   {
/* 301:346 */     return this.map;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public void StartServer()
/* 305:    */   {
/* 306:    */     try
/* 307:    */     {
/* 308:353 */       this.gproperties.server = true;
/* 309:354 */       this.server = new Server();
/* 310:355 */       this.server.start();
/* 311:356 */       this.server.bind(54555, 54777);
/* 312:357 */       RegisterObjects();
/* 313:    */     }
/* 314:    */     catch (IOException e)
/* 315:    */     {
/* 316:361 */       e.printStackTrace();
/* 317:    */     }
/* 318:    */   }
/* 319:    */   
/* 320:    */   public Client setupClient()
/* 321:    */   {
/* 322:367 */     this.client = new Client();
/* 323:368 */     this.client.start();
/* 324:369 */     RegisterObjects();
/* 325:370 */     return this.client;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public String connectClient(String ip)
/* 329:    */   {
/* 330:    */     try
/* 331:    */     {
/* 332:378 */       this.client.connect(10000, ip, 54555, 54777);
/* 333:    */     }
/* 334:    */     catch (IOException e)
/* 335:    */     {
/* 336:383 */       e.printStackTrace();
/* 337:384 */       return e.getMessage();
/* 338:    */     }
/* 339:386 */     return null;
/* 340:    */   }
/* 341:    */   
/* 342:    */   public void RegisterObjects()
/* 343:    */   {
/* 344:    */     Kryo kryo;
/* 345:    */     Kryo kryo;
/* 346:392 */     if (isServer()) {
/* 347:394 */       kryo = this.server.getKryo();
/* 348:    */     } else {
/* 349:397 */       kryo = this.client.getKryo();
/* 350:    */     }
/* 351:399 */     kryo.register(ChatMessage.class);
/* 352:400 */     kryo.register(ReadyMessage.class);
/* 353:401 */     kryo.register(EndTurn.class);
/* 354:402 */     kryo.register(AgentPlacement.class);
/* 355:403 */     kryo.register(AgentMovement.class);
/* 356:404 */     kryo.register(InterruptRequest.class);
/* 357:405 */     kryo.register(Interrupt.class);
/* 358:406 */     kryo.register(BlipConvertInterupt.class);
/* 359:407 */     kryo.register(AgentRemove.class);
/* 360:408 */     kryo.register(EndInterrupt.class);
/* 361:409 */     kryo.register(Overwatch.class);
/* 362:410 */     kryo.register(Finished.class);
/* 363:411 */     kryo.register(PlayerJoined.class);
/* 364:412 */     kryo.register(SyncLijst.class);
/* 365:413 */     kryo.register(MapName.class);
/* 366:414 */     kryo.register([Ljava.lang.String.class);
/* 367:415 */     kryo.register([I.class);
/* 368:416 */     kryo.register([Z.class);
/* 369:417 */     kryo.register(Attack.class);
/* 370:    */   }
/* 371:    */   
/* 372:    */   public boolean isServer()
/* 373:    */   {
/* 374:422 */     return this.gproperties.server;
/* 375:    */   }
/* 376:    */   
/* 377:    */   public void AddServerListener(Listener listener)
/* 378:    */   {
/* 379:427 */     this.server.addListener(listener);
/* 380:    */   }
/* 381:    */   
/* 382:    */   public void AddClientListener(Listener listener)
/* 383:    */   {
/* 384:432 */     this.client.addListener(listener);
/* 385:    */   }
/* 386:    */   
/* 387:    */   public SpaceHulkGame getGame()
/* 388:    */   {
/* 389:437 */     return (SpaceHulkGame)this.game;
/* 390:    */   }
/* 391:    */   
/* 392:    */   public int getWidth()
/* 393:    */   {
/* 394:443 */     return super.getWidth();
/* 395:    */   }
/* 396:    */   
/* 397:    */   public int getHeight()
/* 398:    */   {
/* 399:449 */     return super.getHeight();
/* 400:    */   }
/* 401:    */   
/* 402:    */   public int getScreenWidth()
/* 403:    */   {
/* 404:455 */     return super.getScreenWidth();
/* 405:    */   }
/* 406:    */   
/* 407:    */   public int getScreenHeight()
/* 408:    */   {
/* 409:461 */     return super.getScreenHeight();
/* 410:    */   }
/* 411:    */   
/* 412:    */   public void send(Object endTurn)
/* 413:    */   {
/* 414:466 */     if (isServer()) {
/* 415:468 */       this.server.sendToAllTCP(endTurn);
/* 416:    */     } else {
/* 417:471 */       this.client.sendTCP(endTurn);
/* 418:    */     }
/* 419:    */   }
/* 420:    */   
/* 421:    */   public void send(Object endTurn, Connection cons)
/* 422:    */   {
/* 423:478 */     this.server.sendToAllExceptTCP(cons.getID(), endTurn);
/* 424:    */   }
/* 425:    */   
/* 426:    */   public void setPlayers(int players)
/* 427:    */   {
/* 428:484 */     this.players = players;
/* 429:    */   }
/* 430:    */   
/* 431:    */   public void setOverview(SyncLijst lijst)
/* 432:    */   {
/* 433:490 */     this.overview = lijst;
/* 434:    */   }
/* 435:    */   
/* 436:    */   public int setDefeat(int playerNumber)
/* 437:    */   {
/* 438:496 */     this.overview.defeated[playerNumber] = true;
/* 439:497 */     return this.overview.teams[playerNumber];
/* 440:    */   }
/* 441:    */   
/* 442:    */   public void setVictory(int playerNumber, int team)
/* 443:    */   {
/* 444:502 */     for (int i = 0; i < this.overview.defeated.length; i++) {
/* 445:504 */       if (this.overview.teams[i] == team) {
/* 446:506 */         this.overview.defeated[i] = false;
/* 447:    */       } else {
/* 448:510 */         this.overview.defeated[i] = true;
/* 449:    */       }
/* 450:    */     }
/* 451:    */   }
/* 452:    */   
/* 453:    */   public boolean teamDefeat(int teamNumber)
/* 454:    */   {
/* 455:517 */     for (int i = 0; i < this.overview.teams.length; i++) {
/* 456:519 */       if (this.overview.teams[i] == teamNumber) {
/* 457:521 */         if (this.overview.defeated[i] == 0) {
/* 458:523 */           return false;
/* 459:    */         }
/* 460:    */       }
/* 461:    */     }
/* 462:527 */     return true;
/* 463:    */   }
/* 464:    */   
/* 465:    */   public SyncLijst getOverview()
/* 466:    */   {
/* 467:532 */     return this.overview;
/* 468:    */   }
/* 469:    */   
/* 470:    */   public void removeClientListener(Listener clientListener)
/* 471:    */   {
/* 472:537 */     this.client.removeListener(clientListener);
/* 473:    */   }
/* 474:    */   
/* 475:    */   public void removeServerListener(Listener serverListener)
/* 476:    */   {
/* 477:542 */     this.server.removeListener(serverListener);
/* 478:    */   }
/* 479:    */   
/* 480:    */   public void writeProperty(String key, String value)
/* 481:    */   {
/* 482:    */     try
/* 483:    */     {
/* 484:549 */       FileOutputStream MyOutputStream = new FileOutputStream(this.gproperties.propertiesfile);
/* 485:550 */       this.properties.setProperty(key, value);
/* 486:551 */       this.properties.store(MyOutputStream, "Spaxe Hilk properties file");
/* 487:552 */       MyOutputStream.close();
/* 488:    */     }
/* 489:    */     catch (Exception e)
/* 490:    */     {
/* 491:556 */       e.printStackTrace();
/* 492:    */     }
/* 493:    */   }
/* 494:    */   
/* 495:    */   public boolean getFullScreen()
/* 496:    */   {
/* 497:563 */     return this.gproperties.fullscreen;
/* 498:    */   }
/* 499:    */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.SpaceHulkGameContainer
 * JD-Core Version:    0.7.0.1
 */