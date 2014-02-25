/*   1:    */ package sh.states;
/*   2:    */ 
/*   3:    */ import de.matthiasmann.twl.Button;
/*   4:    */ import de.matthiasmann.twl.Label;
/*   5:    */ import java.io.PrintStream;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.LinkedList;
/*   8:    */ import java.util.List;
/*   9:    */ import org.newdawn.slick.GameContainer;
/*  10:    */ import org.newdawn.slick.Input;
/*  11:    */ import org.newdawn.slick.SlickException;
/*  12:    */ import org.newdawn.slick.state.StateBasedGame;
/*  13:    */ import sh.SpaceHulkGameContainer;
/*  14:    */ import sh.agent.AgentModel;
/*  15:    */ import sh.agent.AgentView;
/*  16:    */ import sh.agent.aliens.AlienModel;
/*  17:    */ import sh.agent.aliens.BlipModel;
/*  18:    */ import sh.agent.door.DoorModel;
/*  19:    */ import sh.agent.fire.FireModel;
/*  20:    */ import sh.agent.marines.MarineModel;
/*  21:    */ import sh.agent.movement.PathAgentModel;
/*  22:    */ import sh.agent.weapons.Weapon;
/*  23:    */ import sh.gui.widgets.InfoBox;
/*  24:    */ import sh.gui.widgets.Objectives;
/*  25:    */ import sh.multiplayer.AgentMovement;
/*  26:    */ import sh.multiplayer.AgentRemove;
/*  27:    */ import sh.multiplayer.Attack;
/*  28:    */ import sh.world.shworld.SpaceHulkWorldModel;
/*  29:    */ import sh.world.shworld.SpaceHulkWorldView;
/*  30:    */ 
/*  31:    */ public class AlienGamePlayState
/*  32:    */   extends AlienGuiState
/*  33:    */ {
/*  34:    */   int alienPlaced;
/*  35:    */   
/*  36:    */   public AlienGamePlayState(int stateID)
/*  37:    */   {
/*  38: 38 */     super(stateID);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void enter(GameContainer container, StateBasedGame game)
/*  42:    */     throws SlickException
/*  43:    */   {
/*  44: 47 */     super.enter(container, game);
/*  45: 48 */     this.shgameContainer = ((SpaceHulkGameContainer)container);
/*  46: 49 */     this.apLabel.setVisible(false);
/*  47: 50 */     this.infobox.setVisible(false);
/*  48: 51 */     this.aliensInBlipLabel.setVisible(false);
/*  49: 52 */     this.rotate.setVisible(false);
/*  50: 53 */     this.finishRotate.setVisible(false);
/*  51: 54 */     this.insertAliens.setVisible(false);
/*  52: 55 */     this.endTurn.setVisible(false);
/*  53: 56 */     System.out.println("Playing as aliens");
/*  54: 57 */     this.objectivesbox.setObjective(this.shgameContainer.getMission());
/*  55: 58 */     if (this.shgameContainer.getPlayerId() == 0)
/*  56:    */     {
/*  57: 60 */       this.state = GameState.State.PLACEMENT;
/*  58: 61 */       this.turnState = GameplayState.TurnState.YOUR;
/*  59: 62 */       this.endTurn.setVisible(true);
/*  60: 63 */       this.feedbackText.setText("Place a blip...");
/*  61:    */     }
/*  62:    */     else
/*  63:    */     {
/*  64: 66 */       this.state = GameState.State.OTHERTURN;
/*  65: 67 */       this.turnState = GameplayState.TurnState.OTHER;
/*  66:    */       
/*  67: 69 */       this.feedbackText.setText("Waiting for others turn");
/*  68:    */     }
/*  69: 73 */     callBacks();
/*  70:    */   }
/*  71:    */   
/*  72:    */   private void callBacks()
/*  73:    */   {
/*  74: 79 */     this.insertAliens.addCallback(new Runnable()
/*  75:    */     {
/*  76:    */       public void run()
/*  77:    */       {
/*  78: 84 */         if (AlienGamePlayState.this.turnState == GameplayState.TurnState.YOUR) {
/*  79: 86 */           if ((AlienGamePlayState.this.selectedAgent != null) && ((AlienGamePlayState.this.selectedAgent instanceof BlipModel)))
/*  80:    */           {
/*  81: 88 */             AlienGamePlayState.this.shgameContainer.playSound("click");
/*  82: 89 */             BlipModel model = (BlipModel)AlienGamePlayState.this.selectedAgent;
/*  83: 90 */             AgentRemove remove = new AgentRemove();
/*  84: 91 */             remove.UUID = model.getUUID();
/*  85: 92 */             remove.killed = false;
/*  86: 93 */             AlienGamePlayState.this.shgameContainer.send(remove);
/*  87:    */             
/*  88: 95 */             AlienGamePlayState.this.removeAgent(model.getUUID(), false);
/*  89: 96 */             AlienGamePlayState.this.feedbackText.setText("Place alien");
/*  90: 97 */             AlienGamePlayState.this.aliensToPlace = model.getStealers();
/*  91: 98 */             AlienGamePlayState.this.alienPlaceing = model;
/*  92: 99 */             AlienGamePlayState.this.state = GameState.State.BLIPCONVERT;
/*  93:100 */             AlienGamePlayState.this.insertAliens.setVisible(false);
/*  94:    */           }
/*  95:    */         }
/*  96:    */       }
/*  97:107 */     });
/*  98:108 */     this.rotate.addCallback(new Runnable()
/*  99:    */     {
/* 100:    */       public void run()
/* 101:    */       {
/* 102:114 */         if ((AlienGamePlayState.this.agentState == GameplayState.AgentState.MOVING) && 
/* 103:115 */           (AlienGamePlayState.this.state == GameState.State.PLAY) && 
/* 104:116 */           ((AlienGamePlayState.this.turnState == GameplayState.TurnState.YOUR) || (AlienGamePlayState.this.turnState == GameplayState.TurnState.INTERRUPT)) && 
/* 105:117 */           (AlienGamePlayState.this.selectedAgent != null))
/* 106:    */         {
/* 107:119 */           AlienGamePlayState.this.shgameContainer.playSound("click");
/* 108:120 */           AlienGamePlayState.this.finishRotate.setVisible(true);
/* 109:121 */           AlienGamePlayState.this.rotate.setVisible(false);
/* 110:122 */           AlienGamePlayState.this.insertAliens.setVisible(false);
/* 111:123 */           AlienGamePlayState.this.startRotation(AlienGamePlayState.this.shgameContainer);
/* 112:124 */           AlienGamePlayState.this.feedbackText.setText("Rotate Marine");
/* 113:    */         }
/* 114:    */       }
/* 115:130 */     });
/* 116:131 */     this.finishRotate.addCallback(new Runnable()
/* 117:    */     {
/* 118:    */       public void run()
/* 119:    */       {
/* 120:137 */         if ((AlienGamePlayState.this.state == GameState.State.BLIPCONVERT) && (AlienGamePlayState.this.agentState == GameplayState.AgentState.TURNING) && 
/* 121:138 */           (AlienGamePlayState.this.selectedAgent != null))
/* 122:    */         {
/* 123:140 */           AlienGamePlayState.this.shgameContainer.playSound("click");
/* 124:141 */           AlienGamePlayState.this.finishInitialTurning(AlienGamePlayState.this.shgameContainer, 0);
/* 125:142 */           AlienGamePlayState.this.finishRotate.setVisible(false);
/* 126:143 */           AlienGamePlayState.this.rotate.setVisible(true);
/* 127:144 */           AlienGamePlayState.this.setSelected(AlienGamePlayState.this.selectedAgent);
/* 128:    */           
/* 129:146 */           AlienGamePlayState.this.feedbackText.setText("Rotating Alien initially finished");
/* 130:    */         }
/* 131:149 */         else if ((AlienGamePlayState.this.agentState == GameplayState.AgentState.TURNING) && 
/* 132:150 */           (AlienGamePlayState.this.state == GameState.State.PLAY) && 
/* 133:151 */           ((AlienGamePlayState.this.turnState == GameplayState.TurnState.YOUR) || (AlienGamePlayState.this.turnState == GameplayState.TurnState.INTERRUPT)) && 
/* 134:152 */           (AlienGamePlayState.this.selectedAgent != null))
/* 135:    */         {
/* 136:154 */           if (AlienGamePlayState.this.finishRotation(AlienGamePlayState.this.shgameContainer))
/* 137:    */           {
/* 138:156 */             AlienGamePlayState.this.shgameContainer.playSound("click");
/* 139:157 */             AlienGamePlayState.this.finishRotate.setVisible(false);
/* 140:158 */             AlienGamePlayState.this.rotate.setVisible(true);
/* 141:    */             
/* 142:160 */             AlienGamePlayState.this.feedbackText.setText("Rotating Alien finished");
/* 143:    */           }
/* 144:    */         }
/* 145:    */       }
/* 146:166 */     });
/* 147:167 */     this.endTurn.addCallback(new Runnable()
/* 148:    */     {
/* 149:    */       public void run()
/* 150:    */       {
/* 151:172 */         if (AlienGamePlayState.this.turnState == GameplayState.TurnState.YOUR)
/* 152:    */         {
/* 153:174 */           AlienGamePlayState.this.shgameContainer.playSound("click");
/* 154:175 */           AlienGamePlayState.this.endTurn(AlienGamePlayState.this.shgameContainer);
/* 155:176 */           AlienGamePlayState.this.rotate.setVisible(false);
/* 156:177 */           AlienGamePlayState.this.finishRotate.setVisible(false);
/* 157:178 */           AlienGamePlayState.this.insertAliens.setVisible(false);
/* 158:179 */           AlienGamePlayState.this.aliensInBlipLabel.setVisible(false);
/* 159:180 */           AlienGamePlayState.this.apLabel.setVisible(false);
/* 160:    */           
/* 161:182 */           AlienGamePlayState.this.endTurn.setVisible(false);
/* 162:    */         }
/* 163:    */       }
/* 164:    */     });
/* 165:    */   }
/* 166:    */   
/* 167:    */   protected void attack(AgentModel model, Weapon weapon)
/* 168:    */   {
/* 169:192 */     AlienModel alien = (AlienModel)this.selectedAgent;
/* 170:    */     
/* 171:194 */     int attacks = alien.getAttacks(model.getUUID());
/* 172:195 */     alien.setAttacks(model.getUUID(), attacks + 1);
/* 173:196 */     int cost = weapon.getFirecost();
/* 174:197 */     if (alien.getAp() >= cost)
/* 175:    */     {
/* 176:199 */       alien.withdrawAp(cost);
/* 177:    */       
/* 178:201 */       Attack attack = new Attack();
/* 179:202 */       attack.attacker = alien.getUUID();
/* 180:203 */       attack.defender = model.getUUID();
/* 181:204 */       attack.ammo = weapon.getAmmo();
/* 182:205 */       attack.sound = weapon.getSound();
/* 183:206 */       alien.setFloatingText("Attacking");
/* 184:207 */       model.setFloatingText("Defending");
/* 185:208 */       this.shgameContainer.send(attack);
/* 186:209 */       this.worldView.getAgentView(alien.getUUID()).setAnimation(weapon.getAgentAnimation(), false);
/* 187:210 */       this.shgameContainer.playSound(weapon.getSound());
/* 188:211 */       if (weapon.attack(alien, model, this.worldModel))
/* 189:    */       {
/* 190:214 */         AgentRemove remove = new AgentRemove();
/* 191:215 */         remove.UUID = model.getUUID();
/* 192:216 */         remove.killed = true;
/* 193:217 */         this.removeList.add(remove);
/* 194:    */         
/* 195:219 */         removeAgent(model.getUUID(), true);
/* 196:    */       }
/* 197:    */     }
/* 198:    */   }
/* 199:    */   
/* 200:    */   protected boolean finishRotation(SpaceHulkGameContainer cont)
/* 201:    */   {
/* 202:228 */     int cost = this.selectedAgent.rotatingCost(this.turned);
/* 203:230 */     if (this.selectedAgent.getAp() >= cost)
/* 204:    */     {
/* 205:233 */       this.selectedAgent.withdrawAp(cost);
/* 206:234 */       this.agentState = GameplayState.AgentState.MOVING;
/* 207:235 */       this.turned = 0.0F;
/* 208:236 */       AgentMovement movement = this.selectedAgent.getMovement(cost);
/* 209:237 */       this.movementOutgoingList.add(movement);
/* 210:238 */       this.apLabel.setText("AP " + this.selectedAgent.getAp());
/* 211:239 */       return true;
/* 212:    */     }
/* 213:241 */     return false;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public void update(GameContainer container, StateBasedGame arg1, int arg2)
/* 217:    */     throws SlickException
/* 218:    */   {
/* 219:248 */     super.update(container, arg1, arg2);
/* 220:249 */     Input input = container.getInput();
/* 221:250 */     SpaceHulkGameContainer cont = (SpaceHulkGameContainer)container;
/* 222:252 */     if ((this.state == GameState.State.BLIPCONVERT) && (this.agentState == GameplayState.AgentState.TURNING) && 
/* 223:253 */       (this.selectedAgent != null))
/* 224:    */     {
/* 225:255 */       if (container.getInput().isKeyDown(203)) {
/* 226:257 */         this.selectedAgent.setAngle(180.0F);
/* 227:    */       }
/* 228:259 */       if (container.getInput().isKeyDown(205)) {
/* 229:261 */         this.selectedAgent.setAngle(0.0F);
/* 230:    */       }
/* 231:263 */       if (container.getInput().isKeyDown(200)) {
/* 232:265 */         this.selectedAgent.setAngle(270.0F);
/* 233:    */       }
/* 234:267 */       if (container.getInput().isKeyDown(208)) {
/* 235:269 */         this.selectedAgent.setAngle(90.0F);
/* 236:    */       }
/* 237:    */     }
/* 238:273 */     if ((this.agentsPlaced >= cont.getPoints()) && (this.aliensToPlace == 0))
/* 239:    */     {
/* 240:275 */       this.state = GameState.State.PLAY;
/* 241:276 */       this.feedbackText.setText("All possible Aliens placed");
/* 242:277 */       if (this.selectedAgent != null)
/* 243:    */       {
/* 244:279 */         if ((this.selectedAgent instanceof AlienModel))
/* 245:    */         {
/* 246:281 */           AlienModel m = (AlienModel)this.selectedAgent;
/* 247:283 */           if (this.agentState == GameplayState.AgentState.TURNING)
/* 248:    */           {
/* 249:285 */             this.rotate.setVisible(false);
/* 250:286 */             this.finishRotate.setVisible(true);
/* 251:    */           }
/* 252:    */           else
/* 253:    */           {
/* 254:290 */             this.rotate.setVisible(true);
/* 255:291 */             this.finishRotate.setVisible(false);
/* 256:    */           }
/* 257:    */         }
/* 258:    */         else
/* 259:    */         {
/* 260:296 */           this.insertAliens.setVisible(true);
/* 261:    */         }
/* 262:300 */         this.apLabel.setVisible(true);
/* 263:    */         
/* 264:    */ 
/* 265:    */ 
/* 266:304 */         this.endTurn.setVisible(true);
/* 267:    */         
/* 268:306 */         this.apLabel.setText("AP " + this.selectedAgent.getAp());
/* 269:    */       }
/* 270:    */     }
/* 271:310 */     if ((container.getInput().isKeyPressed(210)) && 
/* 272:311 */       (this.turnState == GameplayState.TurnState.YOUR)) {
/* 273:313 */       if ((this.selectedAgent != null) && ((this.selectedAgent instanceof BlipModel)))
/* 274:    */       {
/* 275:315 */         BlipModel model = (BlipModel)this.selectedAgent;
/* 276:316 */         AgentRemove remove = new AgentRemove();
/* 277:317 */         remove.UUID = model.getUUID();
/* 278:318 */         remove.killed = false;
/* 279:319 */         cont.send(remove);
/* 280:    */         
/* 281:321 */         removeAgent(model.getUUID(), false);
/* 282:322 */         this.feedbackText.setText("Place alien");
/* 283:323 */         this.aliensToPlace = model.getStealers();
/* 284:324 */         this.alienPlaceing = model;
/* 285:325 */         this.state = GameState.State.BLIPCONVERT;
/* 286:326 */         System.out.println("CONVERTING");
/* 287:    */       }
/* 288:    */     }
/* 289:332 */     input.clearKeyPressedRecord();
/* 290:    */   }
/* 291:    */   
/* 292:    */   private void finishInitialTurning(SpaceHulkGameContainer cont, int cost)
/* 293:    */   {
/* 294:337 */     AgentMovement move = this.selectedAgent.getMovement(cost);
/* 295:338 */     cont.send(move);
/* 296:339 */     this.agentState = GameplayState.AgentState.MOVING;
/* 297:340 */     this.aliensToPlace -= 1;
/* 298:342 */     if ((this.worldModel.getAlienPlaces(this.alienPlaceing).size() == 0) || 
/* 299:343 */       (this.aliensToPlace == 0))
/* 300:    */     {
/* 301:345 */       this.apLabel.setText("AP " + this.selectedAgent.getAp());
/* 302:346 */       this.apLabel.setVisible(true);
/* 303:    */       
/* 304:348 */       this.state = GameState.State.PLAY;
/* 305:    */     }
/* 306:    */     else
/* 307:    */     {
/* 308:351 */       this.feedbackText.setText("You can place a new Alien");
/* 309:    */     }
/* 310:    */   }
/* 311:    */   
/* 312:    */   protected void walking(float tx, float ty)
/* 313:    */   {
/* 314:358 */     if ((this.selectedAgent instanceof BlipModel)) {
/* 315:360 */       if (this.worldModel.lineOfSightEnemies(tx, ty, this.selectedAgent)) {
/* 316:363 */         return;
/* 317:    */       }
/* 318:    */     }
/* 319:367 */     int cost = this.selectedAgent.tileReachable(tx, ty);
/* 320:370 */     if ((cost > 0) && (cost <= this.selectedAgent.getAp()))
/* 321:    */     {
/* 322:374 */       this.worldView.getAgentView(this.selectedAgent.getUUID()).setDefault("walking");
/* 323:375 */       this.selectedAgent.moveTo(tx + 0.5F, ty + 0.5F);
/* 324:376 */       this.selectedAgent.withdrawAp(cost);
/* 325:377 */       AgentMovement movement = this.selectedAgent.getMovement(cost);
/* 326:378 */       movement.x = (tx + 0.5F);
/* 327:379 */       movement.y = (ty + 0.5F);
/* 328:380 */       this.movementOutgoingList.add(movement);
/* 329:381 */       this.apLabel.setText("AP " + this.selectedAgent.getAp());
/* 330:383 */       if ((this.selectedAgent instanceof AlienModel)) {
/* 331:385 */         checkOverwatch();
/* 332:    */       }
/* 333:    */     }
/* 334:    */   }
/* 335:    */   
/* 336:    */   public void mouseClicked(int button, int x, int y, int clickCount)
/* 337:    */   {
/* 338:394 */     super.mouseClicked(button, x, y, clickCount);
/* 339:    */     
/* 340:396 */     int worldX = -this.cameraPositionX + (int)(x / this.scaleX);
/* 341:397 */     int worldY = -this.cameraPositionY + (int)(y / this.scaleY);
/* 342:    */     
/* 343:399 */     float tx = worldX / this.worldModel.getTileWidth();
/* 344:400 */     float ty = worldY / this.worldModel.getTileHeight();
/* 345:402 */     if (this.turnState == GameplayState.TurnState.YOUR)
/* 346:    */     {
/* 347:404 */       if ((button == 0) && (this.agentState != GameplayState.AgentState.TURNING))
/* 348:    */       {
/* 349:406 */         if ((this.state == GameState.State.PLACEMENT) || (this.state == GameState.State.BLIPCONVERT)) {
/* 350:408 */           placeAgents(tx, ty);
/* 351:    */         }
/* 352:410 */         if ((this.state == GameState.State.PLAY) && (this.agentState != GameplayState.AgentState.TURNING)) {
/* 353:413 */           if (this.selectedAgent == null) {
/* 354:415 */             selectAgent(tx, ty);
/* 355:    */           } else {
/* 356:418 */             gamePlayClick(tx, ty);
/* 357:    */           }
/* 358:    */         }
/* 359:    */       }
/* 360:422 */       if ((button == 1) && (this.state != GameState.State.PLACEMENT) && (this.agentState != GameplayState.AgentState.TURNING)) {
/* 361:424 */         deSelect();
/* 362:    */       }
/* 363:    */     }
/* 364:    */   }
/* 365:    */   
/* 366:    */   public void deSelect()
/* 367:    */   {
/* 368:432 */     this.infobox.setVisible(false);
/* 369:433 */     if (this.selectedAgent != null)
/* 370:    */     {
/* 371:435 */       this.apLabel.setVisible(false);
/* 372:436 */       this.aliensInBlipLabel.setVisible(false);
/* 373:437 */       this.rotate.setVisible(false);
/* 374:438 */       this.insertAliens.setVisible(false);
/* 375:439 */       this.finishRotate.setVisible(false);
/* 376:440 */       this.selectedAgent.setSelected(false);
/* 377:441 */       this.selectedAgent = null;
/* 378:    */     }
/* 379:    */   }
/* 380:    */   
/* 381:    */   protected void selectAgent(float tx, float ty)
/* 382:    */   {
/* 383:449 */     AgentModel model = this.worldModel.getAgentOnTile(tx, ty);
/* 384:451 */     if ((model != null) && (model.getPlayer() == this.shgameContainer.getPlayerId())) {
/* 385:454 */       setSelected((PathAgentModel)model);
/* 386:456 */     } else if (model != null) {
/* 387:458 */       setInfo(model);
/* 388:    */     }
/* 389:    */   }
/* 390:    */   
/* 391:    */   private void setInfo(AgentModel model)
/* 392:    */   {
/* 393:464 */     this.infobox.setVisible(true);
/* 394:465 */     this.infoBoxNameLabel.setText(model.getName());
/* 395:466 */     this.infoBoxTeamIdLabel.setText("Team: " + model.getTeam());
/* 396:467 */     this.infoBoxplayerIdLabel.setText("Player " + model.getPlayer());
/* 397:468 */     if (model.getCurrentWeapon() != null) {
/* 398:470 */       this.infoBoxWeaponLabel.setText(model.getCurrentWeapon().getName());
/* 399:    */     }
/* 400:    */   }
/* 401:    */   
/* 402:    */   private void setSelected(PathAgentModel model)
/* 403:    */   {
/* 404:476 */     this.selectedAgent = model;
/* 405:477 */     model.setSelected(true);
/* 406:478 */     if ((model instanceof AlienModel))
/* 407:    */     {
/* 408:480 */       AlienModel m = (AlienModel)model;
/* 409:482 */       if (this.agentState == GameplayState.AgentState.TURNING)
/* 410:    */       {
/* 411:484 */         this.rotate.setVisible(false);
/* 412:485 */         this.finishRotate.setVisible(true);
/* 413:    */       }
/* 414:    */       else
/* 415:    */       {
/* 416:489 */         this.rotate.setVisible(true);
/* 417:490 */         this.finishRotate.setVisible(false);
/* 418:    */       }
/* 419:    */     }
/* 420:493 */     else if ((model instanceof BlipModel))
/* 421:    */     {
/* 422:495 */       BlipModel m = (BlipModel)model;
/* 423:496 */       this.aliensInBlipLabel.setVisible(true);
/* 424:497 */       this.aliensInBlipLabel.setText("Aliens: " + m.getStealers());
/* 425:498 */       this.insertAliens.setVisible(true);
/* 426:    */     }
/* 427:502 */     this.apLabel.setVisible(true);
/* 428:    */     
/* 429:    */ 
/* 430:    */ 
/* 431:506 */     this.endTurn.setVisible(true);
/* 432:    */     
/* 433:508 */     this.apLabel.setText("AP " + model.getAp());
/* 434:    */   }
/* 435:    */   
/* 436:    */   private void gamePlayClick(float tx, float ty)
/* 437:    */   {
/* 438:514 */     AgentModel model = this.worldModel.getAgentOnTile(tx, ty);
/* 439:517 */     if (model == null)
/* 440:    */     {
/* 441:521 */       if (this.worldModel.tileWalkable(tx, ty)) {
/* 442:523 */         walking(tx, ty);
/* 443:    */       }
/* 444:    */     }
/* 445:525 */     else if ((model instanceof DoorModel))
/* 446:    */     {
/* 447:527 */       DoorModel door = (DoorModel)model;
/* 448:529 */       if (!door.isOpen()) {
/* 449:531 */         OpenDoor((DoorModel)model);
/* 450:534 */       } else if (this.worldModel.tileWalkable(tx, ty)) {
/* 451:536 */         walking(tx, ty);
/* 452:    */       }
/* 453:    */     }
/* 454:542 */     else if ((!model.getFaction().equalsIgnoreCase("fires")) && (model.getTeam() != this.shgameContainer.getTeam()) && 
/* 455:543 */       (this.worldModel.lineOfSight(this.selectedAgent, (PathAgentModel)model)))
/* 456:    */     {
/* 457:545 */       Weapon attackWeapon = this.selectedWeapon;
/* 458:546 */       if (attackWeapon == null)
/* 459:    */       {
/* 460:548 */         attackWeapon = this.selectedAgent.getDefaultWeapon();
/* 461:549 */         float distance = SpaceHulkWorldModel.getEuclideanDistance(this.selectedAgent, model);
/* 462:550 */         if (distance <= attackWeapon.getRange()) {
/* 463:552 */           attack(model, attackWeapon);
/* 464:    */         }
/* 465:    */       }
/* 466:    */     }
/* 467:    */   }
/* 468:    */   
/* 469:    */   private void OpenDoor(DoorModel door)
/* 470:    */   {
/* 471:561 */     int cost = 1;
/* 472:563 */     if (this.selectedAgent.getAp() >= cost) {
/* 473:565 */       if ((this.selectedAgent instanceof BlipModel))
/* 474:    */       {
/* 475:567 */         float distance = SpaceHulkWorldModel.getEuclideanDistance(this.selectedAgent, 
/* 476:568 */           door);
/* 477:569 */         if (distance <= 1.0F)
/* 478:    */         {
/* 479:571 */           door.open();
/* 480:572 */           this.shgameContainer.playSound("door");
/* 481:573 */           AgentMovement move = new AgentMovement();
/* 482:574 */           move.UUID = door.getUUID();
/* 483:575 */           move.open = true;
/* 484:576 */           this.movementOutgoingList.add(move);
/* 485:577 */           this.selectedAgent.withdrawAp(cost);
/* 486:    */         }
/* 487:    */       }
/* 488:    */       else
/* 489:    */       {
/* 490:582 */         AlienModel model = (AlienModel)this.selectedAgent;
/* 491:583 */         if (model.fov(door.getX(), door.getY()))
/* 492:    */         {
/* 493:585 */           float distance = SpaceHulkWorldModel.getEuclideanDistance(model, 
/* 494:586 */             door);
/* 495:587 */           if (distance <= 1.0F)
/* 496:    */           {
/* 497:589 */             door.open();
/* 498:590 */             this.shgameContainer.playSound("door");
/* 499:591 */             AgentMovement move = new AgentMovement();
/* 500:592 */             move.UUID = door.getUUID();
/* 501:593 */             move.open = true;
/* 502:594 */             this.movementOutgoingList.add(move);
/* 503:595 */             model.withdrawAp(cost);
/* 504:    */           }
/* 505:    */         }
/* 506:    */       }
/* 507:    */     }
/* 508:    */   }
/* 509:    */   
/* 510:    */   protected void placeAgents(float tx, float ty)
/* 511:    */   {
/* 512:604 */     AgentModel a = this.worldModel.getAgentOnTile(tx, ty);
/* 513:606 */     if ((this.state == GameState.State.BLIPCONVERT) && 
/* 514:607 */       (this.alienPlaceing != null) && 
/* 515:608 */       ((a == null) || (((a instanceof DoorModel)) && 
/* 516:609 */       (((DoorModel)a).isOpen()))) && 
/* 517:610 */       (this.worldModel.tileAlienPlacable(tx, ty, this.alienPlaceing)))
/* 518:    */     {
/* 519:613 */       int UUID = PlaceAgent(tx, ty, 180.0F, this.shgameContainer.getFaction(), true, this.shgameContainer.getPlayerId(), this.shgameContainer.getTeam(), this.shgameContainer.getColour());
/* 520:614 */       this.agentState = GameplayState.AgentState.TURNING;
/* 521:615 */       if (this.selectedAgent != null)
/* 522:    */       {
/* 523:617 */         this.selectedAgent.setSelected(false);
/* 524:618 */         this.selectedAgent = null;
/* 525:    */       }
/* 526:620 */       this.selectedAgent = ((PathAgentModel)this.worldModel.getAgentModel(UUID));
/* 527:621 */       this.selectedAgent.setAp(0);
/* 528:622 */       this.agentPlacement.add(this.selectedAgent.getPlacement());
/* 529:623 */       this.selectedAgent.setSelected(true);
/* 530:624 */       setSelected(this.selectedAgent);
/* 531:625 */       this.feedbackText.setText("Rotate with arrow keys, when finished hit enter");
/* 532:    */     }
/* 533:628 */     if ((this.state == GameState.State.PLACEMENT) && (a == null) && 
/* 534:629 */       (this.worldModel.tilePlayerPlacable(tx, ty, this.shgameContainer.getPlayerId())))
/* 535:    */     {
/* 536:631 */       int UUID = PlaceAgent(tx, ty, 180.0F, this.shgameContainer.getFaction(), this.shgameContainer.getPlayerId(), this.shgameContainer.getTeam(), -1, this.shgameContainer.getColour());
/* 537:632 */       this.agentState = GameplayState.AgentState.MOVING;
/* 538:633 */       if (this.selectedAgent != null)
/* 539:    */       {
/* 540:635 */         this.selectedAgent.setSelected(false);
/* 541:636 */         this.selectedAgent = null;
/* 542:    */       }
/* 543:638 */       this.selectedAgent = ((PathAgentModel)this.worldModel.getAgentModel(UUID));
/* 544:639 */       this.agentPlacement.add(this.selectedAgent.getPlacement());
/* 545:640 */       this.selectedAgent.setSelected(true);
/* 546:641 */       setSelected(this.selectedAgent);
/* 547:642 */       this.agentsPlaced += 1;
/* 548:643 */       this.apLabel.setText("AP " + this.selectedAgent.getAp());
/* 549:644 */       if ((!this.worldModel.hasStartPositions(this.shgameContainer.getPlayerId())) || 
/* 550:645 */         (this.agentsPlaced >= this.shgameContainer.getPoints()))
/* 551:    */       {
/* 552:647 */         this.state = GameState.State.PLAY;
/* 553:    */       }
/* 554:    */       else
/* 555:    */       {
/* 556:650 */         if (this.selectedAgent != null)
/* 557:    */         {
/* 558:652 */           this.selectedAgent.setSelected(false);
/* 559:653 */           this.selectedAgent = null;
/* 560:    */         }
/* 561:655 */         this.feedbackText.setText("You can place a new Blip");
/* 562:    */       }
/* 563:    */     }
/* 564:    */   }
/* 565:    */   
/* 566:    */   private void checkOverwatch()
/* 567:    */   {
/* 568:665 */     ArrayList<MarineModel> overwatchList = this.worldModel
/* 569:666 */       .getOverWatchList(this.shgameContainer.getTeam());
/* 570:668 */     for (MarineModel marine : overwatchList) {
/* 571:670 */       if ((this.worldModel.lineOfSight(marine, this.selectedAgent)) && (marine.getTeam() != this.selectedAgent.getTeam()))
/* 572:    */       {
/* 573:672 */         Weapon attackWeapon = marine.getCurrentWeapon();
/* 574:    */         
/* 575:    */ 
/* 576:675 */         float area = attackWeapon.getArea();
/* 577:676 */         Attack attack = new Attack();
/* 578:677 */         attack.attacker = marine.getUUID();
/* 579:678 */         attack.defender = this.selectedAgent.getUUID();
/* 580:679 */         attack.ammo = attackWeapon.getAmmo();
/* 581:680 */         attack.sound = attackWeapon.getSound();
/* 582:681 */         marine.setFloatingText("Attacking");
/* 583:682 */         this.selectedAgent.setFloatingText("Defending");
/* 584:683 */         this.shgameContainer.send(attack);
/* 585:    */         
/* 586:685 */         List<AgentModel> affected = this.worldModel.getAttackableAgentsInRange(this.selectedAgent, area / 2.0F);
/* 587:686 */         affected.add(this.selectedAgent);
/* 588:687 */         fireBullets(marine, this.selectedAgent.getX(), this.selectedAgent.getY(), attackWeapon);
/* 589:688 */         this.shgameContainer.playSound(attackWeapon.getSound());
/* 590:689 */         for (int i = 0; i < affected.size(); i++)
/* 591:    */         {
/* 592:691 */           System.out.println(i);
/* 593:692 */           if (attackWeapon.attack(marine, (AgentModel)affected.get(i), this.worldModel))
/* 594:    */           {
/* 595:694 */             AgentRemove remove = new AgentRemove();
/* 596:695 */             remove.UUID = ((AgentModel)affected.get(i)).getUUID();
/* 597:696 */             remove.killed = true;
/* 598:697 */             this.removeList.add(remove);
/* 599:698 */             removeAgent(((AgentModel)affected.get(i)).getUUID(), true);
/* 600:    */           }
/* 601:    */         }
/* 602:701 */         if (attackWeapon.isFlameWeapon())
/* 603:    */         {
/* 604:703 */           System.out.println("Setting fire");
/* 605:704 */           setFire(this.selectedAgent, (int)area);
/* 606:    */         }
/* 607:    */       }
/* 608:    */     }
/* 609:    */   }
/* 610:    */   
/* 611:    */   public void setFire(AgentModel model, int area)
/* 612:    */   {
/* 613:711 */     float x = model.getX();
/* 614:712 */     float y = model.getY();
/* 615:713 */     float startX = x - (int)Math.floor(area / 2);
/* 616:714 */     float startY = y - (int)Math.floor(area / 2);
/* 617:716 */     for (int px = (int)startX; px < startX + area; px++) {
/* 618:718 */       for (int py = (int)startY; py < startY + area; py++)
/* 619:    */       {
/* 620:721 */         int uuid = PlaceFire(px, py);
/* 621:722 */         FireModel m = (FireModel)this.worldModel.getAgentModel(uuid);
/* 622:723 */         this.shgameContainer.send(m.getPlacement());
/* 623:724 */         this.fires.add(Integer.valueOf(uuid));
/* 624:    */       }
/* 625:    */     }
/* 626:    */   }
/* 627:    */   
/* 628:    */   public void yourTurn()
/* 629:    */   {
/* 630:733 */     super.yourTurn();
/* 631:734 */     this.state = GameState.State.PLACEMENT;
/* 632:735 */     this.turnState = GameplayState.TurnState.YOUR;
/* 633:736 */     this.worldModel.GiveBackAp(this.shgameContainer.getFaction());
/* 634:737 */     this.feedbackText.setText("It is your turn!");
/* 635:739 */     for (int i = 0; i < this.fires.size(); i++)
/* 636:    */     {
/* 637:741 */       AgentRemove remove = new AgentRemove();
/* 638:742 */       remove.UUID = ((Integer)this.fires.get(i)).intValue();
/* 639:743 */       System.out.println("Send remove " + this.fires.get(i));
/* 640:744 */       remove.killed = false;
/* 641:745 */       this.removeList.add(remove);
/* 642:746 */       removeAgent(((Integer)this.fires.get(i)).intValue(), false);
/* 643:    */     }
/* 644:748 */     this.fires.clear();
/* 645:749 */     this.endTurn.setVisible(true);
/* 646:    */     
/* 647:751 */     deSelect();
/* 648:    */     
/* 649:753 */     this.agentsPlaced = 0;
/* 650:    */   }
/* 651:    */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.states.AlienGamePlayState
 * JD-Core Version:    0.7.0.1
 */