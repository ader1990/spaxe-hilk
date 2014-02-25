/*  1:   */ package sh.agent.fire;
/*  2:   */ 
/*  3:   */ import sh.agent.AgentModel;
/*  4:   */ import sh.world.shworld.SpaceHulkWorldModel;
/*  5:   */ 
/*  6:   */ public class FireModel
/*  7:   */   extends AgentModel
/*  8:   */ {
/*  9:   */   public FireModel(int UUID, String name, float x, float y, float angle, SpaceHulkWorldModel worldModel, String faction, int ap, boolean rotatable, float attackRange, int player, int team, int marineType, int colour)
/* 10:   */   {
/* 11:15 */     super(UUID, name, x, y, angle, worldModel, faction, ap, rotatable, attackRange, player, team, marineType, colour);
/* 12:   */   }
/* 13:   */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.agent.fire.FireModel
 * JD-Core Version:    0.7.0.1
 */