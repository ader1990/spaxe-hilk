/*   1:    */ package sh.agent.movement;
/*   2:    */ 
/*   3:    */ import org.newdawn.slick.geom.Vector2f;
/*   4:    */ import sh.agent.AgentModel;
/*   5:    */ import sh.world.shworld.SpaceHulkWorldModel;
/*   6:    */ 
/*   7:    */ public abstract class MovingAgentModel
/*   8:    */   extends AgentModel
/*   9:    */ {
/*  10: 12 */   protected float maxSpeed = 1.0F;
/*  11: 15 */   protected float maxForce = 1.0F;
/*  12: 18 */   protected float mass = 1.0F;
/*  13: 21 */   protected float damping = 1.0F;
/*  14: 24 */   protected float adrenaline = 1.0F;
/*  15: 27 */   protected Vector2f velocity = new Vector2f();
/*  16: 30 */   protected Vector2f acceleration = new Vector2f();
/*  17: 33 */   protected Vector2f forces = new Vector2f();
/*  18: 35 */   protected float breaking = 0.2F;
/*  19: 37 */   protected float agentRadius = 0.5F;
/*  20: 39 */   protected float obstacleRadius = 0.5F;
/*  21: 41 */   protected float margin = 0.5F;
/*  22:    */   
/*  23:    */   public MovingAgentModel(int UUID, String name, float x, float y, float angle, SpaceHulkWorldModel worldModel, String faction, int ap, boolean rotatable, float attackRange, int player, int team, int marineType, int colour)
/*  24:    */   {
/*  25: 46 */     super(UUID, name, x, y, angle, worldModel, faction, ap, rotatable, attackRange, player, team, marineType, colour);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void act()
/*  29:    */   {
/*  30: 52 */     float length = this.forces.length();
/*  31: 53 */     if (length > this.maxForce) {
/*  32: 54 */       this.forces.scale(this.maxForce / length);
/*  33:    */     }
/*  34: 58 */     Vector2f newAccel = new Vector2f(this.forces);
/*  35:    */     
/*  36:    */ 
/*  37: 61 */     newAccel.scale(1.0F / this.mass);
/*  38:    */     
/*  39:    */ 
/*  40: 64 */     this.forces.set(0.0F, 0.0F);
/*  41:    */     
/*  42:    */ 
/*  43: 67 */     this.acceleration.set(newAccel.x + this.damping * (this.acceleration.x - newAccel.x), newAccel.y + this.damping * (this.acceleration.y - newAccel.y));
/*  44:    */     
/*  45:    */ 
/*  46: 70 */     this.velocity.add(this.acceleration);
/*  47:    */     
/*  48:    */ 
/*  49: 73 */     length = this.velocity.length();
/*  50: 74 */     if (length > this.maxSpeed * this.adrenaline) {
/*  51: 75 */       this.velocity.scale(this.maxSpeed * this.adrenaline / length);
/*  52:    */     }
/*  53: 79 */     detectCollision();
/*  54:    */     
/*  55:    */ 
/*  56: 82 */     this.position.add(this.velocity);
/*  57:    */     
/*  58:    */ 
/*  59: 85 */     float speed = this.velocity.length();
/*  60: 88 */     if (speed > 0.0F)
/*  61:    */     {
/*  62: 89 */       this.xForce.set(this.velocity.x, this.velocity.y).scale(1.0F / speed);
/*  63: 90 */       this.yForce.set(-this.xForce.y, this.xForce.x);
/*  64:    */     }
/*  65:    */   }
/*  66:    */   
/*  67:    */   public Vector2f getVelocity()
/*  68:    */   {
/*  69: 95 */     return this.velocity;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public float getMaxSpeed()
/*  73:    */   {
/*  74: 99 */     return this.maxSpeed;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public float getAdrenaline()
/*  78:    */   {
/*  79:103 */     return this.adrenaline;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void applyGlobalForce(Vector2f force)
/*  83:    */   {
/*  84:107 */     this.forces.add(force);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public Vector2f localizePosition(Vector2f location)
/*  88:    */   {
/*  89:111 */     Vector2f global = new Vector2f(location);
/*  90:112 */     global.sub(this.position);
/*  91:113 */     Vector2f localized = new Vector2f(global.dot(this.xForce), global.dot(this.yForce));
/*  92:    */     
/*  93:115 */     return localized;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void moveTo(float x, float y) {}
/*  97:    */   
/*  98:    */   public void detectCollision() {}
/*  99:    */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.agent.movement.MovingAgentModel
 * JD-Core Version:    0.7.0.1
 */