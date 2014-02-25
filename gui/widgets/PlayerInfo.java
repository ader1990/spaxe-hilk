/*  1:   */ package sh.gui.widgets;
/*  2:   */ 
/*  3:   */ import de.matthiasmann.twl.Label;
/*  4:   */ import de.matthiasmann.twl.ResizableFrame;
/*  5:   */ import sh.SpaceHulkGameContainer;
/*  6:   */ 
/*  7:   */ public class PlayerInfo
/*  8:   */   extends ResizableFrame
/*  9:   */ {
/* 10:   */   Label info;
/* 11:   */   Label playerId;
/* 12:   */   Label teamId;
/* 13:   */   Label weaponInfo;
/* 14:   */   SpaceHulkGameContainer shgameContainer;
/* 15:   */   
/* 16:   */   public PlayerInfo(SpaceHulkGameContainer shgameContainer) {}
/* 17:   */   
/* 18:   */   protected void layout()
/* 19:   */   {
/* 20:28 */     int middleX = this.shgameContainer.getWidth() / 2;
/* 21:29 */     int middleY = this.shgameContainer.getHeight() / 2;
/* 22:   */     
/* 23:31 */     int maxX = this.shgameContainer.getWidth();
/* 24:32 */     int maxY = this.shgameContainer.getHeight();
/* 25:   */     
/* 26:34 */     this.info.setPosition(maxX - maxX / 10 + 10, maxY - 90);
/* 27:35 */     this.info.setSize(getWidth() - 15, 20);
/* 28:   */     
/* 29:37 */     this.playerId.setPosition(maxX - maxX / 10 + 10, maxY - 70);
/* 30:38 */     this.playerId.setSize(getWidth() - 15, 20);
/* 31:   */     
/* 32:40 */     this.teamId.setPosition(maxX - maxX / 10 + 10, maxY - 50);
/* 33:41 */     this.teamId.setSize(getWidth() - 15, 20);
/* 34:   */     
/* 35:43 */     this.weaponInfo.setPosition(maxX - maxX / 10 + 10, maxY - 30);
/* 36:44 */     this.weaponInfo.setSize(getWidth() - 15, 20);
/* 37:   */   }
/* 38:   */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.gui.widgets.PlayerInfo
 * JD-Core Version:    0.7.0.1
 */