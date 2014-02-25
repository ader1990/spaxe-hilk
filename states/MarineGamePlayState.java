/*    1:     */ package sh.states;
/*    2:     */ 
/*    3:     */ import de.matthiasmann.twl.Button;
/*    4:     */ import de.matthiasmann.twl.CallbackWithReason;
/*    5:     */ import de.matthiasmann.twl.Label;
/*    6:     */ import de.matthiasmann.twl.ListBox;
/*    7:     */ import de.matthiasmann.twl.ListBox.CallbackReason;
/*    8:     */ import de.matthiasmann.twl.model.ListModel;
/*    9:     */ import de.matthiasmann.twl.model.SimpleChangableListModel;
/*   10:     */ import java.io.PrintStream;
/*   11:     */ import java.util.ArrayList;
/*   12:     */ import java.util.LinkedList;
/*   13:     */ import java.util.List;
/*   14:     */ import org.newdawn.slick.GameContainer;
/*   15:     */ import org.newdawn.slick.Input;
/*   16:     */ import org.newdawn.slick.SlickException;
/*   17:     */ import org.newdawn.slick.state.StateBasedGame;
/*   18:     */ import sh.Selector.Target;
/*   19:     */ import sh.SpaceHulkGameContainer;
/*   20:     */ import sh.agent.AgentModel;
/*   21:     */ import sh.agent.AgentView;
/*   22:     */ import sh.agent.aliens.BlipModel;
/*   23:     */ import sh.agent.door.DoorModel;
/*   24:     */ import sh.agent.fire.FireModel;
/*   25:     */ import sh.agent.marines.MarineModel;
/*   26:     */ import sh.agent.movement.PathAgentModel;
/*   27:     */ import sh.agent.weapons.Weapon;
/*   28:     */ import sh.gameobject.startposition.StartPositionModel;
/*   29:     */ import sh.gui.widgets.InfoBox;
/*   30:     */ import sh.gui.widgets.MarinePlacement;
/*   31:     */ import sh.gui.widgets.Objectives;
/*   32:     */ import sh.multiplayer.AgentMovement;
/*   33:     */ import sh.multiplayer.AgentRemove;
/*   34:     */ import sh.multiplayer.Attack;
/*   35:     */ import sh.multiplayer.EndInterrupt;
/*   36:     */ import sh.multiplayer.Interrupt;
/*   37:     */ import sh.multiplayer.InterruptRequest;
/*   38:     */ import sh.utils.Diceroller;
/*   39:     */ import sh.world.shworld.SpaceHulkWorldModel;
/*   40:     */ import sh.world.shworld.SpaceHulkWorldView;
/*   41:     */ 
/*   42:     */ public class MarineGamePlayState
/*   43:     */   extends MarineGuiState
/*   44:     */ {
/*   45:  53 */   protected int chosenMarineType = 0;
/*   46:     */   
/*   47:     */   public MarineGamePlayState(int stateID)
/*   48:     */   {
/*   49:  57 */     super(stateID);
/*   50:     */   }
/*   51:     */   
/*   52:     */   protected void attack(AgentModel model, Weapon weapon)
/*   53:     */   {
/*   54:  64 */     MarineModel marine = (MarineModel)this.selectedAgent;
/*   55:  65 */     disableOverwatch(marine);
/*   56:     */     
/*   57:  67 */     int cost = weapon.getFirecost();
/*   58:  69 */     if ((canPerformAction(cost, marine)) && (weapon.getAmmo() != 0))
/*   59:     */     {
/*   60:  71 */       withdraw(cost, marine);
/*   61:     */       
/*   62:  73 */       float area = weapon.getArea();
/*   63:  74 */       marine.setFloatingText("Attacking");
/*   64:  75 */       model.setFloatingText("Defending");
/*   65:  76 */       List<AgentModel> affected = this.worldModel.getAttackableAgentsInRange(model, area / 2.0F);
/*   66:  77 */       affected.add(model);
/*   67:     */       
/*   68:  79 */       TargetAffected(affected, marine, weapon, model.getX(), model.getY());
/*   69:     */     }
/*   70:     */   }
/*   71:     */   
/*   72:     */   protected void attackGround(float tx, float ty, Weapon weapon)
/*   73:     */   {
/*   74:  86 */     MarineModel marine = (MarineModel)this.selectedAgent;
/*   75:  87 */     this.attackinGround = false;
/*   76:  88 */     disableOverwatch(marine);
/*   77:  89 */     int cost = weapon.getFirecost();
/*   78:  93 */     if ((canPerformAction(cost, marine)) && (weapon.getAmmo() != 0))
/*   79:     */     {
/*   80:  95 */       withdraw(cost, marine);
/*   81:     */       
/*   82:  97 */       float area = weapon.getArea();
/*   83:  98 */       marine.setFloatingText("Attack ground");
/*   84:     */       
/*   85: 100 */       List<AgentModel> affected = this.worldModel.getAttackableAgentsInRange(tx, ty, area / 2.0F);
/*   86:     */       
/*   87: 102 */       TargetAffected(affected, marine, weapon, tx, ty);
/*   88:     */     }
/*   89:     */   }
/*   90:     */   
/*   91:     */   private void callBacks()
/*   92:     */   {
/*   93: 109 */     this.attackGround.addCallback(new Runnable()
/*   94:     */     {
/*   95:     */       public void run()
/*   96:     */       {
/*   97: 114 */         MarineGamePlayState.this.attackinGround = true;
/*   98:     */       }
/*   99: 119 */     });
/*  100: 120 */     this.overwatch.addCallback(new Runnable()
/*  101:     */     {
/*  102:     */       public void run()
/*  103:     */       {
/*  104: 125 */         if ((MarineGamePlayState.this.agentState == GameplayState.AgentState.MOVING) && (MarineGamePlayState.this.state == GameState.State.PLAY) && 
/*  105: 126 */           ((MarineGamePlayState.this.turnState == GameplayState.TurnState.YOUR) || (MarineGamePlayState.this.turnState == GameplayState.TurnState.INTERRUPT)) && 
/*  106: 127 */           (MarineGamePlayState.this.selectedAgent != null))
/*  107:     */         {
/*  108: 129 */           if (MarineGamePlayState.this.setOverwatch(MarineGamePlayState.this.shgameContainer))
/*  109:     */           {
/*  110: 131 */             MarineGamePlayState.this.shgameContainer.playSound("click");
/*  111: 132 */             MarineGamePlayState.this.overwatch.setVisible(false);
/*  112: 133 */             MarineGamePlayState.this.cancelOverwatch.setVisible(true);
/*  113:     */             
/*  114: 135 */             MarineGamePlayState.this.feedbackText.setText("Seting overwatch");
/*  115:     */           }
/*  116:     */         }
/*  117:     */         else {
/*  118: 140 */           MarineGamePlayState.this.feedbackText.setText("Not enough points to set Overwatch");
/*  119:     */         }
/*  120:     */       }
/*  121: 144 */     });
/*  122: 145 */     this.cancelOverwatch.addCallback(new Runnable()
/*  123:     */     {
/*  124:     */       public void run()
/*  125:     */       {
/*  126: 151 */         if ((MarineGamePlayState.this.selectedAgent != null) && ((MarineGamePlayState.this.selectedAgent instanceof MarineModel)))
/*  127:     */         {
/*  128: 153 */           MarineGamePlayState.this.shgameContainer.playSound("click");
/*  129: 154 */           MarineModel m = (MarineModel)MarineGamePlayState.this.selectedAgent;
/*  130: 155 */           m.setOverwatch(false);
/*  131: 156 */           MarineGamePlayState.this.overwatch.setVisible(true);
/*  132: 157 */           MarineGamePlayState.this.cancelOverwatch.setVisible(false);
/*  133: 158 */           MarineGamePlayState.this.feedbackText.setText("Overwatch cancelled");
/*  134:     */         }
/*  135:     */       }
/*  136: 162 */     });
/*  137: 163 */     this.rotate.addCallback(new Runnable()
/*  138:     */     {
/*  139:     */       public void run()
/*  140:     */       {
/*  141: 169 */         if ((MarineGamePlayState.this.agentState == GameplayState.AgentState.MOVING) && 
/*  142: 170 */           (MarineGamePlayState.this.state == GameState.State.PLAY) && 
/*  143: 171 */           ((MarineGamePlayState.this.turnState == GameplayState.TurnState.YOUR) || (MarineGamePlayState.this.turnState == GameplayState.TurnState.INTERRUPT)) && 
/*  144: 172 */           (MarineGamePlayState.this.selectedAgent != null))
/*  145:     */         {
/*  146: 174 */           MarineGamePlayState.this.shgameContainer.playSound("click");
/*  147: 175 */           MarineGamePlayState.this.finishRotate.setVisible(true);
/*  148: 176 */           MarineGamePlayState.this.rotate.setVisible(false);
/*  149: 177 */           MarineGamePlayState.this.attackGround.setVisible(false);
/*  150: 178 */           MarineGamePlayState.this.overwatch.setVisible(false);
/*  151: 179 */           MarineGamePlayState.this.startRotation(MarineGamePlayState.this.shgameContainer);
/*  152: 180 */           MarineGamePlayState.this.feedbackText.setText("Rotate Marine");
/*  153:     */         }
/*  154:     */       }
/*  155: 186 */     });
/*  156: 187 */     this.finishRotate.addCallback(new Runnable()
/*  157:     */     {
/*  158:     */       public void run()
/*  159:     */       {
/*  160: 193 */         if ((MarineGamePlayState.this.state == GameState.State.PLACEMENT) && (MarineGamePlayState.this.agentState == GameplayState.AgentState.TURNING) && 
/*  161: 194 */           (MarineGamePlayState.this.selectedAgent != null))
/*  162:     */         {
/*  163: 196 */           MarineGamePlayState.this.shgameContainer.playSound("click");
/*  164: 197 */           MarineGamePlayState.this.finishInitialTurning(MarineGamePlayState.this.shgameContainer, 0);
/*  165:     */           
/*  166:     */ 
/*  167: 200 */           MarineGamePlayState.this.setSelected(MarineGamePlayState.this.selectedAgent);
/*  168: 201 */           MarineGamePlayState.this.feedbackText.setText("Rotating Marine finished");
/*  169:     */         }
/*  170: 204 */         else if ((MarineGamePlayState.this.agentState == GameplayState.AgentState.TURNING) && 
/*  171: 205 */           (MarineGamePlayState.this.state == GameState.State.PLAY) && 
/*  172: 206 */           ((MarineGamePlayState.this.turnState == GameplayState.TurnState.YOUR) || (MarineGamePlayState.this.turnState == GameplayState.TurnState.INTERRUPT)) && 
/*  173: 207 */           (MarineGamePlayState.this.selectedAgent != null))
/*  174:     */         {
/*  175: 209 */           MarineGamePlayState.this.shgameContainer.playSound("click");
/*  176:     */           
/*  177: 211 */           MarineGamePlayState.this.finishRotation(MarineGamePlayState.this.shgameContainer);
/*  178: 212 */           MarineGamePlayState.this.setSelected(MarineGamePlayState.this.selectedAgent);
/*  179: 213 */           MarineGamePlayState.this.feedbackText.setText("Rotating Marine finished 2");
/*  180:     */         }
/*  181:     */       }
/*  182: 217 */     });
/*  183: 218 */     this.endTurn.addCallback(new Runnable()
/*  184:     */     {
/*  185:     */       public void run()
/*  186:     */       {
/*  187: 223 */         if (MarineGamePlayState.this.turnState == GameplayState.TurnState.YOUR)
/*  188:     */         {
/*  189: 225 */           MarineGamePlayState.this.shgameContainer.playSound("click");
/*  190: 226 */           MarineGamePlayState.this.endTurn(MarineGamePlayState.this.shgameContainer);
/*  191: 227 */           MarineGamePlayState.this.deSelect();
/*  192: 228 */           MarineGamePlayState.this.rotate.setVisible(false);
/*  193: 229 */           MarineGamePlayState.this.finishRotate.setVisible(false);
/*  194: 230 */           MarineGamePlayState.this.attackGround.setVisible(false);
/*  195: 231 */           MarineGamePlayState.this.overwatch.setVisible(false);
/*  196: 232 */           MarineGamePlayState.this.cancelOverwatch.setVisible(false);
/*  197: 233 */           MarineGamePlayState.this.closeAlienStart.setVisible(false);
/*  198: 234 */           MarineGamePlayState.this.apLabel.setVisible(false);
/*  199: 235 */           MarineGamePlayState.this.cpLabel.setVisible(false);
/*  200: 236 */           MarineGamePlayState.this.endTurn.setVisible(false);
/*  201: 237 */           MarineGamePlayState.this.closeAlienStart.setVisible(false);
/*  202: 238 */           MarineGamePlayState.this.pickup.setVisible(false);
/*  203:     */         }
/*  204:     */       }
/*  205: 243 */     });
/*  206: 244 */     this.weaponList.addCallback(new CallbackWithReason()
/*  207:     */     {
/*  208:     */       public void callback(ListBox.CallbackReason arg)
/*  209:     */       {
/*  210:     */         try
/*  211:     */         {
/*  212: 252 */           int index = MarineGamePlayState.this.weaponList.getSelected();
/*  213: 254 */           if ((index >= 0) && (MarineGamePlayState.this.selectedAgent != null))
/*  214:     */           {
/*  215: 256 */             String weaponName = (String)MarineGamePlayState.this.weaponList.getModel().getEntry(index);
/*  216: 257 */             MarineGamePlayState.this.selectedWeapon = MarineGamePlayState.this.selectedAgent.getWeapon(weaponName);
/*  217: 258 */             if (MarineGamePlayState.this.selectedWeapon.isAttackGround()) {
/*  218: 260 */               MarineGamePlayState.this.attackGround.setVisible(true);
/*  219:     */             } else {
/*  220: 264 */               MarineGamePlayState.this.attackGround.setVisible(false);
/*  221:     */             }
/*  222: 266 */             MarineGamePlayState.this.selectedAgent.setCurrentWeapon(index);
/*  223: 267 */             System.out.println("Selected weapon " + MarineGamePlayState.this.selectedWeapon.getName());
/*  224:     */           }
/*  225:     */         }
/*  226:     */         catch (Exception localException) {}
/*  227:     */       }
/*  228: 277 */     });
/*  229: 278 */     this.marineTypesList.addCallback(new CallbackWithReason()
/*  230:     */     {
/*  231:     */       public void callback(ListBox.CallbackReason arg)
/*  232:     */       {
/*  233:     */         try
/*  234:     */         {
/*  235: 285 */           int index = MarineGamePlayState.this.marineTypesList.getSelected();
/*  236: 287 */           if (index >= 0)
/*  237:     */           {
/*  238: 289 */             MarineGamePlayState.this.chosenMarineType = index;
/*  239: 290 */             String marineChosen = (String)MarineGamePlayState.this.marineTypesList.getModel().getEntry(index);
/*  240: 291 */             MarineGamePlayState.this.marineTypeLabel.setText(marineChosen);
/*  241: 292 */             System.out.println("Chosen marine " + MarineGamePlayState.this.chosenMarineType);
/*  242: 293 */             MarineGamePlayState.this.cost.setText("Cost: 1");
/*  243: 294 */             MarineGamePlayState.this.description.setText("Description for " + marineChosen);
/*  244:     */           }
/*  245:     */         }
/*  246:     */         catch (Exception localException) {}
/*  247:     */       }
/*  248: 303 */     });
/*  249: 304 */     this.closeAlienStart.addCallback(new Runnable()
/*  250:     */     {
/*  251:     */       public void run()
/*  252:     */       {
/*  253: 309 */         if ((MarineGamePlayState.this.agentState == GameplayState.AgentState.MOVING) && (MarineGamePlayState.this.state == GameState.State.PLAY) && 
/*  254: 310 */           ((MarineGamePlayState.this.turnState == GameplayState.TurnState.YOUR) || (MarineGamePlayState.this.turnState == GameplayState.TurnState.INTERRUPT)) && 
/*  255: 311 */           (MarineGamePlayState.this.selectedAgent != null)) {
/*  256: 313 */           if ((MarineGamePlayState.this.worldModel.standsOnOpenStartPosition(MarineGamePlayState.this.selectedAgent.getX(), MarineGamePlayState.this.selectedAgent.getY())) && (MarineGamePlayState.this.canPerformAction(1, (MarineModel)MarineGamePlayState.this.selectedAgent)))
/*  257:     */           {
/*  258: 315 */             MarineGamePlayState.this.shgameContainer.playSound("click");
/*  259: 316 */             StartPositionModel startPosition = MarineGamePlayState.this.worldModel.getOpenStartPosition(MarineGamePlayState.this.selectedAgent.getX(), MarineGamePlayState.this.selectedAgent.getY());
/*  260: 317 */             MarineGamePlayState.this.feedbackText.setText("Start position closed");
/*  261: 318 */             AgentMovement move = new AgentMovement();
/*  262: 319 */             move.UUID = startPosition.getUUID();
/*  263: 320 */             move.open = true;
/*  264: 321 */             MarineGamePlayState.this.movementOutgoingList.add(move);
/*  265: 322 */             MarineGamePlayState.this.withdraw(1, (MarineModel)MarineGamePlayState.this.selectedAgent);
/*  266:     */             
/*  267: 324 */             MarineGamePlayState.this.closeAlienStart.setVisible(false);
/*  268:     */           }
/*  269:     */         }
/*  270:     */       }
/*  271:     */     });
/*  272:     */   }
/*  273:     */   
/*  274:     */   private boolean canPerformAction(int cost, MarineModel model)
/*  275:     */   {
/*  276: 334 */     if (model.getAp() + this.CP >= cost)
/*  277:     */     {
/*  278: 336 */       System.out.println("Can perform action ");
/*  279: 337 */       return true;
/*  280:     */     }
/*  281: 339 */     return false;
/*  282:     */   }
/*  283:     */   
/*  284:     */   private void checkOverwatch()
/*  285:     */   {
/*  286: 344 */     ArrayList<MarineModel> overwatchList = this.worldModel
/*  287: 345 */       .getOverWatchList(this.shgameContainer.getTeam());
/*  288: 347 */     for (MarineModel marine : overwatchList) {
/*  289: 349 */       if ((this.worldModel.lineOfSight(this.selectedAgent, marine)) && (marine.getTeam() != this.selectedAgent.getTeam()))
/*  290:     */       {
/*  291: 351 */         Weapon attackWeapon = marine.getCurrentWeapon();
/*  292:     */         
/*  293:     */ 
/*  294: 354 */         float area = attackWeapon.getArea();
/*  295:     */         
/*  296: 356 */         List<AgentModel> affected = this.worldModel.getAttackableAgentsInRange(this.selectedAgent, area / 2.0F);
/*  297: 357 */         affected.add(this.selectedAgent);
/*  298: 358 */         marine.setFloatingText("Attacking");
/*  299: 359 */         this.selectedAgent.setFloatingText("Defending");
/*  300: 360 */         Attack attack = new Attack();
/*  301: 361 */         attack.attacker = marine.getUUID();
/*  302: 362 */         attack.defender = this.selectedAgent.getUUID();
/*  303: 363 */         attack.ammo = attackWeapon.getAmmo();
/*  304: 364 */         attack.sound = attackWeapon.getSound();
/*  305: 365 */         this.shgameContainer.send(attack);
/*  306: 366 */         this.shgameContainer.playSound(attackWeapon.getSound());
/*  307: 367 */         for (int i = 0; i < affected.size(); i++) {
/*  308: 370 */           if (attackWeapon.attack(marine, (AgentModel)affected.get(i), this.worldModel))
/*  309:     */           {
/*  310: 372 */             AgentRemove remove = new AgentRemove();
/*  311: 373 */             remove.UUID = ((AgentModel)affected.get(i)).getUUID();
/*  312: 374 */             remove.killed = true;
/*  313: 375 */             this.removeList.add(remove);
/*  314: 376 */             removeAgent(((AgentModel)affected.get(i)).getUUID(), true);
/*  315:     */           }
/*  316:     */         }
/*  317: 379 */         if (attackWeapon.isFlameWeapon()) {
/*  318: 382 */           setFire(this.selectedAgent.getX(), this.selectedAgent.getY(), (int)area);
/*  319:     */         }
/*  320:     */       }
/*  321:     */     }
/*  322:     */   }
/*  323:     */   
/*  324:     */   public void deSelect()
/*  325:     */   {
/*  326: 391 */     this.infobox.setVisible(false);
/*  327: 392 */     this.apLabel.setVisible(false);
/*  328: 393 */     this.cpLabel.setVisible(false);
/*  329: 394 */     this.rotate.setVisible(false);
/*  330: 395 */     this.pickup.setVisible(false);
/*  331: 396 */     this.attackGround.setVisible(false);
/*  332: 397 */     this.closeAlienStart.setVisible(false);
/*  333: 398 */     this.finishRotate.setVisible(false);
/*  334: 399 */     this.overwatch.setVisible(false);
/*  335: 400 */     this.cancelOverwatch.setVisible(false);
/*  336: 401 */     this.marineName.setVisible(false);
/*  337: 402 */     this.weaponList.setVisible(false);
/*  338: 403 */     this.closeAlienStart.setVisible(false);
/*  339: 404 */     this.selectedWeapon = null;
/*  340: 405 */     if (this.selectedAgent != null)
/*  341:     */     {
/*  342: 407 */       this.selectedAgent.setSelected(false);
/*  343:     */       
/*  344: 409 */       this.selectedAgent = null;
/*  345:     */     }
/*  346:     */   }
/*  347:     */   
/*  348:     */   private void disableOverwatch(MarineModel marine)
/*  349:     */   {
/*  350: 416 */     if (marine.isOverwatch())
/*  351:     */     {
/*  352: 418 */       marine.setOverwatch(false);
/*  353: 419 */       this.cancelOverwatch.setVisible(false);
/*  354: 420 */       this.overwatch.setVisible(true);
/*  355: 421 */       AgentMovement move = marine.getMovement(0);
/*  356: 422 */       move.overwatch = false;
/*  357: 423 */       this.movementOutgoingList.add(move);
/*  358:     */     }
/*  359:     */   }
/*  360:     */   
/*  361:     */   private void endInterrupt(SpaceHulkGameContainer cont)
/*  362:     */   {
/*  363: 430 */     EndInterrupt inter = new EndInterrupt();
/*  364: 431 */     inter.player = this.shgameContainer.getPlayerId();
/*  365: 432 */     cont.send(inter);
/*  366: 433 */     this.feedbackText.setText("Waiting for others turn");
/*  367: 434 */     this.state = GameState.State.OTHERTURN;
/*  368: 435 */     this.turnState = GameplayState.TurnState.OTHER;
/*  369:     */   }
/*  370:     */   
/*  371:     */   public void enter(GameContainer container, StateBasedGame game)
/*  372:     */     throws SlickException
/*  373:     */   {
/*  374: 444 */     super.enter(container, game);
/*  375: 445 */     this.shgameContainer = ((SpaceHulkGameContainer)container);
/*  376:     */     
/*  377:     */ 
/*  378: 448 */     this.apLabel.setVisible(false);
/*  379: 449 */     this.pickup.setVisible(false);
/*  380: 450 */     this.rotate.setVisible(false);
/*  381: 451 */     this.attackGround.setVisible(false);
/*  382: 452 */     this.closeAlienStart.setVisible(false);
/*  383: 453 */     this.finishRotate.setVisible(false);
/*  384: 454 */     this.overwatch.setVisible(false);
/*  385: 455 */     this.cancelOverwatch.setVisible(false);
/*  386: 456 */     this.weaponList.setVisible(false);
/*  387: 457 */     this.closeAlienStart.setVisible(false);
/*  388: 458 */     this.marinePlacement.setVisible(false);
/*  389: 459 */     this.endTurn.setVisible(false);
/*  390: 460 */     System.out.println("Mission " + this.shgameContainer.getMission());
/*  391: 461 */     this.objectivesbox.setObjective(this.shgameContainer.getMission());
/*  392: 463 */     if (this.shgameContainer.getPlayerId() == 0)
/*  393:     */     {
/*  394: 465 */       this.state = GameState.State.PLACEMENT;
/*  395: 466 */       this.marinePlacement.setVisible(true);
/*  396: 467 */       this.turnState = GameplayState.TurnState.YOUR;
/*  397: 468 */       this.CP = Diceroller.roll(6);
/*  398:     */       
/*  399:     */ 
/*  400: 471 */       this.pointsLeft.setText("Points: " + (this.shgameContainer.getPoints() - this.agentsPlaced));
/*  401: 472 */       this.cpLabel.setText("CP : " + this.CP);
/*  402: 473 */       this.cpLabel.setVisible(true);
/*  403: 474 */       this.endTurn.setVisible(true);
/*  404: 475 */       this.feedbackText.setText("Place a marine...");
/*  405:     */     }
/*  406:     */     else
/*  407:     */     {
/*  408: 478 */       this.state = GameState.State.OTHERTURN;
/*  409: 479 */       this.turnState = GameplayState.TurnState.OTHER;
/*  410: 480 */       deSelect();
/*  411: 481 */       this.feedbackText.setText("Waiting for others turn");
/*  412:     */     }
/*  413: 485 */     callBacks();
/*  414:     */   }
/*  415:     */   
/*  416:     */   private void finishInitialTurning(SpaceHulkGameContainer cont, int cost)
/*  417:     */   {
/*  418: 490 */     AgentMovement move = this.selectedAgent.getMovement(cost);
/*  419: 491 */     cont.send(move);
/*  420: 492 */     this.agentState = GameplayState.AgentState.MOVING;
/*  421:     */     
/*  422: 494 */     this.agentsPlaced += 1;
/*  423:     */     
/*  424:     */ 
/*  425: 497 */     this.pointsLeft.setText("Points: " + (cont.getPoints() - this.agentsPlaced));
/*  426: 498 */     if ((!this.worldModel.hasStartPositions(this.shgameContainer.getPlayerId())) || (this.agentsPlaced >= cont.getPoints()))
/*  427:     */     {
/*  428: 500 */       this.apLabel.setText("AP " + this.selectedAgent.getAp());
/*  429: 501 */       this.cpLabel.setText("CP " + this.CP);
/*  430: 502 */       setSelected(this.selectedAgent);
/*  431: 503 */       this.marinePlacement.setVisible(false);
/*  432: 504 */       this.state = GameState.State.PLAY;
/*  433:     */     }
/*  434:     */     else
/*  435:     */     {
/*  436: 507 */       deSelect();
/*  437: 508 */       this.feedbackText.setText("You can place a new Marine");
/*  438:     */     }
/*  439:     */   }
/*  440:     */   
/*  441:     */   protected boolean finishRotation(SpaceHulkGameContainer cont)
/*  442:     */   {
/*  443: 514 */     int cost = this.selectedAgent.rotatingCost(this.turned);
/*  444: 515 */     MarineModel marine = (MarineModel)this.selectedAgent;
/*  445: 517 */     if (canPerformAction(cost, marine))
/*  446:     */     {
/*  447: 520 */       withdraw(cost, marine);
/*  448: 521 */       this.agentState = GameplayState.AgentState.MOVING;
/*  449: 522 */       this.turned = 0.0F;
/*  450: 523 */       marine.setOverwatch(false);
/*  451: 524 */       AgentMovement movement = this.selectedAgent.getMovement(cost);
/*  452: 525 */       this.movementOutgoingList.add(movement);
/*  453: 526 */       this.apLabel.setText("AP " + marine.getAp());
/*  454: 527 */       this.cpLabel.setText("CP " + this.CP);
/*  455: 528 */       return true;
/*  456:     */     }
/*  457: 530 */     return false;
/*  458:     */   }
/*  459:     */   
/*  460:     */   private void forceConvertBlips(float tx, float ty)
/*  461:     */   {
/*  462: 535 */     ArrayList<BlipModel> models = this.worldModel
/*  463: 536 */       .lineOfSightMarineBlips((MarineModel)this.selectedAgent);
/*  464: 538 */     for (int i = 0; i < models.size(); i++)
/*  465:     */     {
/*  466: 540 */       BlipModel model = (BlipModel)models.get(i);
/*  467: 541 */       ArrayList<Target> targets = this.worldModel.getAlienPlaces(model);
/*  468: 543 */       for (int j = 0; (j < targets.size()) && (j < model.getStealers()); j++)
/*  469:     */       {
/*  470: 545 */         Target t = (Target)targets.get(j);
/*  471: 546 */         int UUID = PlaceAgent(t.x, t.y, model.getAngle(), 
/*  472: 547 */           "Aliens", true, model.getPlayer(), 
/*  473: 548 */           model.getTeam(), model.getColour());
/*  474:     */         
/*  475: 550 */         this.agentPlacement.add(this.worldModel.getAgentModel(UUID)
/*  476: 551 */           .getPlacement());
/*  477:     */       }
/*  478: 554 */       AgentRemove remove = new AgentRemove();
/*  479: 555 */       remove.UUID = model.getUUID();
/*  480: 556 */       remove.killed = false;
/*  481: 557 */       this.removeList.add(remove);
/*  482: 558 */       removeAgent(model.getUUID(), false);
/*  483:     */     }
/*  484:     */   }
/*  485:     */   
/*  486:     */   private void gamePlayClick(float tx, float ty)
/*  487:     */   {
/*  488: 565 */     AgentModel model = this.worldModel.getAgentOnTile(tx, ty);
/*  489: 566 */     MarineModel marine = (MarineModel)this.selectedAgent;
/*  490: 569 */     if (model == null)
/*  491:     */     {
/*  492: 571 */       float x = tx + 0.5F;
/*  493: 572 */       float y = ty + 0.5F;
/*  494: 573 */       System.out.println(" model is null");
/*  495: 576 */       if (this.attackinGround)
/*  496:     */       {
/*  497: 578 */         System.out.println("Attacking ground");
/*  498: 579 */         this.attackinGround = false;
/*  499:     */         
/*  500: 581 */         Weapon attackWeapon = this.selectedAgent.getCurrentWeapon();
/*  501: 582 */         if ((attackWeapon.isAttackGround()) && (this.worldModel.lineOfSight(marine, x, y)))
/*  502:     */         {
/*  503: 584 */           System.out.println("weapon is right");
/*  504:     */           
/*  505:     */ 
/*  506:     */ 
/*  507: 588 */           float distance = SpaceHulkWorldModel.getEuclideanDistance(marine.getX(), marine.getY(), x, y);
/*  508: 590 */           if (distance <= attackWeapon.getRange())
/*  509:     */           {
/*  510: 592 */             System.out.println("in range");
/*  511:     */             
/*  512: 594 */             attackGround(x, y, attackWeapon);
/*  513:     */           }
/*  514:     */         }
/*  515:     */       }
/*  516: 599 */       else if (this.worldModel.tileWalkable(x, y))
/*  517:     */       {
/*  518: 601 */         System.out.println("walkin");
/*  519: 602 */         walking(tx, ty);
/*  520:     */       }
/*  521:     */       else
/*  522:     */       {
/*  523: 606 */         System.out.println("Not walkin");
/*  524:     */       }
/*  525:     */     }
/*  526: 609 */     else if ((model instanceof DoorModel))
/*  527:     */     {
/*  528: 611 */       DoorModel door = (DoorModel)model;
/*  529: 613 */       if (door.isOpen())
/*  530:     */       {
/*  531: 615 */         if (this.worldModel.tileWalkable(tx, ty)) {
/*  532: 617 */           walking(tx, ty);
/*  533:     */         }
/*  534:     */       }
/*  535: 620 */       else if (!door.isOpen()) {
/*  536: 623 */         OpenDoor((DoorModel)model);
/*  537:     */       }
/*  538:     */     }
/*  539: 629 */     else if ((!model.getFaction().equalsIgnoreCase("fires")) && (model.getTeam() != this.shgameContainer.getTeam()) && (this.worldModel.lineOfSight(marine, (PathAgentModel)model)))
/*  540:     */     {
/*  541: 631 */       Weapon attackWeapon = this.selectedAgent.getCurrentWeapon();
/*  542:     */       
/*  543:     */ 
/*  544:     */ 
/*  545: 635 */       float distance = SpaceHulkWorldModel.getEuclideanDistance(marine, model);
/*  546: 637 */       if (distance <= attackWeapon.getRange()) {
/*  547: 640 */         attack(model, attackWeapon);
/*  548:     */       }
/*  549:     */     }
/*  550:     */   }
/*  551:     */   
/*  552:     */   private void interrupt(SpaceHulkGameContainer cont)
/*  553:     */   {
/*  554: 649 */     if ((this.shgameContainer.isServer()) && (!this.interruptionServerToggle))
/*  555:     */     {
/*  556: 651 */       Interrupt inter = new Interrupt();
/*  557: 652 */       this.interruptionServerToggle = true;
/*  558: 653 */       inter.interrupt = true;
/*  559: 654 */       inter.player = this.shgameContainer.getPlayerId();
/*  560: 655 */       this.state = GameState.State.PLAY;
/*  561: 656 */       this.turnState = GameplayState.TurnState.INTERRUPT;
/*  562: 657 */       cont.send(inter);
/*  563: 658 */       deSelect();
/*  564: 659 */       this.feedbackText.setText("Interrupt with " + this.CP + " CP");
/*  565:     */     }
/*  566:     */     else
/*  567:     */     {
/*  568: 663 */       InterruptRequest inter = new InterruptRequest();
/*  569: 664 */       inter.player = this.shgameContainer.getPlayerId();
/*  570: 665 */       cont.send(inter);
/*  571: 666 */       this.feedbackText.setText("Trying to interrupt with " + this.CP + " CP");
/*  572:     */     }
/*  573:     */   }
/*  574:     */   
/*  575:     */   public void mouseClicked(int button, int x, int y, int clickCount)
/*  576:     */   {
/*  577: 675 */     int worldX = -this.cameraPositionX + (int)(x / this.scaleX);
/*  578: 676 */     int worldY = -this.cameraPositionY + (int)(y / this.scaleY);
/*  579:     */     
/*  580: 678 */     float tx = worldX / this.worldModel.getTileWidth();
/*  581: 679 */     float ty = worldY / this.worldModel.getTileHeight();
/*  582: 681 */     if ((this.turnState == GameplayState.TurnState.YOUR) || (this.turnState == GameplayState.TurnState.INTERRUPT))
/*  583:     */     {
/*  584: 683 */       if (button == 0)
/*  585:     */       {
/*  586: 685 */         if ((this.state == GameState.State.PLACEMENT) && 
/*  587: 686 */           (this.agentState != GameplayState.AgentState.TURNING)) {
/*  588: 689 */           placeAgents(tx, ty);
/*  589:     */         }
/*  590: 691 */         if ((this.state == GameState.State.PLAY) && (this.agentState != GameplayState.AgentState.TURNING))
/*  591:     */         {
/*  592: 693 */           System.out.println("Play");
/*  593: 694 */           if (this.selectedAgent == null) {
/*  594: 696 */             selectAgent(tx, ty);
/*  595:     */           } else {
/*  596: 699 */             gamePlayClick(tx, ty);
/*  597:     */           }
/*  598:     */         }
/*  599:     */       }
/*  600: 703 */       if ((button == 1) && (this.state != GameState.State.PLACEMENT) && (this.agentState != GameplayState.AgentState.TURNING)) {
/*  601: 706 */         deSelect();
/*  602:     */       }
/*  603:     */     }
/*  604:     */   }
/*  605:     */   
/*  606:     */   private void OpenDoor(DoorModel door)
/*  607:     */   {
/*  608: 713 */     int cost = 1;
/*  609: 714 */     MarineModel marine = (MarineModel)this.selectedAgent;
/*  610: 715 */     if (canPerformAction(cost, marine)) {
/*  611: 718 */       if (marine.fov(door.getX(), door.getY()))
/*  612:     */       {
/*  613: 720 */         float distance = SpaceHulkWorldModel.getEuclideanDistance(this.selectedAgent, 
/*  614: 721 */           door);
/*  615: 722 */         if (distance <= 1.0F)
/*  616:     */         {
/*  617: 724 */           System.out.println("opening door");
/*  618: 725 */           door.open();
/*  619: 726 */           this.shgameContainer.playSound("door");
/*  620: 727 */           AgentMovement move = new AgentMovement();
/*  621: 728 */           move.UUID = door.getUUID();
/*  622: 729 */           move.open = true;
/*  623: 730 */           this.movementOutgoingList.add(move);
/*  624: 731 */           withdraw(cost, marine);
/*  625:     */         }
/*  626:     */       }
/*  627:     */     }
/*  628:     */   }
/*  629:     */   
/*  630:     */   protected void placeAgents(float tx, float ty)
/*  631:     */   {
/*  632: 739 */     AgentModel a = this.worldModel.getAgentOnTile(tx, ty);
/*  633: 741 */     if ((a == null) && (this.worldModel.tilePlayerPlacable(tx, ty, this.shgameContainer.getPlayerId())))
/*  634:     */     {
/*  635: 743 */       int UUID = PlaceAgent(tx, ty, 180.0F, this.shgameContainer.getFaction(), this.shgameContainer.getPlayerId(), this.shgameContainer.getTeam(), this.chosenMarineType, this.shgameContainer.getColour());
/*  636:     */       
/*  637: 745 */       this.agentState = GameplayState.AgentState.TURNING;
/*  638: 746 */       deSelect();
/*  639: 747 */       this.selectedAgent = ((PathAgentModel)this.worldModel.getAgentModel(UUID));
/*  640: 748 */       this.selectedAgent.setSelected(true);
/*  641: 749 */       this.finishRotate.setVisible(true);
/*  642: 750 */       this.CPBonus += ((MarineModel)this.selectedAgent).getCpBonus();
/*  643: 751 */       this.CP += ((MarineModel)this.selectedAgent).getCpBonus();
/*  644: 752 */       this.agentPlacement.add(this.selectedAgent.getPlacement());
/*  645:     */       
/*  646: 754 */       this.feedbackText.setText("Rotate with arrow keys, when finished hit enter");
/*  647:     */     }
/*  648:     */   }
/*  649:     */   
/*  650:     */   protected void selectAgent(float tx, float ty)
/*  651:     */   {
/*  652: 760 */     System.out.println("Selecting");
/*  653:     */     
/*  654: 762 */     AgentModel model = this.worldModel.getAgentOnTile(tx, ty);
/*  655:     */     
/*  656: 764 */     setSelected(model);
/*  657:     */   }
/*  658:     */   
/*  659:     */   public void setFire(float x, float y, int area)
/*  660:     */   {
/*  661: 771 */     float startX = x - (int)Math.floor(area / 2);
/*  662: 772 */     float startY = y - (int)Math.floor(area / 2);
/*  663: 774 */     for (int px = (int)startX; px < startX + (area - 1); px++) {
/*  664: 776 */       for (int py = (int)startY; py < startY + (area - 1); py++) {
/*  665: 778 */         if ((this.worldModel.tileWalkable(px, py)) && (!this.worldModel.getFireOnTile(px, py)))
/*  666:     */         {
/*  667: 780 */           int uuid = PlaceFire(px, py);
/*  668:     */           
/*  669: 782 */           FireModel m = (FireModel)this.worldModel.getAgentModel(uuid);
/*  670: 783 */           this.shgameContainer.send(m.getPlacement());
/*  671: 784 */           this.fires.add(Integer.valueOf(uuid));
/*  672: 785 */           if (this.worldModel.checkFire(px, py)) {
/*  673: 787 */             sendVictory();
/*  674:     */           }
/*  675:     */         }
/*  676:     */       }
/*  677:     */     }
/*  678:     */   }
/*  679:     */   
/*  680:     */   private void setInfo(AgentModel model)
/*  681:     */   {
/*  682: 797 */     this.infobox.setVisible(true);
/*  683: 798 */     this.infoBoxNameLabel.setText(model.getName());
/*  684: 799 */     this.infoBoxTeamIdLabel.setText("Team: " + model.getTeam());
/*  685: 800 */     this.infoBoxplayerIdLabel.setText("Player " + model.getPlayer());
/*  686: 801 */     if (model.getCurrentWeapon() != null) {
/*  687: 803 */       this.infoBoxWeaponLabel.setText(model.getCurrentWeapon().getName());
/*  688:     */     }
/*  689:     */   }
/*  690:     */   
/*  691:     */   private boolean setOverwatch(SpaceHulkGameContainer cont)
/*  692:     */   {
/*  693: 809 */     int cost = 2;
/*  694: 810 */     MarineModel model = (MarineModel)this.selectedAgent;
/*  695: 811 */     if (!model.isOverwatch()) {
/*  696: 813 */       if (model.getCurrentWeapon().isOverwatch())
/*  697:     */       {
/*  698: 815 */         if (canPerformAction(cost, model))
/*  699:     */         {
/*  700: 817 */           this.shgameContainer.playSound("overwatch");
/*  701: 818 */           withdraw(cost, model);
/*  702: 819 */           model.setOverwatch(true);
/*  703: 820 */           AgentMovement move = model.getMovement(2);
/*  704: 821 */           move.overwatch = true;
/*  705: 822 */           this.movementOutgoingList.add(move);
/*  706: 823 */           return true;
/*  707:     */         }
/*  708: 827 */         this.feedbackText.setText("This weapon does not support overwatch");
/*  709:     */       }
/*  710:     */     }
/*  711: 831 */     return false;
/*  712:     */   }
/*  713:     */   
/*  714:     */   private void setSelected(AgentModel model)
/*  715:     */   {
/*  716: 836 */     if ((model != null) && (model.getPlayer() == this.shgameContainer.getPlayerId()))
/*  717:     */     {
/*  718: 839 */       this.selectedAgent = ((PathAgentModel)model);
/*  719: 840 */       if ((this.selectedAgent instanceof MarineModel))
/*  720:     */       {
/*  721: 842 */         MarineModel m = (MarineModel)this.selectedAgent;
/*  722: 843 */         this.marineName.setVisible(true);
/*  723: 844 */         this.marineName.setText(m.getName());
/*  724:     */         
/*  725: 846 */         SimpleChangableListModel<String> listModel = new SimpleChangableListModel();
/*  726: 847 */         ArrayList<Weapon> listweapon = m.getWeapons();
/*  727: 848 */         for (int i = 0; i < listweapon.size(); i++) {
/*  728: 850 */           listModel.addElement(((Weapon)listweapon.get(i)).getName());
/*  729:     */         }
/*  730: 853 */         this.weaponList.setModel(listModel);
/*  731: 854 */         this.weaponList.setVisible(true);
/*  732: 856 */         if (m.getCurrentWeapon().isAttackGround()) {
/*  733: 858 */           this.attackGround.setVisible(true);
/*  734:     */         } else {
/*  735: 862 */           this.attackGround.setVisible(false);
/*  736:     */         }
/*  737: 865 */         if (m.isOverwatch())
/*  738:     */         {
/*  739: 867 */           this.overwatch.setVisible(false);
/*  740: 868 */           this.cancelOverwatch.setVisible(true);
/*  741:     */         }
/*  742:     */         else
/*  743:     */         {
/*  744: 872 */           this.overwatch.setVisible(true);
/*  745: 873 */           this.cancelOverwatch.setVisible(false);
/*  746:     */         }
/*  747:     */       }
/*  748: 876 */       if (this.agentState == GameplayState.AgentState.TURNING)
/*  749:     */       {
/*  750: 878 */         this.rotate.setVisible(false);
/*  751: 879 */         this.finishRotate.setVisible(true);
/*  752:     */       }
/*  753:     */       else
/*  754:     */       {
/*  755: 883 */         this.rotate.setVisible(true);
/*  756: 884 */         this.finishRotate.setVisible(false);
/*  757:     */       }
/*  758: 887 */       if (this.worldModel.standsOnOpenStartPosition(model.getX(), model.getY())) {
/*  759: 889 */         this.closeAlienStart.setVisible(true);
/*  760:     */       } else {
/*  761: 893 */         this.closeAlienStart.setVisible(false);
/*  762:     */       }
/*  763: 896 */       this.apLabel.setVisible(true);
/*  764: 897 */       this.cpLabel.setVisible(true);
/*  765:     */       
/*  766:     */ 
/*  767: 900 */       this.endTurn.setVisible(true);
/*  768:     */       
/*  769: 902 */       this.apLabel.setText("AP " + this.selectedAgent.getAp());
/*  770: 903 */       this.cpLabel.setText("CP " + this.CP);
/*  771: 904 */       this.selectedAgent.setSelected(true);
/*  772:     */     }
/*  773: 906 */     else if (model != null)
/*  774:     */     {
/*  775: 908 */       this.infobox.setVisible(true);
/*  776: 909 */       this.infoBoxNameLabel.setText(model.getName());
/*  777: 910 */       this.infoBoxTeamIdLabel.setText("Team: " + model.getTeam());
/*  778: 911 */       this.infoBoxplayerIdLabel.setText("Player " + model.getPlayer());
/*  779: 912 */       if (model.getCurrentWeapon() != null) {
/*  780: 914 */         this.infoBoxWeaponLabel.setText(model.getCurrentWeapon().getName());
/*  781:     */       }
/*  782:     */     }
/*  783:     */   }
/*  784:     */   
/*  785:     */   private void TargetAffected(List<AgentModel> affected, MarineModel marine, Weapon weapon, float x, float y)
/*  786:     */   {
/*  787: 921 */     Attack attack = new Attack();
/*  788: 922 */     attack.attacker = marine.getUUID();
/*  789:     */     
/*  790: 924 */     attack.ammo = weapon.getAmmo();
/*  791: 925 */     attack.sound = weapon.getSound();
/*  792: 926 */     this.shgameContainer.send(attack);
/*  793: 927 */     this.worldView.getAgentView(marine.getUUID()).setAnimation(weapon.getAgentAnimation(), false);
/*  794: 928 */     fireBullets(marine, x, y, weapon);
/*  795: 929 */     for (int i = 0; i < affected.size(); i++) {
/*  796: 934 */       if (weapon.attack(marine, (AgentModel)affected.get(i), this.worldModel))
/*  797:     */       {
/*  798: 936 */         AgentRemove remove = new AgentRemove();
/*  799: 937 */         remove.UUID = ((AgentModel)affected.get(i)).getUUID();
/*  800: 938 */         remove.killed = true;
/*  801: 939 */         this.removeList.add(remove);
/*  802: 940 */         removeAgent(((AgentModel)affected.get(i)).getUUID(), true);
/*  803:     */       }
/*  804:     */     }
/*  805: 943 */     this.shgameContainer.playSound(weapon.getSound());
/*  806: 945 */     if (weapon.isFlameWeapon()) {
/*  807: 948 */       setFire(x, y, weapon.getArea());
/*  808:     */     }
/*  809:     */   }
/*  810:     */   
/*  811:     */   public void update(GameContainer container, StateBasedGame arg1, int arg2)
/*  812:     */     throws SlickException
/*  813:     */   {
/*  814: 957 */     super.update(container, arg1, arg2);
/*  815: 958 */     Input input = container.getInput();
/*  816: 959 */     SpaceHulkGameContainer cont = (SpaceHulkGameContainer)container;
/*  817: 961 */     if (this.state == GameState.State.PLACEMENT) {
/*  818: 963 */       if (this.agentsPlaced >= cont.getPoints())
/*  819:     */       {
/*  820: 965 */         this.state = GameState.State.PLAY;
/*  821: 966 */         this.marinePlacement.setVisible(false);
/*  822:     */       }
/*  823: 967 */       else if ((this.agentState != GameplayState.AgentState.TURNING) && (!this.worldModel.hasStartPositions(this.shgameContainer.getPlayerId())))
/*  824:     */       {
/*  825: 969 */         this.state = GameState.State.PLAY;
/*  826: 970 */         this.marinePlacement.setVisible(false);
/*  827:     */       }
/*  828:     */     }
/*  829: 975 */     if ((this.state == GameState.State.PLACEMENT) && (this.agentState == GameplayState.AgentState.TURNING) && 
/*  830: 976 */       (this.selectedAgent != null))
/*  831:     */     {
/*  832: 978 */       this.finishRotate.setVisible(true);
/*  833: 979 */       if (container.getInput().isKeyDown(203)) {
/*  834: 981 */         this.selectedAgent.setAngle(180.0F);
/*  835:     */       }
/*  836: 983 */       if (container.getInput().isKeyDown(205)) {
/*  837: 985 */         this.selectedAgent.setAngle(0.0F);
/*  838:     */       }
/*  839: 987 */       if (container.getInput().isKeyDown(200)) {
/*  840: 989 */         this.selectedAgent.setAngle(270.0F);
/*  841:     */       }
/*  842: 991 */       if (container.getInput().isKeyDown(208)) {
/*  843: 993 */         this.selectedAgent.setAngle(90.0F);
/*  844:     */       }
/*  845:     */     }
/*  846: 997 */     if (container.getInput().isKeyPressed(207)) {
/*  847:1000 */       if (this.turnState == GameplayState.TurnState.OTHER) {
/*  848:1003 */         interrupt(cont);
/*  849:1004 */       } else if ((this.turnState == GameplayState.TurnState.INTERRUPT) && 
/*  850:1005 */         (this.shgameContainer.getFaction().equalsIgnoreCase("marines"))) {
/*  851:1008 */         endInterrupt(cont);
/*  852:     */       }
/*  853:     */     }
/*  854:1012 */     if (container.getInput().isKeyPressed(59)) {
/*  855:1014 */       this.objectivesbox.setVisible(!this.objectivesbox.isVisible());
/*  856:     */     }
/*  857:1018 */     input.clearKeyPressedRecord();
/*  858:     */   }
/*  859:     */   
/*  860:     */   protected void walking(float tx, float ty)
/*  861:     */   {
/*  862:1024 */     MarineModel marine = (MarineModel)this.selectedAgent;
/*  863:1025 */     System.out.println("Not null");
/*  864:     */     
/*  865:1027 */     int cost = marine.tileReachable(tx, ty);
/*  866:1028 */     System.out.println("Cost " + cost);
/*  867:1029 */     if ((cost > 0) && (canPerformAction(cost, marine)))
/*  868:     */     {
/*  869:1032 */       this.worldView.getAgentView(marine.getUUID()).setDefault("walking");
/*  870:     */       
/*  871:1034 */       marine.moveTo(tx + 0.5F, ty + 0.5F);
/*  872:1035 */       this.shgameContainer.playSound("marinestep");
/*  873:     */       
/*  874:1037 */       withdraw(cost, marine);
/*  875:1038 */       AgentMovement movement = marine.getMovement(cost);
/*  876:1039 */       movement.x = (tx + 0.5F);
/*  877:1040 */       movement.y = (ty + 0.5F);
/*  878:1043 */       if ((this.selectedAgent instanceof MarineModel)) {
/*  879:1046 */         forceConvertBlips(tx, ty);
/*  880:     */       }
/*  881:1049 */       MarineModel m = (MarineModel)this.selectedAgent;
/*  882:     */       
/*  883:1051 */       m.setOverwatch(false);
/*  884:1052 */       this.cancelOverwatch.setVisible(false);
/*  885:1053 */       this.overwatch.setVisible(true);
/*  886:1054 */       movement.overwatch = false;
/*  887:1055 */       this.movementOutgoingList.add(movement);
/*  888:1056 */       checkOverwatch();
/*  889:1058 */       if (this.worldModel.checkReached(tx + 0.5F, ty + 0.5F)) {
/*  890:1060 */         sendVictory();
/*  891:     */       }
/*  892:1063 */       if (this.worldModel.standsOnOpenStartPosition(tx + 0.5F, ty + 0.5F)) {
/*  893:1065 */         this.closeAlienStart.setVisible(true);
/*  894:     */       } else {
/*  895:1069 */         this.closeAlienStart.setVisible(false);
/*  896:     */       }
/*  897:     */     }
/*  898:     */   }
/*  899:     */   
/*  900:     */   private void withdraw(int cost, MarineModel marine)
/*  901:     */   {
/*  902:1076 */     if (marine.getAp() >= cost)
/*  903:     */     {
/*  904:1078 */       marine.withdrawAp(cost);
/*  905:     */     }
/*  906:     */     else
/*  907:     */     {
/*  908:1081 */       int rest = cost - marine.getAp();
/*  909:1082 */       this.CP -= rest;
/*  910:1083 */       marine.setAp(0);
/*  911:     */     }
/*  912:1085 */     this.apLabel.setText("AP " + this.selectedAgent.getAp());
/*  913:1086 */     this.cpLabel.setText("CP " + this.CP);
/*  914:     */   }
/*  915:     */   
/*  916:     */   public void yourTurn()
/*  917:     */   {
/*  918:1094 */     this.state = GameState.State.PLACEMENT;
/*  919:1095 */     this.turnState = GameplayState.TurnState.YOUR;
/*  920:1096 */     this.worldModel.GiveBackAp(this.shgameContainer.getFaction());
/*  921:1097 */     this.feedbackText.setText("It is your turn!");
/*  922:1098 */     this.marinePlacement.setVisible(true);
/*  923:     */     try
/*  924:     */     {
/*  925:1102 */       for (int i = 0; i < this.fires.size(); i++)
/*  926:     */       {
/*  927:1104 */         AgentRemove remove = new AgentRemove();
/*  928:     */         
/*  929:1106 */         remove.UUID = ((Integer)this.fires.get(i)).intValue();
/*  930:1107 */         remove.killed = false;
/*  931:1108 */         this.removeList.add(remove);
/*  932:1109 */         removeAgent(((Integer)this.fires.get(i)).intValue(), false);
/*  933:     */       }
/*  934:     */     }
/*  935:     */     catch (Exception e)
/*  936:     */     {
/*  937:1113 */       e.printStackTrace();
/*  938:     */     }
/*  939:1115 */     this.fires.clear();
/*  940:1116 */     this.CP = (Diceroller.roll(6) + this.CPBonus);
/*  941:1117 */     this.cpLabel.setVisible(true);
/*  942:1118 */     this.endTurn.setVisible(true);
/*  943:     */     
/*  944:1120 */     deSelect();
/*  945:     */   }
/*  946:     */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.states.MarineGamePlayState
 * JD-Core Version:    0.7.0.1
 */