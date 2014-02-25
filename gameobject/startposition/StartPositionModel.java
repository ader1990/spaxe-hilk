/*  1:   */ package sh.gameobject.startposition;
/*  2:   */ 
/*  3:   */ import sh.gameobject.GameModel;
/*  4:   */ import sh.world.shworld.SpaceHulkWorldModel;
/*  5:   */ 
/*  6:   */ public class StartPositionModel
/*  7:   */   extends GameModel
/*  8:   */ {
/*  9:   */   private String faction;
/* 10:   */   private int playerId;
/* 11:   */   private boolean open;
/* 12:   */   
/* 13:   */   public StartPositionModel(int UUID, String name, float x, float y, float angle, SpaceHulkWorldModel worldModel, boolean rotatable, int colour, String faction, int playerId)
/* 14:   */   {
/* 15:17 */     super(UUID, name, x, y, angle, worldModel, rotatable, colour);
/* 16:18 */     this.faction = faction;
/* 17:19 */     this.playerId = playerId;
/* 18:20 */     this.open = true;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public boolean isOpen()
/* 22:   */   {
/* 23:24 */     return this.open;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void setOpen(boolean open)
/* 27:   */   {
/* 28:28 */     this.open = open;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public String getFaction()
/* 32:   */   {
/* 33:32 */     return this.faction;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void setFaction(String faction)
/* 37:   */   {
/* 38:37 */     this.faction = faction;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public int getPlayerId()
/* 42:   */   {
/* 43:42 */     return this.playerId;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void setPlayerId(int playerId)
/* 47:   */   {
/* 48:47 */     this.playerId = playerId;
/* 49:   */   }
/* 50:   */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.gameobject.startposition.StartPositionModel
 * JD-Core Version:    0.7.0.1
 */