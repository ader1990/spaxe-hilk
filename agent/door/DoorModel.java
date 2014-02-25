/*  1:   */ package sh.agent.door;
/*  2:   */ 
/*  3:   */ import sh.agent.AgentModel;
/*  4:   */ import sh.world.shworld.SpaceHulkWorldModel;
/*  5:   */ 
/*  6:   */ public class DoorModel
/*  7:   */   extends AgentModel
/*  8:   */ {
/*  9: 8 */   boolean open = false;
/* 10:   */   
/* 11:   */   public DoorModel(int UUID, String name, float x, float y, float angle, SpaceHulkWorldModel worldModel, String faction, int ap, boolean rotatable, float attackRange, int player, int team, int marineType, int colour)
/* 12:   */   {
/* 13:14 */     super(UUID, name, x, y, angle, worldModel, faction, ap, rotatable, attackRange, player, team, marineType, colour);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public boolean isOpen()
/* 17:   */   {
/* 18:19 */     return this.open;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void open()
/* 22:   */   {
/* 23:23 */     this.open = true;
/* 24:   */   }
/* 25:   */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.agent.door.DoorModel
 * JD-Core Version:    0.7.0.1
 */