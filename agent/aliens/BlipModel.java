/*  1:   */ package sh.agent.aliens;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ import org.newdawn.slick.geom.Vector2f;
/*  5:   */ import sh.agent.movement.PathAgentModel;
/*  6:   */ import sh.world.shworld.SpaceHulkWorldModel;
/*  7:   */ 
/*  8:   */ public class BlipModel
/*  9:   */   extends PathAgentModel
/* 10:   */ {
/* 11:12 */   int stealers = 0;
/* 12:   */   public boolean converting;
/* 13:   */   
/* 14:   */   public BlipModel(int UUID, String name, float x, float y, float angle, SpaceHulkWorldModel worldModel, String faction, int ap, boolean rotatable, int stealers, float attackRange, int player, int team, int marineType, int colour)
/* 15:   */   {
/* 16:20 */     super(UUID, name, x, y, angle, worldModel, faction, ap, rotatable, attackRange, player, team, marineType, colour);
/* 17:21 */     this.stealers = stealers;
/* 18:   */     
/* 19:23 */     this.mass = 0.1F;
/* 20:24 */     this.maxSpeed = 0.035F;
/* 21:25 */     this.maxForce = 0.005F;
/* 22:26 */     this.damping = 0.4F;
/* 23:27 */     this.breaking = 0.9F;
/* 24:   */     
/* 25:29 */     this.agentRadius = 0.2F;
/* 26:30 */     this.obstacleRadius = 0.1F;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public int getStealers()
/* 30:   */   {
/* 31:34 */     return this.stealers;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void setStealers(int stealers)
/* 35:   */   {
/* 36:38 */     this.stealers = stealers;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void setPosition(Vector2f newPosition)
/* 40:   */   {
/* 41:43 */     this.position.set(newPosition);
/* 42:   */   }
/* 43:   */   
/* 44:   */   public int tileReachable(float dx, float dy)
/* 45:   */   {
/* 46:49 */     int x = (int)(getX() - 0.5F);
/* 47:50 */     int y = (int)(getY() - 0.5F);
/* 48:51 */     int tx = (int)dx;
/* 49:52 */     int ty = (int)dy;
/* 50:53 */     System.out.println(x + " " + y + " " + tx + " " + ty);
/* 51:54 */     if ((tx >= x - 1) && (tx <= x + 1) && (ty >= y - 1) && (ty <= y + 1))
/* 52:   */     {
/* 53:55 */       System.out.println("close enough");
/* 54:56 */       return 1;
/* 55:   */     }
/* 56:59 */     return 0;
/* 57:   */   }
/* 58:   */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.agent.aliens.BlipModel
 * JD-Core Version:    0.7.0.1
 */