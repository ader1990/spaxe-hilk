/*   1:    */ package sh.states;
/*   2:    */ 
/*   3:    */ import com.esotericsoftware.kryonet.Connection;
/*   4:    */ import com.esotericsoftware.kryonet.Listener;
/*   5:    */ import com.esotericsoftware.kryonet.Server;
/*   6:    */ import de.matthiasmann.twl.GUI;
/*   7:    */ import de.matthiasmann.twl.Label;
/*   8:    */ import java.io.PrintStream;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.Iterator;
/*  11:    */ import java.util.LinkedList;
/*  12:    */ import java.util.concurrent.ConcurrentLinkedQueue;
/*  13:    */ import org.newdawn.slick.GameContainer;
/*  14:    */ import org.newdawn.slick.Graphics;
/*  15:    */ import org.newdawn.slick.Input;
/*  16:    */ import org.newdawn.slick.SlickException;
/*  17:    */ import org.newdawn.slick.state.StateBasedGame;
/*  18:    */ import sh.SpaceHulkGame;
/*  19:    */ import sh.SpaceHulkGameContainer;
/*  20:    */ import sh.agent.AgentModel;
/*  21:    */ import sh.agent.AgentView;
/*  22:    */ import sh.agent.aliens.BlipModel;
/*  23:    */ import sh.agent.door.DoorModel;
/*  24:    */ import sh.agent.marines.MarineModel;
/*  25:    */ import sh.agent.movement.PathAgentModel;
/*  26:    */ import sh.agent.weapons.Weapon;
/*  27:    */ import sh.gameobject.GameModel;
/*  28:    */ import sh.gameobject.startposition.StartPositionModel;
/*  29:    */ import sh.gui.TWL.RootPane;
/*  30:    */ import sh.gui.widgets.ChatBox;
/*  31:    */ import sh.multiplayer.AgentMovement;
/*  32:    */ import sh.multiplayer.AgentPlacement;
/*  33:    */ import sh.multiplayer.AgentRemove;
/*  34:    */ import sh.multiplayer.Attack;
/*  35:    */ import sh.multiplayer.BlipConvertInterupt;
/*  36:    */ import sh.multiplayer.ChatMessage;
/*  37:    */ import sh.multiplayer.EndInterrupt;
/*  38:    */ import sh.multiplayer.EndTurn;
/*  39:    */ import sh.multiplayer.Finished;
/*  40:    */ import sh.multiplayer.Interrupt;
/*  41:    */ import sh.multiplayer.InterruptRequest;
/*  42:    */ import sh.multiplayer.Overwatch;
/*  43:    */ import sh.multiplayer.SyncLijst;
/*  44:    */ import sh.world.shworld.SpaceHulkWorldModel;
/*  45:    */ import sh.world.shworld.SpaceHulkWorldView;
/*  46:    */ 
/*  47:    */ public class GameplayState
/*  48:    */   extends GameGuiState
/*  49:    */ {
/*  50:    */   PathAgentModel selectedAgent;
/*  51:    */   AgentModel infoAgent;
/*  52: 46 */   ConcurrentLinkedQueue<AgentMovement> movementIncomingList = new ConcurrentLinkedQueue();
/*  53: 47 */   LinkedList<AgentPlacement> agentPlacement = new LinkedList();
/*  54: 48 */   LinkedList<AgentRemove> removeList = new LinkedList();
/*  55: 49 */   LinkedList<AgentMovement> movementOutgoingList = new LinkedList();
/*  56: 50 */   protected ArrayList<Integer> fires = new ArrayList();
/*  57:    */   public Weapon selectedWeapon;
/*  58:    */   public boolean attackinGround;
/*  59:    */   protected GameClientListener gameclientListener;
/*  60:    */   protected GameServerListener gameserverListener;
/*  61:    */   
/*  62:    */   protected static enum TurnState
/*  63:    */   {
/*  64: 57 */     YOUR,  INTERRUPT,  OTHER;
/*  65:    */   }
/*  66:    */   
/*  67:    */   protected static enum AgentState
/*  68:    */   {
/*  69: 62 */     NOTHING,  TURNING,  MOVING,  OTHER;
/*  70:    */   }
/*  71:    */   
/*  72: 65 */   protected TurnState turnState = TurnState.OTHER;
/*  73: 66 */   public float turned = 0.0F;
/*  74: 67 */   public BlipModel alienPlaceing = null;
/*  75: 68 */   AgentState agentState = AgentState.NOTHING;
/*  76: 69 */   int agentsPlaced = 0;
/*  77: 70 */   int aliensToPlace = 0;
/*  78: 71 */   int CP = 0;
/*  79:    */   int CPBonus;
/*  80: 73 */   boolean interrupted = false;
/*  81: 75 */   int interruptingPlayer = 0;
/*  82:    */   
/*  83:    */   public GameplayState(int stateID)
/*  84:    */   {
/*  85: 79 */     super(stateID);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void render(GameContainer container, StateBasedGame arg1, Graphics g)
/*  89:    */     throws SlickException
/*  90:    */   {
/*  91: 87 */     super.render(container, arg1, g);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void enter(GameContainer container, StateBasedGame game)
/*  95:    */     throws SlickException
/*  96:    */   {
/*  97: 96 */     super.enter(container, game);
/*  98: 97 */     SpaceHulkGameContainer cont = (SpaceHulkGameContainer)container;
/*  99: 99 */     if (cont.isServer())
/* 100:    */     {
/* 101:101 */       this.gameserverListener = new GameServerListener(cont.getServer());
/* 102:102 */       cont.AddServerListener(this.gameserverListener);
/* 103:    */     }
/* 104:    */     else
/* 105:    */     {
/* 106:106 */       this.gameclientListener = new GameClientListener();
/* 107:107 */       cont.AddClientListener(this.gameclientListener);
/* 108:    */     }
/* 109:109 */     float[] position = this.worldModel.getAbsolutePosition(this.worldModel.GetStartPosition(this.shgameContainer.getPlayerId()));
/* 110:    */     
/* 111:111 */     int deltax = 0 - (int)position[0];
/* 112:112 */     int deltay = 0 - (int)position[1];
/* 113:    */     
/* 114:    */ 
/* 115:115 */     this.cameraPositionX += (int)(deltax * (1.0F / this.scaleX) + this.shgameContainer.getWidth() / 2);
/* 116:116 */     this.cameraPositionY += (int)(deltay * (1.0F / this.scaleY) + this.shgameContainer.getHeight() / 2);
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void update(GameContainer container, StateBasedGame arg1, int arg2)
/* 120:    */     throws SlickException
/* 121:    */   {
/* 122:125 */     super.update(container, arg1, arg2);
/* 123:126 */     Input input = container.getInput();
/* 124:127 */     SpaceHulkGameContainer cont = (SpaceHulkGameContainer)container;
/* 125:129 */     if (this.agentPlacement != null)
/* 126:    */     {
/* 127:131 */       Iterator<AgentPlacement> iter = this.agentPlacement.iterator();
/* 128:132 */       while (iter.hasNext())
/* 129:    */       {
/* 130:134 */         AgentPlacement placement = (AgentPlacement)iter.next();
/* 131:135 */         cont.send(placement);
/* 132:136 */         iter.remove();
/* 133:    */       }
/* 134:138 */       this.agentPlacement.clear();
/* 135:    */     }
/* 136:140 */     if (this.removeList != null)
/* 137:    */     {
/* 138:142 */       Iterator<AgentRemove> iter = this.removeList.iterator();
/* 139:143 */       while (iter.hasNext())
/* 140:    */       {
/* 141:145 */         AgentRemove remove = (AgentRemove)iter.next();
/* 142:146 */         cont.send(remove);
/* 143:    */       }
/* 144:148 */       this.removeList.clear();
/* 145:    */     }
/* 146:150 */     if (this.movementOutgoingList != null)
/* 147:    */     {
/* 148:152 */       Iterator<AgentMovement> iter = this.movementOutgoingList.iterator();
/* 149:153 */       while (iter.hasNext())
/* 150:    */       {
/* 151:155 */         AgentMovement move = (AgentMovement)iter.next();
/* 152:156 */         cont.send(move);
/* 153:    */       }
/* 154:158 */       this.movementOutgoingList.clear();
/* 155:    */     }
/* 156:161 */     if (this.movementIncomingList.size() > 0) {
/* 157:163 */       processMovement(cont);
/* 158:    */     }
/* 159:167 */     if (this.gameEnds)
/* 160:    */     {
/* 161:169 */       if (this.shgameContainer.isServer()) {
/* 162:171 */         this.shgameContainer.removeServerListener(this.gameserverListener);
/* 163:    */       } else {
/* 164:175 */         this.shgameContainer.removeClientListener(this.gameclientListener);
/* 165:    */       }
/* 166:177 */       this.shgame.enterState(6);
/* 167:    */     }
/* 168:180 */     if ((container.getInput().isKeyDown(199)) && 
/* 169:181 */       (this.agentState == AgentState.MOVING) && 
/* 170:182 */       (this.state == GameState.State.PLAY) && 
/* 171:183 */       ((this.turnState == TurnState.YOUR) || (this.turnState == TurnState.INTERRUPT)) && 
/* 172:184 */       (this.selectedAgent != null)) {
/* 173:186 */       startRotation(cont);
/* 174:    */     }
/* 175:190 */     if ((this.agentState == AgentState.TURNING) && 
/* 176:191 */       (this.state == GameState.State.PLAY) && 
/* 177:192 */       ((this.turnState == TurnState.YOUR) || (this.turnState == TurnState.INTERRUPT)) && 
/* 178:193 */       (this.selectedAgent != null))
/* 179:    */     {
/* 180:195 */       if (container.getInput().isKeyPressed(203))
/* 181:    */       {
/* 182:197 */         this.selectedAgent.alterAngle(-90.0F);
/* 183:198 */         this.turned = ((this.turned - 90.0F) % 360.0F);
/* 184:    */       }
/* 185:201 */       if (container.getInput().isKeyPressed(205))
/* 186:    */       {
/* 187:203 */         this.selectedAgent.alterAngle(90.0F);
/* 188:204 */         this.turned = ((this.turned + 90.0F) % 360.0F);
/* 189:205 */         System.out.println(this.turned);
/* 190:    */       }
/* 191:    */     }
/* 192:209 */     if (this.agentState != AgentState.TURNING)
/* 193:    */     {
/* 194:211 */       if (container.getInput().isKeyDown(203)) {
/* 195:213 */         this.cameraPositionX += (int)(20.0F * (1.0F / this.scaleY));
/* 196:    */       }
/* 197:216 */       if (container.getInput().isKeyDown(205)) {
/* 198:218 */         this.cameraPositionX -= (int)(20.0F * (1.0F / this.scaleY));
/* 199:    */       }
/* 200:220 */       if (container.getInput().isKeyDown(200)) {
/* 201:222 */         this.cameraPositionY += (int)(20.0F * (1.0F / this.scaleY));
/* 202:    */       }
/* 203:224 */       if (container.getInput().isKeyDown(208)) {
/* 204:226 */         this.cameraPositionY -= (int)(20.0F * (1.0F / this.scaleY));
/* 205:    */       }
/* 206:    */     }
/* 207:235 */     if ((container.getInput().isKeyDown(29)) && 
/* 208:236 */       (this.turnState == TurnState.YOUR)) {
/* 209:238 */       if ((container.getInput().isKeyPressed(44)) && 
/* 210:239 */         (this.selectedAgent != null))
/* 211:    */       {
/* 212:241 */         AgentMovement move = this.selectedAgent.undo();
/* 213:242 */         if (move != null)
/* 214:    */         {
/* 215:244 */           this.CP += move.cost;
/* 216:245 */           cont.send(move);
/* 217:    */         }
/* 218:    */       }
/* 219:    */     }
/* 220:    */   }
/* 221:    */   
/* 222:    */   private void processMovement(SpaceHulkGameContainer cont)
/* 223:    */   {
/* 224:254 */     while (!this.movementIncomingList.isEmpty())
/* 225:    */     {
/* 226:256 */       AgentMovement movement = (AgentMovement)this.movementIncomingList.poll();
/* 227:257 */       AgentModel model = this.worldModel.getAgentModel(movement.UUID);
/* 228:259 */       if ((model != null) && ((model instanceof DoorModel)))
/* 229:    */       {
/* 230:261 */         ((DoorModel)model).open();
/* 231:262 */         this.shgameContainer.playSound("door");
/* 232:    */       }
/* 233:264 */       else if ((model != null) && ((model instanceof PathAgentModel)))
/* 234:    */       {
/* 235:266 */         PathAgentModel pathModel = (PathAgentModel)model;
/* 236:267 */         pathModel.setAngle(movement.angle);
/* 237:268 */         if ((model instanceof MarineModel))
/* 238:    */         {
/* 239:270 */           System.out.println("Overwatch: " + movement.overwatch);
/* 240:271 */           MarineModel m = (MarineModel)model;
/* 241:272 */           m.setOverwatch(movement.overwatch, movement.weapon);
/* 242:    */         }
/* 243:274 */         if (((int)movement.x != (int)model.getX()) || ((int)movement.y != (int)model.getY()))
/* 244:    */         {
/* 245:276 */           this.worldView.getAgentView(pathModel.getUUID()).setDefault("walking");
/* 246:    */           
/* 247:278 */           pathModel.moveTo(movement.x, movement.y);
/* 248:279 */           if ((model instanceof MarineModel)) {
/* 249:281 */             this.shgameContainer.playSound("marinestep");
/* 250:    */           }
/* 251:    */         }
/* 252:    */       }
/* 253:    */       else
/* 254:    */       {
/* 255:290 */         GameModel start = this.worldModel.getGameModel(movement.UUID);
/* 256:291 */         if ((start != null) && ((start instanceof StartPositionModel)))
/* 257:    */         {
/* 258:293 */           ((StartPositionModel)start).setOpen(false);
/* 259:294 */           this.shgameContainer.playSound("door");
/* 260:    */         }
/* 261:    */       }
/* 262:    */     }
/* 263:    */   }
/* 264:    */   
/* 265:    */   protected void endTurn(SpaceHulkGameContainer cont)
/* 266:    */   {
/* 267:334 */     this.state = GameState.State.OTHERTURN;
/* 268:335 */     this.worldModel.endTurn(this.shgameContainer.getPlayerId());
/* 269:336 */     this.turnState = TurnState.OTHER;
/* 270:339 */     if (this.selectedAgent != null)
/* 271:    */     {
/* 272:341 */       this.selectedAgent.setSelected(false);
/* 273:342 */       this.selectedAgent = null;
/* 274:    */     }
/* 275:344 */     EndTurn end = new EndTurn();
/* 276:    */     
/* 277:346 */     end.player = ((this.shgameContainer.getPlayerId() + 1) % cont.getPlayers());
/* 278:347 */     this.feedbackText.setText("Turn has ended, it is now player " + end.player + " its turn");
/* 279:348 */     cont.send(end);
/* 280:    */   }
/* 281:    */   
/* 282:    */   protected boolean finishRotation(SpaceHulkGameContainer cont)
/* 283:    */   {
/* 284:355 */     return false;
/* 285:    */   }
/* 286:    */   
/* 287:    */   protected void startRotation(SpaceHulkGameContainer cont)
/* 288:    */   {
/* 289:360 */     this.feedbackText.setText("Rotatate unit");
/* 290:361 */     this.agentState = AgentState.TURNING;
/* 291:    */     
/* 292:363 */     cont.getInput().clearKeyPressedRecord();
/* 293:    */   }
/* 294:    */   
/* 295:    */   public void mouseClicked(int button, int x, int y, int clickCount)
/* 296:    */   {
/* 297:369 */     super.mouseClicked(button, x, y, clickCount);
/* 298:    */   }
/* 299:    */   
/* 300:    */   protected void attack(AgentModel model, Weapon weapon) {}
/* 301:    */   
/* 302:    */   protected void walking(float tx, float ty) {}
/* 303:    */   
/* 304:    */   public synchronized void addRowThreadsafe(final ChatMessage msg)
/* 305:    */   {
/* 306:385 */     GUI gui = getRootPane().getGUI();
/* 307:386 */     if (gui != null) {
/* 308:388 */       gui.invokeLater(new Runnable()
/* 309:    */       {
/* 310:    */         public void run()
/* 311:    */         {
/* 312:392 */           GameplayState.this.chatbox.appendRow(msg.name, msg.text);
/* 313:    */         }
/* 314:    */       });
/* 315:    */     }
/* 316:    */   }
/* 317:    */   
/* 318:    */   public void deSelect() {}
/* 319:    */   
/* 320:    */   public void yourTurn() {}
/* 321:    */   
/* 322:    */   public class GameServerListener
/* 323:    */     extends Listener
/* 324:    */   {
/* 325:    */     private Server server;
/* 326:    */     
/* 327:    */     public void connected(Connection arg0)
/* 328:    */     {
/* 329:405 */       super.connected(arg0);
/* 330:    */     }
/* 331:    */     
/* 332:    */     public GameServerListener(Server server)
/* 333:    */     {
/* 334:413 */       this.server = server;
/* 335:    */     }
/* 336:    */     
/* 337:    */     public void received(Connection connection, Object object)
/* 338:    */     {
/* 339:419 */       this.server.sendToAllExceptTCP(connection.getID(), object);
/* 340:421 */       if ((object instanceof EndTurn))
/* 341:    */       {
/* 342:423 */         EndTurn end = (EndTurn)object;
/* 343:424 */         GameplayState.this.interruptionServerToggle = false;
/* 344:425 */         if (end.player == GameplayState.this.shgameContainer.getPlayerId())
/* 345:    */         {
/* 346:427 */           if (GameplayState.this.isDefeated) {
/* 347:429 */             GameplayState.this.endTurn(GameplayState.this.shgameContainer);
/* 348:    */           } else {
/* 349:433 */             GameplayState.this.yourTurn();
/* 350:    */           }
/* 351:    */         }
/* 352:    */         else {
/* 353:439 */           GameplayState.this.feedbackText.setText("It's player's " + end.player + " turn");
/* 354:    */         }
/* 355:    */       }
/* 356:442 */       if ((object instanceof AgentPlacement))
/* 357:    */       {
/* 358:444 */         AgentPlacement a = (AgentPlacement)object;
/* 359:445 */         GameplayState.this.PlaceAgent(a.x, a.y, a.angle, a.faction, a.UUID, a.blip, 
/* 360:446 */           a.playerId, a.team, a.marineType, a.colour);
/* 361:    */       }
/* 362:448 */       if ((object instanceof AgentMovement))
/* 363:    */       {
/* 364:450 */         AgentMovement a = (AgentMovement)object;
/* 365:451 */         GameplayState.this.movementIncomingList.add(a);
/* 366:    */       }
/* 367:454 */       if ((object instanceof BlipConvertInterupt))
/* 368:    */       {
/* 369:458 */         BlipConvertInterupt blipconv = (BlipConvertInterupt)object;
/* 370:459 */         GameplayState.this.selectedAgent = ((PathAgentModel)GameplayState.this.worldModel.getAgentModel(blipconv.UUID));
/* 371:460 */         BlipModel model = (BlipModel)GameplayState.this.selectedAgent;
/* 372:461 */         GameplayState.this.selectedAgent.setSelected(true);
/* 373:462 */         GameplayState.this.feedbackText.setText("You must place " + model.getStealers() + " Aliens.");
/* 374:    */       }
/* 375:464 */       if ((object instanceof AgentRemove))
/* 376:    */       {
/* 377:466 */         AgentRemove a = (AgentRemove)object;
/* 378:    */         
/* 379:468 */         GameplayState.this.removeAgent(a.UUID, a.killed);
/* 380:    */       }
/* 381:471 */       if ((object instanceof InterruptRequest))
/* 382:    */       {
/* 383:473 */         InterruptRequest inter = (InterruptRequest)object;
/* 384:474 */         if ((GameplayState.this.shgameContainer.isServer()) && (!GameplayState.this.interruptionServerToggle))
/* 385:    */         {
/* 386:476 */           GameplayState.this.interruptionServerToggle = true;
/* 387:    */           
/* 388:478 */           Interrupt response = new Interrupt();
/* 389:479 */           response.player = inter.player;
/* 390:480 */           response.interrupt = true;
/* 391:481 */           GameplayState.this.shgameContainer.send(response);
/* 392:482 */           if (GameplayState.this.turnState == GameplayState.TurnState.YOUR)
/* 393:    */           {
/* 394:484 */             GameplayState.this.interrupted = true;
/* 395:485 */             GameplayState.this.interruptingPlayer = inter.player;
/* 396:486 */             GameplayState.this.previousState = GameplayState.this.state;
/* 397:487 */             GameplayState.this.state = GameState.State.OTHERTURN;
/* 398:488 */             GameplayState.this.turnState = GameplayState.TurnState.OTHER;
/* 399:489 */             GameplayState.this.feedbackText.setText("Other player is interrupting");
/* 400:    */           }
/* 401:    */         }
/* 402:    */         else
/* 403:    */         {
/* 404:494 */           Interrupt response = new Interrupt();
/* 405:495 */           response.player = inter.player;
/* 406:496 */           response.interrupt = false;
/* 407:497 */           GameplayState.this.shgameContainer.send(response);
/* 408:    */         }
/* 409:    */       }
/* 410:500 */       if ((object instanceof Overwatch))
/* 411:    */       {
/* 412:502 */         Overwatch a = (Overwatch)object;
/* 413:503 */         MarineModel model = (MarineModel)GameplayState.this.worldModel.getAgentModel(a.UUID);
/* 414:504 */         if (a.overwatch) {
/* 415:506 */           GameplayState.this.shgameContainer.playSound("overwatch");
/* 416:    */         }
/* 417:508 */         model.setOverwatch(a.overwatch, a.weaponId);
/* 418:    */       }
/* 419:510 */       if ((object instanceof EndInterrupt))
/* 420:    */       {
/* 421:512 */         EndInterrupt end = (EndInterrupt)object;
/* 422:514 */         if ((GameplayState.this.interrupted) && (end.player == GameplayState.this.interruptingPlayer))
/* 423:    */         {
/* 424:516 */           GameplayState.this.interrupted = false;
/* 425:517 */           GameplayState.this.interruptingPlayer = 0;
/* 426:518 */           GameplayState.this.interruptionServerToggle = false;
/* 427:519 */           GameplayState.this.state = GameplayState.this.previousState;
/* 428:520 */           GameplayState.this.turnState = GameplayState.TurnState.YOUR;
/* 429:521 */           GameplayState.this.feedbackText.setText("It is your turn, you can place new units!");
/* 430:    */         }
/* 431:523 */         GameplayState.this.interruptionServerToggle = false;
/* 432:    */       }
/* 433:525 */       if ((object instanceof Finished))
/* 434:    */       {
/* 435:527 */         Finished d = (Finished)object;
/* 436:529 */         if (!d.defeat)
/* 437:    */         {
/* 438:531 */           GameplayState.this.shgameContainer.setVictory(d.playerId, d.teamId);
/* 439:532 */           SyncLijst end = GameplayState.this.shgameContainer.getOverview();
/* 440:533 */           end.endGame = true;
/* 441:534 */           GameplayState.this.shgameContainer.send(end);
/* 442:    */           
/* 443:536 */           GameplayState.this.shgameContainer.removeServerListener(GameplayState.this.gameserverListener);
/* 444:    */           
/* 445:538 */           GameplayState.this.shgame.enterState(6);
/* 446:    */         }
/* 447:    */         else
/* 448:    */         {
/* 449:542 */           int team = GameplayState.this.shgameContainer.setDefeat(d.playerId);
/* 450:543 */           if (GameplayState.this.shgameContainer.teamDefeat(team))
/* 451:    */           {
/* 452:545 */             SyncLijst end = GameplayState.this.shgameContainer.getOverview();
/* 453:546 */             end.endGame = true;
/* 454:547 */             GameplayState.this.shgameContainer.send(end);
/* 455:    */             
/* 456:549 */             GameplayState.this.shgameContainer.removeServerListener(GameplayState.this.gameserverListener);
/* 457:    */             
/* 458:551 */             GameplayState.this.shgame.enterState(6);
/* 459:    */           }
/* 460:    */         }
/* 461:    */       }
/* 462:555 */       if ((object instanceof ChatMessage))
/* 463:    */       {
/* 464:557 */         ChatMessage message = (ChatMessage)object;
/* 465:558 */         GameplayState.this.addRowThreadsafe(message);
/* 466:    */       }
/* 467:561 */       if ((object instanceof Attack))
/* 468:    */       {
/* 469:563 */         Attack attack = (Attack)object;
/* 470:564 */         AgentModel attacker = GameplayState.this.worldModel.getAgentModel(attack.attacker);
/* 471:565 */         AgentModel defender = GameplayState.this.worldModel.getAgentModel(attack.defender);
/* 472:566 */         if (attacker != null) {
/* 473:568 */           attacker.setFloatingText("Attacks");
/* 474:    */         }
/* 475:570 */         if (defender != null)
/* 476:    */         {
/* 477:572 */           GameplayState.this.fireBullets(attacker, defender.getX(), defender.getY(), attacker.getWeapon(attack.weapon));
/* 478:573 */           defender.setFloatingText("Defends");
/* 479:    */         }
/* 480:575 */         if (!attack.sound.isEmpty()) {
/* 481:577 */           GameplayState.this.shgameContainer.playSound(attack.sound);
/* 482:    */         }
/* 483:    */       }
/* 484:    */     }
/* 485:    */   }
/* 486:    */   
/* 487:    */   public class GameClientListener
/* 488:    */     extends Listener
/* 489:    */   {
/* 490:    */     public GameClientListener() {}
/* 491:    */     
/* 492:    */     public void received(Connection connection, Object object)
/* 493:    */     {
/* 494:587 */       if ((object instanceof EndTurn))
/* 495:    */       {
/* 496:589 */         EndTurn end = (EndTurn)object;
/* 497:591 */         if (end.player == GameplayState.this.shgameContainer.getPlayerId())
/* 498:    */         {
/* 499:593 */           if (GameplayState.this.isDefeated)
/* 500:    */           {
/* 501:595 */             GameplayState.this.endTurn(GameplayState.this.shgameContainer);
/* 502:596 */             GameplayState.this.feedbackText.setText("It's player's " + end.player + " turn");
/* 503:    */           }
/* 504:    */           else
/* 505:    */           {
/* 506:600 */             GameplayState.this.yourTurn();
/* 507:    */           }
/* 508:    */         }
/* 509:    */         else {
/* 510:606 */           GameplayState.this.feedbackText.setText("It's player's " + end.player + " turn");
/* 511:    */         }
/* 512:    */       }
/* 513:609 */       if ((object instanceof AgentPlacement))
/* 514:    */       {
/* 515:611 */         AgentPlacement a = (AgentPlacement)object;
/* 516:    */         
/* 517:613 */         GameplayState.this.PlaceAgent(a.x, a.y, a.angle, a.faction, a.UUID, a.blip, 
/* 518:614 */           a.playerId, a.team, a.marineType, a.colour);
/* 519:    */       }
/* 520:616 */       if ((object instanceof AgentMovement))
/* 521:    */       {
/* 522:618 */         AgentMovement a = (AgentMovement)object;
/* 523:619 */         GameplayState.this.movementIncomingList.add(a);
/* 524:    */       }
/* 525:622 */       if ((object instanceof BlipConvertInterupt))
/* 526:    */       {
/* 527:626 */         BlipConvertInterupt blipconv = (BlipConvertInterupt)object;
/* 528:627 */         GameplayState.this.selectedAgent = ((PathAgentModel)GameplayState.this.worldModel.getAgentModel(blipconv.UUID));
/* 529:628 */         BlipModel model = (BlipModel)GameplayState.this.selectedAgent;
/* 530:629 */         GameplayState.this.selectedAgent.setSelected(true);
/* 531:630 */         GameplayState.this.feedbackText.setText("You must place " + model.getStealers() + " Aliens.");
/* 532:    */       }
/* 533:632 */       if ((object instanceof AgentRemove))
/* 534:    */       {
/* 535:635 */         AgentRemove a = (AgentRemove)object;
/* 536:636 */         System.out.println("Removing " + a.UUID);
/* 537:637 */         GameplayState.this.removeAgent(a.UUID, a.killed);
/* 538:    */       }
/* 539:640 */       if ((object instanceof Interrupt))
/* 540:    */       {
/* 541:642 */         Interrupt inter = (Interrupt)object;
/* 542:644 */         if ((inter.interrupt) && (GameplayState.this.turnState == GameplayState.TurnState.YOUR))
/* 543:    */         {
/* 544:647 */           GameplayState.this.interrupted = true;
/* 545:648 */           GameplayState.this.interruptingPlayer = inter.player;
/* 546:649 */           GameplayState.this.previousState = GameplayState.this.state;
/* 547:650 */           GameplayState.this.state = GameState.State.OTHERTURN;
/* 548:651 */           GameplayState.this.turnState = GameplayState.TurnState.OTHER;
/* 549:652 */           GameplayState.this.feedbackText.setText("Other player is interrupting");
/* 550:    */         }
/* 551:654 */         else if ((inter.interrupt) && (inter.player == GameplayState.this.shgameContainer.getPlayerId()))
/* 552:    */         {
/* 553:656 */           GameplayState.this.state = GameState.State.PLAY;
/* 554:657 */           GameplayState.this.turnState = GameplayState.TurnState.INTERRUPT;
/* 555:658 */           GameplayState.this.feedbackText.setText("Interrupt with " + GameplayState.this.CP + " CP");
/* 556:    */         }
/* 557:660 */         else if ((!inter.interrupt) && (inter.player == GameplayState.this.shgameContainer.getPlayerId()))
/* 558:    */         {
/* 559:662 */           GameplayState.this.feedbackText.setText("Can not interrupt at this time");
/* 560:    */         }
/* 561:    */       }
/* 562:667 */       if ((object instanceof Overwatch))
/* 563:    */       {
/* 564:670 */         Overwatch a = (Overwatch)object;
/* 565:671 */         MarineModel model = (MarineModel)GameplayState.this.worldModel.getAgentModel(a.UUID);
/* 566:672 */         if (a.overwatch) {
/* 567:674 */           GameplayState.this.shgameContainer.playSound("overwatch");
/* 568:    */         }
/* 569:676 */         model.setOverwatch(a.overwatch, a.weaponId);
/* 570:    */       }
/* 571:679 */       if ((object instanceof EndInterrupt))
/* 572:    */       {
/* 573:682 */         EndInterrupt end = (EndInterrupt)object;
/* 574:683 */         if ((GameplayState.this.interrupted) && (end.player == GameplayState.this.interruptingPlayer))
/* 575:    */         {
/* 576:685 */           GameplayState.this.interrupted = false;
/* 577:686 */           GameplayState.this.interruptingPlayer = 0;
/* 578:687 */           GameplayState.this.state = GameplayState.this.previousState;
/* 579:688 */           GameplayState.this.turnState = GameplayState.TurnState.YOUR;
/* 580:689 */           GameplayState.this.feedbackText.setText("It is your turn, you can place new units!");
/* 581:    */         }
/* 582:    */       }
/* 583:692 */       if ((object instanceof SyncLijst))
/* 584:    */       {
/* 585:694 */         SyncLijst end = (SyncLijst)object;
/* 586:695 */         if (end.endGame)
/* 587:    */         {
/* 588:697 */           GameplayState.this.shgameContainer.setOverview(end);
/* 589:698 */           GameplayState.this.gameEnds = true;
/* 590:    */         }
/* 591:    */       }
/* 592:701 */       if ((object instanceof ChatMessage))
/* 593:    */       {
/* 594:703 */         ChatMessage message = (ChatMessage)object;
/* 595:    */         
/* 596:705 */         GameplayState.this.addRowThreadsafe(message);
/* 597:    */       }
/* 598:708 */       if ((object instanceof Attack))
/* 599:    */       {
/* 600:710 */         Attack attack = (Attack)object;
/* 601:711 */         AgentModel attacker = GameplayState.this.worldModel.getAgentModel(attack.attacker);
/* 602:712 */         AgentModel defender = GameplayState.this.worldModel.getAgentModel(attack.defender);
/* 603:713 */         if (attacker != null) {
/* 604:715 */           attacker.setFloatingText("Attacks");
/* 605:    */         }
/* 606:717 */         if (defender != null)
/* 607:    */         {
/* 608:719 */           defender.setFloatingText("Defends");
/* 609:720 */           GameplayState.this.fireBullets(attacker, defender.getX(), defender.getY(), attacker.getWeapon(attack.weapon));
/* 610:    */         }
/* 611:723 */         if (!attack.sound.isEmpty()) {
/* 612:725 */           GameplayState.this.shgameContainer.playSound(attack.sound);
/* 613:    */         }
/* 614:    */       }
/* 615:    */     }
/* 616:    */   }
/* 617:    */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.states.GameplayState
 * JD-Core Version:    0.7.0.1
 */