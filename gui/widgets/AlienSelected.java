/*  1:   */ package sh.gui.widgets;
/*  2:   */ 
/*  3:   */ import de.matthiasmann.twl.Button;
/*  4:   */ import de.matthiasmann.twl.Label;
/*  5:   */ import de.matthiasmann.twl.ResizableFrame;
/*  6:   */ import de.matthiasmann.twl.ResizableFrame.ResizableAxis;
/*  7:   */ import sh.SpaceHulkGameContainer;
/*  8:   */ 
/*  9:   */ public class AlienSelected
/* 10:   */   extends ResizableFrame
/* 11:   */ {
/* 12:   */   Label apLabel;
/* 13:   */   Label aliensInBlipLabel;
/* 14:   */   Button insertAliens;
/* 15:   */   Button rotate;
/* 16:   */   Button finishRotate;
/* 17:   */   Button endTurn;
/* 18:   */   SpaceHulkGameContainer shgameContainer;
/* 19:   */   
/* 20:   */   public AlienSelected(SpaceHulkGameContainer shgameContainer, Label acLabel, Label aliens, Button endturn, Button rotate, Button finishRotate, Button insertAliens)
/* 21:   */   {
/* 22:31 */     this.shgameContainer = shgameContainer;
/* 23:32 */     this.endTurn = endturn;
/* 24:33 */     setResizableAxis(ResizableFrame.ResizableAxis.NONE);
/* 25:34 */     this.apLabel = acLabel;
/* 26:35 */     this.aliensInBlipLabel = aliens;
/* 27:36 */     this.rotate = rotate;
/* 28:37 */     this.finishRotate = finishRotate;
/* 29:38 */     this.insertAliens = insertAliens;
/* 30:39 */     add(this.apLabel);
/* 31:40 */     add(insertAliens);
/* 32:41 */     add(this.aliensInBlipLabel);
/* 33:42 */     add(rotate);
/* 34:43 */     add(finishRotate);
/* 35:44 */     add(this.endTurn);
/* 36:   */   }
/* 37:   */   
/* 38:   */   protected void layout()
/* 39:   */   {
/* 40:50 */     int middleX = this.shgameContainer.getWidth() / 2;
/* 41:51 */     int middleY = this.shgameContainer.getHeight() / 2;
/* 42:   */     
/* 43:53 */     int maxX = this.shgameContainer.getWidth();
/* 44:54 */     int maxY = this.shgameContainer.getHeight();
/* 45:   */     
/* 46:   */ 
/* 47:57 */     this.apLabel.setPosition(15, 45);
/* 48:58 */     this.apLabel.setSize(getWidth() - 15, 20);
/* 49:   */     
/* 50:60 */     this.aliensInBlipLabel.setPosition(15, 70);
/* 51:61 */     this.aliensInBlipLabel.setSize(getWidth() - 15, 20);
/* 52:   */     
/* 53:63 */     this.insertAliens.setPosition(10, 215);
/* 54:64 */     this.insertAliens.setSize(getWidth() - 20, 20);
/* 55:   */     
/* 56:66 */     this.rotate.setPosition(10, 240);
/* 57:67 */     this.rotate.setSize(getWidth() - 20, 20);
/* 58:   */     
/* 59:69 */     this.finishRotate.setPosition(10, 240);
/* 60:70 */     this.finishRotate.setSize(getWidth() - 20, 20);
/* 61:   */     
/* 62:72 */     this.endTurn.setPosition(10, getHeight() - 30);
/* 63:73 */     this.endTurn.setSize(getWidth() - 20, 20);
/* 64:   */   }
/* 65:   */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.gui.widgets.AlienSelected
 * JD-Core Version:    0.7.0.1
 */