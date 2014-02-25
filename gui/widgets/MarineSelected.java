/*   1:    */ package sh.gui.widgets;
/*   2:    */ 
/*   3:    */ import de.matthiasmann.twl.Button;
/*   4:    */ import de.matthiasmann.twl.Label;
/*   5:    */ import de.matthiasmann.twl.ListBox;
/*   6:    */ import de.matthiasmann.twl.ResizableFrame;
/*   7:    */ import de.matthiasmann.twl.ResizableFrame.ResizableAxis;
/*   8:    */ import sh.SpaceHulkGameContainer;
/*   9:    */ 
/*  10:    */ public class MarineSelected
/*  11:    */   extends ResizableFrame
/*  12:    */ {
/*  13:    */   Label acLabel;
/*  14:    */   Label cpLabel;
/*  15:    */   Label marineName;
/*  16:    */   Button overWatch;
/*  17:    */   Button cancelOverWatch;
/*  18:    */   Button rotate;
/*  19:    */   Button finishRotate;
/*  20:    */   Button endturn;
/*  21:    */   Button attackGround;
/*  22:    */   Button closeAlienStart;
/*  23:    */   SpaceHulkGameContainer shgameContainer;
/*  24:    */   ListBox<String> weapons;
/*  25:    */   
/*  26:    */   public MarineSelected(SpaceHulkGameContainer shgameContainer, Label acLabel, Label cpLabel, Label marineName, Button endturn, ListBox<String> weapons, Button overWatch, Button cancelOverWatch, Button rotate, Button finishRotate, Button attackGround, Button closeAlienStart)
/*  27:    */   {
/*  28: 40 */     this.shgameContainer = shgameContainer;
/*  29: 41 */     this.endturn = endturn;
/*  30: 42 */     setResizableAxis(ResizableFrame.ResizableAxis.NONE);
/*  31: 43 */     this.acLabel = acLabel;
/*  32: 44 */     this.cpLabel = cpLabel;
/*  33: 45 */     this.marineName = marineName;
/*  34: 46 */     this.weapons = weapons;
/*  35: 47 */     this.overWatch = overWatch;
/*  36: 48 */     this.cancelOverWatch = cancelOverWatch;
/*  37: 49 */     this.rotate = rotate;
/*  38: 50 */     this.finishRotate = finishRotate;
/*  39: 51 */     this.attackGround = attackGround;
/*  40: 52 */     this.closeAlienStart = closeAlienStart;
/*  41: 53 */     add(acLabel);
/*  42: 54 */     add(cpLabel);
/*  43: 55 */     add(marineName);
/*  44: 56 */     add(weapons);
/*  45: 57 */     add(overWatch);
/*  46: 58 */     add(cancelOverWatch);
/*  47: 59 */     add(rotate);
/*  48: 60 */     add(finishRotate);
/*  49: 61 */     add(attackGround);
/*  50: 62 */     add(closeAlienStart);
/*  51: 63 */     add(endturn);
/*  52:    */   }
/*  53:    */   
/*  54:    */   protected void layout()
/*  55:    */   {
/*  56: 73 */     int middleX = this.shgameContainer.getWidth() / 2;
/*  57: 74 */     int middleY = this.shgameContainer.getHeight() / 2;
/*  58:    */     
/*  59: 76 */     int maxX = this.shgameContainer.getWidth();
/*  60: 77 */     int maxY = this.shgameContainer.getHeight();
/*  61:    */     
/*  62: 79 */     this.marineName.setPosition(15, 20);
/*  63: 80 */     this.marineName.setSize(getWidth() - 15, 20);
/*  64:    */     
/*  65: 82 */     this.acLabel.setPosition(15, 45);
/*  66: 83 */     this.acLabel.setSize(getWidth() - 15, 20);
/*  67:    */     
/*  68: 85 */     this.cpLabel.setPosition(15, 70);
/*  69: 86 */     this.cpLabel.setSize(getWidth() - 15, 20);
/*  70:    */     
/*  71: 88 */     this.weapons.setPosition(15, 95);
/*  72: 89 */     this.weapons.setSize(getWidth() - 30, 100);
/*  73:    */     
/*  74: 91 */     this.overWatch.setPosition(10, 215);
/*  75: 92 */     this.overWatch.setSize(getWidth() - 20, 20);
/*  76:    */     
/*  77: 94 */     this.cancelOverWatch.setPosition(10, 215);
/*  78: 95 */     this.cancelOverWatch.setSize(getWidth() - 20, 20);
/*  79:    */     
/*  80: 97 */     this.rotate.setPosition(10, 240);
/*  81: 98 */     this.rotate.setSize(getWidth() - 20, 20);
/*  82:    */     
/*  83:100 */     this.finishRotate.setPosition(10, 240);
/*  84:101 */     this.finishRotate.setSize(getWidth() - 20, 20);
/*  85:    */     
/*  86:103 */     this.attackGround.setPosition(10, 280);
/*  87:104 */     this.attackGround.setSize(getWidth() - 20, 20);
/*  88:    */     
/*  89:106 */     this.closeAlienStart.setPosition(10, 320);
/*  90:107 */     this.closeAlienStart.setSize(getWidth() - 20, 20);
/*  91:    */     
/*  92:109 */     this.endturn.setPosition(10, getHeight() - 30);
/*  93:110 */     this.endturn.setSize(getWidth() - 20, 20);
/*  94:    */   }
/*  95:    */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.gui.widgets.MarineSelected
 * JD-Core Version:    0.7.0.1
 */