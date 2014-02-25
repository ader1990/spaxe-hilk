/*   1:    */ package sh.states;
/*   2:    */ 
/*   3:    */ import de.matthiasmann.twl.Button;
/*   4:    */ import de.matthiasmann.twl.Label;
/*   5:    */ import de.matthiasmann.twl.ListBox;
/*   6:    */ import sh.SpaceHulkGameContainer;
/*   7:    */ import sh.gui.TWL.RootPane;
/*   8:    */ import sh.gui.widgets.InfoBox;
/*   9:    */ import sh.gui.widgets.MarinePlacement;
/*  10:    */ import sh.gui.widgets.MarineSelected;
/*  11:    */ import sh.gui.widgets.Objectives;
/*  12:    */ 
/*  13:    */ public class MarineGuiState
/*  14:    */   extends GameplayState
/*  15:    */ {
/*  16:    */   Label cpLabel;
/*  17:    */   Label marineName;
/*  18:    */   Button overwatch;
/*  19:    */   Button cancelOverwatch;
/*  20:    */   Button attackGround;
/*  21:    */   Button rotate;
/*  22:    */   Button finishRotate;
/*  23:    */   Button endTurn;
/*  24:    */   Button pickup;
/*  25:    */   Button closeAlienStart;
/*  26:    */   ListBox<String> weaponList;
/*  27:    */   ListBox<String> marineTypesList;
/*  28:    */   Label marineTypeLabel;
/*  29:    */   Label cost;
/*  30:    */   Label description;
/*  31:    */   Label pointsLeft;
/*  32:    */   Label infoBoxNameLabel;
/*  33:    */   Label infoBoxplayerIdLabel;
/*  34:    */   Label infoBoxTeamIdLabel;
/*  35:    */   Label infoBoxWeaponLabel;
/*  36:    */   InfoBox infobox;
/*  37:    */   Objectives objectivesbox;
/*  38:    */   protected MarineSelected marineSelection;
/*  39:    */   protected MarinePlacement marinePlacement;
/*  40:    */   
/*  41:    */   public MarineGuiState(int stateID)
/*  42:    */   {
/*  43: 46 */     super(stateID);
/*  44:    */   }
/*  45:    */   
/*  46:    */   protected RootPane createRootPane()
/*  47:    */   {
/*  48: 54 */     RootPane rp = super.createRootPane();
/*  49:    */     
/*  50: 56 */     this.apLabel = new Label();
/*  51: 57 */     this.apLabel.setText("AP");
/*  52:    */     
/*  53: 59 */     this.cpLabel = new Label();
/*  54: 60 */     this.cpLabel.setText("CP");
/*  55:    */     
/*  56: 62 */     this.marineName = new Label();
/*  57: 63 */     this.marineName.setText("Selection");
/*  58:    */     
/*  59: 65 */     this.marineTypeLabel = new Label();
/*  60: 66 */     this.marineTypeLabel.setText("Marine");
/*  61:    */     
/*  62: 68 */     this.cost = new Label();
/*  63: 69 */     this.cost.setText("Cost: ");
/*  64:    */     
/*  65: 71 */     this.pointsLeft = new Label();
/*  66: 72 */     this.pointsLeft.setText("Points left: ");
/*  67:    */     
/*  68: 74 */     this.description = new Label();
/*  69: 75 */     this.description.setText("Description");
/*  70:    */     
/*  71: 77 */     this.weaponList = new ListBox();
/*  72: 78 */     this.marineTypesList = new ListBox();
/*  73: 79 */     this.overwatch = new Button();
/*  74: 80 */     this.overwatch.setText("Overwatch");
/*  75: 81 */     this.cancelOverwatch = new Button();
/*  76: 82 */     this.cancelOverwatch.setText("Cancel Overwatch");
/*  77:    */     
/*  78: 84 */     this.rotate = new Button();
/*  79: 85 */     this.rotate.setText("Rotate");
/*  80: 86 */     this.attackGround = new Button();
/*  81: 87 */     this.attackGround.setText("Attack Ground");
/*  82:    */     
/*  83:    */ 
/*  84: 90 */     this.finishRotate = new Button();
/*  85: 91 */     this.finishRotate.setText("Finish rotate");
/*  86:    */     
/*  87: 93 */     this.pickup = new Button();
/*  88: 94 */     this.pickup.setText("Pickup Item");
/*  89:    */     
/*  90: 96 */     this.closeAlienStart = new Button();
/*  91: 97 */     this.closeAlienStart.setText("Close");
/*  92:    */     
/*  93: 99 */     this.endTurn = new Button();
/*  94:100 */     this.endTurn.setText("End Turn");
/*  95:    */     
/*  96:102 */     this.infoBoxWeaponLabel = new Label();
/*  97:103 */     this.infoBoxNameLabel = new Label();
/*  98:104 */     this.infoBoxplayerIdLabel = new Label();
/*  99:105 */     this.infoBoxTeamIdLabel = new Label();
/* 100:106 */     this.objectivesbox = new Objectives(this.shgameContainer);
/* 101:107 */     this.objectivesbox.setTheme("infobox");
/* 102:108 */     this.infobox = new InfoBox(this.shgameContainer, this.infoBoxNameLabel, this.infoBoxplayerIdLabel, this.infoBoxTeamIdLabel, this.infoBoxWeaponLabel);
/* 103:109 */     this.marineSelection = new MarineSelected(this.shgameContainer, this.apLabel, this.cpLabel, this.marineName, this.endTurn, this.weaponList, this.overwatch, this.cancelOverwatch, this.rotate, this.finishRotate, this.attackGround, this.closeAlienStart);
/* 104:110 */     this.marinePlacement = new MarinePlacement(this.shgameContainer, this.marineTypesList, this.marineTypeLabel, this.cost, this.description, this.pointsLeft);
/* 105:    */     
/* 106:    */ 
/* 107:    */ 
/* 108:    */ 
/* 109:115 */     rp.add(this.marinePlacement);
/* 110:116 */     rp.add(this.marineSelection);
/* 111:117 */     rp.add(this.infobox);
/* 112:    */     
/* 113:119 */     return rp;
/* 114:    */   }
/* 115:    */   
/* 116:    */   protected void layoutRootPane()
/* 117:    */   {
/* 118:124 */     super.layoutRootPane();
/* 119:    */     
/* 120:126 */     int middleX = this.shgameContainer.getWidth() / 2;
/* 121:127 */     int middleY = this.shgameContainer.getHeight() / 2;
/* 122:    */     
/* 123:129 */     int maxX = this.shgameContainer.getWidth();
/* 124:130 */     int maxY = this.shgameContainer.getHeight();
/* 125:    */     
/* 126:132 */     this.objectivesbox.setSize(middleX, 100);
/* 127:133 */     this.objectivesbox.setPosition(maxX / 10, 0);
/* 128:    */     
/* 129:135 */     this.pickup.setSize(100, 20);
/* 130:136 */     this.pickup.setPosition(maxX - 600, maxY - 80);
/* 131:    */     
/* 132:138 */     this.closeAlienStart.setSize(100, 20);
/* 133:139 */     this.closeAlienStart.setPosition(maxX - 600, maxY - 40);
/* 134:    */     
/* 135:141 */     this.marineSelection.setSize(maxX / 10, maxY - 100);
/* 136:142 */     this.marineSelection.setPosition(0, 0);
/* 137:    */     
/* 138:144 */     this.marinePlacement.setSize(maxX / 10, maxY - 100);
/* 139:145 */     this.marinePlacement.setPosition(maxX - maxX / 10, 0);
/* 140:    */     
/* 141:147 */     this.infobox.setSize(maxX / 10, 100);
/* 142:148 */     this.infobox.setPosition(maxX - maxX / 10, maxY - 100);
/* 143:    */   }
/* 144:    */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.states.MarineGuiState
 * JD-Core Version:    0.7.0.1
 */