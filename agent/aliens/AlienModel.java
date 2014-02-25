/*   1:    */ package sh.agent.aliens;
/*   2:    */ 
/*   3:    */ import sh.agent.movement.PathAgentModel;
/*   4:    */ import sh.world.shworld.SpaceHulkWorldModel;
/*   5:    */ 
/*   6:    */ public class AlienModel
/*   7:    */   extends PathAgentModel
/*   8:    */ {
/*   9:    */   public AlienModel(int UUID, String name, float x, float y, float angle, SpaceHulkWorldModel worldModel, String faction, int ap, boolean rotatable, float attackRange, int player, int team, int marineType, int colour)
/*  10:    */   {
/*  11: 14 */     super(UUID, name, x, y, angle, worldModel, faction, ap, rotatable, attackRange, player, team, marineType, colour);
/*  12:    */     
/*  13:    */ 
/*  14: 17 */     this.mass = 0.1F;
/*  15: 18 */     this.maxSpeed = 0.05F;
/*  16: 19 */     this.maxForce = 0.005F;
/*  17: 20 */     this.damping = 0.4F;
/*  18: 21 */     this.breaking = 0.9F;
/*  19:    */     
/*  20: 23 */     this.agentRadius = 0.2F;
/*  21: 24 */     this.obstacleRadius = 0.1F;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public int rotatingCost(float turned)
/*  25:    */   {
/*  26: 29 */     if (turned < 0.0F) {
/*  27: 30 */       turned = -turned;
/*  28:    */     }
/*  29: 32 */     switch ((int)turned)
/*  30:    */     {
/*  31:    */     case 0: 
/*  32: 34 */       return 0;
/*  33:    */     case 90: 
/*  34: 37 */       return 0;
/*  35:    */     case 180: 
/*  36: 40 */       return 1;
/*  37:    */     case 270: 
/*  38: 43 */       return 0;
/*  39:    */     }
/*  40: 46 */     return 0;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public boolean fov(float x, float y)
/*  44:    */   {
/*  45: 51 */     switch ((int)getAngle())
/*  46:    */     {
/*  47:    */     case 0: 
/*  48: 53 */       if (x > getX() - 0.5F) {
/*  49: 54 */         return true;
/*  50:    */       }
/*  51:    */       break;
/*  52:    */     case 90: 
/*  53: 60 */       if (y > getY() - 0.5F) {
/*  54: 61 */         return true;
/*  55:    */       }
/*  56:    */       break;
/*  57:    */     case 180: 
/*  58: 67 */       if (x < getX() - 0.5F) {
/*  59: 68 */         return true;
/*  60:    */       }
/*  61:    */       break;
/*  62:    */     case 270: 
/*  63: 73 */       if (y < getY() - 0.5F) {
/*  64: 74 */         return true;
/*  65:    */       }
/*  66:    */       break;
/*  67:    */     }
/*  68: 81 */     return false;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int tileReachable(float tx, float ty)
/*  72:    */   {
/*  73: 87 */     float x = getX() - 0.5F;
/*  74: 88 */     float y = getY() - 0.5F;
/*  75: 92 */     switch ((int)getAngle())
/*  76:    */     {
/*  77:    */     case 0: 
/*  78: 94 */       if ((x + 1.0F == tx) && (ty >= y - 1.0F) && (ty <= y + 1.0F)) {
/*  79: 95 */         return 1;
/*  80:    */       }
/*  81: 97 */       if ((x - 1.0F == tx) && (ty == y)) {
/*  82: 98 */         return 2;
/*  83:    */       }
/*  84:    */       break;
/*  85:    */     case 90: 
/*  86:104 */       if ((y + 1.0F == ty) && (tx >= x - 1.0F) && (tx <= x + 1.0F)) {
/*  87:105 */         return 1;
/*  88:    */       }
/*  89:107 */       if ((y - 1.0F == ty) && (tx == x)) {
/*  90:108 */         return 2;
/*  91:    */       }
/*  92:    */       break;
/*  93:    */     case 180: 
/*  94:113 */       if ((x - 1.0F == tx) && (ty >= y - 1.0F) && (ty <= y + 1.0F)) {
/*  95:115 */         return 1;
/*  96:    */       }
/*  97:117 */       if ((x + 1.0F == tx) && (ty == y)) {
/*  98:118 */         return 2;
/*  99:    */       }
/* 100:    */       break;
/* 101:    */     case 270: 
/* 102:123 */       if ((y - 1.0F == ty) && (tx >= x - 1.0F) && (tx <= x + 1.0F)) {
/* 103:124 */         return 1;
/* 104:    */       }
/* 105:126 */       if ((y + 1.0F == ty) && (tx == x)) {
/* 106:127 */         return 2;
/* 107:    */       }
/* 108:    */       break;
/* 109:    */     }
/* 110:132 */     return 0;
/* 111:    */   }
/* 112:    */ }


/* Location:           C:\Users\Adrian\Downloads\Spaxe Hilk 0.12\sh\
 * Qualified Name:     sh.agent.aliens.AlienModel
 * JD-Core Version:    0.7.0.1
 */