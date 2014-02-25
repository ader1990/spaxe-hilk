/*  1:   */ package sh.states;
/*  2:   */ 
/*  3:   */ import de.matthiasmann.twl.Button;
/*  4:   */ import de.matthiasmann.twl.Label;
/*  5:   */ import sh.SpaceHulkGameContainer;
/*  6:   */ import sh.gui.TWL.RootPane;
/*  7:   */ import sh.gui.widgets.AlienSelected;
/*  8:   */ import sh.gui.widgets.InfoBox;
/*  9:   */ import sh.gui.widgets.Objectives;
/* 10:   */ 
/* 11:   */ public class AlienGuiState
/* 12:   */   extends GameplayState
/* 13:   */ {
/* 14:   */   Button insertAliens;
/* 15:   */   Label aliensInBlipLabel;
/* 16:   */   Label infoBoxNameLabel;
/* 17:   */   Label infoBoxplayerIdLabel;
/* 18:   */   Label infoBoxTeamIdLabel;
/* 19:   */   Label infoBoxWeaponLabel;
/* 20:   */   Button rotate;
/* 21:   */   Button finishRotate;
/* 22:   */   Button endTurn;
/* 23:   */   AlienSelected alienSelected;
/* 24:   */   InfoBox infobox;
/* 25:   */   Objectives objectivesbox;
/* 26:   */   
/* 27:   */   public AlienGuiState(int stateID)
/* 28:   */   {
/* 29:28 */     super(stateID);
/* 30:   */   }
/* 31:   */   
/* 32:   */   protected RootPane createRootPane()
/* 33:   */   {
/* 34:36 */     RootPane rp = super.createRootPane();
/* 35:   */     
/* 36:   */ 
/* 37:   */ 
/* 38:40 */     this.apLabel = new Label();
/* 39:41 */     this.apLabel.setText("AP");
/* 40:   */     
/* 41:   */ 
/* 42:44 */     this.insertAliens = new Button();
/* 43:45 */     this.insertAliens.setText("Insert Aliens");
/* 44:   */     
/* 45:47 */     this.rotate = new Button();
/* 46:48 */     this.rotate.setText("Rotate");
/* 47:49 */     this.finishRotate = new Button();
/* 48:50 */     this.finishRotate.setText("Finish rotate");
/* 49:51 */     this.endTurn = new Button();
/* 50:52 */     this.endTurn.setText("End Turn");
/* 51:   */     
/* 52:54 */     this.aliensInBlipLabel = new Label();
/* 53:55 */     this.aliensInBlipLabel.setText("Aliens:");
/* 54:56 */     this.infoBoxWeaponLabel = new Label();
/* 55:57 */     this.infoBoxNameLabel = new Label();
/* 56:58 */     this.infoBoxplayerIdLabel = new Label();
/* 57:59 */     this.infoBoxTeamIdLabel = new Label();
/* 58:   */     
/* 59:61 */     this.infobox = new InfoBox(this.shgameContainer, this.infoBoxNameLabel, this.infoBoxplayerIdLabel, this.infoBoxTeamIdLabel, this.infoBoxWeaponLabel);
/* 60:62 */     this.alienSelected = new AlienSelected(this.shgameContainer, this.apLabel, this.aliensInBlipLabel, this.endTurn, this.rotate, this.finishRotate, this.insertAliens);
/* 61:63 */     this.objectivesbox = new Objectives(this.shgameContainer);
/* 62:64 */     this.objectivesbox.setTheme("infobox");
/* 63:65 */     rp.add(this.alienSelected);
/* 64:66 */     rp.add(this.infobox);
/* 65:   */     
/* 66:68 */     return rp;
/* 67:   */   }
/* 68:   */   
/* 69:   */   protected void layoutRootPane()
/* 70:   */   {
/* 71:73 */     super.layoutRootPane();
/* 72:   */     
/* 73:75 */     int middleX = this.shgameContainer.getWidth() / 2;
/* 74:76 */     int middleY = this.shgameContainer.getHeight() / 2;
/* 75:   */     
/* 76:78 */     int maxX = this.shgameContainer.getWidth();
/* 77:79 */     int maxY = this.shgameContainer.getHeight();
/* 78:   */     
/* 79:81 */     this.alienSelected.setSize(maxX / 10, maxY);
/* 80:82 */     this.alienSelected.setPosition(0, 0);
/* 81:   */     
/* 82:84 */     this.objectivesbox.setSize(middleX, 100);
/* 83:85 */     this.objectivesbox.setPosition(maxX / 10, 0);
/* 84:   */     
/* 85:87 */     this.infobox.setSize(maxX / 10, 100);
/* 86:88 */     this.infobox.setPosition(maxX - maxX / 10, maxY - 100);
/* 87:   */   }
/* 88:   */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.states.AlienGuiState
 * JD-Core Version:    0.7.0.1
 */