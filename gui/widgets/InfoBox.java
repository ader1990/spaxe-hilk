/*  1:   */ package sh.gui.widgets;
/*  2:   */ 
/*  3:   */ import de.matthiasmann.twl.Label;
/*  4:   */ import de.matthiasmann.twl.ResizableFrame;
/*  5:   */ import sh.SpaceHulkGameContainer;
/*  6:   */ 
/*  7:   */ public class InfoBox
/*  8:   */   extends ResizableFrame
/*  9:   */ {
/* 10:   */   Label info;
/* 11:   */   Label playerId;
/* 12:   */   Label teamId;
/* 13:   */   Label weaponInfo;
/* 14:   */   SpaceHulkGameContainer shgameContainer;
/* 15:   */   
/* 16:   */   public InfoBox(SpaceHulkGameContainer shgameContainer, Label name, Label playerId, Label teamId, Label weaponInfo)
/* 17:   */   {
/* 18:27 */     this.info = name;
/* 19:28 */     this.playerId = playerId;
/* 20:29 */     this.teamId = teamId;
/* 21:30 */     this.shgameContainer = shgameContainer;
/* 22:31 */     this.weaponInfo = weaponInfo;
/* 23:32 */     add(this.info);
/* 24:33 */     add(playerId);
/* 25:34 */     add(teamId);
/* 26:35 */     add(weaponInfo);
/* 27:   */   }
/* 28:   */   
/* 29:   */   protected void layout()
/* 30:   */   {
/* 31:45 */     int middleX = this.shgameContainer.getWidth() / 2;
/* 32:46 */     int middleY = this.shgameContainer.getHeight() / 2;
/* 33:   */     
/* 34:48 */     int maxX = this.shgameContainer.getWidth();
/* 35:49 */     int maxY = this.shgameContainer.getHeight();
/* 36:   */     
/* 37:51 */     this.info.setPosition(maxX - maxX / 10 + 10, maxY - 90);
/* 38:52 */     this.info.setSize(getWidth() - 15, 20);
/* 39:   */     
/* 40:54 */     this.playerId.setPosition(maxX - maxX / 10 + 10, maxY - 70);
/* 41:55 */     this.playerId.setSize(getWidth() - 15, 20);
/* 42:   */     
/* 43:57 */     this.teamId.setPosition(maxX - maxX / 10 + 10, maxY - 50);
/* 44:58 */     this.teamId.setSize(getWidth() - 15, 20);
/* 45:   */     
/* 46:60 */     this.weaponInfo.setPosition(maxX - maxX / 10 + 10, maxY - 30);
/* 47:61 */     this.weaponInfo.setSize(getWidth() - 15, 20);
/* 48:   */   }
/* 49:   */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.gui.widgets.InfoBox
 * JD-Core Version:    0.7.0.1
 */