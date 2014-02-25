/*   1:    */ package sh.agent.weapons.projectiles;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import org.newdawn.slick.geom.Vector2f;
/*   5:    */ import sh.agent.movement.MovingAgentModel;
/*   6:    */ import sh.world.shworld.SpaceHulkWorldModel;
/*   7:    */ 
/*   8:    */ public class BulletModel
/*   9:    */   extends MovingAgentModel
/*  10:    */ {
/*  11:    */   boolean finished;
/*  12:    */   
/*  13:    */   public boolean isFinished()
/*  14:    */   {
/*  15: 18 */     return this.finished;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public void setFinished(boolean finished)
/*  19:    */   {
/*  20: 24 */     this.finished = finished;
/*  21:    */   }
/*  22:    */   
/*  23: 27 */   Vector2f future = new Vector2f();
/*  24: 28 */   Vector2f target = null;
/*  25: 31 */   protected float predict = 2.0F;
/*  26: 33 */   protected float pathDistance = 0.3F;
/*  27: 35 */   protected float slowDistance = 0.5F;
/*  28: 37 */   protected float arrivalDistance = 0.3F;
/*  29:    */   protected float angle;
/*  30:    */   
/*  31:    */   public BulletModel(int UUID, String name, float x, float y, float angle, SpaceHulkWorldModel worldModel, String faction, int ap, boolean rotatable, float attackRange, int player, int team, int marineType, int colour)
/*  32:    */   {
/*  33: 43 */     super(UUID, name, x, y, angle, worldModel, faction, ap, rotatable, attackRange, player, team, marineType, colour);
/*  34:    */     
/*  35: 45 */     this.finished = false;
/*  36:    */     
/*  37: 47 */     this.mass = 1.0E-006F;
/*  38: 48 */     this.maxSpeed = 0.2F;
/*  39: 49 */     this.maxForce = 0.01F;
/*  40: 50 */     this.damping = 1.1E-013F;
/*  41: 51 */     this.breaking = 0.1F;
/*  42:    */     
/*  43: 53 */     this.agentRadius = 0.1F;
/*  44: 54 */     this.obstacleRadius = 0.1F;
/*  45:    */     
/*  46: 56 */     this.future.set(x, y);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void moveTo(float x, float y)
/*  50:    */   {
/*  51: 64 */     System.out.println("move to " + x + " " + y + " " + System.currentTimeMillis());
/*  52: 66 */     if (((int)x == (int)this.position.x) && ((int)y == (int)this.position.y))
/*  53:    */     {
/*  54: 67 */       this.target = new Vector2f(x, y);
/*  55: 68 */       return;
/*  56:    */     }
/*  57: 73 */     this.target = new Vector2f(x, y);
/*  58:    */   }
/*  59:    */   
/*  60:    */   protected Vector2f steerTowardsTarget()
/*  61:    */   {
/*  62: 78 */     Vector2f steering = new Vector2f(0.0F, 0.0F);
/*  63: 81 */     if (this.target != null)
/*  64:    */     {
/*  65: 84 */       float distance = this.position.distance(this.target);
/*  66: 88 */       if ((distance < this.arrivalDistance) || ((distance < 1.0F) && (this.velocity.length() == 0.0F)))
/*  67:    */       {
/*  68: 90 */         this.target = null;
/*  69:    */         
/*  70:    */ 
/*  71: 93 */         this.acceleration.set(0.0F, 0.0F);
/*  72: 94 */         this.velocity.set(0.0F, 0.0F);
/*  73: 95 */         this.finished = true;
/*  74:    */         
/*  75: 97 */         float px = (float)Math.floor(this.position.x) + 0.5F;
/*  76: 98 */         float py = (float)Math.floor(this.position.y) + 0.5F;
/*  77:    */         
/*  78:    */ 
/*  79:    */ 
/*  80:    */ 
/*  81:    */ 
/*  82:104 */         this.future.set(this.position);
/*  83:    */       }
/*  84:    */       else
/*  85:    */       {
/*  86:107 */         Vector2f arrivalSteer = steerToArrive(this.target);
/*  87:    */         
/*  88:    */ 
/*  89:110 */         steering.add(arrivalSteer);
/*  90:    */       }
/*  91:    */     }
/*  92:114 */     return steering;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void think()
/*  96:    */   {
/*  97:121 */     this.future.set(this.velocity);
/*  98:122 */     this.future.scale(this.predict / this.maxSpeed);
/*  99:123 */     this.future.add(this.position);
/* 100:    */     
/* 101:125 */     Vector2f steering = new Vector2f(0.0F, 0.0F);
/* 102:    */     
/* 103:    */ 
/* 104:128 */     Vector2f arrivalSteer = steerTowardsTarget();
/* 105:    */     
/* 106:    */ 
/* 107:    */ 
/* 108:    */ 
/* 109:133 */     steering.add(arrivalSteer);
/* 110:    */     
/* 111:    */ 
/* 112:136 */     applyGlobalForce(steering);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void applyGlobalForce(Vector2f force)
/* 116:    */   {
/* 117:140 */     this.forces.add(force);
/* 118:    */   }
/* 119:    */   
/* 120:    */   protected Vector2f steerToArrive(Vector2f target)
/* 121:    */   {
/* 122:144 */     Vector2f steering = new Vector2f(0.0F, 0.0F);
/* 123:    */     
/* 124:146 */     steering.set(target.x, target.y).sub(this.position);
/* 125:    */     
/* 126:148 */     steering.sub(this.velocity);
/* 127:    */     
/* 128:150 */     return steering;
/* 129:    */   }
/* 130:    */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.agent.weapons.projectiles.BulletModel
 * JD-Core Version:    0.7.0.1
 */