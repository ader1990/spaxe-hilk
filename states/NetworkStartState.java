/*   1:    */ package sh.states;
/*   2:    */ 
/*   3:    */ import com.esotericsoftware.kryonet.Connection;
/*   4:    */ import com.esotericsoftware.kryonet.Listener;
/*   5:    */ import de.matthiasmann.twl.Button;
/*   6:    */ import de.matthiasmann.twl.ComboBox;
/*   7:    */ import de.matthiasmann.twl.GUI;
/*   8:    */ import de.matthiasmann.twl.Label;
/*   9:    */ import de.matthiasmann.twl.TextArea;
/*  10:    */ import de.matthiasmann.twl.ValueAdjusterInt;
/*  11:    */ import de.matthiasmann.twl.model.SimpleChangableListModel;
/*  12:    */ import de.matthiasmann.twl.textarea.SimpleTextAreaModel;
/*  13:    */ import java.io.PrintStream;
/*  14:    */ import org.newdawn.slick.GameContainer;
/*  15:    */ import org.newdawn.slick.Graphics;
/*  16:    */ import org.newdawn.slick.Input;
/*  17:    */ import org.newdawn.slick.SlickException;
/*  18:    */ import org.newdawn.slick.state.StateBasedGame;
/*  19:    */ import org.newdawn.slick.tiled.TiledMap;
/*  20:    */ import sh.SpaceHulkGameContainer;
/*  21:    */ import sh.gui.TWL.BasicTWLGameState;
/*  22:    */ import sh.gui.TWL.RootPane;
/*  23:    */ import sh.gui.widgets.ChatBox;
/*  24:    */ import sh.multiplayer.ChatMessage;
/*  25:    */ import sh.multiplayer.MapName;
/*  26:    */ import sh.multiplayer.PlayerJoined;
/*  27:    */ import sh.multiplayer.ReadyMessage;
/*  28:    */ import sh.multiplayer.SyncLijst;
/*  29:    */ import sh.multiplayer.player.Player;
/*  30:    */ 
/*  31:    */ public class NetworkStartState
/*  32:    */   extends BasicTWLGameState
/*  33:    */ {
/*  34: 56 */   private int stateID = -1;
/*  35:    */   private SpaceHulkGameContainer container;
/*  36:    */   private StateBasedGame sb;
/*  37:    */   private Button startGame;
/*  38:    */   private Button sync;
/*  39:    */   private Label[] races;
/*  40:    */   private String[] playerRace;
/*  41: 63 */   private boolean showChat = true;
/*  42:    */   private Label mapNameLabel;
/*  43:    */   private Label playerLabel;
/*  44:    */   private Label factionLabel;
/*  45:    */   private Label pointsLabel;
/*  46:    */   private Label teamLabel;
/*  47:    */   private Label colourLabel;
/*  48:    */   private ChatBox chatbox;
/*  49:    */   private ComboBox<String>[] playerNames;
/*  50:    */   private ComboBox<String>[] playerColours;
/*  51: 74 */   private SimpleChangableListModel<String> playersModel = new SimpleChangableListModel();
/*  52:    */   private ComboBox<String> objectives;
/*  53: 77 */   private SimpleChangableListModel<String> objectivesModel = new SimpleChangableListModel();
/*  54: 78 */   private SimpleChangableListModel<String> colourModel = new SimpleChangableListModel();
/*  55:    */   private TextArea objectiveArea;
/*  56:    */   private ValueAdjusterInt kills;
/*  57:    */   private ValueAdjusterInt[] points;
/*  58:    */   private ValueAdjusterInt[] teams;
/*  59:    */   private String mapName;
/*  60:    */   private String name;
/*  61: 89 */   private int aliens = 0;
/*  62: 90 */   private int marines = 0;
/*  63: 91 */   private int playerAmount = 1;
/*  64:    */   private int players;
/*  65: 93 */   private int playerID = -1;
/*  66: 94 */   private int connectionID = -1;
/*  67:    */   private JoinClientListener clientListener;
/*  68:    */   private JoinServerListener serverListener;
/*  69: 97 */   boolean mapGoal = false;
/*  70:    */   String mapObjectiveText;
/*  71:    */   private boolean hasStarted;
/*  72:    */   
/*  73:    */   public void enter(GameContainer cont, StateBasedGame game)
/*  74:    */     throws SlickException
/*  75:    */   {
/*  76:107 */     this.container = ((SpaceHulkGameContainer)cont);
/*  77:108 */     if (this.container.isServer())
/*  78:    */     {
/*  79:110 */       this.serverListener = new JoinServerListener();
/*  80:111 */       this.container.AddServerListener(this.serverListener);
/*  81:    */     }
/*  82:    */     else
/*  83:    */     {
/*  84:115 */       this.clientListener = new JoinClientListener();
/*  85:116 */       this.container.AddClientListener(this.clientListener);
/*  86:    */     }
/*  87:119 */     TiledMap map = new TiledMap(this.container.getMap2());
/*  88:120 */     this.container.setMap(map);
/*  89:121 */     this.mapName = map.getMapProperty("name", "No name");
/*  90:122 */     this.name = this.container.getName();
/*  91:123 */     this.mapObjectiveText = map.getMapProperty("goal", "");
/*  92:124 */     if (this.mapObjectiveText != "") {
/*  93:126 */       this.mapGoal = true;
/*  94:    */     }
/*  95:129 */     this.players = Integer.parseInt(map.getMapProperty("players", "2"));
/*  96:130 */     this.playerRace = new String[this.players];
/*  97:131 */     for (int i = 0; i < this.players; i++)
/*  98:    */     {
/*  99:133 */       String faction = map.getMapProperty("player" + i, "marines");
/* 100:134 */       if (faction.equalsIgnoreCase("marines")) {
/* 101:136 */         this.playerRace[i] = "Marines";
/* 102:    */       } else {
/* 103:140 */         this.playerRace[i] = "Aliens";
/* 104:    */       }
/* 105:    */     }
/* 106:144 */     if (this.container.isServer())
/* 107:    */     {
/* 108:146 */       this.playersModel.addElement(this.container.getName());
/* 109:147 */       this.connectionID = 0;
/* 110:    */     }
/* 111:149 */     super.enter(this.container, game);
/* 112:151 */     if (!this.container.isServer()) {
/* 113:153 */       this.container.send(new ReadyMessage());
/* 114:    */     }
/* 115:    */   }
/* 116:    */   
/* 117:    */   public NetworkStartState(int stateID)
/* 118:    */   {
/* 119:159 */     this.stateID = stateID;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public int getID()
/* 123:    */   {
/* 124:165 */     return this.stateID;
/* 125:    */   }
/* 126:    */   
/* 127:    */   protected RootPane createRootPane()
/* 128:    */   {
/* 129:171 */     RootPane rp = super.createRootPane();
/* 130:172 */     rp.setSize(this.container.getScreenWidth(), this.container.getScreenHeight());
/* 131:173 */     rp.setTheme("");
/* 132:    */     
/* 133:175 */     this.mapNameLabel = new Label();
/* 134:176 */     this.mapNameLabel.setText("Map: " + this.mapName);
/* 135:177 */     this.playerLabel = new Label();
/* 136:178 */     this.playerLabel.setText("Players:");
/* 137:179 */     this.factionLabel = new Label();
/* 138:180 */     this.factionLabel.setText("Faction:");
/* 139:181 */     this.colourLabel = new Label();
/* 140:182 */     this.colourLabel.setText("Colour:");
/* 141:183 */     this.pointsLabel = new Label();
/* 142:184 */     this.pointsLabel.setText("Points to spend:");
/* 143:185 */     this.teamLabel = new Label();
/* 144:186 */     this.teamLabel.setText("Team:");
/* 145:187 */     this.objectiveArea = new TextArea();
/* 146:188 */     this.chatbox = new ChatBox(this.container);
/* 147:189 */     this.kills = new ValueAdjusterInt();
/* 148:    */     
/* 149:191 */     this.races = new Label[this.players];
/* 150:192 */     this.playerNames = new ComboBox[this.players];
/* 151:194 */     if (this.mapGoal) {
/* 152:196 */       this.objectivesModel.addElements(new String[] { "Mission" });
/* 153:    */     }
/* 154:198 */     this.objectivesModel.addElements(new String[] { "Slaugther" });
/* 155:199 */     this.objectives = new ComboBox(this.objectivesModel);
/* 156:    */     
/* 157:201 */     this.objectives.addCallback(new Runnable()
/* 158:    */     {
/* 159:    */       public void run()
/* 160:    */       {
/* 161:206 */         System.out.println("objective changed");
/* 162:207 */         String obj = (String)NetworkStartState.this.objectivesModel.getEntry(NetworkStartState.this.objectives.getSelected());
/* 163:208 */         String text = "";
/* 164:209 */         if (obj.equalsIgnoreCase("Mission"))
/* 165:    */         {
/* 166:211 */           NetworkStartState.this.kills.setVisible(false);
/* 167:212 */           text = NetworkStartState.this.mapObjectiveText;
/* 168:    */         }
/* 169:214 */         else if (obj.equalsIgnoreCase("Slaugther"))
/* 170:    */         {
/* 171:216 */           NetworkStartState.this.kills.setVisible(true);
/* 172:217 */           text = "Kill " + NetworkStartState.this.kills.getValue() + " aliens to win.";
/* 173:    */         }
/* 174:219 */         SimpleTextAreaModel textModel = new SimpleTextAreaModel();
/* 175:220 */         textModel.setText(text);
/* 176:221 */         NetworkStartState.this.objectiveArea.setModel(textModel);
/* 177:    */       }
/* 178:227 */     });
/* 179:228 */     this.playerColours = new ComboBox[this.players];
/* 180:    */     
/* 181:230 */     this.colourModel.addElement("Red");
/* 182:231 */     this.colourModel.addElement("Cyan");
/* 183:232 */     this.colourModel.addElement("Blue");
/* 184:233 */     this.colourModel.addElement("Yellow");
/* 185:234 */     this.colourModel.addElement("Purple");
/* 186:235 */     this.colourModel.addElement("Green");
/* 187:237 */     for (int i = 0; i < this.playerColours.length; i++)
/* 188:    */     {
/* 189:239 */       this.playerColours[i] = new ComboBox(this.colourModel);
/* 190:240 */       rp.add(this.playerColours[i]);
/* 191:    */     }
/* 192:244 */     this.points = new ValueAdjusterInt[this.players];
/* 193:245 */     this.teams = new ValueAdjusterInt[this.players];
/* 194:246 */     System.out.println(this.players);
/* 195:247 */     this.startGame = new Button("Start game");
/* 196:248 */     this.startGame.setTheme("button");
/* 197:249 */     this.startGame.addCallback(new Runnable()
/* 198:    */     {
/* 199:    */       public void run()
/* 200:    */       {
/* 201:254 */         System.out.println(NetworkStartState.this.playerAmount + " " + NetworkStartState.this.players);
/* 202:255 */         SyncLijst lijst = NetworkStartState.this.newSyncLijst(true);
/* 203:256 */         System.out.println("Lijst objective" + lijst.objective);
/* 204:257 */         if ((NetworkStartState.this.playerAmount == NetworkStartState.this.players) && (NetworkStartState.this.container.isServer()) && (lijst.objective >= 0))
/* 205:    */         {
/* 206:259 */           System.out.println("ready");
/* 207:261 */           for (int i = 0; i < lijst.places.length; i++) {
/* 208:263 */             if (lijst.places[i] >= 0) {
/* 209:265 */               if (lijst.places[i] == NetworkStartState.this.container.getConnectionID())
/* 210:    */               {
/* 211:267 */                 NetworkStartState.this.playerID = i;
/* 212:268 */                 NetworkStartState.this.container.setPlayerId(i);
/* 213:    */               }
/* 214:    */             }
/* 215:    */           }
/* 216:273 */           NetworkStartState.this.container.playSound("click");
/* 217:274 */           lijst.defeated = new boolean[NetworkStartState.this.players];
/* 218:275 */           NetworkStartState.this.container.send(lijst);
/* 219:276 */           int playerid = NetworkStartState.this.playerID;
/* 220:277 */           int team = NetworkStartState.this.teams[playerid].getValue();
/* 221:278 */           String faction = NetworkStartState.this.races[playerid].getText();
/* 222:279 */           int point = NetworkStartState.this.points[playerid].getValue();
/* 223:280 */           int colour = NetworkStartState.this.playerColours[playerid].getSelected();
/* 224:281 */           int killCount = NetworkStartState.this.kills.getValue();
/* 225:282 */           String mission = (String)NetworkStartState.this.objectivesModel.getEntry(NetworkStartState.this.objectives.getSelected());
/* 226:283 */           Player player = new Player(playerid, 0, faction, team, point, 0);
/* 227:284 */           System.out.println("Start");
/* 228:285 */           NetworkStartState.this.container.setPlayer(player);
/* 229:    */           
/* 230:287 */           NetworkStartState.this.container.setPlayerId(playerid);
/* 231:288 */           NetworkStartState.this.container.setTeam(team);
/* 232:289 */           NetworkStartState.this.container.setFaction(faction);
/* 233:290 */           NetworkStartState.this.container.setPoints(point);
/* 234:291 */           NetworkStartState.this.container.setColour(colour);
/* 235:292 */           NetworkStartState.this.container.setPlayers(NetworkStartState.this.players);
/* 236:293 */           NetworkStartState.this.container.setKills(killCount);
/* 237:294 */           NetworkStartState.this.container.setMission(mission);
/* 238:295 */           NetworkStartState.this.container.setOverview(lijst);
/* 239:296 */           NetworkStartState.this.hasStarted = true;
/* 240:    */         }
/* 241:    */       }
/* 242:302 */     });
/* 243:303 */     this.sync = new Button("Synchronize");
/* 244:304 */     this.sync.setTheme("button");
/* 245:305 */     this.sync.addCallback(new Runnable()
/* 246:    */     {
/* 247:    */       public void run()
/* 248:    */       {
/* 249:309 */         NetworkStartState.this.container.playSound("click");
/* 250:310 */         NetworkStartState.this.container.send(NetworkStartState.this.newSyncLijst(false));
/* 251:    */       }
/* 252:    */     });
/* 253:315 */     for (int i = 0; i < this.playerNames.length; i++)
/* 254:    */     {
/* 255:317 */       this.playerNames[i] = new ComboBox(this.playersModel);
/* 256:318 */       rp.add(this.playerNames[i]);
/* 257:    */     }
/* 258:321 */     for (int i = 0; i < this.races.length; i++)
/* 259:    */     {
/* 260:323 */       this.races[i] = new Label();
/* 261:324 */       this.races[i].setText(this.playerRace[i]);
/* 262:    */       
/* 263:326 */       rp.add(this.races[i]);
/* 264:    */     }
/* 265:330 */     for (int i = 0; i < this.points.length; i++)
/* 266:    */     {
/* 267:332 */       this.points[i] = new ValueAdjusterInt();
/* 268:    */       
/* 269:334 */       rp.add(this.points[i]);
/* 270:    */     }
/* 271:337 */     for (int i = 0; i < this.teams.length; i++)
/* 272:    */     {
/* 273:339 */       this.teams[i] = new ValueAdjusterInt();
/* 274:    */       
/* 275:341 */       rp.add(this.teams[i]);
/* 276:    */     }
/* 277:344 */     rp.add(this.mapNameLabel);
/* 278:345 */     rp.add(this.playerLabel);
/* 279:346 */     rp.add(this.colourLabel);
/* 280:347 */     rp.add(this.pointsLabel);
/* 281:348 */     rp.add(this.teamLabel);
/* 282:349 */     rp.add(this.factionLabel);
/* 283:350 */     rp.add(this.sync);
/* 284:351 */     rp.add(this.chatbox);
/* 285:352 */     if (this.container.isServer()) {
/* 286:354 */       rp.add(this.startGame);
/* 287:    */     }
/* 288:356 */     rp.add(this.objectives);
/* 289:357 */     rp.add(this.objectiveArea);
/* 290:358 */     rp.add(this.kills);
/* 291:359 */     return rp;
/* 292:    */   }
/* 293:    */   
/* 294:    */   protected void layoutRootPane()
/* 295:    */   {
/* 296:366 */     int middleX = this.container.getWidth() / 2;
/* 297:367 */     int middleY = this.container.getHeight() / 2;
/* 298:    */     
/* 299:369 */     int maxX = this.container.getWidth();
/* 300:370 */     int maxY = this.container.getHeight();
/* 301:    */     
/* 302:    */ 
/* 303:373 */     this.mapNameLabel.setPosition(20, 20);
/* 304:374 */     this.mapNameLabel.setSize(100, 20);
/* 305:    */     
/* 306:376 */     this.playerLabel.setPosition(100, 100);
/* 307:377 */     this.playerLabel.setSize(100, 20);
/* 308:378 */     for (int i = 0; i < this.playerNames.length; i++)
/* 309:    */     {
/* 310:380 */       this.playerNames[i].setPosition(100, 140 + 50 * i);
/* 311:381 */       this.playerNames[i].setSize(100, 20);
/* 312:    */     }
/* 313:384 */     this.factionLabel.setPosition(250, 100);
/* 314:385 */     this.factionLabel.setSize(100, 20);
/* 315:386 */     for (int i = 0; i < this.races.length; i++)
/* 316:    */     {
/* 317:388 */       this.races[i].setPosition(250, 140 + 50 * i);
/* 318:389 */       this.races[i].setSize(100, 20);
/* 319:    */     }
/* 320:392 */     this.colourLabel.setPosition(350, 100);
/* 321:393 */     this.colourLabel.setSize(100, 20);
/* 322:394 */     for (int i = 0; i < this.playerColours.length; i++)
/* 323:    */     {
/* 324:396 */       this.playerColours[i].setPosition(350, 140 + 50 * i);
/* 325:397 */       this.playerColours[i].setSize(75, 20);
/* 326:    */     }
/* 327:400 */     this.pointsLabel.setPosition(450, 100);
/* 328:401 */     this.pointsLabel.setSize(100, 20);
/* 329:402 */     for (int i = 0; i < this.points.length; i++)
/* 330:    */     {
/* 331:404 */       this.points[i].setPosition(450, 140 + 50 * i);
/* 332:405 */       this.points[i].setSize(150, 25);
/* 333:    */     }
/* 334:408 */     this.teamLabel.setPosition(650, 100);
/* 335:409 */     this.teamLabel.setSize(100, 20);
/* 336:410 */     for (int i = 0; i < this.teams.length; i++)
/* 337:    */     {
/* 338:412 */       this.teams[i].setPosition(650, 140 + 50 * i);
/* 339:413 */       this.teams[i].setSize(150, 25);
/* 340:    */     }
/* 341:416 */     this.objectives.setSize(100, 20);
/* 342:417 */     this.objectives.setPosition(130, 20);
/* 343:418 */     this.kills.setSize(130, 25);
/* 344:419 */     this.kills.setPosition(115, 50);
/* 345:    */     
/* 346:421 */     this.objectiveArea.setSize(middleX, 80);
/* 347:422 */     this.objectiveArea.setPosition(245, 20);
/* 348:    */     
/* 349:424 */     this.startGame.setSize(100, 20);
/* 350:425 */     this.startGame.setPosition(maxX - 100, 20);
/* 351:    */     
/* 352:427 */     this.sync.setSize(100, 20);
/* 353:428 */     this.sync.setPosition(middleX + middleX / 2, 20);
/* 354:429 */     this.chatbox.setPosition(0, maxY - 100);
/* 355:430 */     this.chatbox.setSize(maxX - 100, 100);
/* 356:    */   }
/* 357:    */   
/* 358:    */   public void init(GameContainer container, StateBasedGame sb)
/* 359:    */     throws SlickException
/* 360:    */   {
/* 361:437 */     this.container = ((SpaceHulkGameContainer)container);
/* 362:438 */     this.sb = sb;
/* 363:    */   }
/* 364:    */   
/* 365:    */   public SyncLijst newSyncLijst(boolean start)
/* 366:    */   {
/* 367:444 */     SyncLijst lijst = new SyncLijst();
/* 368:445 */     lijst.startGame = start;
/* 369:446 */     String[] namen = new String[this.playersModel.getNumEntries()];
/* 370:448 */     for (int i = 0; i < namen.length; i++) {
/* 371:450 */       namen[i] = ((String)this.playersModel.getEntry(i));
/* 372:    */     }
/* 373:453 */     lijst.namen = namen;
/* 374:    */     
/* 375:455 */     int[] placesValues = new int[this.players];
/* 376:456 */     int[] connectionIDs = new int[this.players];
/* 377:457 */     for (int i = 0; i < placesValues.length; i++) {
/* 378:459 */       placesValues[i] = this.playerNames[i].getSelected();
/* 379:    */     }
/* 380:462 */     lijst.places = placesValues;
/* 381:    */     
/* 382:464 */     int[] colours = new int[this.players];
/* 383:465 */     for (int i = 0; i < colours.length; i++) {
/* 384:467 */       colours[i] = this.playerColours[i].getSelected();
/* 385:    */     }
/* 386:470 */     lijst.colours = colours;
/* 387:    */     
/* 388:472 */     int[] values = new int[this.players];
/* 389:473 */     for (int i = 0; i < values.length; i++) {
/* 390:475 */       values[i] = this.points[i].getValue();
/* 391:    */     }
/* 392:478 */     lijst.points = values;
/* 393:    */     
/* 394:480 */     int[] teamValues = new int[this.players];
/* 395:481 */     for (int i = 0; i < this.teams.length; i++) {
/* 396:483 */       teamValues[i] = this.teams[i].getValue();
/* 397:    */     }
/* 398:485 */     lijst.teams = teamValues;
/* 399:    */     
/* 400:487 */     lijst.kills = this.kills.getValue();
/* 401:    */     
/* 402:489 */     lijst.objective = this.objectives.getSelected();
/* 403:    */     
/* 404:    */ 
/* 405:    */ 
/* 406:493 */     return lijst;
/* 407:    */   }
/* 408:    */   
/* 409:    */   public void verwerkSyncLijst(SyncLijst lijst)
/* 410:    */   {
/* 411:498 */     this.playersModel.clear();
/* 412:499 */     this.playersModel.addElements(lijst.namen);
/* 413:503 */     for (int i = 0; i < lijst.places.length; i++) {
/* 414:505 */       if (lijst.places[i] >= 0)
/* 415:    */       {
/* 416:507 */         if (lijst.places[i] == this.container.getConnectionID())
/* 417:    */         {
/* 418:509 */           this.playerID = i;
/* 419:510 */           this.container.setPlayerId(i);
/* 420:    */         }
/* 421:512 */         this.playerNames[i].setSelected(lijst.places[i]);
/* 422:    */       }
/* 423:    */     }
/* 424:516 */     for (int i = 0; i < lijst.points.length; i++) {
/* 425:518 */       this.points[i].setValue(lijst.points[i]);
/* 426:    */     }
/* 427:521 */     for (int i = 0; i < lijst.colours.length; i++) {
/* 428:523 */       this.playerColours[i].setSelected(lijst.colours[i]);
/* 429:    */     }
/* 430:526 */     for (int i = 0; i < lijst.teams.length; i++) {
/* 431:528 */       this.teams[i].setValue(lijst.teams[i]);
/* 432:    */     }
/* 433:531 */     this.kills.setValue(lijst.kills);
/* 434:    */     
/* 435:533 */     this.objectives.setSelected(lijst.objective);
/* 436:534 */     if (this.objectives.getSelected() > -1)
/* 437:    */     {
/* 438:536 */       String obj = (String)this.objectivesModel.getEntry(this.objectives.getSelected());
/* 439:537 */       String text = "";
/* 440:538 */       if (obj.equalsIgnoreCase("Mission"))
/* 441:    */       {
/* 442:540 */         this.kills.setVisible(false);
/* 443:541 */         text = this.mapObjectiveText;
/* 444:    */       }
/* 445:543 */       else if (obj.equalsIgnoreCase("Slaugther"))
/* 446:    */       {
/* 447:545 */         this.kills.setVisible(true);
/* 448:546 */         text = "Kill " + this.kills.getValue() + " aliens to win.";
/* 449:    */       }
/* 450:548 */       SimpleTextAreaModel textModel = new SimpleTextAreaModel();
/* 451:549 */       textModel.setText(text);
/* 452:550 */       this.objectiveArea.setModel(textModel);
/* 453:    */     }
/* 454:554 */     if (lijst.startGame)
/* 455:    */     {
/* 456:556 */       int playerid = this.playerID;
/* 457:557 */       int team = this.teams[playerid].getValue();
/* 458:558 */       String faction = this.races[playerid].getText();
/* 459:559 */       int point = this.points[playerid].getValue();
/* 460:560 */       int killCount = this.kills.getValue();
/* 461:561 */       String mission = (String)this.objectivesModel.getEntry(this.objectives.getSelected());
/* 462:562 */       int colour = this.playerColours[playerid].getSelected();
/* 463:563 */       Player player = new Player(playerid, 0, faction, team, point, 0);
/* 464:564 */       this.container.setPlayer(player);
/* 465:565 */       this.container.setOverview(lijst);
/* 466:566 */       this.container.setPlayerId(playerid);
/* 467:567 */       this.container.setTeam(team);
/* 468:568 */       this.container.setColour(colour);
/* 469:569 */       this.container.setFaction(faction);
/* 470:570 */       this.container.setPoints(point);
/* 471:571 */       this.container.setPlayers(this.players);
/* 472:572 */       this.container.setKills(killCount);
/* 473:573 */       this.container.setMission(mission);
/* 474:574 */       this.container.setOverview(lijst);
/* 475:575 */       this.hasStarted = true;
/* 476:    */     }
/* 477:    */   }
/* 478:    */   
/* 479:    */   public synchronized void addRowThreadsafe(final ChatMessage msg)
/* 480:    */   {
/* 481:582 */     GUI gui = getRootPane().getGUI();
/* 482:583 */     if (gui != null) {
/* 483:585 */       gui.invokeLater(new Runnable()
/* 484:    */       {
/* 485:    */         public void run()
/* 486:    */         {
/* 487:589 */           NetworkStartState.this.chatbox.appendRow(msg.name, msg.text);
/* 488:    */         }
/* 489:    */       });
/* 490:    */     }
/* 491:    */   }
/* 492:    */   
/* 493:    */   public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
/* 494:    */     throws SlickException
/* 495:    */   {}
/* 496:    */   
/* 497:    */   public class JoinServerListener
/* 498:    */     extends Listener
/* 499:    */   {
/* 500:    */     public JoinServerListener() {}
/* 501:    */     
/* 502:    */     public void connected(Connection arg0)
/* 503:    */     {
/* 504:602 */       super.connected(arg0);
/* 505:    */     }
/* 506:    */     
/* 507:    */     public void received(Connection connection, Object object)
/* 508:    */     {
/* 509:608 */       if ((object instanceof PlayerJoined))
/* 510:    */       {
/* 511:610 */         PlayerJoined join = (PlayerJoined)object;
/* 512:    */         
/* 513:612 */         NetworkStartState.this.playersModel.addElement(join.naam);
/* 514:613 */         MapName map = new MapName();
/* 515:614 */         map.mapName = NetworkStartState.this.container.getMap2();
/* 516:615 */         map.connectionID = NetworkStartState.this.playerAmount;
/* 517:616 */         System.out.println("Sending map " + map.mapName);
/* 518:617 */         connection.sendTCP(map);
/* 519:618 */         NetworkStartState.this.playerAmount += 1;
/* 520:    */       }
/* 521:621 */       if ((object instanceof ReadyMessage)) {
/* 522:623 */         connection.sendTCP(NetworkStartState.this.newSyncLijst(false));
/* 523:    */       }
/* 524:625 */       if ((object instanceof SyncLijst))
/* 525:    */       {
/* 526:627 */         NetworkStartState.this.verwerkSyncLijst((SyncLijst)object);
/* 527:628 */         NetworkStartState.this.container.send(object);
/* 528:    */       }
/* 529:630 */       if ((object instanceof ChatMessage))
/* 530:    */       {
/* 531:632 */         ChatMessage message = (ChatMessage)object;
/* 532:633 */         NetworkStartState.this.container.send(message, connection);
/* 533:634 */         NetworkStartState.this.addRowThreadsafe(message);
/* 534:    */       }
/* 535:    */     }
/* 536:    */   }
/* 537:    */   
/* 538:    */   public class JoinClientListener
/* 539:    */     extends Listener
/* 540:    */   {
/* 541:    */     public JoinClientListener() {}
/* 542:    */     
/* 543:    */     public void received(Connection connection, Object object)
/* 544:    */     {
/* 545:644 */       if ((object instanceof ReadyMessage)) {
/* 546:646 */         System.out.println("Ready");
/* 547:    */       }
/* 548:650 */       if ((object instanceof SyncLijst)) {
/* 549:652 */         NetworkStartState.this.verwerkSyncLijst((SyncLijst)object);
/* 550:    */       }
/* 551:654 */       if ((object instanceof MapName))
/* 552:    */       {
/* 553:656 */         MapName map = (MapName)object;
/* 554:    */         
/* 555:658 */         NetworkStartState.this.container.setMap2(map.mapName);
/* 556:    */       }
/* 557:661 */       if ((object instanceof ChatMessage))
/* 558:    */       {
/* 559:663 */         ChatMessage message = (ChatMessage)object;
/* 560:    */         
/* 561:665 */         NetworkStartState.this.addRowThreadsafe(message);
/* 562:    */       }
/* 563:    */     }
/* 564:    */   }
/* 565:    */   
/* 566:    */   public void update(GameContainer cont, StateBasedGame arg1, int arg2)
/* 567:    */     throws SlickException
/* 568:    */   {
/* 569:685 */     if (this.hasStarted)
/* 570:    */     {
/* 571:687 */       if (this.container.isServer()) {
/* 572:689 */         this.container.removeServerListener(this.serverListener);
/* 573:    */       } else {
/* 574:693 */         this.container.removeClientListener(this.clientListener);
/* 575:    */       }
/* 576:696 */       if (this.container.getFaction().equalsIgnoreCase("marines")) {
/* 577:698 */         this.sb.enterState(2);
/* 578:    */       } else {
/* 579:702 */         this.sb.enterState(3);
/* 580:    */       }
/* 581:    */     }
/* 582:705 */     if (this.container.getInput().isKeyDown(15))
/* 583:    */     {
/* 584:707 */       this.showChat = (!this.showChat);
/* 585:708 */       this.chatbox.setVisible(this.showChat);
/* 586:    */     }
/* 587:    */   }
/* 588:    */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.states.NetworkStartState
 * JD-Core Version:    0.7.0.1
 */